package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.ElephantRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.satisfy.wildernature.client.model.entity.model.AnimatedAnimalModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BabyElephantModel;
import net.satisfy.wildernature.client.model.entity.model.ElephantModel;
import net.satisfy.wildernature.core.entity.animal.defensive.ElephantEntity;
import org.jetbrains.annotations.NotNull;

public class ElephantRenderer extends MobRenderer<ElephantEntity, ElephantRenderState, AnimatedAnimalModel<ElephantRenderState>> {
    private static final Identifier ADULT_TEXTURE = WilderNature.identifier("textures/entity/elephant.png");
    private static final Identifier BABY_TEXTURE = WilderNature.identifier("textures/entity/baby_elephant.png");

    private final ElephantModel<ElephantRenderState> adultModel;
    private final BabyElephantModel<ElephantRenderState> babyModel;

    public ElephantRenderer(EntityRendererProvider.Context context) {
        super(context, new ElephantModel<>(context.bakeLayer(ElephantModel.LAYER_LOCATION)), 1.2F);
        this.adultModel = new ElephantModel<>(context.bakeLayer(ElephantModel.LAYER_LOCATION));
        this.babyModel = new BabyElephantModel<>(context.bakeLayer(BabyElephantModel.LAYER_LOCATION));
        this.model = this.adultModel;
    }

    @Override
    public @NotNull Identifier getTextureLocation(ElephantRenderState entity) {
        return entity.isBaby() ? BABY_TEXTURE : ADULT_TEXTURE;
    }

    @Override
    public void submit(ElephantRenderState entity, PoseStack poseStack, SubmitNodeCollector multiBufferSource, CameraRenderState cameraState) {
        if (entity.isBaby()) {
            this.model = this.babyModel;
        } else {
            this.model = this.adultModel;
        }

        super.submit(entity, poseStack, multiBufferSource, cameraState);
    }

    @Override
    protected float getShadowRadius(ElephantRenderState state) { return state.isBaby ? 1F : 1.5F; }

    @Override
    public ElephantRenderState createRenderState() { return new ElephantRenderState(); }

    @Override
    public void extractRenderState(ElephantEntity entity, ElephantRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.chargeState.copyFrom(entity.chargeState);
        state.drinkState.copyFrom(entity.drinkState);
        state.idleState.copyFrom(entity.idleState);
        state.stampState.copyFrom(entity.stampState);
        state.throwState.copyFrom(entity.throwState);
        state.trumpetState.copyFrom(entity.trumpetState);
        state.getDeltaMovementValue = entity.getDeltaMovement();
        state.isChargingValue = entity.isCharging();
        state.isDrinkingValue = entity.isDrinking();
        state.isStampingValue = entity.isStamping();
        state.isThrowingValue = entity.isThrowing();
        state.isTrumpetingValue = entity.isTrumpeting();
    }
}
