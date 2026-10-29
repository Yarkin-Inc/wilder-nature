package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class BisonRenderState extends LivingEntityRenderState {
    public final AnimationState alertAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState grazeAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState snortAnimationState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
}
