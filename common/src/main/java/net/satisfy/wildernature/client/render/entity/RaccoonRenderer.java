package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.RaccoonRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.RaccoonModel;
import net.satisfy.wildernature.client.render.entity.layer.RaccoonHeldItemLayer;
import net.satisfy.wildernature.core.entity.animal.neutral.RaccoonEntity;
import org.jetbrains.annotations.NotNull;

@Environment(value = EnvType.CLIENT)
public class RaccoonRenderer extends MobRenderer<RaccoonEntity, RaccoonRenderState, RaccoonModel<RaccoonRenderState>> {
    private static final Identifier RACCOON_TEXTURE = WilderNature.identifier("textures/entity/raccoon.png");
    private static final Identifier RACCOON_SLEEP_TEXTURE = WilderNature.identifier("textures/entity/raccoon_sleep.png");

    public RaccoonRenderer(EntityRendererProvider.Context context) {
        super(context, new RaccoonModel<>(context.bakeLayer(RaccoonModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new RaccoonHeldItemLayer(this));
    }



    @Override
    public @NotNull Identifier getTextureLocation(RaccoonRenderState entity) {
        return entity.isSleeping() ? RACCOON_SLEEP_TEXTURE : RACCOON_TEXTURE;
    }

    @Override
    public void submit(RaccoonRenderState pEntity, PoseStack pMatrixStack, SubmitNodeCollector pBuffer, CameraRenderState cameraState) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.submit(pEntity, pMatrixStack, pBuffer, cameraState);
    }

    @Override
    public RaccoonRenderState createRenderState() { return new RaccoonRenderState(); }

    @Override
    public void extractRenderState(RaccoonEntity entity, RaccoonRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        itemModelResolver.updateForLiving(state.heldItem, entity.getMainHandItem(), net.minecraft.world.item.ItemDisplayContext.FIXED, entity);
        state.openDoorState.copyFrom(entity.openDoorState);
        state.sleepState.copyFrom(entity.sleepState);
        state.washingState.copyFrom(entity.washingState);
        state.isRaccoonRunningValue = entity.isRaccoonRunning();
        state.isSleepingValue = entity.isSleeping();
    }
}
