package net.satisfy.wildernature.test;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.satisfy.wildernature.client.gui.screen.FieldGuideScreen;
import net.satisfy.wildernature.client.gui.screen.BountyBoardScreen;
import net.satisfy.wildernature.client.util.EntityPreview;
import net.satisfy.wildernature.core.registry.ObjectRegistry;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class WilderNatureClientGameTest implements FabricClientGameTest {
    @Override public void runTest(ClientGameTestContext c) {
        try (var world = c.worldBuilder().create()) {
            c.runOnClient(client -> verifyCassowaryAnimations());
            world.getServer().runCommand("gamemode creative @a");
            c.waitFor(client -> client.player.isCreative());
            for (boolean baby : List.of(false, true)) {
                c.runOnClient(client -> {
                    List<LivingEntity> animals = new ArrayList<>();
                    for (var type : BuiltInRegistries.ENTITY_TYPE) {
                        if (!BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("wildernature")) continue;
                        if (type.create(client.level, EntitySpawnReason.COMMAND) instanceof net.minecraft.world.entity.Mob animal) {
                            animal.setId(-1);
                            if (animal instanceof Animal breedable) breedable.setAge(baby ? -24000 : 0);
                            animal.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.APPLE));
                            animal.tick();
                            animals.add(animal);
                        }
                    }
                    client.gui.setScreen(new Screen(Component.literal("WilderNature models")) {
                        @Override public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
                            g.fill(0, 0, width, height, 0xFFB6D7DE);
                            for (int i = 0; i < animals.size(); i++) {
                                var animal = animals.get(i);
                                int x = (i % 6) * width / 6 + width / 12;
                                int y = (i / 6) * height / 3 + height / 3 - 20;
                                float scale = Math.min(25, (height / 3F - 30) / Math.max(animal.getBbHeight(), 1));
                                EntityPreview.extract(g, x, y, scale, new Vector3f(), new Quaternionf().rotationZ((float)Math.PI), null, animal);
                                g.text(font, animal.getType().getDescription(), x - 30, y + 4, 0xFF202020, false);
                            }
                        }
                        @Override public boolean isPauseScreen() { return false; }
                    });
                });
                c.waitTicks(10);
                c.takeScreenshot(baby ? "wildernature-baby-animals" : "wildernature-adult-animals");
            }
            c.runOnClient(client -> client.gui.setScreen(new Screen(Component.literal("WilderNature items")) {
                @Override public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
                    g.fill(0, 0, width, height, 0xFFB6D7DE);
                    int i = 0;
                    for (var item : BuiltInRegistries.ITEM) {
                        if (!BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("wildernature")) continue;
                        g.item(new ItemStack(item), 12 + (i % 15) * 20, 12 + (i / 15) * 22);
                        i++;
                    }
                }
                @Override public boolean isPauseScreen() { return false; }
            }));
            c.waitTicks(10);
            c.takeScreenshot("wildernature-items");
            c.runOnClient(client -> client.gui.setScreen(null));
            world.getServer().runOnServer(server -> {
                var p = server.getPlayerList().getPlayers().getFirst();
                p.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ObjectRegistry.FIELD_GUIDE.get()));
                p.getMainHandItem().use(p.level(), p, InteractionHand.MAIN_HAND);
            });
            c.waitFor(client -> client.gui.screen() instanceof FieldGuideScreen);
            c.waitTicks(10);
            c.takeScreenshot("wildernature-field-guide");
            c.runOnClient(client -> client.player.closeContainer());
            world.getServer().waitFor(server -> server.getPlayerList().getPlayers().getFirst().containerMenu instanceof net.minecraft.world.inventory.InventoryMenu);
            world.getServer().runOnServer(server -> {
                var p = server.getPlayerList().getPlayers().getFirst();
                var pos = p.blockPosition().relative(p.getDirection(), 2);
                p.level().setBlock(pos, ObjectRegistry.BOUNTY_BOARD.get().defaultBlockState(), 3);
                p.level().getBlockState(pos).useWithoutItem(p.level(), p,
                        new net.minecraft.world.phys.BlockHitResult(net.minecraft.world.phys.Vec3.atCenterOf(pos), net.minecraft.core.Direction.UP, pos, false));
            });
            c.waitFor(client -> client.gui.screen() instanceof BountyBoardScreen);
            c.waitTicks(10);
            c.takeScreenshot("wildernature-bounty-board");
            c.runOnClient(client -> net.satisfy.wildernature.core.network.BountyBoardNetworking.sendAccept());
            c.waitTicks(10);
            world.getServer().runOnServer(server -> {
                var p = server.getPlayerList().getPlayers().getFirst();
                if (!(p.containerMenu instanceof net.satisfy.wildernature.core.gui.handler.BountyBoardMenu menu)) throw new AssertionError("Server bounty menu closed: " + p.containerMenu);
                if (!menu.hasActiveBounty()) throw new AssertionError("Server did not accept contract; selected=" + menu.getSelectedBountyIndex() + "; total=" + menu.getVisibleBounties().size() + "; active=" + menu.getPlayerBountyData().hasActiveBounty());
            });
            c.waitFor(client -> client.player.containerMenu instanceof net.satisfy.wildernature.core.gui.handler.BountyBoardMenu menu && menu.hasActiveBounty());
            c.runOnClient(client -> net.satisfy.wildernature.core.network.BountyBoardNetworking.sendAbandon());
            c.waitFor(client -> client.player.containerMenu instanceof net.satisfy.wildernature.core.gui.handler.BountyBoardMenu menu && !menu.hasActiveBounty());

            c.runOnClient(client -> client.player.closeContainer());
            world.getServer().waitFor(server -> server.getPlayerList().getPlayers().getFirst().containerMenu instanceof net.minecraft.world.inventory.InventoryMenu);

            world.getServer().runOnServer(server -> {
                var p = server.getPlayerList().getPlayers().getFirst();
                var level = p.level();
                var origin = p.blockPosition();
                level.setBlock(origin.offset(-2, 0, 3), ObjectRegistry.HOLLOW_CACHE.get().defaultBlockState(), 3);
                level.setBlock(origin.offset(2, 0, 3), ObjectRegistry.FOX_TRAPPER_BANNER.get().defaultBlockState(), 3);
                var glow = net.satisfy.wildernature.core.registry.EntityTypeRegistry.GLOWING_BLOCK.get().create(level, EntitySpawnReason.COMMAND);
                glow.setGlowColor(0x33FF66);
                glow.setPos(origin.getX()+3.5, origin.getY()+1.5, origin.getZ()+3.5);
                level.addFreshEntity(glow);
                for (var type : BuiltInRegistries.PARTICLE_TYPE) {
                    if (BuiltInRegistries.PARTICLE_TYPE.getKey(type).getNamespace().equals("wildernature") && type instanceof net.minecraft.core.particles.SimpleParticleType particle)
                        level.sendParticles(particle, p.getX(), p.getY()+1.5, p.getZ()+2, 2, .5, .5, .5, 0);
                }
            });
            world.getServer().runCommand("execute as @a at @s run tp @s ~ ~ ~-3 0 5");
            c.waitTicks(10);
            c.takeScreenshot("wildernature-blocks-and-particles");
            c.runOnClient(client -> {
                var stand = net.minecraft.world.entity.EntityTypes.ARMOR_STAND.create(client.level, EntitySpawnReason.COMMAND);
                stand.setId(-1);
                stand.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.STYLIN_PURPLE_HAT.get()));
                stand.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.FUR_CLOAK.get()));
                var renderer = client.getEntityRenderDispatcher().getRenderer(stand);
                var state = (net.minecraft.client.renderer.entity.state.HumanoidRenderState) renderer.createRenderState(stand, 1);
                if (!state.chestEquipment.is(ObjectRegistry.FUR_CLOAK.get())) throw new AssertionError("Cloak passed to armor renderer");
                if (!state.headEquipment.is(ObjectRegistry.STYLIN_PURPLE_HAT.get())) throw new AssertionError("Hat passed to armor renderer");
                client.gui.setScreen(new Screen(Component.literal("WilderNature equipment")) {
                    @Override public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
                        g.fill(0, 0, width, height, 0xFFB6D7DE);
                        EntityPreview.extract(g, width / 3, height - 25, 70, new Vector3f(), new Quaternionf().rotationZ((float)Math.PI), null, stand);
                        EntityPreview.extract(g, width * 2 / 3, height - 25, 70, new Vector3f(), new Quaternionf().rotationZ((float)Math.PI).rotateY((float)Math.PI), null, stand);
                    }
                    @Override public boolean isPauseScreen() { return false; }
                });
            });
            c.waitTicks(10);
            c.takeScreenshot("wildernature-equipment");
            c.runOnClient(client -> client.gui.setScreen(null));
        }
    }

    private static void verifyCassowaryAnimations() {
        var adult = new net.satisfy.wildernature.client.model.entity.model.CassowaryModel<>(
                net.satisfy.wildernature.client.model.entity.model.CassowaryModel.getTexturedModelData().bakeRoot());
        var baby = new net.satisfy.wildernature.client.model.entity.model.BabyCassowaryModel<>(
                net.satisfy.wildernature.client.model.entity.model.BabyCassowaryModel.getTexturedModelData().bakeRoot());
        for (var model : List.of(adult, baby)) {
            for (String animation : List.of("idle", "walk", "alert", "threatening", "attack")) {
                var state = new net.satisfy.wildernature.client.render.state.CassowaryRenderState();
                state.ageInTicks = 13;
                switch (animation) {
                    case "idle" -> state.idleAnimationState.start(0);
                    case "walk" -> { state.walkAnimationPos = 13; state.walkAnimationSpeed = .8F; }
                    case "alert" -> { state.isAlertValue = true; state.alertAnimationState.start(0); }
                    case "threatening" -> { state.isThreateningValue = true; state.threateningAnimationState.start(0); }
                    case "attack" -> { state.isAttackingValue = true; state.attackAnimationState.start(0); }
                }
                model.setupAnim(state);
                var expected = model.root().getAllParts().stream().map(net.minecraft.client.model.geom.ModelPart::storePose).toList();
                for (int frame = 0; frame < 1000; frame++) model.setupAnim(state);
                var actual = model.root().getAllParts().stream().map(net.minecraft.client.model.geom.ModelPart::storePose).toList();
                if (!actual.equals(expected)) throw new AssertionError(model.getClass().getSimpleName() + " accumulates transforms during " + animation
                        + "; expected root=" + expected.getFirst() + "; actual=" + actual.getFirst());
            }
        }
    }
}
