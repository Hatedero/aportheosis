package com.hatedero.compendiummod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaBoltProjectile extends AbstractHurtingProjectile {
    private int age = 0;
    private int maxLife;
    private float damage;

    public ManaBoltProjectile(EntityType<? extends ManaBoltProjectile> type, Level level, int maxLife, float damage) {
        super(type, level);
        this.maxLife = maxLife;
        this.damage = damage;
    }

    public ManaBoltProjectile(EntityType<ManaBoltProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (level() instanceof ServerLevel serverLevel)
                serverLevel.sendParticles(
                        ParticleTypes.FLAME,
                        this.getX(), this.getY(), this.getZ(),
                        1,
                        0, 0, 0,
                        0
                );


            this.age++;

            if (this.age >= maxLife) {
                if (!this.level().isClientSide) {
                    this.discard();
                }
            }

            super.tick();
            Vec3 movement = this.getDeltaMovement();
            double speedLimit = 0.5D;
                if (movement.length() > speedLimit) {
                    this.setDeltaMovement(movement.normalize().scale(speedLimit));
                }
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Age", this.age);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.age = compound.getInt("Age");
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide()){
            if(result.getEntity() instanceof LivingEntity entity) {
                entity.hurt(this.level().damageSources().generic(), this.damage);
                entity.knockback(1, -getDeltaMovement().x, -getDeltaMovement().z);
            }
            this.discard();
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }
}
