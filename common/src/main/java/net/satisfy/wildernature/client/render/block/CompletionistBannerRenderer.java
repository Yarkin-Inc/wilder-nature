package net.satisfy.wildernature.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.block.CompletionistBannerBlock;
import net.satisfy.wildernature.core.block.CompletionistWallBannerBlock;
import net.satisfy.wildernature.core.block.entity.CompletionistBannerEntity;

public class CompletionistBannerRenderer implements BlockEntityRenderer<CompletionistBannerEntity, CompletionistBannerRenderer.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(WilderNature.identifier("banner"), "main");

    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private static final String BAR = "bar";
    private final ModelPart flag;
    private final ModelPart pole;
    private final ModelPart bar;

    public CompletionistBannerRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.flag = modelPart.getChild(FLAG);
        this.pole = modelPart.getChild(POLE);
        this.bar = modelPart.getChild(BAR);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(FLAG, CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -1.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.0F, -1.0F, -0.0349F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild(POLE, CubeListBuilder.create().texOffs(44, 0).addBox(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f), PartPose.ZERO);
        partDefinition.addOrReplaceChild(BAR, CubeListBuilder.create().texOffs(0, 42).addBox(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public static class State extends BlockEntityRenderState {
        public Identifier texture;
        public boolean wall;
        public float rotation, wave;
    }

    @Override public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(CompletionistBannerEntity banner, State state, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(banner, state, partialTick, cameraPosition, breakProgress);
        BlockState block = banner.getBlockState();
        state.texture = ((CompletionistBannerBlock) block.getBlock()).getRenderTexture();
        state.wall = block.getBlock() instanceof CompletionistWallBannerBlock;
        state.rotation = state.wall ? -block.getValue(CompletionistWallBannerBlock.FACING).toYRot() + 180.0F
            : -block.getValue(CompletionistBannerBlock.ROTATION) * 360.0F / 16.0F;
        long time = banner.getLevel() == null ? 0 : banner.getLevel().getGameTime();
        BlockPos pos = banner.getBlockPos();
        float phase = (Math.floorMod(pos.getX() * 7L + pos.getY() * 9L + pos.getZ() * 13L + time, 100L) + partialTick) / 100.0F;
        state.wave = (-0.0125F + 0.01F * Mth.cos((float) Math.PI * 2 * phase)) * (float) Math.PI;
    }

    @Override
    public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState cameraState) {
        pose.pushPose();
        pose.translate(0.5, state.wall ? -0.1666666716337204 : 0.5, 0.5);
        pose.rotate(Axis.YP.rotationDegrees(state.rotation));
        if (state.wall) pose.translate(0, -0.3125, -0.4375);
        pose.scale(0.66F, -0.66F, -0.66F);
        var supportType = RenderTypes.entitySolid(Identifier.withDefaultNamespace("textures/entity/banner/banner_base.png"));
        if (!state.wall) collector.submitModelPart(pole, pose, supportType, state.lightCoords, OverlayTexture.NO_OVERLAY, null);
        collector.submitModelPart(bar, pose, supportType, state.lightCoords, OverlayTexture.NO_OVERLAY, null);
        flag.xRot = state.wave;
        flag.y = -32;
        collector.submitModelPart(flag, pose, RenderTypes.entitySolid(state.texture), state.lightCoords, OverlayTexture.NO_OVERLAY, null);
        pose.popPose();
    }
}
