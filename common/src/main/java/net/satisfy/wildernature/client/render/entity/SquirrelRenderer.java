package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.SquirrelRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.SquirrelModel;
import net.satisfy.wildernature.client.render.entity.layer.SquirrelHeldItemLayer;
import net.satisfy.wildernature.core.entity.animal.neutral.SquirrelEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SquirrelRenderer extends MobRenderer<SquirrelEntity, SquirrelRenderState, SquirrelModel<SquirrelRenderState>> {
    private static final Identifier BROWN = WilderNature.identifier("textures/entity/squirrel_brown.png");
    private static final Identifier RED = WilderNature.identifier("textures/entity/squirrel_red.png");
    private static final Identifier GRAY = WilderNature.identifier("textures/entity/squirrel_gray.png");

    public SquirrelRenderer(EntityRendererProvider.Context context) {
        super(context, new SquirrelModel<>(context.bakeLayer(SquirrelModel.LAYER_LOCATION)), 0.4F);
        this.addLayer(new SquirrelHeldItemLayer(this));
    }

    @Override
    protected void scale(SquirrelRenderState entity, PoseStack poseStack) {
        if (entity.isBaby()) {
            poseStack.scale(0.55F, 0.55F, 0.55F);
        } else {
        }
    }

    @Override
    public @NotNull Identifier getTextureLocation(SquirrelRenderState entity) {
        return switch (entity.getVariant()) {
            case 1 -> RED;
            case 2 -> GRAY;
            default -> BROWN;
        };
    }

    @Override
    protected float getShadowRadius(SquirrelRenderState state) { return state.isBaby ? 0.25F : 0.4F; }

    @Override
    public SquirrelRenderState createRenderState() { return new SquirrelRenderState(); }

    @Override
    public void extractRenderState(SquirrelEntity entity, SquirrelRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.wiggleAnimationState.copyFrom(entity.wiggleAnimationState);
        state.getVariantValue = entity.getVariant();
        state.isWigglingValue = entity.isWiggling();
        itemModelResolver.updateForLiving(state.heldItem, entity.getMainHandItem(), net.minecraft.world.item.ItemDisplayContext.FIXED, entity);
    }
}
