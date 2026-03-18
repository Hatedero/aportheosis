package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CleaveSpell extends Spell {
    public CleaveSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            
            List<BlockPos> blocks = getBlocksIn3DRotatedBox(player, 15, 2, 5, 1);
            blocks.forEach((blockPos -> {
                tryTurningBlockInBlockEntity(blockPos, level);
                serverLevel.sendParticles(
                    ParticleTypes.DUST_PLUME,
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    1,
                    0, 0, 0,
                    0
            );
            }));
        }
    }

    protected boolean tryTurningBlockInBlockEntity(BlockPos pos, Level level) {
        float resistanceThreshold = 600.0F;
        BlockState state = level.getBlockState(pos);

        float blockResistance = state.getBlock().getExplosionResistance();

        if (!state.isAir() && !state.liquid() && state.getDestroySpeed(level, pos) >= 0 && blockResistance < resistanceThreshold) {

            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);

            fallingBlock.dropItem = false;

            level.removeBlock(pos, false);
            Random r = new Random();

            fallingBlock.setDeltaMovement(r.nextFloat(), 1, r.nextFloat());
            fallingBlock.hurtMarked = true;
            level.addFreshEntity(fallingBlock);
            return true;
        }
        return false;
    }

    public List<BlockPos> getBlocksIn3DRotatedBox(Player player, double length, double width, double height, double distanceInFront) {
        List<BlockPos> hitBlocks = new ArrayList<>();
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition();

        float yawRad = player.getYRot() * ((float) Math.PI / 180F);
        float pitchRad = -player.getXRot() * ((float) Math.PI / 180F);

        double sY = Math.sin(-yawRad);
        double cY = Math.cos(-yawRad);
        double sP = Math.sin(pitchRad);
        double cP = Math.cos(pitchRad);

        Vec3 lookVec = player.getLookAngle();
        Vec3 centerPoint = eyePos.add(lookVec.scale(distanceInFront));

        double maxDim = Math.max(length, Math.max(width, height));
        AABB searchArea = new AABB(centerPoint, centerPoint).inflate(maxDim);

        BlockPos min = BlockPos.containing(searchArea.minX, searchArea.minY, searchArea.minZ);
        BlockPos max = BlockPos.containing(searchArea.maxX, searchArea.maxY, searchArea.maxZ);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            double dx = (pos.getX() + 0.5) - eyePos.x;
            double dy = (pos.getY() + 0.5) - eyePos.y;
            double dz = (pos.getZ() + 0.5) - eyePos.z;

            double tempX = dx * cY - dz * sY;
            double tempZ = dx * sY + dz * cY;

            double localX = tempX;
            double localY = dy * cP - tempZ * sP;
            double localZ = dy * sP + tempZ * cP;

            if (Math.abs(localZ - distanceInFront) <= (length / 2.0) &&
                    Math.abs(localX) <= (width / 2.0) &&
                    Math.abs(localY) <= (height / 2.0)) {

                //if (!level.getBlockState(pos).isAir()) {
                    hitBlocks.add(pos.immutable());
                //}
            }
        }
        return hitBlocks;
    }
}
