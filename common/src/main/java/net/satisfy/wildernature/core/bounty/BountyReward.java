package net.satisfy.wildernature.core.bounty;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

public record BountyReward(Identifier lootTableId, int experienceReward, Identifier previewItemId, int previewCount) {
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("LootTable", this.lootTableId.toString());
        tag.putInt("Experience", this.experienceReward);
        tag.putString("PreviewItem", this.previewItemId.toString());
        tag.putInt("PreviewCount", this.previewCount);
        return tag;
    }

    public static BountyReward load(CompoundTag tag) {
        Identifier lootTableId = Identifier.parse(tag.getStringOr("LootTable", ""));
        int experienceReward = tag.getIntOr("Experience", 0);
        Identifier previewItemId = tag.contains("PreviewItem") ? Identifier.parse(tag.getStringOr("PreviewItem", "")) : Identifier.withDefaultNamespace("paper");
        int previewCount = tag.contains("PreviewCount") ? tag.getIntOr("PreviewCount", 0) : 1;
        return new BountyReward(lootTableId, experienceReward, previewItemId, previewCount);
    }
}