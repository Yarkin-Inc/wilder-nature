package net.satisfy.wildernature.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.satisfy.wildernature.client.model.armor.FurCloakModel;
import net.satisfy.wildernature.core.registry.ObjectRegistry;

public class FurCloakRenderer implements net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer {
    private final FurCloakModel model;
    public FurCloakRenderer(net.minecraft.client.renderer.entity.EntityRendererProvider.Context context) {
        model = new FurCloakModel(context.bakeLayer(FurCloakModel.LAYER_LOCATION));
    }
    @Override
    public void render(PoseStack pose, SubmitNodeCollector collector, net.minecraft.world.item.ItemStack stack,
            HumanoidRenderState state, net.minecraft.world.entity.EquipmentSlot slot, int light,
            HumanoidModel<HumanoidRenderState> parentModel) {
        parentModel.setupAnim(state);
        float flap = 0, lean = 0, side = 0;
        if (state instanceof AvatarRenderState avatar) {
            flap = avatar.capeFlap;
            lean = avatar.capeLean;
            side = avatar.capeLean2;
        }
        var cape = new FurCloakModel.State(parentModel.body.storePose(), state.isCrouching, flap, lean, side);
        collector.submitModel(model, cape, pose, RenderTypes.entityCutout(FurCloakModel.TEXTURE), light, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
