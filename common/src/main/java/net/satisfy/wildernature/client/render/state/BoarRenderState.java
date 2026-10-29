package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class BoarRenderState extends LivingEntityRenderState {
    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public int getDenyAnimationTickValue;
    public int getDenyAnimationTick() { return getDenyAnimationTickValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isDiggingValue;
    public boolean isDigging() { return isDiggingValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
}
