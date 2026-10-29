package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class HedgehogRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState noAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sniffAnimationState = new AnimationState();
    public net.minecraft.world.phys.Vec3 getDeltaMovementValue;
    public net.minecraft.world.phys.Vec3 getDeltaMovement() { return getDeltaMovementValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isCurledValue;
    public boolean isCurled() { return isCurledValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
}
