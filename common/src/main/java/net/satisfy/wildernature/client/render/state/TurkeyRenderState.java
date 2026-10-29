package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class TurkeyRenderState extends LivingEntityRenderState {
    public final AnimationState attackAnimationState = new AnimationState();
    public boolean isBaby() { return isBaby; }
}
