//package com.hatedero.compendiummod.mana.spell.spells;
//
//import com.hatedero.compendiummod.entity.ManaBoltProjectile;
//import com.hatedero.compendiummod.entity.ModEntities;
//import com.hatedero.compendiummod.entity.RedProjectile;
//import com.hatedero.compendiummod.mana.spell.Spell;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.Vec3;
//
//public class ManaBoltSpell extends Spell {
//    public ManaBoltSpell(float costPerTick) {
//        super(costPerTick);
//    }
//
//    @Override
//    public int getUseDuration() {
//        return 100;
//    }
//
//    @Override
//    public void chargeTick(Level level, LivingEntity livingEntity, int manaLevel) {
//        super.chargeTick(level, livingEntity, manaLevel);
//    }
//
//    @Override
//    public void release(Level level, LivingEntity livingEntity, int remainingUseDuration) {
//        if (!level.isClientSide() && livingEntity instanceof Player player) {
//            ManaBoltProjectile projectile = new ManaBoltProjectile(ModEntities.MANA_BOLT_PROJECTILE.get(), level, 200,remainingUseDuration%20*2f);
//            projectile.setOwner(player);
//            Vec3 eyePos = getPointInFront(livingEntity, 1);
//            projectile.setPos(eyePos.x(), eyePos.y() - projectile.getBbHeight()/2, eyePos.z());
//
//            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.01F, 1.0F);
//
//            level.addFreshEntity(projectile);
//        }
//        super.release(level, livingEntity, remainingUseDuration);
//    }
//
//    public Vec3 getPointInFront(LivingEntity entity, double distance) {
//        Vec3 eyePos = entity.getEyePosition().add(0,-entity.getBbHeight() * 0.2,0);
//
//        Vec3 lookDir = entity.getLookAngle();
//
//        return eyePos.add(lookDir.scale(distance));
//    }
//}
