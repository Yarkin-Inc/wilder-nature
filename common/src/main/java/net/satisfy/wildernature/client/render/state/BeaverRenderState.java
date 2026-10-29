package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class BeaverRenderState extends LivingEntityRenderState {
    public final AnimationState gnawState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState nodState = new AnimationState();
    public final AnimationState sleepState = new AnimationState();
    public final AnimationState walkState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
}
