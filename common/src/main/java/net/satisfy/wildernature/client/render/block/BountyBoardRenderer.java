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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.foundation.client.render.WobbleAnimation;
import net.satisfy.wildernature.platform.PlatformHelper;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.ClientBountyBoardNotes;
import net.satisfy.wildernature.client.model.block.BountyBoardModel;
import net.satisfy.wildernature.core.block.BountyBoardBlock;
import net.satisfy.wildernature.core.block.entity.BountyBoardBlockEntity;
import org.joml.Quaternionf;

public class BountyBoardRenderer implements BlockEntityRenderer<BountyBoardBlockEntity, BountyBoardRenderer.State> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/bounty_board.png");
    private final BountyBoardModel model;

    public BountyBoardRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new BountyBoardModel(context.bakeLayer(BountyBoardModel.LAYER_LOCATION));
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        Direction direction = state.direction;
        poseStack.pushPose();

        switch (direction) {
            case NORTH -> {
                poseStack.translate(1.5, 1.5, 0.5);
                poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(0.0F)));
            }
            case SOUTH -> {
                poseStack.translate(-0.5, 1.5, 0.5);
                poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(180.0F)));
            }
            case WEST -> {
                poseStack.translate(0.5, 1.5, -0.5);
                poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(90.0F)));
            }
            case EAST -> {
                poseStack.translate(0.5, 1.5, 1.5);
                poseStack.rotate(new Quaternionf().rotateY((float) Math.toRadians(-90.0F)));
            }
        }

        poseStack.rotate(new Quaternionf().rotateZ((float) Math.toRadians(180.0F)));
        collector.submitModel(model, state, poseStack, net.minecraft.client.renderer.rendertype.RenderTypes.entityCutout(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public Direction direction;
        public int contracts;
        public float time, wobble, phase;
    }
    @Override public State createRenderState() { return new State(); }
    @Override
    public void extractRenderState(BountyBoardBlockEntity blockEntity, State state, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPosition, breakProgress);
        state.direction = blockEntity.getBlockState().getValue(BountyBoardBlock.FACING);
        Level level = blockEntity.getLevel();
        boolean animated = level != null && PlatformHelper.isAnimationsEnabled();
        state.time = animated ? level.getGameTime() + partialTick : 0;
        state.phase = WobbleAnimation.seed(blockEntity.getBlockPos().asLong());
        state.wobble = animated ? WobbleAnimation.angle(level, blockEntity.getWobbleStart(), partialTick) * Mth.DEG_TO_RAD : 0;
        state.contracts = level == null ? BountyBoardModel.CONTRACT_COUNT : ClientBountyBoardNotes.getVisibleContracts(BountyBoardModel.CONTRACT_COUNT);
    }
}
