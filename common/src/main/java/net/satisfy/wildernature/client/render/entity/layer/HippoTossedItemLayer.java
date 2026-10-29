package net.satisfy.wildernature.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.satisfy.wildernature.client.model.entity.model.HippoModel;
import net.satisfy.wildernature.client.render.state.HippoRenderState;

public class HippoTossedItemLayer extends RenderLayer<HippoRenderState, EntityModel<HippoRenderState>> {
public HippoTossedItemLayer(RenderLayerParent<HippoRenderState, EntityModel<HippoRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, HippoRenderState entity, float netHeadYaw, float headPitch) {
        this.getParentModel().setupAnim(entity);
        if (entity.heldItem.isEmpty() || !entity.isTossFeeding()) return;

        EntityModel<HippoRenderState> parentModel = this.getParentModel();
        if (!(parentModel instanceof HippoModel<HippoRenderState> hippoModel)) return;

        float tossProgress = entity.getTossFeedProgress(0);
        float arcHeight = Mth.sin(tossProgress * (float) Math.PI) * 0.85F;
        float forwardOffset = tossProgress < 0.5F ? Mth.lerp(tossProgress / 0.5F, -0.25F, -1.2F) : Mth.lerp((tossProgress - 0.5F) / 0.5F, -1.2F, -0.2F);
        float verticalOffset = Mth.lerp(tossProgress, 0.2F, 0.05F) + arcHeight;

        ModelPart bodyPart = hippoModel.getRenderBody();
        ModelPart headPart = hippoModel.getRenderHead();
        ModelPart jawPart = hippoModel.getRenderJaw();

        poseStack.pushPose();
        bodyPart.translateAndRotate(poseStack);
        headPart.translateAndRotate(poseStack);
        jawPart.translateAndRotate(poseStack);
        poseStack.translate(0.0F, verticalOffset, forwardOffset);
        poseStack.rotate(Axis.XP.rotationDegrees(90.0F - tossProgress * 60.0F));
        poseStack.rotate(Axis.YP.rotationDegrees(tossProgress * 180.0F));
        poseStack.scale(0.85F, 0.85F, 0.85F);
        entity.heldItem.submit(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        poseStack.popPose();
    }
}