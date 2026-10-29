package net.satisfy.wildernature.core.bounty;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public record BountyDefinition(UUID id, BountyType type, BountyTargetType targetType, BountyCategory category, Identifier targetId, int requiredAmount, BountyReward reward, boolean guildCommission) {
    public CompoundTag save() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.store("id", net.minecraft.core.UUIDUtil.CODEC, this.id);
        compoundTag.putString("type", this.type.getSerializedName());
        compoundTag.putString("target_type", this.targetType.getSerializedName());
        compoundTag.putString("category", this.category.getName());
        compoundTag.putString("target_id", this.targetId.toString());
        compoundTag.putInt("required_amount", this.requiredAmount);
        compoundTag.put("reward", this.reward.save());
        compoundTag.putBoolean("guild_commission", this.guildCommission);
        return compoundTag;
    }

    public static BountyDefinition load(CompoundTag compoundTag) {
        return new BountyDefinition(
                compoundTag.read("id", net.minecraft.core.UUIDUtil.CODEC).orElseThrow(),
                BountyType.byName(compoundTag.getStringOr("type", "")),
                BountyTargetType.byName(compoundTag.getStringOr("target_type", "")),
                BountyCategory.byName(compoundTag.getStringOr("category", "")),
                Identifier.parse(compoundTag.getStringOr("target_id", "")),
                compoundTag.getIntOr("required_amount", 0),
                BountyReward.load(compoundTag.getCompoundOrEmpty("reward")),
                compoundTag.getBooleanOr("guild_commission", false)
        );
    }

    public boolean isGuildCommission() {
        return this.guildCommission;
    }

    public enum BountyType {
        HUNT("hunt"),
        GATHER("gather"),
        OBSERVE("observe"),
        EXPLORE("explore");

        private final String serializedName;

        BountyType(String serializedName) {
            this.serializedName = serializedName;
        }

        public String getSerializedName() {
            return this.serializedName;
        }

        public static BountyType byName(String serializedName) {
            for (BountyType bountyType : values()) {
                if (bountyType.serializedName.equals(serializedName)) {
                    return bountyType;
                }
            }

            return HUNT;
        }
    }

    public enum BountyTargetType {
        ENTITY("entity"),
        ITEM("item"),
        BIOME("biome");

        private final String serializedName;

        BountyTargetType(String serializedName) {
            this.serializedName = serializedName;
        }

        public String getSerializedName() {
            return this.serializedName;
        }

        public static BountyTargetType byName(String serializedName) {
            for (BountyTargetType bountyTargetType : values()) {
                if (bountyTargetType.serializedName.equals(serializedName)) {
                    return bountyTargetType;
                }
            }

            return ENTITY;
        }
    }
}