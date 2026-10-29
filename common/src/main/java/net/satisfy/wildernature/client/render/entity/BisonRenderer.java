package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.BisonRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BisonModel;
import net.satisfy.wildernature.core.entity.animal.defensive.BisonEntity;
import org.jetbrains.annotations.NotNull;

public class BisonRenderer extends MobRenderer<BisonEntity, BisonRenderState, BisonModel<BisonRenderState>> {
    private static final Identifier DEFAULT_TEXTURE = WilderNature.identifier("textures/entity/bison.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/bison_sleep.png");

    public BisonRenderer(EntityRendererProvider.Context context) {
        super(context, new BisonModel<>(context.bakeLayer(BisonModel.LAYER_LOCATION)), 0.9f);
    }

    @Override
    public @NotNull Identifier getTextureLocation(BisonRenderState entity) {
        return entity.isSleeping() ? SLEEPING_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public void submit(BisonRenderState entity, PoseStack pose, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        if (entity.isBaby()) {
            pose.scale(0.5f, 0.5f, 0.5f);
            super.submit(entity, pose, buffer, cameraState);
            return;
        }
        super.submit(entity, pose, buffer, cameraState);
    }

    @Override
    protected float getShadowRadius(BisonRenderState state) { return state.isBaby ? 0.3F : 0.9F; }

    @Override
    public BisonRenderState createRenderState() { return new BisonRenderState(); }

    @Override
    public void extractRenderState(BisonEntity entity, BisonRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.alertAnimationState.copyFrom(entity.alertAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.grazeAnimationState.copyFrom(entity.grazeAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.snortAnimationState.copyFrom(entity.snortAnimationState);
        state.isSleepingValue = entity.isSleeping();
    }
}
