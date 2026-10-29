package net.satisfy.wildernature.core.registry;

import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.block.*;
import net.satisfy.foundation.block.FacingBlock;
import net.satisfy.wildernature.core.item.*;
import net.satisfy.foundation.util.RegistryUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(WilderNature.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(WilderNature.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Block> HAZELNUT_BUSH = registerWithoutItem("hazelnut_bush", () -> new HazelnutBushBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("hazelnut_bush"))).mapColor(MapColor.PLANT).randomTicks().noCollision().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.POPPED)));
    public static final RegistrySupplier<Block> BOUNTY_BOARD = registerWithItem("bounty_board", () -> new BountyBoardBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("bounty_board")))));
    public static final RegistrySupplier<Block> DEER_TROPHY = registerWithItem("deer_trophy", () -> new DeerTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("deer_trophy")))));
    public static final RegistrySupplier<Block> SWIFT_FOX_TROPHY = registerWithItem("swift_fox_trophy", () -> new RedWolfTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("swift_fox_trophy")))));
    public static final RegistrySupplier<Block> BISON_TROPHY = registerWithItem("bison_trophy", () -> new BisonTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("bison_trophy")))));
    public static final RegistrySupplier<Block> FOX_TRAPPER_BANNER = registerWithItem("fox_trapper_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("fox_trapper_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FOX_TRAPPER_WALL_BANNER = registerWithoutItem("fox_trapper_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("fox_trapper_wall_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BUNNY_STALKER_BANNER = registerWithItem("bunny_stalker_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("bunny_stalker_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BUNNY_STALKER_WALL_BANNER = registerWithoutItem("bunny_stalker_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("bunny_stalker_wall_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> COD_CATCHER_BANNER = registerWithItem("cod_catcher_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("cod_catcher_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> COD_CATCHER_WALL_BANNER = registerWithoutItem("cod_catcher_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("cod_catcher_wall_banner"))).strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TRUFFLE_BAG = registerWithItem("truffle_bag", () -> new FacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.black()).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("truffle_bag")))));
    public static final RegistrySupplier<Block> HOLLOW_CACHE = registerWithItem("hollow_cache", () -> new HollowCacheBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("hollow_cache"))).mapColor(MapColor.WOOD).strength(2.5F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BURROW = registerWithItem("burrow", () -> new BurrowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("burrow")))));
    public static final RegistrySupplier<Block> TERMITE_MOUND_STORAGE = registerWithItem("termite_mound_storage", () -> new BurrowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("termite_mound_storage")))));
    public static final RegistrySupplier<Block> BEAVER_DAM = registerWithItem("beaver_dam", () -> new BeaverDamBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("beaver_dam"))).strength(0.3F).sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.POPPED).instabreak()));
    public static final RegistrySupplier<Block> BROWN_MUSHROOM_COLONY = registerWithItem("brown_mushroom_colony", () -> new MushroomColonyBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("brown_mushroom_colony"))).mapColor(MapColor.PLANT).randomTicks().noCollision().sound(SoundType.CROP).pushReaction(PushReaction.POPPED), random -> List.of(new ItemStack(Items.BROWN_MUSHROOM, 2 + random.nextInt(3)))));
    public static final RegistrySupplier<Block> RED_MUSHROOM_COLONY = registerWithItem("red_mushroom_colony", () -> new MushroomColonyBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("red_mushroom_colony"))).mapColor(MapColor.PLANT).randomTicks().noCollision().sound(SoundType.CROP).pushReaction(PushReaction.POPPED), random -> List.of(new ItemStack(Items.RED_MUSHROOM, 2 + random.nextInt(3)))));
    public static final RegistrySupplier<Block> ROTTEN_LOG = registerWithItem("rotten_log", () -> new RottenLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("rotten_log")))));
    public static final RegistrySupplier<Block> TERMITE_MOUND = registerWithItem("termite_mound", () -> new TermiteMoundBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, WilderNature.identifier("termite_mound")))));
    public static final RegistrySupplier<Item> BISON_MEAT = registerItem("bison_meat", () -> new Item(getSettings("bison_meat").food(Foods.BEEF)));
    public static final RegistrySupplier<Item> COOKED_BISON_MEAT = registerItem("cooked_bison_meat", () -> new Item(getSettings("cooked_bison_meat").food(Foods.COOKED_BEEF)));
    public static final RegistrySupplier<Item> VENISON = registerItem("venison", () -> new Item(getSettings("venison").food(Foods.MUTTON)));
    public static final RegistrySupplier<Item> COOKED_VENISON = registerItem("cooked_venison", () -> new Item(getSettings("cooked_venison").food(Foods.COOKED_MUTTON)));
    public static final RegistrySupplier<Item> TURKEY_MEAT = registerItem("turkey_meat", () -> new Item(getSettings("turkey_meat").food(Foods.CHICKEN)));
    public static final RegistrySupplier<Item> COOKED_TURKEY_MEAT = registerItem("cooked_turkey_meat", () -> new Item(getSettings("cooked_turkey_meat").food(Foods.COOKED_CHICKEN)));
    public static final RegistrySupplier<Item> CASSOWARY_MEAT = registerItem("cassowary_meat", () -> new Item(getSettings("cassowary_meat").food(Foods.RABBIT)));
    public static final RegistrySupplier<Item> COOKED_CASSOWARY_MEAT = registerItem("cooked_cassowary_meat", () -> new Item(getSettings("cooked_cassowary_meat").food(Foods.COOKED_RABBIT)));
    public static final RegistrySupplier<Item> HAZELNUT = registerItem("hazelnut", () -> new BlockItem(ObjectRegistry.HAZELNUT_BUSH.get(), getSettings("hazelnut").food((new FoodProperties.Builder()).nutrition(4).saturationModifier(0.3F).build())));
    public static final RegistrySupplier<Item> TRUFFLE = registerItem("truffle", () -> new Item(getSettings("truffle")));
    public static final RegistrySupplier<Item> TURKEY_EGG = registerItem("turkey_egg", () -> new TurkeyEggItem(getSettings("turkey_egg")));
    public static final RegistrySupplier<Item> BLUNDERBUSS = registerItem("blunderbuss", () -> new BlunderBussItem(getSettings("blunderbuss")));
    public static final RegistrySupplier<Item> FLINT_AMMUNITION = registerItem("flint_ammunition", () -> new FlintAmmunitionItem(getSettings("flint_ammunition"), 2));
    public static final RegistrySupplier<Item> DIAMOND_AMMUNITION = registerItem("diamond_ammunition", () -> new AmmunitionItem(getSettings("diamond_ammunition"), 12));
    public static final RegistrySupplier<Item> FUR_CLOAK = registerItem("fur_cloak", () -> new FurCloakItem(ArmorMaterials.LEATHER, ArmorType.CHESTPLATE, getSettings("fur_cloak").stacksTo(1)));
    public static final RegistrySupplier<Item> STYLIN_PURPLE_HAT = registerItem("stylin_purple_hat", () -> new StylinPurpleHatItem(ArmorMaterialRegistry.STYLIN_HAT, ArmorType.HELMET, getSettings("stylin_purple_hat").stacksTo(1), WilderNature.identifier("textures/models/armor/stylin_purple_hat.png")));
    public static final RegistrySupplier<Item> FISH_OIL = registerItem("fish_oil", () -> new Item(getSettings("fish_oil").component(net.minecraft.core.component.DataComponents.COOKING_FUEL, new net.minecraft.world.item.component.CookingFuel(new net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt.Constant(1600), new net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat.Constant(1.0F))).stacksTo(16)));
    public static final RegistrySupplier<Item> LOOT_BAG = registerItem("loot_bag", () -> new LootBagItem(getSettings("loot_bag")));
    public static final RegistrySupplier<Item> BISON_HORN = registerItem("bison_horn", () -> new BisonHornItem(getSettings("bison_horn").stacksTo(1), SoundEventRegistry.BISON_HORN.get()));
    public static final RegistrySupplier<Item> FIELD_GUIDE = registerItem("field_guide", () -> new FieldGuideItem(getSettings("field_guide")));
    public static final RegistrySupplier<Item> FIELD_NOTES = registerItem("field_notes", () -> new ContractItem(getSettings("field_notes")));
    public static final RegistrySupplier<Item> PATHFINDERS_CALL = registerItem("pathfinders_call", () -> new ContractItem(getSettings("pathfinders_call")));
    public static final RegistrySupplier<Item> PROVISION_REQUEST = registerItem("provision_request", () -> new ContractItem(getSettings("provision_request")));
    public static final RegistrySupplier<Item> TRACKING_ORDER = registerItem("tracking_order", () -> new ContractItem(getSettings("tracking_order")));
    public static final RegistrySupplier<Item> GUILD_COMMISSION = registerItem("guild_commission", () -> new ContractItem(getSettings("guild_commission")));
    public static final RegistrySupplier<Item> ELITE_BOUNTY = registerItem("elite_bounty", () -> new ContractItem(getSettings("elite_bounty")));
    public static final RegistrySupplier<Item> BURST_OF_EXPERIENCE = registerItem("burst_of_experience", () -> new ExperienceBurstItem(getSettings("burst_of_experience")));
    public static final RegistrySupplier<Item> THICK_LEATHER = registerItem("thick_leather", () -> new Item(getSettings("thick_leather")));
    public static final RegistrySupplier<Item> WOODMEAL = registerItem("woodmeal", () -> new Item(getSettings("woodmeal").component(net.minecraft.core.component.DataComponents.COOKING_FUEL, new net.minecraft.world.item.component.CookingFuel(new net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt.Constant(150), new net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat.Constant(1.0F)))));
    public static final RegistrySupplier<Item> DEER_SPAWN_EGG = registerItem("deer_spawn_egg", () -> new SpawnEggItem(getSettings("deer_spawn_egg").spawnEgg(EntityTypeRegistry.DEER.get())));
    public static final RegistrySupplier<Item> SWIFT_FOX_SPAWN_EGG = registerItem("swift_fox_spawn_egg", () -> new SpawnEggItem(getSettings("swift_fox_spawn_egg").spawnEgg(EntityTypeRegistry.SWIFT_FOX.get())));
    public static final RegistrySupplier<Item> RACCOON_SPAWN_EGG = registerItem("raccoon_spawn_egg", () -> new SpawnEggItem(getSettings("raccoon_spawn_egg").spawnEgg(EntityTypeRegistry.RACCOON.get())));
    public static final RegistrySupplier<Item> SQUIRREL_SPAWN_EGG = registerItem("squirrel_spawn_egg", () -> new SpawnEggItem(getSettings("squirrel_spawn_egg").spawnEgg(EntityTypeRegistry.SQUIRREL.get())));
    public static final RegistrySupplier<Item> BOAR_SPAWN_EGG = registerItem("boar_spawn_egg", () -> new SpawnEggItem(getSettings("boar_spawn_egg").spawnEgg(EntityTypeRegistry.BOAR.get())));
    public static final RegistrySupplier<Item> BISON_SPAWN_EGG = registerItem("bison_spawn_egg", () -> new SpawnEggItem(getSettings("bison_spawn_egg").spawnEgg(EntityTypeRegistry.BISON.get())));
    public static final RegistrySupplier<Item> GIRAFFE_SPAWN_EGG = registerItem("giraffe_spawn_egg", () -> new SpawnEggItem(getSettings("giraffe_spawn_egg").spawnEgg(EntityTypeRegistry.GIRAFFE.get())));
    public static final RegistrySupplier<Item> DOG_SPAWN_EGG = registerItem("dog_spawn_egg", () -> new SpawnEggItem(getSettings("dog_spawn_egg").spawnEgg(EntityTypeRegistry.DOG.get())));
    public static final RegistrySupplier<Item> TERMITE_SPAWN_EGG = registerItem("termite_spawn_egg", () -> new SpawnEggItem(getSettings("termite_spawn_egg").spawnEgg(EntityTypeRegistry.TERMITE.get())));
    public static final RegistrySupplier<Item> MINISHEEP_SPAWN_EGG = registerItem("minisheep_spawn_egg", () -> new SpawnEggItem(getSettings("minisheep_spawn_egg").spawnEgg(EntityTypeRegistry.MINISHEEP.get())));
    public static final RegistrySupplier<Item> HIPPO_SPAWN_EGG = registerItem("hippo_spawn_egg", () -> new SpawnEggItem(getSettings("hippo_spawn_egg").spawnEgg(EntityTypeRegistry.HIPPO.get())));
    public static final RegistrySupplier<Item> TURKEY_SPAWN_EGG = registerItem("turkey_spawn_egg", () -> new SpawnEggItem(getSettings("turkey_spawn_egg").spawnEgg(EntityTypeRegistry.TURKEY.get())));
    public static final RegistrySupplier<Item> CASSOWARY_SPAWN_EGG = registerItem("cassowary_spawn_egg", () -> new SpawnEggItem(getSettings("cassowary_spawn_egg").spawnEgg(EntityTypeRegistry.CASSOWARY.get())));
    public static final RegistrySupplier<Item> HEDGEHOG_SPAWN_EGG = registerItem("hedgehog_spawn_egg", () -> new SpawnEggItem(getSettings("hedgehog_spawn_egg").spawnEgg(EntityTypeRegistry.HEDGEHOG.get())));
    public static final RegistrySupplier<Item> ELEPHANT_SPAWN_EGG = registerItem("elephant_spawn_egg", () -> new SpawnEggItem(getSettings("elephant_spawn_egg").spawnEgg(EntityTypeRegistry.ELEPHANT.get())));
    public static final RegistrySupplier<Item> BEAVER_SPAWN_EGG = registerItem("beaver_spawn_egg", () -> new SpawnEggItem(getSettings("beaver_spawn_egg").spawnEgg(EntityTypeRegistry.BEAVER.get())));
    public static final RegistrySupplier<Item> LION_SPAWN_EGG = registerItem("lion_spawn_egg", () -> new SpawnEggItem(getSettings("lion_spawn_egg").spawnEgg(EntityTypeRegistry.LION.get())));
    public static final RegistrySupplier<Item> SCORPION_SPAWN_EGG = registerItem("scorpion_spawn_egg", () -> new SpawnEggItem(getSettings("scorpion_spawn_egg").spawnEgg(EntityTypeRegistry.SCORPION.get())));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings(String name) {
        return new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, WilderNature.identifier(name)));
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return RegistryUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, WilderNature.identifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return RegistryUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, WilderNature.identifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return RegistryUtil.registerItem(ITEMS, ITEM_REGISTRAR, WilderNature.identifier(path), itemSupplier);
    }
}
