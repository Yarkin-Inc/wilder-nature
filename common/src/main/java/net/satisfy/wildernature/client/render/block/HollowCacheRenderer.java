package net.satisfy.wildernature.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.block.HollowCacheModel;
import net.satisfy.wildernature.core.block.HollowCacheBlock;
import net.satisfy.wildernature.core.block.entity.HollowCacheBlockEntity;
import org.joml.Quaternionf;

public class HollowCacheRenderer implements BlockEntityRenderer<HollowCacheBlockEntity, HollowCacheRenderer.State> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/hollow_cache.png");
    private final HollowCacheModel model;

    public HollowCacheRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new HollowCacheModel(context.bakeLayer(HollowCacheModel.LAYER_LOCATION));
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        Direction direction = state.direction;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.75D, 0.5D);

        switch (direction) {
            case NORTH -> poseStack.rotate(new Quaternionf().rotateY(0.0F));
            case SOUTH -> poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(180.0D)));
            case WEST -> poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(90.0D)));
            case EAST -> poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(-90.0D)));
        }

        poseStack.scale(1.0F, -1.0F, -1.0F);

        collector.submitModel(model, state, poseStack, net.minecraft.client.renderer.rendertype.RenderTypes.entityCutout(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public Direction direction;
        public float openProgress;
    }
    @Override public State createRenderState() { return new State(); }
    @Override
    public void extractRenderState(HollowCacheBlockEntity blockEntity, State state, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPosition, breakProgress);
        state.direction = blockEntity.getBlockState().getValue(HollowCacheBlock.FACING);
        state.openProgress = blockEntity.getOpenProgress(partialTick);
    }
}
