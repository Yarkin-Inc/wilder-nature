package net.satisfy.wildernature.client.render.entity;

import net.satisfy.wildernature.client.render.state.GiraffeRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.client.model.entity.model.BabyGiraffeModel;
import net.satisfy.wildernature.client.model.entity.model.GiraffeModel;
import net.satisfy.wildernature.core.entity.animal.neutral.GiraffeEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GiraffeRenderer extends MobRenderer<GiraffeEntity, GiraffeRenderState, EntityModel<GiraffeRenderState>> {
    private static final Identifier TEXTURE = WilderNature.identifier("textures/entity/giraffe.png");
    private static final Identifier BABY_TEXTURE = WilderNature.identifier("textures/entity/baby_giraffe.png");
    private static final Identifier COWSTICKER_TEXTURE = WilderNature.identifier("textures/entity/giraffe_cowsticker.png");

    private final GiraffeModel<GiraffeRenderState> adultModel;
    private final BabyGiraffeModel<GiraffeRenderState> babyModel;

    public GiraffeRenderer(EntityRendererProvider.Context context) {
        super(context, new GiraffeModel<>(context.bakeLayer(GiraffeModel.LAYER_LOCATION)), 1.0F);
        this.adultModel = new GiraffeModel<>(context.bakeLayer(GiraffeModel.LAYER_LOCATION));
        this.babyModel = new BabyGiraffeModel<>(context.bakeLayer(BabyGiraffeModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull Identifier getTextureLocation(GiraffeRenderState entity) {
        if (entity.hasCustomName()) {
            Component nameComponent = entity.getCustomName();
            if (nameComponent != null && "cowsticker".equalsIgnoreCase(nameComponent.getString())) {
                return COWSTICKER_TEXTURE;
            }
        }

        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    public void submit(GiraffeRenderState entity, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState cameraState) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;

        poseStack.pushPose();

        if (entity.isBaby()) {
            if (entity.isSleeping()) {
                poseStack.translate(0.0D, 1.2D, 0.0D);
            }
        } else {
            poseStack.translate(0.0D, 1.4D, 0.0D);
        }

        super.submit(entity, poseStack, buffer, cameraState);
        poseStack.popPose();
    }

    @Override
    public GiraffeRenderState createRenderState() { return new GiraffeRenderState(); }

    @Override
    public void extractRenderState(GiraffeEntity entity, GiraffeRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.alertState.copyFrom(entity.alertState);
        state.eatingState.copyFrom(entity.eatingState);
        state.idleState.copyFrom(entity.idleState);
        state.sleepState.copyFrom(entity.sleepState);
        state.getCustomNameValue = entity.getCustomName();
        state.hasCustomNameValue = entity.hasCustomName();
        state.isRidingBurstValue = entity.isRidingBurst();
        state.isSleepingValue = entity.isSleeping();
    }
}
