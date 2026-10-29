package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class TermiteRenderState extends LivingEntityRenderState {
    public final AnimationState eatState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public net.minecraft.world.phys.Vec3 getDeltaMovementValue;
    public net.minecraft.world.phys.Vec3 getDeltaMovement() { return getDeltaMovementValue; }
    public boolean isEatingValue;
    public boolean isEating() { return isEatingValue; }
    public final net.minecraft.client.renderer.item.ItemStackRenderState heldItem = new net.minecraft.client.renderer.item.ItemStackRenderState();
}
