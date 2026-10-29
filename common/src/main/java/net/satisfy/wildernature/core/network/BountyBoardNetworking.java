package net.satisfy.wildernature.core.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.ClientBountyBoardNotes;
import net.satisfy.wildernature.core.bounty.*;
import net.satisfy.wildernature.core.gui.handler.BountyBoardMenu;
import java.util.*;

public final class BountyBoardNetworking {
    private static final int NOTES_INTERVAL = 40;
    private static final Map<UUID, Integer> SENT_NOTES = new HashMap<>();

    public enum Action { ACCEPT, ABANDON, CLAIM, SELECT }

    public record Request(Action action, int selectedIndex) implements CustomPacketPayload {
        public static final Type<Request> TYPE = new Type<>(WilderNature.identifier("bounty_request"));
        public static final StreamCodec<RegistryFriendlyByteBuf, Request> CODEC = StreamCodec.of(
            (buffer, value) -> { buffer.writeEnum(value.action); buffer.writeInt(value.selectedIndex); },
            buffer -> new Request(buffer.readEnum(Action.class), buffer.readInt()));
        @Override public Type<Request> type() { return TYPE; }
    }

    public record Notes(int open, int total) implements CustomPacketPayload {
        public static final Type<Notes> TYPE = new Type<>(WilderNature.identifier("bounty_board_notes"));
        public static final StreamCodec<RegistryFriendlyByteBuf, Notes> CODEC = StreamCodec.of(
            (buffer, value) -> { buffer.writeInt(value.open); buffer.writeInt(value.total); },
            buffer -> new Notes(buffer.readInt(), buffer.readInt()));
        @Override public Type<Notes> type() { return TYPE; }
    }

    public record Sync(int selected, boolean hasActive, int activeIndex, int progress, int required,
            boolean completed, boolean restoreAvailable, boolean rewardsUnlocked, List<UUID> abandoned) implements CustomPacketPayload {
        public static final Type<Sync> TYPE = new Type<>(WilderNature.identifier("bounty_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, Sync> CODEC = StreamCodec.of(Sync::write, Sync::read);
        public Sync { abandoned = List.copyOf(abandoned); }
        @Override public Type<Sync> type() { return TYPE; }
        private static void write(RegistryFriendlyByteBuf buffer, Sync value) {
            buffer.writeInt(value.selected);
            buffer.writeBoolean(value.hasActive);
            buffer.writeInt(value.activeIndex);
            buffer.writeInt(value.progress);
            buffer.writeInt(value.required);
            buffer.writeBoolean(value.completed);
            buffer.writeBoolean(value.restoreAvailable);
            buffer.writeBoolean(value.rewardsUnlocked);
            buffer.writeVarInt(value.abandoned.size());
            value.abandoned.forEach(buffer::writeUUID);
        }
        private static Sync read(RegistryFriendlyByteBuf buffer) {
            int selected = buffer.readInt();
            boolean hasActive = buffer.readBoolean();
            int activeIndex = buffer.readInt();
            int progress = buffer.readInt();
            int required = buffer.readInt();
            boolean completed = buffer.readBoolean();
            boolean restore = buffer.readBoolean();
            boolean unlocked = buffer.readBoolean();
            int count = buffer.readVarInt();
            if (count < 0 || count > 4096) throw new IllegalArgumentException("Invalid abandoned bounty count");
            List<UUID> abandoned = new ArrayList<>(count);
            for (int i = 0; i < count; i++) abandoned.add(buffer.readUUID());
            return new Sync(selected, hasActive, activeIndex, progress, required, completed, restore, unlocked, abandoned);
        }
    }

    public static void init() {
        if (Platform.getEnvironment() == Env.SERVER) {
            NetworkManager.registerS2CPayloadType(Notes.TYPE, Notes.CODEC);
            NetworkManager.registerS2CPayloadType(Sync.TYPE, Sync.CODEC);
        }
        NetworkManager.registerReceiver(NetworkManager.c2s(), Request.TYPE, Request.CODEC, (request, context) -> context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (!(player.containerMenu instanceof BountyBoardMenu menu)) return;
            switch (request.action()) {
                case ACCEPT -> menu.acceptSelectedBounty();
                case ABANDON -> menu.abandonActiveBounty(player);
                case CLAIM -> menu.claimActiveBounty();
                case SELECT -> {
                    if (request.selectedIndex() < 0 || request.selectedIndex() >= menu.getVisibleBounties().size()) return;
                    menu.setSelectedBountyIndex(request.selectedIndex());
                }
            }
            menu.broadcastChanges();
            sendSync(player, menu);
            sendNotes(player, true);
        }));
    }

    public static void initClient() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), Notes.TYPE, Notes.CODEC, (notes, context) ->
            context.queue(() -> ClientBountyBoardNotes.set(notes.open(), notes.total())));
        NetworkManager.registerReceiver(NetworkManager.s2c(), Sync.TYPE, Sync.CODEC, (sync, context) -> context.queue(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof BountyBoardMenu menu) {
                menu.applySyncFromServer(sync.selected(), sync.hasActive(), sync.activeIndex(), sync.progress(),
                    sync.required(), sync.completed(), sync.restoreAvailable(), sync.rewardsUnlocked(), sync.abandoned());
            }
        }));
    }

    public static void sendAccept() { NetworkManager.sendToServer(new Request(Action.ACCEPT, 0)); }
    public static void sendAbandon() { NetworkManager.sendToServer(new Request(Action.ABANDON, 0)); }
    public static void sendSelect(int selectedIndex) { NetworkManager.sendToServer(new Request(Action.SELECT, selectedIndex)); }

    public static void sendSync(ServerPlayer player, BountyBoardMenu menu) {
        sendSync(player, menu.getSelectedBountyIndex(), menu.hasActiveBounty(),
            menu.getActiveBounty().map(bounty -> menu.getVisibleBounties().indexOf(bounty)).orElse(-1),
            menu.getActiveProgress(), menu.getActiveRequiredKills(), menu.hasCompletedActiveBounty(),
            menu.hasRestoreContractAvailable(), menu.areRewardSlotsUnlocked(), new ArrayList<>(menu.getAbandonedBountyIds()));
    }

    public static void sendSync(ServerPlayer player, int selected, boolean hasActive, int activeIndex,
            int progress, int required, boolean completed, boolean restoreAvailable, boolean rewardsUnlocked, List<UUID> abandoned) {
        NetworkManager.sendToPlayer(player, new Sync(selected, hasActive, activeIndex, progress, required, completed,
            restoreAvailable, rewardsUnlocked, abandoned));
    }

    public static void tickNotes(ServerPlayer serverPlayer) {
        if (serverPlayer.tickCount % NOTES_INTERVAL == 0) {
            sendNotes(serverPlayer, false);
        }
    }

    public static void sendNotes(ServerPlayer serverPlayer, boolean force) {
        ServerLevel serverLevel = serverPlayer.level();
        List<BountyDefinition> dailyBounties = BountyManager.getDailyBounties(serverLevel);
        PlayerBountyData playerBountyData = BountyBoardSavedData.get(serverLevel).getPlayerBountyData(serverPlayer.getUUID());
        BountyDefinition activeBounty = playerBountyData.hasActiveBounty() ? playerBountyData.getActiveBounty() : null;
        int openBounties = 0;

        for (BountyDefinition dailyBounty : dailyBounties) {
            if (!playerBountyData.hasAbandoned(dailyBounty.id()) && (activeBounty == null || !activeBounty.id().equals(dailyBounty.id()))) {
                openBounties++;
            }
        }

        int totalBounties = dailyBounties.size();
        int packed = openBounties * 1000 + totalBounties;
        Integer previous = SENT_NOTES.put(serverPlayer.getUUID(), packed);
        if (!force && previous != null && previous == packed) {
            return;
        }

        NetworkManager.sendToPlayer(serverPlayer, new Notes(openBounties, totalBounties));
    }

    public static void forgetNotes(ServerPlayer serverPlayer) {
        SENT_NOTES.remove(serverPlayer.getUUID());
    }

    private BountyBoardNetworking() {}
}
