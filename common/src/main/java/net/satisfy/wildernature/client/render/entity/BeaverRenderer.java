package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.BeaverRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BeaverModel;
import net.satisfy.wildernature.core.entity.animal.passive.BeaverEntity;
import org.jetbrains.annotations.NotNull;

public class BeaverRenderer extends MobRenderer<BeaverEntity, BeaverRenderState, BeaverModel<BeaverRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/beaver.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/beaver_sleep.png");

    public BeaverRenderer(EntityRendererProvider.Context context) {
        super(context, new BeaverModel<>(context.bakeLayer(BeaverModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    public @NotNull Identifier getTextureLocation(BeaverRenderState entity) {
        return entity.isSleeping() ? SLEEPING_TEXTURE : TEXTURE;
    }

    @Override
    public void submit(BeaverRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(0.55F, 0.55F, 0.55F);
        }

        if (entity.isSleeping()) {
            poseStack.translate(0.0D, 0.2D, 0.0D);
            poseStack.rotate(Axis.ZP.rotationDegrees(90.0F));
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public BeaverRenderState createRenderState() { return new BeaverRenderState(); }

    @Override
    public void extractRenderState(BeaverEntity entity, BeaverRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.gnawState.copyFrom(entity.gnawState);
        state.idleState.copyFrom(entity.idleState);
        state.nodState.copyFrom(entity.nodState);
        state.sleepState.copyFrom(entity.sleepState);
        state.walkState.copyFrom(entity.walkState);
        state.isSleepingValue = entity.isSleeping();
    }
}
