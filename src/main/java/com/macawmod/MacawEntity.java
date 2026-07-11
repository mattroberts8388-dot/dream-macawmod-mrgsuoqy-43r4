package com.macawmod;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MacawEntity extends TameableShoulderEntity {

    public MacawEntity(EntityType<? extends MacawEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMacawAttributes() {
        return TameableShoulderEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 1.0, 5.0f, 1.0f, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    private boolean isTamingFood(ItemStack stack) {
        return stack.isOf(Items.WHEAT_SEEDS)
            || stack.isOf(Items.MELON_SEEDS)
            || stack.isOf(Items.PUMPKIN_SEEDS)
            || stack.isOf(Items.BEETROOT_SEEDS);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.isTamed() && this.isTamingFood(stack)) {
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (!this.getWorld().isClient) {
                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.setSitting(true);
                    this.getWorld().sendEntityStatus(this, (byte) 7);
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        if (this.isTamed() && this.isOwner(player) && !this.getWorld().isClient && hand == Hand.MAIN_HAND) {
            if (stack.isEmpty()) {
                // Attempt to mount the player's shoulder.
                if (this.mountOnto((net.minecraft.server.network.ServerPlayerEntity) player)) {
                    return ActionResult.SUCCESS;
                }
                // Otherwise toggle sitting.
                this.setSitting(!this.isSitting());
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return this.isTamingFood(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MacawEntity child = MacawModEntities.MACAW.create(world);
        if (child != null && this.isTamed() && this.getOwnerUuid() != null) {
            child.setOwnerUuid(this.getOwnerUuid());
            child.setTamed(true);
        }
        return child;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PARROT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(net.minecraft.entity.damage.DamageSource source) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, net.minecraft.block.BlockState state, net.minecraft.util.math.BlockPos landedPosition) {
        // Macaws glide; no fall damage.
    }
}