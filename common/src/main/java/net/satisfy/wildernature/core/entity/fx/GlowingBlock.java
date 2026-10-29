package net.satisfy.wildernature.core.entity.fx;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GlowingBlock extends Entity {
    private static final EntityDataAccessor<Integer> GLOW_COLOR =
        SynchedEntityData.defineId(GlowingBlock.class, EntityDataSerializers.INT);

    public GlowingBlock(EntityType<?> type, Level level) {
        super(type, level);
        this.setInvisible(true);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public void setGlowColor(int color) {
        this.entityData.set(GLOW_COLOR, color);
    }

    public int getGlowColor() {
        return this.entityData.get(GLOW_COLOR);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(GLOW_COLOR, 0xFFFFFF);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && tickCount >= 600) {
            this.discard();
        }
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel level, net.minecraft.world.damagesource.DamageSource source, float amount) { return false; }

    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    @Override
    public int getTeamColor() {
        return getGlowColor();
    }

    @Override
    protected void readAdditionalSaveData(@NotNull net.minecraft.world.level.storage.ValueInput tag) {
        {
            setGlowColor(tag.getIntOr("GlowColor", 0));
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull net.minecraft.world.level.storage.ValueOutput tag) {
        tag.putInt("GlowColor", getGlowColor());
    }
}