package net.satisfy.wildernature.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.wildernature.client.WilderNatureClient;
import net.satisfy.wildernature.core.registry.ObjectRegistry;
import net.satisfy.wildernature.core.util.WilderNatureUtil;
import net.satisfy.wildernature.fabric.client.renderer.StylinPurpleHatRenderer;


import java.util.List;

public class WilderNatureClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WilderNatureClient.preInitClient();
        WilderNatureClient.onInitializeClient();

        ArmorRenderer.register(StylinPurpleHatRenderer::new, ObjectRegistry.STYLIN_PURPLE_HAT.get());


        ArmorRenderer.register(net.satisfy.wildernature.fabric.client.renderer.FurCloakRenderer::new, ObjectRegistry.FUR_CLOAK.get());

        ItemTooltipCallback.EVENT.register(this::onItemTooltip);
    }

    private void onItemTooltip(ItemStack itemStack, Item.TooltipContext var2,  TooltipFlag context, List<Component> tooltip) {
        WilderNatureUtil.Truffling.addTruffledTooltip(itemStack, tooltip);
    }
}
