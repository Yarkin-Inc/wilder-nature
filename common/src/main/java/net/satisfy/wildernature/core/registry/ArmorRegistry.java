package net.satisfy.wildernature.core.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;
import net.satisfy.wildernature.client.model.armor.StylinPurpleHatModel;

import java.util.HashMap;
import java.util.Map;

public class ArmorRegistry {
    private static final Map<Item, StylinPurpleHatModel> models = new HashMap<>();

    public static StylinPurpleHatModel getHatModel(Item item) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        StylinPurpleHatModel model = models.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.STYLIN_PURPLE_HAT.get()) {
                return new StylinPurpleHatModel(modelSet.bakeLayer(StylinPurpleHatModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;


        return model;
    }
}
