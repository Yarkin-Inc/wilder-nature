package net.satisfy.wildernature.core.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BisonHornItem extends Item {
    private final SoundEvent soundEvent;

    public BisonHornItem(Item.Properties properties, SoundEvent soundEvent) {
        super(properties);
        this.soundEvent = soundEvent;
    }

    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!player.getCooldowns().isOnCooldown(player.getItemInHand(interactionHand))) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, SoundSource.NEUTRAL, 1.0F, 1.25F);
            player.startUsingItem(interactionHand);
            player.getCooldowns().addCooldown(player.getItemInHand(interactionHand), 200);
            if (!level.isClientSide()) {
                level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(10.0), e -> e instanceof Monster).forEach(monster -> {
                    monster.setTarget(null);
                    double dx = monster.getX() - player.getX();
                    double dz = monster.getZ() - player.getZ();
                    double distance = Math.sqrt(dx * dx + dz * dz);
                    double speed = 2.0 / distance;
                    monster.setDeltaMovement(dx * speed, 0.2, dz * speed);
                    monster.syncVelocity = true;
                });
            }
            return InteractionResult.CONSUME; //itemStack);
        }
        return InteractionResult.FAIL; //itemStack);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.TOOT_HORN;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 72000;
    }
}
