package net.satisfy.wildernature.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.world.feature.HollowCacheFeature;
import net.satisfy.wildernature.core.world.placement.ConfigRarityFilter;
import net.satisfy.wildernature.core.world.tree.decorator.TermiteMoundDecorator;

public class WorldgenRegistry {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(WilderNature.MOD_ID, Registries.TREE_DECORATOR_TYPE);
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURES = DeferredRegister.create(WilderNature.MOD_ID, Registries.FEATURE_TYPE);
    public static final DeferredRegister<MapCodec<? extends PlacementModifier>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(WilderNature.MOD_ID, Registries.PLACEMENT_MODIFIER_TYPE);

    public static final RegistrySupplier<TreeDecoratorType<TermiteMoundDecorator>> TERMITE_MOUND_DECORATOR = TREE_DECORATOR_TYPES.register("termite_mound_decorator", () -> new TreeDecoratorType<>(TermiteMoundDecorator.CODEC));
    public static final RegistrySupplier<MapCodec<? extends Feature>> HOLLOW_CACHE = FEATURES.register("hollow_cache", () -> HollowCacheFeature.CODEC);
    public static final RegistrySupplier<MapCodec<? extends PlacementModifier>> CONFIG_RARITY_FILTER = PLACEMENT_MODIFIER_TYPES.register("config_rarity_filter", () -> ConfigRarityFilter.CODEC);

    public static void init() {
        TREE_DECORATOR_TYPES.register();
        FEATURES.register();
        PLACEMENT_MODIFIER_TYPES.register();
    }
}