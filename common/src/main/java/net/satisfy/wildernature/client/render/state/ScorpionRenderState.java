package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class ScorpionRenderState extends LivingEntityRenderState {
    public final AnimationState attackState = new AnimationState();
    public final AnimationState burrowState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public float getSitAnimValue;
    public float getSitAnim(float partialTick) { return getSitAnimValue; }
    public boolean isCalmedValue;
    public boolean isCalmed() { return isCalmedValue; }
    public int tickCount;
}
