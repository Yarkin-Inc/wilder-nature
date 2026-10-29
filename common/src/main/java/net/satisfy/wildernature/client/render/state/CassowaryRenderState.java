package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class CassowaryRenderState extends LivingEntityRenderState {
    public final AnimationState alertAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState threateningAnimationState = new AnimationState();
    public boolean isAlertValue;
    public boolean isAlert() { return isAlertValue; }
    public boolean isAttackingValue;
    public boolean isAttacking() { return isAttackingValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isThreateningValue;
    public boolean isThreatening() { return isThreateningValue; }
}
