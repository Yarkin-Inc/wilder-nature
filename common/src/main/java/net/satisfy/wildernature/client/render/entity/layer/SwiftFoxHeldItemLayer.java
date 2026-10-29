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
import net.satisfy.wildernature.client.model.entity.model.SwiftFoxModel;
import net.satisfy.wildernature.client.render.state.SwiftFoxRenderState;

@Environment(EnvType.CLIENT)
public class SwiftFoxHeldItemLayer extends RenderLayer<SwiftFoxRenderState, SwiftFoxModel<SwiftFoxRenderState>> {
public SwiftFoxHeldItemLayer(RenderLayerParent<SwiftFoxRenderState, SwiftFoxModel<SwiftFoxRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, SwiftFoxRenderState entity, float netHeadYaw, float headPitch) {
        this.getParentModel().setupAnim(entity);
        if (entity.heldItem.isEmpty()) {
            return;
        }

        ModelPart rootPart = this.getParentModel().root();
        ModelPart swiftFoxPart = rootPart.getChild("swift_fox");
        ModelPart bodyPart = swiftFoxPart.getChild("body");
        ModelPart headPart = bodyPart.getChild("head");

        poseStack.pushPose();
        swiftFoxPart.translateAndRotate(poseStack);
        bodyPart.translateAndRotate(poseStack);
        headPart.translateAndRotate(poseStack);

        if (entity.isBaby()) {
            poseStack.scale(0.85F, 0.85F, 0.85F);
        }

        poseStack.translate(0.05F, 0.15F, -0.45F);
        poseStack.rotate(Axis.XP.rotationDegrees(-90.0F));
        poseStack.rotate(Axis.ZN.rotationDegrees(33.0F));
        poseStack.rotate(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.4F, 0.4F, 0.4F);

        entity.heldItem.submit(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        poseStack.popPose();
    }
}