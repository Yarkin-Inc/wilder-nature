package net.satisfy.wildernature.test;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
import net.satisfy.wildernature.core.util.WilderNatureUtil;

public final class WilderNatureGameTest {
    @GameTest public void lionsDoNotRestartWarningsDuringCombat(GameTestHelper h) {
        var player = createSurvivalPlayer(h);
        player.setPos(h.absoluteVec(new net.minecraft.world.phys.Vec3(1, 2, 1)));
        for (boolean male : java.util.List.of(false, true)) {
            var lion = net.satisfy.wildernature.core.registry.EntityTypeRegistry.LION.get().create(h.getLevel(), EntitySpawnReason.COMMAND);
            lion.setMale(male);
            lion.setNoAi(true);
            lion.setNoGravity(true);
            lion.setPos(player.position().add(1, 0, 0));
            lion.setTarget(player);
            lion.setThreatLevel(100);
            lion.startWarning();
            for (int tick = 0; tick < 120; tick++) {
                lion.tick();
                if (tick >= 40) h.assertFalse(lion.isWarning(), "Lion with a combat target must finish warning (male=" + male + ", tick=" + tick + ")");
            }
            h.assertTrue(lion.canUseCombatGoal(), "Melee must be available after warning");
            h.assertTrue(lion.canStartPounce(), "Pounce must be available after warning");
        }
        h.getLevel().getServer().getPlayerList().remove(player);
        h.succeed();
    }

    @GameTest(maxTicks = 400) public void lionsWarnThenAttackSurvivalPlayers(GameTestHelper h) {
        for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++)
            h.setBlock(x, 1, z, net.minecraft.world.level.block.Blocks.STONE);
        var player = createSurvivalPlayer(h);
        player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(200);
        player.setNoGravity(true);
        for (int scenario = 0; scenario < 2; scenario++) {
            final boolean male = scenario == 1;
            int start = scenario * 180;
            h.runAtTickTime(start + 1, () -> {
                player.setPos(h.absoluteVec(new net.minecraft.world.phys.Vec3(4, 2, 4)));
                player.setHealth(player.getMaxHealth());
                player.setShiftKeyDown(!male);
                var lion = h.spawn(net.satisfy.wildernature.core.registry.EntityTypeRegistry.LION.get(), 4, 2, 2);
                lion.setAge(0);
                lion.setMale(male);
                h.runAtTickTime(start + 20, () -> h.assertTrue(lion.isWarning(), "Approaching survival player receives warning; target="
                        + lion.getTarget() + "; threat=" + lion.getThreatLevel() + "; distance=" + lion.distanceTo(player) + "; baby=" + lion.isBaby()));
                h.runAtTickTime(start + 170, () -> {
                    h.assertTrue(player.getHealth() < player.getMaxHealth() && player.getLastHurtByMob() == lion,
                            "Lion must actually damage player after warning (male=" + male + "; target=" + lion.getTarget()
                                    + "; warning=" + lion.isWarning() + "; threat=" + lion.getThreatLevel()
                                    + "; health=" + player.getHealth() + "; alive=" + player.isAlive()
                                    + "; lastAttacker=" + player.getLastHurtByMob() + "; distance=" + lion.distanceTo(player) + ")");
                    lion.discard();
                    if (male) {
                        h.getLevel().getServer().getPlayerList().remove(player);
                        h.succeed();
                    }
                });
            });
        }
    }

    private static net.minecraft.server.level.ServerPlayer createSurvivalPlayer(GameTestHelper h) {
        // Vanilla's makeMockServerPlayerInLevel overrides gameMode() to CREATIVE even after setGameMode.
        var profile = new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "lion-test-player");
        var cookie = net.minecraft.server.network.CommonListenerCookie.createInitial(profile, false);
        var player = new net.minecraft.server.level.ServerPlayer(h.getLevel().getServer(), h.getLevel(), profile, cookie.clientInformation());
        var connection = new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND);
        new io.netty.channel.embedded.EmbeddedChannel(connection);
        h.getLevel().getServer().getPlayerList().placeNewPlayer(connection, player, cookie);
        player.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
        h.assertFalse(player.isCreative(), "Combat test player is in survival");
        return player;
    }

    @GameTest public void animalsSpawnAndTick(GameTestHelper h) {
        int count = 0;
        for (var type : BuiltInRegistries.ENTITY_TYPE) {
            if (!BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("wildernature")) continue;
            var entity = type.create(h.getLevel(), EntitySpawnReason.COMMAND);
            h.assertTrue(entity != null, "Entity factory: " + type);
            if (entity instanceof net.minecraft.world.entity.Mob animal) {
                animal.setPos(h.absoluteVec(new net.minecraft.world.phys.Vec3(1, 2, 1)));
                h.getLevel().addFreshEntity(animal);
                animal.tick();
                if (animal instanceof Animal breedable) breedable.setAge(-24000);
                animal.tick();
                if (animal instanceof Animal) h.assertTrue(animal.isBaby(), "Baby state: " + type);
                if (animal instanceof net.satisfy.wildernature.core.entity.animal.passive.TermiteEntity termite) {
                    termite.setHasWoodmeal(true);
                    termite.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, new ItemStack(net.satisfy.wildernature.core.registry.ObjectRegistry.WOODMEAL.get()));
                } else animal.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, new ItemStack(Items.APPLE));
                for (int i = 0; i < 40; i++) animal.tick();
                var output = net.minecraft.world.level.storage.TagValueOutput.createWithContext(net.minecraft.util.ProblemReporter.DISCARDING, h.getLevel().registryAccess());
                animal.saveWithoutId(output);
                var restored = (net.minecraft.world.entity.Mob) type.create(h.getLevel(), EntitySpawnReason.LOAD);
                restored.load(net.minecraft.world.level.storage.TagValueInput.create(net.minecraft.util.ProblemReporter.DISCARDING, h.getLevel().registryAccess(), output.buildResult()));
                h.assertTrue(restored.getUUID().equals(animal.getUUID()), "Entity UUID survives save: " + type);
                h.assertTrue(restored.isBaby() == animal.isBaby(), "Age survives save: " + type);
                h.assertTrue(restored.getMainHandItem().is(animal.getMainHandItem().getItem()), "Held item survives save: " + type);
                animal.discard();
                count++;
            }
        }
        h.assertTrue(count == 18, "All 18 animal types initialize (got " + count + ")");
        h.succeed();
    }
    @GameTest public void trufflingIsPerStackAndIdempotent(GameTestHelper h) {
        var original = new ItemStack(Items.COOKED_BEEF);
        var food = original.get(DataComponents.FOOD);
        var truffled = WilderNatureUtil.Truffling.setTruffled(original.copy());
        var enriched = truffled.get(DataComponents.FOOD);
        h.assertTrue(enriched.nutrition() == food.nutrition() + (int)(food.nutrition() * .2F), "Nutrition bonus");
        h.assertTrue(Math.abs(enriched.saturation() - food.saturation() * 1.2F) < .001F, "Saturation bonus");
        h.assertTrue(original.get(DataComponents.FOOD).equals(food), "Other food unaffected");
        WilderNatureUtil.Truffling.setTruffled(truffled);
        h.assertTrue(truffled.get(DataComponents.FOOD).equals(enriched), "Bonus cannot accumulate");
        h.succeed();
    }

    @GameTest public void blockEntitiesKeepInventory(GameTestHelper h) {
        int count = 0;
        var pos = new net.minecraft.core.BlockPos(1, 2, 1);
        for (var block : BuiltInRegistries.BLOCK) {
            if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals("wildernature")) continue;
            if (!(block instanceof net.minecraft.world.level.block.EntityBlock factory)) continue;
            var state = block.defaultBlockState();
            var original = factory.newBlockEntity(h.absolutePos(pos), state);
            h.assertTrue(original != null, "Block entity factory: " + block);
            original.setLevel(h.getLevel());
            if (original instanceof net.minecraft.world.Container container && container.getContainerSize() > 0)
                container.setItem(0, new ItemStack(Items.DIAMOND, 7));
            var tag = original.saveWithFullMetadata(h.getLevel().registryAccess());
            var restored = net.minecraft.world.level.block.entity.BlockEntity.loadStatic(h.absolutePos(pos), state, tag, h.getLevel().registryAccess());
            h.assertTrue(restored != null && restored.getType() == original.getType(), "Block entity reload: " + block);
            if (restored instanceof net.minecraft.world.Container container && container.getContainerSize() > 0)
                h.assertTrue(container.getItem(0).is(Items.DIAMOND) && container.getItem(0).getCount() == 7, "Inventory reload: " + block);
            count++;
        }
        h.assertTrue(count >= 6, "All block entity factories checked");
        h.succeed();
    }

    @GameTest public void bountiesKeepProgressAndRewards(GameTestHelper h) {
        var data = net.satisfy.wildernature.core.bounty.BountyBoardSavedData.get(h.getLevel());
        h.assertTrue(data == net.satisfy.wildernature.core.bounty.BountyBoardSavedData.get(h.getLevel()), "Saved data is cached");
        data.ensureCurrentBounties(h.getLevel());
        h.assertFalse(data.getDailyBounties().isEmpty(), "Bounties generated from datapack");
        var id = java.util.UUID.randomUUID();
        var player = data.getPlayerBountyData(id);
        var bounty = data.getDailyBounties().getFirst();
        player.setActiveBounty(bounty);
        player.incrementProgress(bounty.requiredAmount());
        player.setRewardsUnlocked(true);
        player.setItemRewardClaimed(true);
        var restored = net.satisfy.wildernature.core.bounty.BountyBoardSavedData.load(data.save(new net.minecraft.nbt.CompoundTag()));
        var saved = restored.getPlayerBountyData(id);
        h.assertTrue(saved.isCompleted() && saved.areRewardsUnlocked() && saved.isItemRewardClaimed(), "Progress and reward flags survive reload");
        h.assertTrue(saved.getActiveBounty().id().equals(bounty.id()), "Contract identity survives reload");
        saved.abandonActiveBounty();
        var abandoned = net.satisfy.wildernature.core.bounty.PlayerBountyData.load(saved.save());
        h.assertTrue(abandoned.hasAbandoned(bounty.id()) && !abandoned.hasActiveBounty(), "Abandoned contract survives reload");
        h.succeed();
    }

    @GameTest public void trufflingRecipeAndGunWork(GameTestHelper h) {
        var input = net.minecraft.world.item.crafting.CraftingInput.of(2, 1, java.util.List.of(
                new ItemStack(Items.COOKED_BEEF), new ItemStack(net.satisfy.wildernature.core.registry.ObjectRegistry.TRUFFLE.get())));
        var recipe = h.getLevel().getServer().getRecipeManager().getRecipeFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING, input, h.getLevel()).orElseThrow();
        h.assertTrue(recipe.value() instanceof net.satisfy.wildernature.core.recipe.TrufflingRecipe, "Truffling datapack recipe matches");
        var result = recipe.value().assemble(input);
        h.assertTrue(result.is(Items.COOKED_BEEF) && WilderNatureUtil.Truffling.isTruffled(result), "Craft produces enriched food");
        var p = h.makeMockServerPlayerInLevel();
        p.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
        p.setPos(h.absoluteVec(new net.minecraft.world.phys.Vec3(1, 2, 1)));
        var gun = new ItemStack(net.satisfy.wildernature.core.registry.ObjectRegistry.BLUNDERBUSS.get());
        var ammo = new ItemStack(net.satisfy.wildernature.core.registry.ObjectRegistry.FLINT_AMMUNITION.get(), 3);
        p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, gun);
        p.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, ammo);
        gun.use(h.getLevel(), p, net.minecraft.world.InteractionHand.MAIN_HAND);
        h.assertTrue(ammo.getCount() == 2, "Shot consumes one ammunition");
        h.assertTrue(gun.getDamageValue() == 1 && p.getCooldowns().isOnCooldown(gun), "Shot applies durability and cooldown");
        var bullets = h.getLevel().getEntitiesOfClass(net.satisfy.wildernature.core.entity.projectile.BulletEntity.class, p.getBoundingBox().inflate(4));
        h.assertTrue(bullets.size() >= 4 && bullets.size() <= 8, "Flint ammunition creates pellets");
        bullets.forEach(net.minecraft.world.entity.Entity::discard);
        h.succeed();
    }

    @GameTest public void worldgenFeaturesPlace(GameTestHelper h) {
        var level = h.getLevel();
        var pos = h.absolutePos(new net.minecraft.core.BlockPos(1, 2, 1));
        for (String name : java.util.List.of("patch_hazelnut_bush", "termite_mound")) {
            for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
                level.setBlock(pos.offset(x, -1, z), net.minecraft.world.level.block.Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                for (int y = 0; y <= 8; y++) level.setBlock(pos.offset(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
            }
            var key = net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.FEATURE, net.satisfy.wildernature.WilderNature.identifier(name));
            var feature = level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.FEATURE).getOrThrow(key).value();
            h.assertTrue(feature.place(level, level.getChunkSource().getGenerator(), net.minecraft.util.RandomSource.create(7), pos), "Feature places: " + name);
        }
        h.assertTrue(level.getBlockState(pos).is(net.satisfy.wildernature.core.registry.ObjectRegistry.TERMITE_MOUND_STORAGE.get()), "Termite storage remains accessible at the mound entrance");
        h.assertTrue(level.getBlockState(pos.above()).is(net.satisfy.wildernature.core.registry.ObjectRegistry.TERMITE_MOUND.get()), "Ground decoration preserves the mound column");
        h.succeed();
    }
}
