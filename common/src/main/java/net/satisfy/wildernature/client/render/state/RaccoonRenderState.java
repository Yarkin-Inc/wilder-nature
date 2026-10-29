package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class RaccoonRenderState extends LivingEntityRenderState {
    public final AnimationState openDoorState = new AnimationState();
    public final AnimationState sleepState = new AnimationState();
    public final AnimationState washingState = new AnimationState();
    public boolean isBaby() { return isBaby; }
    public boolean isRaccoonRunningValue;
    public boolean isRaccoonRunning() { return isRaccoonRunningValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
    public final net.minecraft.client.renderer.item.ItemStackRenderState heldItem = new net.minecraft.client.renderer.item.ItemStackRenderState();
}
