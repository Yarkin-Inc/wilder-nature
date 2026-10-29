package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.SwiftFoxRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.SwiftFoxModel;
import net.satisfy.wildernature.client.render.entity.layer.SwiftFoxHeldItemLayer;
import net.satisfy.wildernature.core.entity.animal.neutral.SwiftFoxEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SwiftFoxRenderer extends MobRenderer<SwiftFoxEntity, SwiftFoxRenderState, SwiftFoxModel<SwiftFoxRenderState>> {
    private static final Identifier DEFAULT_TEXTURE = WilderNature.identifier("textures/entity/swift_fox.png");
    private static final Identifier SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/swift_fox_sleep.png");

    public SwiftFoxRenderer(EntityRendererProvider.Context context) {
        super(context, new SwiftFoxModel<>(context.bakeLayer(SwiftFoxModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new SwiftFoxHeldItemLayer(this));
    }

    @Override
    public @NotNull Identifier getTextureLocation(SwiftFoxRenderState entity) {
        return entity.isSleeping() ? SLEEPING_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public void submit(SwiftFoxRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public SwiftFoxRenderState createRenderState() { return new SwiftFoxRenderState(); }

    @Override
    public void extractRenderState(SwiftFoxEntity entity, SwiftFoxRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.sneakAnimationState.copyFrom(entity.sneakAnimationState);
        state.isSleepingValue = entity.isSleeping();
        itemModelResolver.updateForLiving(state.heldItem, entity.getMainHandItem(), net.minecraft.world.item.ItemDisplayContext.FIXED, entity);
    }
}
