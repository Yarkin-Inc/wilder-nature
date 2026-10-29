package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class SquirrelRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState wiggleAnimationState = new AnimationState();
    public int getVariantValue;
    public int getVariant() { return getVariantValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isWigglingValue;
    public boolean isWiggling() { return isWigglingValue; }
    public final net.minecraft.client.renderer.item.ItemStackRenderState heldItem = new net.minecraft.client.renderer.item.ItemStackRenderState();
}
