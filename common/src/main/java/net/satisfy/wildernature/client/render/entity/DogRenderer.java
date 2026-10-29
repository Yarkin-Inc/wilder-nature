package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.DogRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.DogModel;
import net.satisfy.wildernature.client.render.entity.layer.DogHeldItemLayer;
import net.satisfy.wildernature.core.entity.animal.tameable.DogEntity;
import org.jetbrains.annotations.NotNull;

public class DogRenderer extends MobRenderer<DogEntity, DogRenderState, DogModel<DogRenderState>> {
    private static final Identifier BROWN = WilderNature.identifier("textures/entity/dog_brown.png");
    private static final Identifier WHITE = WilderNature.identifier("textures/entity/dog_white.png");
    private static final Identifier GRAY = WilderNature.identifier("textures/entity/dog_gray.png");

    private static final Identifier BROWN_SLEEP = WilderNature.identifier("textures/entity/dog_brown_sleep.png");
    private static final Identifier WHITE_SLEEP = WilderNature.identifier("textures/entity/dog_white_sleep.png");
    private static final Identifier GRAY_SLEEP = WilderNature.identifier("textures/entity/dog_gray_sleep.png");

    public DogRenderer(EntityRendererProvider.Context context) {
        super(context, new DogModel<>(context.bakeLayer(DogModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new DogHeldItemLayer(this));
    }

    @Override
    public @NotNull Identifier getTextureLocation(DogRenderState entity) {
        int variant = entity.getVariant();

        if (entity.isSleeping() || entity.isLying()) {
            if (variant == 0) {
                return BROWN_SLEEP;
            }
            if (variant == 1) {
                return WHITE_SLEEP;
            }
            return GRAY_SLEEP;
        }

        if (variant == 0) {
            return BROWN;
        }
        if (variant == 1) {
            return WHITE;
        }
        return GRAY;
    }

    @Override
    public void submit(DogRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        if (entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }

        super.submit(entity, poseStack, buffer, cameraState);
    }

    @Override
    public DogRenderState createRenderState() { return new DogRenderState(); }

    @Override
    public void extractRenderState(DogEntity entity, DogRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.digAnimationState.copyFrom(entity.digAnimationState);
        state.howlingAnimationState.copyFrom(entity.howlingAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.lyingAnimationState.copyFrom(entity.lyingAnimationState);
        state.sitAnimationState.copyFrom(entity.sitAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.getVariantValue = entity.getVariant();
        state.isLyingValue = entity.isLying();
        state.isSleepingValue = entity.isSleeping();
        itemModelResolver.updateForLiving(state.heldItem, entity.getDisplayedItem(), net.minecraft.world.item.ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
    }
}
