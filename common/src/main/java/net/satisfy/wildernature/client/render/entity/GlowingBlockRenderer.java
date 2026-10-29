package net.satisfy.wildernature.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.entity.fx.GlowingBlock;

public class GlowingBlockRenderer extends EntityRenderer<GlowingBlock, GlowingBlockRenderer.State> {
    private static final RenderType LINES_NO_DEPTH = RenderType.create("wildernature_glowing_block",
            RenderSetup.builder(RenderPipelines.register(RenderPipeline.builder(RenderPipelines.LINES_SNIPPET)
                    .withLocation(WilderNature.identifier("pipeline/glowing_block"))
                    .withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, false))
                    .withColorTargetState(com.mojang.renderpearl.api.pipeline.ColorTargetState.DEFAULT)
                    .withCull(false).build())).createRenderSetup());

    public static class State extends EntityRenderState {
        public VoxelShape shape = Shapes.empty();
        public int color;
    }

    public GlowingBlockRenderer(EntityRendererProvider.Context context) { super(context); }

    @Override public State createRenderState() { return new State(); }

    @Override public boolean shouldRender(GlowingBlock entity, Frustum frustum, double x, double y, double z, float partialTicks) {
        return true;
    }

    @Override public void extractRenderState(GlowingBlock entity, State state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.shape = Shapes.create(entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ()));
        state.color = 0xFF000000 | entity.getGlowColor();
    }

    @Override public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        pose.pushPose();
        pose.translate(0, -0.5, 0);
        collector.submitShapeOutline(pose, state.shape, LINES_NO_DEPTH, state.color, 6.0F, false);
        pose.popPose();
        super.submit(state, pose, collector, camera);
    }
}
