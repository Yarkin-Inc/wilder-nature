package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.LionRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.satisfy.wildernature.client.model.entity.model.AnimatedAnimalModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BabyLionModel;
import net.satisfy.wildernature.client.model.entity.model.LionModel;
import net.satisfy.wildernature.core.entity.animal.defensive.LionEntity;
import org.jetbrains.annotations.NotNull;

public class LionRenderer extends MobRenderer<LionEntity, LionRenderState, AnimatedAnimalModel<LionRenderState>> {
    private static final Identifier ADULT_TEXTURE = WilderNature.identifier("textures/entity/lion.png");
    private static final Identifier BABY_TEXTURE = WilderNature.identifier("textures/entity/baby_lion.png");
    private static final Identifier ADULT_SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/lion_sleep.png");
    private static final Identifier BABY_SLEEPING_TEXTURE = WilderNature.identifier("textures/entity/baby_lion_sleep.png");

    private final LionModel<LionRenderState> adultModel;
    private final BabyLionModel<LionRenderState> babyModel;

    public LionRenderer(EntityRendererProvider.Context context) {
        super(context, new LionModel<>(context.bakeLayer(LionModel.LAYER_LOCATION)), 0.9F);
        this.adultModel = new LionModel<>(context.bakeLayer(LionModel.LAYER_LOCATION));
        this.babyModel = new BabyLionModel<>(context.bakeLayer(BabyLionModel.LAYER_LOCATION));
        this.model = this.adultModel;
    }

    @Override
    public @NotNull Identifier getTextureLocation(LionRenderState entity) {
        if (entity.isBaby()) {
            return entity.isSleeping() ? BABY_SLEEPING_TEXTURE : BABY_TEXTURE;
        }
        return entity.isSleeping() ? ADULT_SLEEPING_TEXTURE : ADULT_TEXTURE;
    }

    @Override
    protected void setupRotations(LionRenderState entity, PoseStack poseStack, float yBodyRot, float scale) {
        super.setupRotations(entity, poseStack, yBodyRot, scale);

        if (entity.isSleeping()) {
            if (entity.isBaby()) {
                poseStack.translate(0.0F, -0.15F, 0.0F);
                poseStack.rotate(Axis.ZN.rotationDegrees(22.5F));
            } else {
                poseStack.translate(0.0F, -0.3F, 0.0F);
                poseStack.rotate(Axis.ZN.rotationDegrees(45.0F));
            }
        }
    }

    @Override
    public void submit(LionRenderState entity, PoseStack poseStack, SubmitNodeCollector multiBufferSource, CameraRenderState cameraState) {
        if (entity.isBaby()) {
            this.model = this.babyModel;
        } else {
            this.model = this.adultModel;
        }

        super.submit(entity, poseStack, multiBufferSource, cameraState);
    }

    @Override
    protected float getShadowRadius(LionRenderState state) { return state.isBaby ? 0.45F : 0.9F; }

    @Override
    public LionRenderState createRenderState() { return new LionRenderState(); }

    @Override
    public void extractRenderState(LionEntity entity, LionRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.idleState.copyFrom(entity.idleState);
        state.pounceState.copyFrom(entity.pounceState);
        state.roarState.copyFrom(entity.roarState);
        state.sleepState.copyFrom(entity.sleepState);
        state.warnState.copyFrom(entity.warnState);
        state.isMaleValue = entity.isMale();
        state.isPouncingValue = entity.isPouncing();
        state.isSleepingValue = entity.isSleeping();
        state.isStalkingValue = entity.isStalking();
        state.isWarningValue = entity.isWarning();
    }
}
