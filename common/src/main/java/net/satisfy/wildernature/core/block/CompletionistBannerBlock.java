package net.satisfy.wildernature.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.wildernature.WilderNature;
import net.satisfy.wildernature.core.block.entity.CompletionistBannerEntity;
import net.satisfy.wildernature.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CompletionistBannerBlock extends BaseEntityBlock {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public CompletionistBannerBlock(Properties properties) {
        super(properties);
        makeDefaultState();
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CompletionistBannerEntity(blockPos, blockState);
    }

    protected void makeDefaultState() {
        registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState belowBlockState = levelReader.getBlockState(blockPos.below());
        return belowBlockState.isSolid();
    }

    @Override
    public boolean isPossibleToRespawnInThis(BlockState blockState) {
        return true;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) {
            return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) ((180.0f + context.getRotation()) * 16.0f / 360.0f) + 0.5) & 0xF);
        } else {
            if (this == ObjectRegistry.FOX_TRAPPER_BANNER.get()) {
                return ObjectRegistry.FOX_TRAPPER_WALL_BANNER.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, clickedFace.getOpposite());
            } else if (this == ObjectRegistry.BUNNY_STALKER_BANNER.get()) {
                return ObjectRegistry.BUNNY_STALKER_WALL_BANNER.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, clickedFace.getOpposite());
            } else if (this == ObjectRegistry.COD_CATCHER_BANNER.get()) {
                return ObjectRegistry.COD_CATCHER_WALL_BANNER.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, clickedFace.getOpposite());
            } else {
                return ObjectRegistry.FOX_TRAPPER_WALL_BANNER.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, clickedFace.getOpposite());
            }
        }
    }

    @Override
    public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(ROTATION, rotation.rotate(blockState.getValue(ROTATION), 16));
    }

    @Override
    public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.setValue(ROTATION, mirror.mirror(blockState.getValue(ROTATION), 16));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, net.minecraft.world.level.LevelReader levelAccessor, net.minecraft.world.level.ScheduledTickAccess scheduledTicks, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, net.minecraft.util.RandomSource random) {
        if (direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, levelAccessor, scheduledTicks, blockPos, direction, blockPos2, blockState2, random);
    }

    public Identifier getRenderTexture() {
        if (this == ObjectRegistry.FOX_TRAPPER_BANNER.get()) {
            return WilderNature.identifier("textures/banner/fox_trapper.png");
        } else if (this == ObjectRegistry.BUNNY_STALKER_BANNER.get()) {
            return WilderNature.identifier("textures/banner/rabbit_hunter.png");
        } else if (this == ObjectRegistry.BUNNY_STALKER_WALL_BANNER.get()) {
            return WilderNature.identifier("textures/banner/rabbit_hunter.png");
        } else if (this == ObjectRegistry.COD_CATCHER_BANNER.get()) {
            return WilderNature.identifier("textures/banner/cod_catcher.png");
        } else if (this == ObjectRegistry.COD_CATCHER_WALL_BANNER.get()) {
            return WilderNature.identifier("textures/banner/cod_catcher.png");
        }

        return WilderNature.identifier("textures/banner/fox_trapper.png");
    }
}
