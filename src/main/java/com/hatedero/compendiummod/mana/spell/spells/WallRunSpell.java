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
import net.neoforged.neoforge.network.PacketDistributor;

public class WallRunSpell extends Spell {
    public WallRunSpell(int minManaCostPerTick, int maxManaCharge, int cooldown) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
    }

    private boolean isWallInFront(Player player) {
        Direction direction = player.getDirection();
        BlockPos pos = player.blockPosition().relative(direction);

        return player.level().getBlockState(pos).isCollisionShapeFullBlock(player.level(), pos) ||
                player.level().getBlockState(pos.above()).isCollisionShapeFullBlock(player.level(), pos.above());
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {

    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide() && player instanceof ServerPlayer) {
            if (isWallInFront(player) && player.isSprinting()) {
                player.setDeltaMovement(player.getDeltaMovement().x, 0.7, player.getDeltaMovement().z);
                ((ServerPlayer) player).connection.send(new ClientboundSetEntityMotionPacket(player));
            }
        }
    }
}
