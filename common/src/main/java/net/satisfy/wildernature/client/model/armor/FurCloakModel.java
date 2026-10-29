package net.satisfy.wildernature.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.satisfy.wildernature.WilderNature;

public class FurCloakModel extends Model<FurCloakModel.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.parse("minecraft:player"), "wolf_fur_chestplate");
    public static final Identifier TEXTURE = WilderNature.identifier("textures/models/armor/fur_cloak.png");

    private final ModelPart chestplate;
    private final ModelPart cape;

    public FurCloakModel(ModelPart root) {
        super(root, net.minecraft.client.renderer.rendertype.RenderTypes::entityCutout);
        this.chestplate = root.getChild("chestplate");
        this.cape = this.chestplate.getChild("cape");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition rootPart = meshDefinition.getRoot();

        PartDefinition chestplatePart = rootPart.addOrReplaceChild("chestplate",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-17.0F, -3.5F, -1.0F, 18.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 22).addBox(-12.0F, -4.5F, 5.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 25).addBox(-11.0F, -0.5F, 5.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.0F, 2.5F, -2.0F));

        chestplatePart.addOrReplaceChild("cape",
                CubeListBuilder.create().texOffs(0, 10).addBox(-6.0F, 0.5F, 0.5F, 12.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-8.0F, -0.5F, 3.5F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public record State(PartPose body, boolean crouching, float flap, float lean, float side) {}

    @Override
    public void setupAnim(State state) {
        resetPose();
        chestplate.xRot = state.body.xRot();
        chestplate.yRot = state.body.yRot();
        chestplate.zRot = state.body.zRot();
        chestplate.x = 8.0F;
        chestplate.y = state.crouching ? 5.2F : 2.5F;
        chestplate.z = state.crouching ? -1.35F : -2.0F;
        cape.xRot = (6.0F + state.lean / 2.0F + state.flap) * Mth.DEG_TO_RAD;
        cape.yRot = -state.side / 2.0F * Mth.DEG_TO_RAD;
        cape.zRot = state.side / 2.0F * Mth.DEG_TO_RAD;
    }
}
