package com.hatedero.compendiummod.block.custom;

import com.hatedero.compendiummod.dimensions.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AbyssPortalBlock extends Block {
    public AbyssPortalBlock(Properties props) { super(props); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isPassenger() && !serverPlayer.isVehicle()) {

            ResourceKey<Level> destination = (level.dimension() == ModDimensions.ABYSS_KEY)
                    ? Level.OVERWORLD : ModDimensions.ABYSS_KEY;

            ServerLevel destLevel = player.getServer().getLevel(destination);
            if (destLevel != null && serverPlayer.canChangeDimensions(serverPlayer.level(), destLevel)) {
                serverPlayer.teleportTo(destLevel, serverPlayer.position().x, serverPlayer.position().y, serverPlayer.position().z, serverPlayer.getYRot(), serverPlayer.getXRot());
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}