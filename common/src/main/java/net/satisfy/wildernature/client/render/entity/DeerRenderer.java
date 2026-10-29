package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.DeerRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.DeerModel;
import net.satisfy.wildernature.core.entity.animal.neutral.DeerEntity;
import org.jetbrains.annotations.NotNull;

@Environment(value = EnvType.CLIENT)
public class DeerRenderer extends MobRenderer<DeerEntity, DeerRenderState, DeerModel<DeerRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/deer.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/deer_sleep.png");
    private static final Identifier WHITE_TEXTURE = WilderNature.identifier("textures/entity/deer_white.png");

    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel<>(context.bakeLayer(DeerModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull Identifier getTextureLocation(DeerRenderState entity) {
        if (entity.isSleeping()) {
            return SLEEPING_TEXTURE;
        }

        if (entity.isWhite()) {
            return WHITE_TEXTURE;
        }

        return TEXTURE;
    }

    @Override
    public void submit(DeerRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        if (entity.isSleeping()) {
            poseStack.translate(0.0D, -0.4D, 0.0D);
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public DeerRenderState createRenderState() { return new DeerRenderState(); }

    @Override
    public void extractRenderState(DeerEntity entity, DeerRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.eatingState.copyFrom(entity.eatingState);
        state.idleState.copyFrom(entity.idleState);
        state.lookAroundState.copyFrom(entity.lookAroundState);
        state.sleepState.copyFrom(entity.sleepState);
        state.isDeerRunningValue = entity.isDeerRunning();
        state.isSleepingValue = entity.isSleeping();
        state.isWhiteValue = entity.isWhite();
    }
}
