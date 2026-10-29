package net.satisfy.wildernature.core.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import org.jetbrains.annotations.NotNull;

public class StylinPurpleHatItem extends Item {
    private final Identifier hatTexture;

    public StylinPurpleHatItem(ArmorMaterial armorMaterial, ArmorType type, Properties properties, Identifier hatTexture) {
        super(properties.durability(type.getDurability(armorMaterial.durability()))
                .attributes(armorMaterial.createAttributes(type))
                .component(net.minecraft.core.component.DataComponents.EQUIPPABLE,
                        net.minecraft.world.item.equipment.Equippable.builder(type.getSlot())
                                .setEquipSound(armorMaterial.equipSound()).build())
                .repairable(armorMaterial.repairIngredient()));
        this.hatTexture = hatTexture;
    }

    public Identifier getHatTexture() {
        return hatTexture;
    }

    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
