package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.MiniSheepRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.MiniSheepModel;
import net.satisfy.wildernature.core.entity.animal.neutral.MiniSheepEntity;
import org.jetbrains.annotations.NotNull;

@Environment(value = EnvType.CLIENT)
public class MiniSheepRenderer extends MobRenderer<MiniSheepEntity, MiniSheepRenderState, MiniSheepModel<MiniSheepRenderState>> {
    private static final Identifier SHEARED_TEXTURE = WilderNature.identifier("textures/entity/minisheep_sheared.png");
    private static final Identifier DEFAULT_TEXTURE = WilderNature.identifier("textures/entity/minisheep.png");

    public MiniSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniSheepModel<>(context.bakeLayer(MiniSheepModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull Identifier getTextureLocation(MiniSheepRenderState entity) {
        return entity.isSheared() ? SHEARED_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public void submit(MiniSheepRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        poseStack.pushPose();

        if (entity.isMiniSheepSleeping()) {
            poseStack.translate(0.0F, -0.15F, 0.0F);
        }

        if (entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public MiniSheepRenderState createRenderState() { return new MiniSheepRenderState(); }

    @Override
    public void extractRenderState(MiniSheepEntity entity, MiniSheepRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.eatAnimationState.copyFrom(entity.eatAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.maehAnimationState.copyFrom(entity.maehAnimationState);
        state.runAnimationState.copyFrom(entity.runAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.isMaehAnimatingValue = entity.isMaehAnimating();
        state.isMiniSheepRunningValue = entity.isMiniSheepRunning();
        state.isMiniSheepSleepingValue = entity.isMiniSheepSleeping();
        state.isShearedValue = entity.isSheared();
    }
}
