package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.CassowaryRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.satisfy.wildernature.client.model.entity.model.AnimatedAnimalModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BabyCassowaryModel;
import net.satisfy.wildernature.client.model.entity.model.CassowaryModel;
import net.satisfy.wildernature.core.entity.animal.defensive.CassowaryEntity;
import org.jetbrains.annotations.NotNull;

public class CassowaryRenderer extends MobRenderer<CassowaryEntity, CassowaryRenderState, AnimatedAnimalModel<CassowaryRenderState>> {
    private static final Identifier ADULT_TEXTURE = WilderNature.identifier("textures/entity/cassowary.png");
    private static final Identifier BABY_TEXTURE = WilderNature.identifier("textures/entity/baby_cassowary.png");

    private final CassowaryModel<CassowaryRenderState> adultModel;
    private final BabyCassowaryModel<CassowaryRenderState> babyModel;

    public CassowaryRenderer(EntityRendererProvider.Context context) {
        super(context, new CassowaryModel<>(context.bakeLayer(CassowaryModel.LAYER_LOCATION)), 0.7f);
        this.adultModel = new CassowaryModel<>(context.bakeLayer(CassowaryModel.LAYER_LOCATION));
        this.babyModel = new BabyCassowaryModel<>(context.bakeLayer(BabyCassowaryModel.LAYER_LOCATION));
        this.model = this.adultModel;
    }

    @Override
    public @NotNull Identifier getTextureLocation(CassowaryRenderState entity) {
        return entity.isBaby() ? BABY_TEXTURE : ADULT_TEXTURE;
    }

    @Override
    public void submit(CassowaryRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        if (entity.isBaby()) {
            this.model = this.babyModel;
        } else {
            this.model = this.adultModel;
        }

        super.submit(entity, poseStack, buffer, cameraState);
    }

    @Override
    protected float getShadowRadius(CassowaryRenderState state) { return state.isBaby ? 0.3F : 0.7F; }

    @Override
    public CassowaryRenderState createRenderState() { return new CassowaryRenderState(); }

    @Override
    public void extractRenderState(CassowaryEntity entity, CassowaryRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.alertAnimationState.copyFrom(entity.alertAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.threateningAnimationState.copyFrom(entity.threateningAnimationState);
        state.isAlertValue = entity.isAlert();
        state.isAttackingValue = entity.isAttacking();
        state.isThreateningValue = entity.isThreatening();
    }
}
