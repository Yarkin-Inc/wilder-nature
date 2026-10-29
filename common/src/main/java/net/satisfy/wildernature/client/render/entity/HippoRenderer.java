package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.HippoRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BabyHippoModel;
import net.satisfy.wildernature.client.model.entity.model.HippoModel;
import net.satisfy.wildernature.client.render.entity.layer.HippoTossedItemLayer;
import net.satisfy.wildernature.core.entity.animal.defensive.HippoEntity;
import org.jetbrains.annotations.NotNull;

public class HippoRenderer extends MobRenderer<HippoEntity, HippoRenderState, EntityModel<HippoRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/hippo.png");
    private static final Identifier BABY_TEXTURE = WilderNature.identifier("textures/entity/baby_hippo.png");

    private final HippoModel<HippoRenderState> adultModel;
    private final BabyHippoModel<HippoRenderState> babyModel;

    @SuppressWarnings("unused")
    public HippoRenderer(EntityRendererProvider.Context context) {
        super(context, new HippoModel<>(context.bakeLayer(HippoModel.LAYER_LOCATION)), 1.1F);
        this.adultModel = new HippoModel<>(context.bakeLayer(HippoModel.LAYER_LOCATION));
        this.babyModel = new BabyHippoModel<>(context.bakeLayer(BabyHippoModel.LAYER_LOCATION));
        this.model = this.adultModel;
        this.addLayer(new HippoTossedItemLayer(this));
    }

    @Override
    public @NotNull Identifier getTextureLocation(HippoRenderState entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    public void submit(HippoRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;

        poseStack.pushPose();

        if (entity.isInWater() && !entity.isBaby()) {
            poseStack.translate(0.0D, -0.3D, 0.0D);
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public HippoRenderState createRenderState() { return new HippoRenderState(); }

    @Override
    public void extractRenderState(HippoEntity entity, HippoRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.biteState.copyFrom(entity.biteState);
        state.eatState.copyFrom(entity.eatState);
        state.idleState.copyFrom(entity.idleState);
        state.snapState.copyFrom(entity.snapState);
        state.threatState.copyFrom(entity.threatState);
        state.yawnState.copyFrom(entity.yawnState);
        state.getTossFeedProgressValue = entity.getTossFeedProgress(partialTick);
        state.isBitingValue = entity.isBiting();
        state.isEatingValue = entity.isEating();
        state.isSnappingValue = entity.isSnapping();
        state.isThreateningValue = entity.isThreatening();
        state.isTossFeedingValue = entity.isTossFeeding();
        itemModelResolver.updateForLiving(state.heldItem, entity.getDisplayedItem(), net.minecraft.world.item.ItemDisplayContext.GROUND, entity);
    }
}
