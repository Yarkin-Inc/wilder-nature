package net.satisfy.wildernature.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.satisfy.wildernature.WilderNature;

public class HollowCacheModel extends Model<net.satisfy.wildernature.client.render.block.HollowCacheRenderer.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(WilderNature.identifier("hollow_cache"), "main");
    public final ModelPart hollowCache;

    public HollowCacheModel(ModelPart root) {
        super(root, net.minecraft.client.renderer.rendertype.RenderTypes::entityCutout);
        this.hollowCache = root.getChild("hollowcache");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild("hollowcache", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -12.0F, -12.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 6.0F));

        return LayerDefinition.create(meshDefinition, 48, 48);
    }




    @Override
    public void setupAnim(net.satisfy.wildernature.client.render.block.HollowCacheRenderer.State state) {
        resetPose();
        float eased = 1.0F - (float) Math.pow(1.0F - state.openProgress, 3);
        hollowCache.xRot = -eased * ((float) Math.PI / 3.0F);
    }
}
