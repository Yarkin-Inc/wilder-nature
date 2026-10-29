package net.satisfy.wildernature.core.bounty;

import net.minecraft.nbt.CompoundTag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerBountyData {
    private BountyDefinition activeBounty;
    private int currentProgress;
    private int trackedStartAmount;
    private int trackedHighestAmount;
    private boolean rewardsUnlocked;
    private boolean itemRewardClaimed;
    private boolean experienceRewardClaimed;
    private final Set<UUID> abandonedBounties = new HashSet<>();

    public BountyDefinition getActiveBounty() {
        return this.activeBounty;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public boolean hasActiveBounty() {
        return this.activeBounty != null;
    }

    public boolean hasAbandoned(UUID bountyId) {
        return this.abandonedBounties.contains(bountyId);
    }

    public Set<UUID> getAbandonedBounties() {
        return this.abandonedBounties;
    }

    public boolean areRewardsUnlocked() {
        return this.rewardsUnlocked;
    }

    public void setRewardsUnlocked(boolean rewardsUnlocked) {
        this.rewardsUnlocked = rewardsUnlocked;
    }

    public boolean isItemRewardClaimed() {
        return this.itemRewardClaimed;
    }

    public void setItemRewardClaimed(boolean itemRewardClaimed) {
        this.itemRewardClaimed = itemRewardClaimed;
    }

    public boolean isExperienceRewardClaimed() {
        return this.experienceRewardClaimed;
    }

    public void setExperienceRewardClaimed(boolean experienceRewardClaimed) {
        this.experienceRewardClaimed = experienceRewardClaimed;
    }

    public void setActiveBounty(BountyDefinition activeBounty) {
        this.activeBounty = activeBounty;
        this.currentProgress = 0;
        this.trackedStartAmount = 0;
        this.trackedHighestAmount = 0;
        this.rewardsUnlocked = false;
        this.itemRewardClaimed = false;
        this.experienceRewardClaimed = false;
    }

    public void clearActiveBounty() {
        this.activeBounty = null;
        this.currentProgress = 0;
        this.trackedStartAmount = 0;
        this.trackedHighestAmount = 0;
        this.rewardsUnlocked = false;
        this.itemRewardClaimed = false;
        this.experienceRewardClaimed = false;
    }

    public void abandonActiveBounty() {
        if (this.activeBounty != null) {
            this.abandonedBounties.add(this.activeBounty.id());
        }
        this.clearActiveBounty();
    }

    public void incrementProgress(int amount) {
        if (this.activeBounty != null && amount > 0 && this.currentProgress < this.activeBounty.requiredAmount()) {
            this.currentProgress = Math.min(this.activeBounty.requiredAmount(), this.currentProgress + amount);
        }
    }

    public void setProgress(int progress) {
        if (this.activeBounty != null) {
            this.currentProgress = Math.max(0, Math.min(this.activeBounty.requiredAmount(), progress));
        }
    }

    public boolean isCompleted() {
        return this.activeBounty != null && this.currentProgress >= this.activeBounty.requiredAmount();
    }

    public CompoundTag save() {
        CompoundTag compoundTag = new CompoundTag();

        if (this.activeBounty != null) {
            compoundTag.put("active_bounty", this.activeBounty.save());
            compoundTag.putInt("current_progress", this.currentProgress);
            compoundTag.putInt("tracked_start_amount", this.trackedStartAmount);
            compoundTag.putInt("tracked_highest_amount", this.trackedHighestAmount);
            compoundTag.putBoolean("rewards_unlocked", this.rewardsUnlocked);
            compoundTag.putBoolean("item_reward_claimed", this.itemRewardClaimed);
            compoundTag.putBoolean("experience_reward_claimed", this.experienceRewardClaimed);
        }

        int abandonedIndex = 0;
        for (UUID bountyId : this.abandonedBounties) {
            compoundTag.store("abandoned_" + abandonedIndex, net.minecraft.core.UUIDUtil.CODEC, bountyId);
            abandonedIndex++;
        }

        compoundTag.putInt("abandoned_size", abandonedIndex);
        return compoundTag;
    }

    public static PlayerBountyData load(CompoundTag compoundTag) {
        PlayerBountyData playerBountyData = new PlayerBountyData();

        if (compoundTag.contains("active_bounty")) {
            playerBountyData.activeBounty = BountyDefinition.load(compoundTag.getCompoundOrEmpty("active_bounty"));
            playerBountyData.currentProgress = compoundTag.getIntOr("current_progress", 0);
            playerBountyData.trackedStartAmount = compoundTag.getIntOr("tracked_start_amount", 0);
            playerBountyData.trackedHighestAmount = compoundTag.getIntOr("tracked_highest_amount", 0);
            playerBountyData.rewardsUnlocked = compoundTag.getBooleanOr("rewards_unlocked", false);
            playerBountyData.itemRewardClaimed = compoundTag.getBooleanOr("item_reward_claimed", false);
            playerBountyData.experienceRewardClaimed = compoundTag.getBooleanOr("experience_reward_claimed", false);
        }

        int abandonedSize = compoundTag.getIntOr("abandoned_size", 0);
        for (int index = 0; index < abandonedSize; index++) {
            playerBountyData.abandonedBounties.add(compoundTag.read("abandoned_" + index, net.minecraft.core.UUIDUtil.CODEC).orElseThrow());
        }

        return playerBountyData;
    }
}