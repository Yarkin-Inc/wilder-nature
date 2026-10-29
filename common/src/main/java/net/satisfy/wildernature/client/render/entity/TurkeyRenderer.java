package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.TurkeyRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.TurkeyModel;
import net.satisfy.wildernature.core.entity.animal.neutral.TurkeyEntity;
import org.jetbrains.annotations.NotNull;

public class TurkeyRenderer extends MobRenderer<TurkeyEntity, TurkeyRenderState, TurkeyModel<TurkeyRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/turkey.png");

    public TurkeyRenderer(EntityRendererProvider.Context context) {
        super(context, new TurkeyModel<>(context.bakeLayer(TurkeyModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull Identifier getTextureLocation(TurkeyRenderState entity) {
        return TEXTURE;
    }

    @Override
    public void submit(TurkeyRenderState pEntity, PoseStack pMatrixStack, SubmitNodeCollector pBuffer, CameraRenderState cameraState) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.submit(pEntity, pMatrixStack, pBuffer, cameraState);

    }

    @Override
    public TurkeyRenderState createRenderState() { return new TurkeyRenderState(); }

    @Override
    public void extractRenderState(TurkeyEntity entity, TurkeyRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
    }
}
