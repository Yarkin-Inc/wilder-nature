package net.satisfy.wildernature.core.registry;

import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.satisfy.wildernature.WilderNature;

public final class ArmorMaterialRegistry {
    public static final ArmorMaterial STYLIN_HAT = new ArmorMaterial(5,
        Map.of(ArmorType.HELMET, 1, ArmorType.CHESTPLATE, 0, ArmorType.LEGGINGS, 0, ArmorType.BOOTS, 0, ArmorType.BODY, 0),
        0, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0,
        TagKey.create(Registries.ITEM, WilderNature.identifier("repairs_stylin_hat")),
        ResourceKey.create(EquipmentAssets.ROOT_ID, WilderNature.identifier("stylin_purple_hat")));
}
