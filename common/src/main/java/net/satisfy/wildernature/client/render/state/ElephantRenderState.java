package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class ElephantRenderState extends LivingEntityRenderState {
    public final AnimationState chargeState = new AnimationState();
    public final AnimationState drinkState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState stampState = new AnimationState();
    public final AnimationState throwState = new AnimationState();
    public final AnimationState trumpetState = new AnimationState();
    public net.minecraft.world.phys.Vec3 getDeltaMovementValue;
    public net.minecraft.world.phys.Vec3 getDeltaMovement() { return getDeltaMovementValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isChargingValue;
    public boolean isCharging() { return isChargingValue; }
    public boolean isDrinkingValue;
    public boolean isDrinking() { return isDrinkingValue; }
    public boolean isStampingValue;
    public boolean isStamping() { return isStampingValue; }
    public boolean isThrowingValue;
    public boolean isThrowing() { return isThrowingValue; }
    public boolean isTrumpetingValue;
    public boolean isTrumpeting() { return isTrumpetingValue; }
}
