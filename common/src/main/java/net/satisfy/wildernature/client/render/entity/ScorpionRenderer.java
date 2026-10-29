package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.ScorpionRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.ScorpionModel;
import net.satisfy.wildernature.core.entity.animal.tameable.ScorpionEntity;
import org.jetbrains.annotations.NotNull;

public class ScorpionRenderer extends MobRenderer<ScorpionEntity, ScorpionRenderState, ScorpionModel<ScorpionRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/scorpion.png");
    private static final Identifier EYES_TEXTURE = WilderNature.identifier("textures/entity/scorpion_eyes.png");

    public ScorpionRenderer(EntityRendererProvider.Context context) {
        super(context, new ScorpionModel<>(context.bakeLayer(ScorpionModel.LAYER_LOCATION)), 0.35F);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public @NotNull RenderType renderType() {
                return net.minecraft.client.renderer.rendertype.RenderTypes.eyes(EYES_TEXTURE);
            }
        });
    }

    @Override
    public @NotNull Identifier getTextureLocation(ScorpionRenderState entity) {
        return TEXTURE;
    }

    @Override
    public ScorpionRenderState createRenderState() { return new ScorpionRenderState(); }

    @Override
    public void extractRenderState(ScorpionEntity entity, ScorpionRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackState.copyFrom(entity.attackState);
        state.burrowState.copyFrom(entity.burrowState);
        state.idleState.copyFrom(entity.idleState);
        state.getSitAnimValue = entity.getSitAnim(partialTick);
        state.isCalmedValue = entity.isCalmed();
        state.tickCount = entity.tickCount;
    }
}
