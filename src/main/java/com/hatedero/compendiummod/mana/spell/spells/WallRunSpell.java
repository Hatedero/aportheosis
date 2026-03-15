package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class WallRunSpell extends Spell {
    public WallRunSpell(float costPerTick) {
        super(costPerTick);
    }

    @Override
    public int getUseDuration() {
        return 1;
    }

    @Override
    public int getCooldown() {
        return 1;
    }

    @Override
    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
        if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
            if (isWallInFront(player) && player.isSprinting()) {
                player.setDeltaMovement(player.getDeltaMovement().x, 0.7, player.getDeltaMovement().z);
                player.connection.send(new ClientboundSetEntityMotionPacket(player));
            }
        }
        super.release(level, livingEntity, remainingUseDuration);
    }

    private boolean isWallInFront(Player player) {
        Direction direction = player.getDirection();
        BlockPos pos = player.blockPosition().relative(direction);

        return player.level().getBlockState(pos).isCollisionShapeFullBlock(player.level(), pos) ||
                player.level().getBlockState(pos.above()).isCollisionShapeFullBlock(player.level(), pos.above());
    }
}
