package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.hatedero.compendiummod.mana.ModAttachments.CHARGE_TIME;
import static com.hatedero.compendiummod.mana.ModAttachments.MANA;

public class BlinkSpell extends Spell {
    protected double baseReach;

    public BlinkSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, double baseReach) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
        this.baseReach = baseReach;
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide()) {
            boolean groundedLaunch = player.onGround();

            double reach = baseReach + manaLevel / 20.f * 1.5;
            Vec3 eyePosition = player.getEyePosition();
            Vec3 lookVector = player.getViewVector(1.0F);
            Vec3 endVector = eyePosition.add(lookVector.scale(reach));

            BlockHitResult hitResult = level.clip(new ClipContext(
                    eyePosition,
                    endVector,
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    player
            ));

            Vec3 targetPos = hitResult.getLocation();
            player.teleportTo(targetPos.x, targetPos.y, targetPos.z);

            if (!groundedLaunch) {
                double factor = 1.1 + (double) manaLevel / this.maxManaCharge;
                player.setDeltaMovement(lookVector.multiply(factor, factor, factor));
            }
            if(player instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(player));

            player.resetFallDistance();
        }
    }
}
