package net.satisfy.wildernature.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.satisfy.wildernature.client.model.entity.model.RaccoonModel;
import net.satisfy.wildernature.client.render.state.RaccoonRenderState;

@Environment(EnvType.CLIENT)
public class RaccoonHeldItemLayer extends RenderLayer<RaccoonRenderState, RaccoonModel<RaccoonRenderState>> {
public RaccoonHeldItemLayer(RenderLayerParent<RaccoonRenderState, RaccoonModel<RaccoonRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, RaccoonRenderState entity, float netHeadYaw, float headPitch) {
        this.getParentModel().setupAnim(entity);
        if (entity.heldItem.isEmpty()) {
            return;
        }

        ModelPart rootPart = this.getParentModel().root();
        ModelPart raccoonPart = rootPart.getChild("raccoon");
        ModelPart bodyPart = raccoonPart.getChild("body");
        ModelPart headPart = bodyPart.getChild("head");

        poseStack.pushPose();
        raccoonPart.translateAndRotate(poseStack);
        bodyPart.translateAndRotate(poseStack);
        headPart.translateAndRotate(poseStack);

        if (entity.isBaby()) {
            poseStack.scale(0.85F, 0.85F, 0.85F);
        }

        poseStack.translate(0.0F, 0.15F, -0.5F);
        poseStack.rotate(Axis.XP.rotationDegrees(-90.0F));
        poseStack.rotate(Axis.ZN.rotationDegrees(33.0F));
        poseStack.rotate(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.4F, 0.4F, 0.4F);

        entity.heldItem.submit(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        poseStack.popPose();
    }
}