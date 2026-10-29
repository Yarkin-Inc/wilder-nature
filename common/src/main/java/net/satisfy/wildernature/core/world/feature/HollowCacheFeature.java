package net.satisfy.wildernature.core.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;


import net.minecraft.world.level.storage.loot.LootTable;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.block.HollowCacheBlock;
import net.satisfy.wildernature.core.block.entity.HollowCacheBlockEntity;
import net.satisfy.wildernature.core.registry.ObjectRegistry;

public class HollowCacheFeature implements Feature {
    private static final ResourceKey<LootTable> HOLLOW_CACHE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, WilderNature.identifier("chests/hollow_cache"));

    public static final com.mojang.serialization.MapCodec<HollowCacheFeature> CODEC = com.mojang.serialization.MapCodec.unit(HollowCacheFeature::new);

    @Override
    public com.mojang.serialization.MapCodec<HollowCacheFeature> codec() { return CODEC; }

    @Override
    public boolean place(WorldGenLevel level, net.minecraft.world.level.chunk.ChunkGenerator generator, RandomSource randomSource, BlockPos origin) {
        BlockPos blockPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin);

        if (!level.isEmptyBlock(blockPos)) return false;

        BlockState groundState = level.getBlockState(blockPos.below());
        if (!(groundState.is(Blocks.DIRT) || groundState.is(Blocks.COARSE_DIRT) || groundState.is(Blocks.GRASS_BLOCK))) return false;

        BlockState blockState = ObjectRegistry.HOLLOW_CACHE.get().defaultBlockState().setValue(HollowCacheBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource)).setValue(HollowCacheBlock.OPEN, false);
        if (!level.setBlock(blockPos, blockState, 3)) return false;

        if (level.getBlockEntity(blockPos) instanceof HollowCacheBlockEntity hollowCacheBlockEntity) {
            hollowCacheBlockEntity.setLootTable(HOLLOW_CACHE_LOOT_TABLE, randomSource.nextLong());
            hollowCacheBlockEntity.setChanged();
        }

        return true;
    }
}