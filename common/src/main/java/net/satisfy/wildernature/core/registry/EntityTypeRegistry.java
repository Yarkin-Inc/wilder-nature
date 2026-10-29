package net.satisfy.wildernature.core.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.block.entity.*;
import net.satisfy.wildernature.core.entity.animal.defensive.*;
import net.satisfy.wildernature.core.entity.animal.neutral.*;
import net.satisfy.wildernature.core.entity.animal.passive.BeaverEntity;
import net.satisfy.wildernature.core.entity.animal.passive.BoarEntity;
import net.satisfy.wildernature.core.entity.animal.passive.HedgehogEntity;
import net.satisfy.wildernature.core.entity.animal.passive.TermiteEntity;
import net.satisfy.wildernature.core.entity.animal.tameable.DogEntity;
import net.satisfy.wildernature.core.entity.animal.tameable.ScorpionEntity;
import net.satisfy.wildernature.core.entity.fx.GlowingBlock;
import net.satisfy.wildernature.core.entity.projectile.BulletEntity;
import net.satisfy.wildernature.core.entity.projectile.ThrownBoneEntity;
import net.satisfy.wildernature.core.entity.projectile.ThrownTurkeyEgg;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(WilderNature.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(WilderNature.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> COMPLETIONIST_BANNER_BLOCK_ENTITY = createBlockEntity("completionist_banner", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(CompletionistBannerEntity::new, ObjectRegistry.FOX_TRAPPER_BANNER.get(), ObjectRegistry.FOX_TRAPPER_WALL_BANNER.get(), ObjectRegistry.BUNNY_STALKER_BANNER.get(), ObjectRegistry.BUNNY_STALKER_WALL_BANNER.get(), ObjectRegistry.COD_CATCHER_BANNER.get(), ObjectRegistry.COD_CATCHER_WALL_BANNER.get()).build());
    public static final RegistrySupplier<BlockEntityType<BountyBoardBlockEntity>> BOUNTY_BOARD_BLOCK_ENTITY = createBlockEntity("bounty_board", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(BountyBoardBlockEntity::new, ObjectRegistry.BOUNTY_BOARD.get()).build());
    public static final RegistrySupplier<BlockEntityType<HollowCacheBlockEntity>> HOLLOW_CACHE_BLOCK_ENTITY = createBlockEntity("hollow_cache", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(HollowCacheBlockEntity::new, ObjectRegistry.HOLLOW_CACHE.get()).build());
    public static final RegistrySupplier<BlockEntityType<BurrowBlockEntity>> BURROW_BLOCK_ENTITY = createBlockEntity("burrow", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(BurrowBlockEntity::new, ObjectRegistry.BURROW.get(), ObjectRegistry.TERMITE_MOUND_STORAGE.get()).build());
    public static final RegistrySupplier<BlockEntityType<TermiteMoundBlockEntity>> TERMITE_MOUND_BLOCK_ENTITY = createBlockEntity("termite_mound", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, ObjectRegistry.TERMITE_MOUND.get()).build());
    public static final RegistrySupplier<BlockEntityType<RottenLogBlockEntity>> ROTTEN_LOG_BLOCK_ENTITY = createBlockEntity("rotten_log", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(RottenLogBlockEntity::new, ObjectRegistry.ROTTEN_LOG.get()).build());

    public static final RegistrySupplier<EntityType<GlowingBlock>> GLOWING_BLOCK = createEntity("glowing_block", () -> EntityType.Builder.of(GlowingBlock::new, MobCategory.MISC).sized(1.0f, 1.0f).clientTrackingRange(64).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("glowing_block"))));
    public static final RegistrySupplier<EntityType<TermiteEntity>> TERMITE = createEntity("termite", () -> EntityType.Builder.of(TermiteEntity::new, MobCategory.CREATURE).sized(0.2f, 0.2f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("termite"))));
    public static final RegistrySupplier<EntityType<BeaverEntity>> BEAVER = createEntity("beaver", () -> EntityType.Builder.of(BeaverEntity::new, MobCategory.CREATURE).sized(0.6f, 0.5f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("beaver"))));
    public static final RegistrySupplier<EntityType<HippoEntity>> HIPPO = createEntity("hippo", () -> EntityType.Builder.of(HippoEntity::new, MobCategory.CREATURE).sized(2f, 2.4f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("hippo"))));
    public static final RegistrySupplier<EntityType<BisonEntity>> BISON = createEntity("bison", () -> EntityType.Builder.of(BisonEntity::new, MobCategory.CREATURE).sized(1.8f, 2.2f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("bison"))));
    public static final RegistrySupplier<EntityType<BoarEntity>> BOAR = createEntity("boar", () -> EntityType.Builder.of(BoarEntity::new, MobCategory.CREATURE).sized(1.1f, 1.1f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("boar"))));
    public static final RegistrySupplier<EntityType<CassowaryEntity>> CASSOWARY = createEntity("cassowary", () -> EntityType.Builder.of(CassowaryEntity::new, MobCategory.CREATURE).sized(0.8f, 0.8f).clientTrackingRange(10).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("cassowary"))));
    public static final RegistrySupplier<EntityType<DeerEntity>> DEER = createEntity("deer", () -> EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE).sized(1.3F, 1.6F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("deer"))));
    public static final RegistrySupplier<EntityType<DogEntity>> DOG = createEntity("dog", () -> EntityType.Builder.of(DogEntity::new, MobCategory.CREATURE).sized(0.9f, 1.3f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("dog"))));
    public static final RegistrySupplier<EntityType<HedgehogEntity>> HEDGEHOG = createEntity("hedgehog", () -> EntityType.Builder.of(HedgehogEntity::new, MobCategory.CREATURE).sized(0.3f, 0.3f).clientTrackingRange(10).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("hedgehog"))));
    public static final RegistrySupplier<EntityType<MiniSheepEntity>> MINISHEEP = createEntity("minisheep", () -> EntityType.Builder.of(MiniSheepEntity::new, MobCategory.CREATURE).sized(0.9f, 1.3f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("minisheep"))));
    public static final RegistrySupplier<EntityType<RaccoonEntity>> RACCOON = createEntity("raccoon", () -> EntityType.Builder.of(RaccoonEntity::new, MobCategory.CREATURE).sized(0.6f, 0.6f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("raccoon"))));
    public static final RegistrySupplier<EntityType<SwiftFoxEntity>> SWIFT_FOX = createEntity("swift_fox", () -> EntityType.Builder.of(SwiftFoxEntity::new, MobCategory.CREATURE).sized(0.7f, 0.9f).clientTrackingRange(10).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("swift_fox"))));
    public static final RegistrySupplier<EntityType<SquirrelEntity>> SQUIRREL = createEntity("squirrel", () -> EntityType.Builder.of(SquirrelEntity::new, MobCategory.CREATURE).sized(0.4f, 0.9f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("squirrel"))));
    public static final RegistrySupplier<EntityType<TurkeyEntity>> TURKEY = createEntity("turkey", () -> EntityType.Builder.of(TurkeyEntity::new, MobCategory.CREATURE).sized(0.6F, 1.0F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("turkey"))));
    public static final RegistrySupplier<EntityType<GiraffeEntity>> GIRAFFE = createEntity("giraffe", () -> EntityType.Builder.of(GiraffeEntity::new, MobCategory.CREATURE).sized(1.5F, 3.5F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("giraffe"))));
    public static final RegistrySupplier<EntityType<ElephantEntity>> ELEPHANT = createEntity("elephant", () -> EntityType.Builder.of(ElephantEntity::new, MobCategory.CREATURE).sized(2.5F, 3.5F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("elephant"))));
    public static final RegistrySupplier<EntityType<LionEntity>> LION = createEntity("lion", () -> EntityType.Builder.of(LionEntity::new, MobCategory.CREATURE).sized(1F, 1.2F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("lion"))));
    public static final RegistrySupplier<EntityType<ScorpionEntity>> SCORPION = createEntity("scorpion", () -> EntityType.Builder.of(ScorpionEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("scorpion"))));

    public static final RegistrySupplier<EntityType<BulletEntity>> BULLET = createEntity("bullet", () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f).clientTrackingRange(64).updateInterval(2).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("bullet"))));
    public static final RegistrySupplier<EntityType<ThrownTurkeyEgg>> TURKEY_EGG = createEntity("turkey_egg", () -> EntityType.Builder.<ThrownTurkeyEgg>of(ThrownTurkeyEgg::new, MobCategory.MISC).sized(0.25f, 0.25f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("turkey_egg"))));
    public static final RegistrySupplier<EntityType<ThrownBoneEntity>> BONE = createEntity("bone", () -> EntityType.Builder.<ThrownBoneEntity>of(ThrownBoneEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(net.minecraft.resources.ResourceKey.create(Registries.ENTITY_TYPE, WilderNature.identifier("bone"))));

    public static <T extends EntityType<?>> RegistrySupplier<T> createEntity(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.getRegistrar().register(WilderNature.identifier(path), type);
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> createBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITIES.register(WilderNature.identifier(path), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
        BLOCK_ENTITIES.register();
        EntityAttributeRegistry.register(SCORPION, ScorpionEntity::createMobAttributes);
        EntityAttributeRegistry.register(TERMITE, TermiteEntity::createMobAttributes);
        EntityAttributeRegistry.register(LION, LionEntity::createMobAttributes);
        EntityAttributeRegistry.register(BEAVER, BeaverEntity::createMobAttributes);
        EntityAttributeRegistry.register(ELEPHANT, ElephantEntity::createMobAttributes);
        EntityAttributeRegistry.register(HIPPO, HippoEntity::createMobAttributes);
        EntityAttributeRegistry.register(BISON, BisonEntity::createMobAttributes);
        EntityAttributeRegistry.register(BOAR, BoarEntity::createMobAttributes);
        EntityAttributeRegistry.register(CASSOWARY, CassowaryEntity::createMobAttributes);
        EntityAttributeRegistry.register(DEER, DeerEntity::createMobAttributes);
        EntityAttributeRegistry.register(DOG, DogEntity::createMobAttributes);
        EntityAttributeRegistry.register(HEDGEHOG, HedgehogEntity::createMobAttributes);
        EntityAttributeRegistry.register(MINISHEEP, MiniSheepEntity::createMobAttributes);
        EntityAttributeRegistry.register(RACCOON, RaccoonEntity::createMobAttributes);
        EntityAttributeRegistry.register(SWIFT_FOX, SwiftFoxEntity::createMobAttributes);
        EntityAttributeRegistry.register(SQUIRREL, SquirrelEntity::createMobAttributes);
        EntityAttributeRegistry.register(TURKEY, TurkeyEntity::createMobAttributes);
        EntityAttributeRegistry.register(GIRAFFE, GiraffeEntity::createMobAttributes);
    }
}