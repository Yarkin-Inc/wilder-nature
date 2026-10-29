package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.TermiteRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.TermiteModel;
import net.satisfy.wildernature.client.render.entity.layer.TermiteHeldItemLayer;
import net.satisfy.wildernature.core.entity.animal.passive.TermiteEntity;
import org.jetbrains.annotations.NotNull;

public class TermiteRenderer extends MobRenderer<TermiteEntity, TermiteRenderState, TermiteModel<TermiteRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/termite.png");

    public TermiteRenderer(EntityRendererProvider.Context context) {
        super(context, new TermiteModel<>(context.bakeLayer(TermiteModel.LAYER_LOCATION)), 0.2F);
        this.addLayer(new TermiteHeldItemLayer(this));
    }

    @Override
    public @NotNull Identifier getTextureLocation(TermiteRenderState entity) {
        return TEXTURE;
    }

    @Override
    public TermiteRenderState createRenderState() { return new TermiteRenderState(); }

    @Override
    public void extractRenderState(TermiteEntity entity, TermiteRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        itemModelResolver.updateForLiving(state.heldItem, entity.getMainHandItem(), net.minecraft.world.item.ItemDisplayContext.FIXED, entity);
        state.eatState.copyFrom(entity.eatState);
        state.idleState.copyFrom(entity.idleState);
        state.getDeltaMovementValue = entity.getDeltaMovement();
        state.isEatingValue = entity.isEating();
    }
}
