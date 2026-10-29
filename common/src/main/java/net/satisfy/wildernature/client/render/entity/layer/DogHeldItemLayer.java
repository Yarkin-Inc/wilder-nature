package net.satisfy.wildernature.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.satisfy.wildernature.client.model.entity.model.DogModel;
import net.satisfy.wildernature.client.render.state.DogRenderState;

public class DogHeldItemLayer extends RenderLayer<DogRenderState, DogModel<DogRenderState>> {
public DogHeldItemLayer(RenderLayerParent<DogRenderState, DogModel<DogRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, DogRenderState entity, float netHeadYaw, float headPitch) {
        this.getParentModel().setupAnim(entity);
        if (entity.heldItem.isEmpty()) {
            return;
        }

        ModelPart dogPart = this.getParentModel().root().getChild("dog");
        ModelPart headPart = dogPart.getChild("head");
        ModelPart mouthPart = headPart.getChild("mouth");

        poseStack.pushPose();
        dogPart.translateAndRotate(poseStack);
        headPart.translateAndRotate(poseStack);
        mouthPart.translateAndRotate(poseStack);

        if (entity.isBaby()) {
            poseStack.scale(0.85F, 0.85F, 0.85F);
        }

        poseStack.translate(0.2F, 0.15F, -0.1F);
        poseStack.rotate(Axis.XP.rotationDegrees(90.0F));
        poseStack.rotate(Axis.ZP.rotationDegrees(90.0F));
        poseStack.scale(0.65F, 0.65F, 0.65F);

        entity.heldItem.submit(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        poseStack.popPose();
    }


}