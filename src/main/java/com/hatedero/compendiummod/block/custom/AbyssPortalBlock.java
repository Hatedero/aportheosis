package com.hatedero.compendiummod.block.custom;

import com.hatedero.compendiummod.dimensions.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AbyssPortalBlock extends Block {
    public AbyssPortalBlock(Properties props) { super(props); }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ServerPlayer player && !entity.isPassenger() && !entity.isVehicle()) {
            
            ResourceKey<Level> destination = (level.dimension() == ModDimensions.ABYSS_KEY)
                                             ? Level.OVERWORLD : ModDimensions.ABYSS_KEY;

            ServerLevel destLevel = player.getServer().getLevel(destination);
            if (destLevel != null && entity.canChangeDimensions(entity.level(), entity.level())) {
                ((ServerPlayer) entity).teleportTo(destLevel, player.position().x, player.position().y, player.position().z, player.getYRot(), player.getXRot());
            }
        }
    }
}