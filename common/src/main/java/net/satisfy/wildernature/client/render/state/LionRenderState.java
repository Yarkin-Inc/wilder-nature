package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class LionRenderState extends LivingEntityRenderState {
    public final AnimationState idleState = new AnimationState();
    public final AnimationState pounceState = new AnimationState();
    public final AnimationState roarState = new AnimationState();
    public final AnimationState sleepState = new AnimationState();
    public final AnimationState warnState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isMaleValue;
    public boolean isMale() { return isMaleValue; }
    public boolean isPouncingValue;
    public boolean isPouncing() { return isPouncingValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
    public boolean isStalkingValue;
    public boolean isStalking() { return isStalkingValue; }
    public boolean isWarningValue;
    public boolean isWarning() { return isWarningValue; }
}
