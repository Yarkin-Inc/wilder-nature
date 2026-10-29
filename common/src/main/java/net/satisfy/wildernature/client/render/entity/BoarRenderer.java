package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.BoarRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BoarModel;
import net.satisfy.wildernature.core.entity.animal.passive.BoarEntity;
import org.jetbrains.annotations.NotNull;

@Environment(value = EnvType.CLIENT)
public class BoarRenderer extends MobRenderer<BoarEntity, BoarRenderState, BoarModel<BoarRenderState>> {
    private static final Identifier DEFAULT_TEXTURE = WilderNature.identifier("textures/entity/boar.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/boar_sleep.png");

    public BoarRenderer(EntityRendererProvider.Context context) {
        super(context, new BoarModel<>(context.bakeLayer(BoarModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull Identifier getTextureLocation(BoarRenderState entity) {
        return entity.isSleeping() ? SLEEPING_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public void submit(BoarRenderState pEntity, PoseStack pMatrixStack, SubmitNodeCollector pBuffer, CameraRenderState cameraState) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.submit(pEntity, pMatrixStack, pBuffer, cameraState);
    }

    @Override
    public BoarRenderState createRenderState() { return new BoarRenderState(); }

    @Override
    public void extractRenderState(BoarEntity entity, BoarRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.diggingAnimationState.copyFrom(entity.diggingAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.getDenyAnimationTickValue = entity.getDenyAnimationTick();
        state.isDiggingValue = entity.isDigging();
        state.isSleepingValue = entity.isSleeping();
    }
}
