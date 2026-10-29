package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class DogRenderState extends LivingEntityRenderState {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState digAnimationState = new AnimationState();
    public final AnimationState howlingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState lyingAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public int getVariantValue;
    public int getVariant() { return getVariantValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isLyingValue;
    public boolean isLying() { return isLyingValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
    public final net.minecraft.client.renderer.item.ItemStackRenderState heldItem = new net.minecraft.client.renderer.item.ItemStackRenderState();
}
