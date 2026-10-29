package net.satisfy.wildernature.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.wildernature.core.block.RottenLogBlock;
import net.satisfy.wildernature.core.registry.EntityTypeRegistry;
import net.satisfy.wildernature.core.registry.ObjectRegistry;

public class RottenLogBlockEntity extends BlockEntity {
    private static final int MIN_SPREAD_COOLDOWN = 80;
    private static final int MAX_SPREAD_COOLDOWN = 180;

    private int termiteCount;
    private int spreadCooldown;
    private int infestationId;
    private BlockPos moundPos;

    public RottenLogBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.ROTTEN_LOG_BLOCK_ENTITY.get(), pos, state);
    }

    public int getTermiteCount() {
        return this.termiteCount;
    }

    public int getInfestationId() {
        return this.infestationId;
    }

    public BlockPos getMoundPos() {
        return this.moundPos;
    }

    public void setTermiteCount(int termiteCount) {
        this.termiteCount = Math.max(termiteCount, 0);
        this.setChanged();
    }

    public void removeTermites(int termiteCount) {
        this.termiteCount = Math.max(0, this.termiteCount - termiteCount);
        this.setChanged();
    }

    public void resetSpreadCooldown(ServerLevel level) {
        this.spreadCooldown = MIN_SPREAD_COOLDOWN + level.getRandom().nextInt(MAX_SPREAD_COOLDOWN - MIN_SPREAD_COOLDOWN + 1);
        this.setChanged();
    }

    public static void initializeInfestation(ServerLevel level, BlockPos pos, BlockPos moundPos, int infestationId, int termiteCount) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof RottenLogBlockEntity rottenLogBlockEntity) {
            rottenLogBlockEntity.termiteCount = Math.max(termiteCount, 0);
            rottenLogBlockEntity.infestationId = infestationId;
            rottenLogBlockEntity.moundPos = moundPos;
            rottenLogBlockEntity.resetSpreadCooldown(level);
            rottenLogBlockEntity.setChanged();
        }
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state, RottenLogBlockEntity blockEntity) {
        if (!state.is(ObjectRegistry.ROTTEN_LOG.get()) || state.getValue(RottenLogBlock.STAGE) != RottenLogBlock.Stage.INFESTED) {
            if (blockEntity.termiteCount != 0 || blockEntity.spreadCooldown != 0 || blockEntity.infestationId != 0 || blockEntity.moundPos != null) {
                blockEntity.termiteCount = 0;
                blockEntity.spreadCooldown = 0;
                blockEntity.infestationId = 0;
                blockEntity.moundPos = null;
                blockEntity.setChanged();
            }
            return;
        }

        if (blockEntity.termiteCount <= 1 || blockEntity.infestationId <= 0 || blockEntity.moundPos == null) return;

        if (blockEntity.spreadCooldown > 0) {
            blockEntity.spreadCooldown--;
            blockEntity.setChanged();
            return;
        }

        DirectionOrder directionOrder = new DirectionOrder(level.getRandom().nextInt(6));
        for (int index = 0; index < 6; index++) {
            BlockPos targetPos = pos.relative(directionOrder.get(index));
            BlockState targetState = level.getBlockState(targetPos);

            if (!targetState.is(BlockTags.LOGS) || targetState.is(ObjectRegistry.ROTTEN_LOG.get())) continue;

            level.setBlock(targetPos, RottenLogBlock.createInfestedState(targetState), 3);
            initializeInfestation(level, targetPos, blockEntity.moundPos, blockEntity.infestationId, 1);
            blockEntity.removeTermites(1);
            blockEntity.resetSpreadCooldown(level);
            return;
        }

        blockEntity.resetSpreadCooldown(level);
    }

    @Override
    public void loadAdditional(net.minecraft.world.level.storage.ValueInput tag) {
        super.loadAdditional(tag);
        this.termiteCount = tag.getIntOr("TermiteCount", 0);
        this.spreadCooldown = tag.getIntOr("SpreadCooldown", 0);
        this.infestationId = tag.getIntOr("InfestationId", 0);
        if (tag.read("MoundX", com.mojang.serialization.Codec.INT).isPresent()) {
            this.moundPos = new BlockPos(tag.getIntOr("MoundX", 0), tag.getIntOr("MoundY", 0), tag.getIntOr("MoundZ", 0));
        } else {
            this.moundPos = null;
        }
    }

    @Override
    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput tag) {
        super.saveAdditional(tag);
        tag.putInt("TermiteCount", this.termiteCount);
        tag.putInt("SpreadCooldown", this.spreadCooldown);
        tag.putInt("InfestationId", this.infestationId);
        if (this.moundPos != null) {
            tag.putInt("MoundX", this.moundPos.getX());
            tag.putInt("MoundY", this.moundPos.getY());
            tag.putInt("MoundZ", this.moundPos.getZ());
        }
    }

    private record DirectionOrder(int startIndex) {
        private static final net.minecraft.core.Direction[] DIRECTIONS = net.minecraft.core.Direction.values();

        private net.minecraft.core.Direction get(int index) {
            return DIRECTIONS[(this.startIndex + index) % DIRECTIONS.length];
        }
    }
}