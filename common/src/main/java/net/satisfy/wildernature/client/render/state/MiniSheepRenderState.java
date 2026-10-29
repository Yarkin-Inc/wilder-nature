package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class MiniSheepRenderState extends LivingEntityRenderState {
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState maehAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isMaehAnimatingValue;
    public boolean isMaehAnimating() { return isMaehAnimatingValue; }
    public boolean isMiniSheepRunningValue;
    public boolean isMiniSheepRunning() { return isMiniSheepRunningValue; }
    public boolean isMiniSheepSleepingValue;
    public boolean isMiniSheepSleeping() { return isMiniSheepSleepingValue; }
    public boolean isShearedValue;
    public boolean isSheared() { return isShearedValue; }
}
