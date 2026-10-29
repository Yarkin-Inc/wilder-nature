package net.satisfy.wildernature.client.render.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

/** Values captured on the client before this animal is submitted for rendering. */
public class GiraffeRenderState extends LivingEntityRenderState {
    public final AnimationState alertState = new AnimationState();
    public final AnimationState eatingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState sleepState = new AnimationState();
    public net.minecraft.network.chat.Component getCustomNameValue;
    public net.minecraft.network.chat.Component getCustomName() { return getCustomNameValue; }
    public boolean hasCustomNameValue;
    public boolean hasCustomName() { return hasCustomNameValue; }
    public boolean isBaby() { return isBaby; }
    public boolean isRidingBurstValue;
    public boolean isRidingBurst() { return isRidingBurstValue; }
    public boolean isSleepingValue;
    public boolean isSleeping() { return isSleepingValue; }
}
