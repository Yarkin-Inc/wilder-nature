package net.satisfy.wildernature.core.bounty;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.satisfy.wildernature.WilderNature;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BountyBoardSavedData extends SavedData {
    public static final String DATA_NAME = WilderNature.MOD_ID + "_bounty_board";

    public static final long BOUNTY_CYCLE_DAYS = 3L;
    private long currentDay;
    private final List<BountyDefinition> dailyBounties = new ArrayList<>();
    private final Map<UUID, PlayerBountyData> playerBountyDataMap = new LinkedHashMap<>();

    public static BountyBoardSavedData get(ServerLevel serverLevel) {
        return serverLevel.getServer().overworld().getDataStorage().computeIfAbsent(
                new net.minecraft.world.level.saveddata.SavedDataType<>(net.satisfy.wildernature.WilderNature.identifier(DATA_NAME), BountyBoardSavedData::new, CompoundTag.CODEC.xmap(BountyBoardSavedData::load, data -> data.save(new CompoundTag())), null)
        );
    }

    public List<BountyDefinition> getDailyBounties() {
        return this.dailyBounties;
    }

    public PlayerBountyData getPlayerBountyData(UUID playerId) {
        return this.playerBountyDataMap.computeIfAbsent(playerId, ignoredPlayerId -> {
            this.setDirty();
            return new PlayerBountyData();
        });
    }

    public void setPlayerBountyData(UUID playerId, PlayerBountyData playerBountyData) {
        this.playerBountyDataMap.put(playerId, playerBountyData);
        this.setDirty();
    }

    public void ensureCurrentBounties(ServerLevel serverLevel) {
        long day = serverLevel.getOverworldClockTime() / (24000L * BOUNTY_CYCLE_DAYS);

        if (day != this.currentDay || this.dailyBounties.isEmpty()) {
            this.currentDay = day;
            this.dailyBounties.clear();
            this.dailyBounties.addAll(BountyGenerator.generateDailyBounties(serverLevel));
            this.resetRepeatableGuildCommissionCooldowns(serverLevel);
            this.setDirty();
        }
    }

    private void resetRepeatableGuildCommissionCooldowns(ServerLevel serverLevel) {
        List<GuildCommissionDefinition> guildCommissions = GuildCommissionLoader.load(serverLevel.getServer().getResourceManager());
        Set<UUID> repeatableCommissionIds = new HashSet<>();

        for (GuildCommissionDefinition guildCommission : guildCommissions) {
            if (guildCommission.repeatable()) {
                repeatableCommissionIds.add(guildCommission.id());
            }
        }

        if (repeatableCommissionIds.isEmpty()) {
            return;
        }

        for (PlayerBountyData playerBountyData : this.playerBountyDataMap.values()) {
            playerBountyData.getAbandonedBounties().removeAll(repeatableCommissionIds);
        }
    }

    public Optional<BountyDefinition> getBounty(UUID bountyId) {
        return this.dailyBounties.stream().filter(bountyDefinition -> bountyDefinition.id().equals(bountyId)).findFirst();
    }

    public @NotNull CompoundTag save(CompoundTag tag) {
        tag.putLong("current_day", this.currentDay);
        tag.putInt("daily_bounty_size", this.dailyBounties.size());

        for (int index = 0; index < this.dailyBounties.size(); index++) {
            tag.put("daily_bounty_" + index, this.dailyBounties.get(index).save());
        }

        tag.putInt("player_bounty_data_size", this.playerBountyDataMap.size());

        int playerIndex = 0;
        for (Map.Entry<UUID, PlayerBountyData> entry : this.playerBountyDataMap.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.store("player_id", net.minecraft.core.UUIDUtil.CODEC, entry.getKey());
            playerTag.put("data", entry.getValue().save());
            tag.put("player_bounty_data_" + playerIndex, playerTag);
            playerIndex++;
        }

        return tag;
    }

    public static BountyBoardSavedData load(CompoundTag tag) {
        BountyBoardSavedData savedData = new BountyBoardSavedData();

        if (tag.contains("current_day")) {
            savedData.currentDay = tag.getLongOr("current_day", 0L);
        }

        int dailyBountySize = tag.getIntOr("daily_bounty_size", 0);
        for (int index = 0; index < dailyBountySize; index++) {
            savedData.dailyBounties.add(BountyDefinition.load(tag.getCompoundOrEmpty("daily_bounty_" + index)));
        }

        int playerBountyDataSize = tag.getIntOr("player_bounty_data_size", 0);
        for (int index = 0; index < playerBountyDataSize; index++) {
            CompoundTag playerTag = tag.getCompoundOrEmpty("player_bounty_data_" + index);
            UUID playerId = playerTag.read("player_id", net.minecraft.core.UUIDUtil.CODEC).orElseThrow();
            PlayerBountyData playerBountyData = PlayerBountyData.load(playerTag.getCompoundOrEmpty("data"));
            savedData.playerBountyDataMap.put(playerId, playerBountyData);
        }

        return savedData;
    }
}