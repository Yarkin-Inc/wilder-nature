package net.satisfy.wildernature.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContractItem extends Item {
    public ContractItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return super.getName(itemStack);
        }

        CompoundTag customDataTag = customData.copyTag();
        if (!customDataTag.contains("BountyType") || !customDataTag.contains("TargetType") || !customDataTag.contains("TargetId")) {
            return super.getName(itemStack);
        }

        String bountyType = customDataTag.getStringOr("BountyType", "");
        return Component.translatable("gui.wildernature.bounty_board.title." + bountyType);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return;
        }

        CompoundTag customDataTag = customData.copyTag();
        if (!customDataTag.contains("BountyType") || !customDataTag.contains("TargetType") || !customDataTag.contains("TargetId")) {
            return;
        }

        Component targetName = this.getTargetName(customDataTag);
        String bountyType = customDataTag.getStringOr("BountyType", "");
        int requiredAmount = customDataTag.contains("RequiredAmount") ? customDataTag.getIntOr("RequiredAmount", 0) : 0;
        int currentProgress = customDataTag.contains("Progress") ? customDataTag.getIntOr("Progress", 0) : 0;
        boolean isCompleted = currentProgress >= requiredAmount && requiredAmount > 0;

        if (isCompleted) {
            tooltipComponents.accept(Component.translatable("item.wildernature.contract.completed").copy().withStyle(ChatFormatting.GRAY));
            tooltipComponents.accept(Component.translatable("item.wildernature.contract.turn_in").copy().withStyle(ChatFormatting.GREEN));
            return;
        }

        int previewCount = customDataTag.contains("PreviewCount") ? customDataTag.getIntOr("PreviewCount", 0) : 1;
        int experienceReward = customDataTag.contains("ExperienceReward") ? customDataTag.getIntOr("ExperienceReward", 0) : 0;

        tooltipComponents.accept(this.getObjectiveComponent(bountyType, requiredAmount, targetName).copy().withStyle(ChatFormatting.GRAY));
        tooltipComponents.accept(
                Component.translatable("gui.wildernature.bounty_board.progress")
                        .copy()
                        .append(Component.literal(": "))
                        .append(Component.literal(currentProgress + "/" + requiredAmount))
                        .withStyle(ChatFormatting.GRAY)
        );

        if (customDataTag.contains("PreviewItemId")) {
            Identifier previewItemId = Identifier.parse(customDataTag.getStringOr("PreviewItemId", ""));
            Item previewItem = BuiltInRegistries.ITEM.getValue(previewItemId);
            ItemStack rewardStack = new ItemStack(previewItem);
            Component rewardName = rewardStack.getHoverName();
            Component rewardLine = previewCount > 1 ? Component.literal(previewCount + "x ").append(rewardName) : rewardName;

            tooltipComponents.accept(
                    Component.translatable("gui.wildernature.bounty_board.reward")
                            .copy()
                            .append(Component.literal(": "))
                            .append(rewardLine)
                            .withStyle(ChatFormatting.GRAY)
            );
        }

        if (experienceReward > 0) {
            tooltipComponents.accept(
                    Component.translatable("gui.wildernature.bounty_board.experience")
                            .copy()
                            .append(Component.literal(": "))
                            .append(Component.literal(String.valueOf(experienceReward)))
                            .withStyle(ChatFormatting.GRAY)
            );
        }
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        return InteractionResult.PASS; //player.getItemInHand(hand));
    }

    private Component getObjectiveComponent(String bountyType, int requiredAmount, Component targetName) {
        return switch (bountyType) {
            case "hunt" -> requiredAmount == 1
                    ? Component.translatable("gui.wildernature.bounty_board.objective.hunt.single", targetName)
                    : Component.translatable("gui.wildernature.bounty_board.objective.hunt.plural", requiredAmount, targetName);
            case "gather" -> Component.translatable("gui.wildernature.bounty_board.objective.gather", targetName);
            case "observe" -> Component.translatable("gui.wildernature.bounty_board.objective.observe", targetName);
            case "explore" -> Component.translatable("gui.wildernature.bounty_board.objective.explore", targetName);
            default -> Component.empty();
        };
    }

    private Component getTargetName(CompoundTag customDataTag) {
        String targetType = customDataTag.getStringOr("TargetType", "");
        Identifier targetId = Identifier.parse(customDataTag.getStringOr("TargetId", ""));

        return switch (targetType) {
            case "entity" -> {
                if (BuiltInRegistries.ENTITY_TYPE.containsKey(targetId)) {
                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(targetId);
                    yield entityType.getDescription();
                }
                yield Component.literal(targetId.getPath());
            }
            case "item" -> new ItemStack(BuiltInRegistries.ITEM.getValue(targetId)).getHoverName();
            case "biome" -> Component.translatable("biome." + targetId.getNamespace() + "." + targetId.getPath());
            default -> Component.literal(targetId.getPath());
        };
    }
}