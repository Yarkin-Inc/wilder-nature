package net.satisfy.wildernature.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.wildernature.core.registry.RecipeRegistry;
import net.satisfy.wildernature.core.util.WilderNatureUtil;
import org.jetbrains.annotations.NotNull;

public class TrufflingRecipe extends CustomRecipe {
    private final String group;
    private final java.util.List<Ingredient> ingredients;

    public TrufflingRecipe(String group, java.util.List<Ingredient> ingredients) {
        super();
        this.group = group;
        this.ingredients = java.util.List.copyOf(ingredients);
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
        return NonNullList.withSize(recipeInput.items().size(), ItemStack.EMPTY);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        boolean hasFoodInput = false;
        NonNullList<Boolean> matches = NonNullList.withSize(this.ingredients.size(), false);
        int itemsCount = 0;

        for (int slotIndex = 0; slotIndex < recipeInput.items().size(); slotIndex++) {
            ItemStack stackInSlot = recipeInput.getItem(slotIndex);
            if (stackInSlot.isEmpty())
                continue;

            itemsCount++;

            if ((stackInSlot.has(DataComponents.FOOD)) && !hasFoodInput && !WilderNatureUtil.Truffling.isTruffled(stackInSlot))
                hasFoodInput = true;

            for (int ingredientIndex = 0; ingredientIndex < this.ingredients.size(); ingredientIndex++) {
                if (this.ingredients.get(ingredientIndex).test(stackInSlot) && !matches.get(ingredientIndex))
                    matches.set(ingredientIndex, true);
            }
        }

        return hasFoodInput && matches.stream().allMatch(match -> match) && itemsCount == this.ingredients.size() + 1;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput) {
        for (int index = 0; index < recipeInput.items().size(); index++) {
            ItemStack itemStack = recipeInput.getItem(index);

            if (itemStack.has(DataComponents.FOOD)) {
                ItemStack resultStack = itemStack.copy();
                resultStack.setCount(1);

                return WilderNatureUtil.Truffling.setTruffled(resultStack);
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<TrufflingRecipe> getSerializer() {
        return RecipeRegistry.TRUFFLING.get();
    }

    public @NotNull java.util.List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static final MapCodec<TrufflingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
        Ingredient.CODEC.listOf(1, 8).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
    ).apply(instance, TrufflingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TrufflingRecipe> STREAM_CODEC = StreamCodec.of(
        (buffer, recipe) -> {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.ingredients.size());
            recipe.ingredients.forEach(ingredient -> Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient));
        }, buffer -> {
            String group = buffer.readUtf();
            int count = buffer.readVarInt();
            if (count < 1 || count > 8) throw new IllegalArgumentException("Invalid truffling ingredient count");
            var ingredients = new java.util.ArrayList<Ingredient>(count);
            for (int i = 0; i < count; i++) ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            return new TrufflingRecipe(group, ingredients);
        });
}
