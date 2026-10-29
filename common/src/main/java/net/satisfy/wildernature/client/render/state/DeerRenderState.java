package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class DeerRenderState extends LivingEntityRenderState {
    public final AnimationState eatingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState lookAroundState = new AnimationState();
    public final AnimationState sleepState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isDeerRunningValue;
    public boolean isDeerRunning() { return isDeerRunningValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
    public boolean isWhiteValue;
    public boolean isWhite() { return isWhiteValue; }
}
