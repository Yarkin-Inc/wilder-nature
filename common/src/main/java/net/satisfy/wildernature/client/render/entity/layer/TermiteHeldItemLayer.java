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
import net.satisfy.wildernature.client.model.entity.model.TermiteModel;
import net.satisfy.wildernature.client.render.state.TermiteRenderState;

@Environment(EnvType.CLIENT)
public class TermiteHeldItemLayer extends RenderLayer<TermiteRenderState, TermiteModel<TermiteRenderState>> {
public TermiteHeldItemLayer(RenderLayerParent<TermiteRenderState, TermiteModel<TermiteRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, TermiteRenderState entity, float netHeadYaw, float headPitch) {
        this.getParentModel().setupAnim(entity);
        if (entity.heldItem.isEmpty()) {
            return;
        }

        ModelPart basePart = this.getParentModel().root().getChild("base");
        ModelPart torsoPart = basePart.getChild("torso");

        poseStack.pushPose();
        basePart.translateAndRotate(poseStack);
        torsoPart.translateAndRotate(poseStack);

        poseStack.translate(0.0F, -0.25F, -0.15F);
        poseStack.rotate(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.6F, 0.6F, 0.6F);

        entity.heldItem.submit(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        poseStack.popPose();
    }
}