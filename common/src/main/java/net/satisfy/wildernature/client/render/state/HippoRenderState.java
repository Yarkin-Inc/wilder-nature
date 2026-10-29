package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class HippoRenderState extends LivingEntityRenderState {
    public final AnimationState biteState = new AnimationState();
    public final AnimationState eatState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState snapState = new AnimationState();
    public final AnimationState threatState = new AnimationState();
    public final AnimationState yawnState = new AnimationState();
    public float getTossFeedProgressValue;
    public float getTossFeedProgress(float partialTick) { return getTossFeedProgressValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isBitingValue;
    public boolean isBiting() { return isBitingValue; }
    public boolean isEatingValue;
    public boolean isEating() { return isEatingValue; }
    public boolean isInWater() { return isInWater; }
    public boolean isSnappingValue;
    public boolean isSnapping() { return isSnappingValue; }
    public boolean isThreateningValue;
    public boolean isThreatening() { return isThreateningValue; }
    public boolean isTossFeedingValue;
    public boolean isTossFeeding() { return isTossFeedingValue; }
    public final net.minecraft.client.renderer.item.ItemStackRenderState heldItem = new net.minecraft.client.renderer.item.ItemStackRenderState();
}
