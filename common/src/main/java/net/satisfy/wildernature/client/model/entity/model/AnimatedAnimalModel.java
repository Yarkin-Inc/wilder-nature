package net.satisfy.wildernature.client.model.entity.model;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public abstract class AnimatedAnimalModel<S extends LivingEntityRenderState> extends EntityModel<S> {
    private final Map<AnimationDefinition, KeyframeAnimation> animations = new IdentityHashMap<>();

    protected AnimatedAnimalModel(ModelPart root) { super(root); }

    private KeyframeAnimation animation(AnimationDefinition definition) {
        return animations.computeIfAbsent(definition, key -> key.bake(root()));
    }

    protected void animate(AnimationState state, AnimationDefinition definition, float age) {
        animation(definition).apply(state, age);
    }

    protected void animate(AnimationState state, AnimationDefinition definition, float age, float speed) {
        animation(definition).apply(state, age, speed);
    }

    protected void animateWalk(AnimationDefinition definition, float position, float speed, float timeScale, float amplitude) {
        animation(definition).applyWalk(position, speed, timeScale, amplitude);
    }
}
