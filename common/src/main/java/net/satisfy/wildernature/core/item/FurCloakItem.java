package net.satisfy.wildernature.core.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.satisfy.foundation.armor.Wearing;
import net.satisfy.wildernature.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class FurCloakItem extends Item {

    protected final ArmorType type;

    public FurCloakItem(ArmorMaterial pMaterial, ArmorType pType, Properties pProperties) {
        super(pProperties.equippable(EquipmentSlot.CHEST).durability(pMaterial.defense().get(pType)));
        this.type = pType;
    }

    public static boolean isVisibleOn(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof FurCloakItem) {
            return true;
        }
        return chest.isEmpty() && Wearing.isWearing(entity, ObjectRegistry.FUR_CLOAK.get());
    }

    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }
}
