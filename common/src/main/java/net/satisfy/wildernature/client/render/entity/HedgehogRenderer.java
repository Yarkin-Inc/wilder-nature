package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.HedgehogRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.HedgehogModel;
import net.satisfy.wildernature.core.entity.animal.passive.HedgehogEntity;
import org.jetbrains.annotations.NotNull;


@Environment(value = EnvType.CLIENT)
public class HedgehogRenderer extends MobRenderer<HedgehogEntity, HedgehogRenderState, HedgehogModel<HedgehogRenderState>> {
    private static final Identifier DEFAULT_TEXTURE = WilderNature.identifier("textures/entity/hedgehog.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/hedgehog_sleep.png");

    public HedgehogRenderer(EntityRendererProvider.Context context) {
        super(context, new HedgehogModel<>(context.bakeLayer(HedgehogModel.LAYER_LOCATION)), 0.7f);
    }


    @Override
    public @NotNull Identifier getTextureLocation(HedgehogRenderState entity) {
        return entity.isSleeping() ? SLEEPING_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public void submit(HedgehogRenderState pEntity, PoseStack pMatrixStack, SubmitNodeCollector pBuffer, CameraRenderState cameraState) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.submit(pEntity, pMatrixStack, pBuffer, cameraState);
    }

    @Override
    public HedgehogRenderState createRenderState() { return new HedgehogRenderState(); }

    @Override
    public void extractRenderState(HedgehogEntity entity, HedgehogRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.noAnimationState.copyFrom(entity.noAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.sniffAnimationState.copyFrom(entity.sniffAnimationState);
        state.getDeltaMovementValue = entity.getDeltaMovement();
        state.isCurledValue = entity.isCurled();
        state.isSleepingValue = entity.isSleeping();
    }
}
