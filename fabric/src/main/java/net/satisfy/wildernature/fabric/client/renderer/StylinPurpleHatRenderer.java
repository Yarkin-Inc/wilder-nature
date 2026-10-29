package net.satisfy.wildernature.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.wildernature.core.item.StylinPurpleHatItem;
import net.satisfy.wildernature.core.registry.ArmorRegistry;

public class StylinPurpleHatRenderer implements ArmorRenderer {
    private final net.satisfy.wildernature.client.model.armor.StylinPurpleHatModel model;
    public StylinPurpleHatRenderer(net.minecraft.client.renderer.entity.EntityRendererProvider.Context context) {
        model = new net.satisfy.wildernature.client.model.armor.StylinPurpleHatModel(context.bakeLayer(net.satisfy.wildernature.client.model.armor.StylinPurpleHatModel.LAYER_LOCATION));
    }
    @Override public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) { return false; }
    @Override
    public void render(PoseStack matrices, SubmitNodeCollector vertexConsumers, ItemStack stack, HumanoidRenderState entity, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        StylinPurpleHatItem hat = (StylinPurpleHatItem) stack.getItem();

        contextModel.setupAnim(entity);
        matrices.pushPose();
        matrices.scale(1.05F, 1.05F, 1.05F);

        vertexConsumers.submitModel(model, contextModel.getHead().storePose(), matrices, model.renderType(hat.getHatTexture()), light, OverlayTexture.NO_OVERLAY, entity.outlineColor);
        matrices.popPose();
    }
}