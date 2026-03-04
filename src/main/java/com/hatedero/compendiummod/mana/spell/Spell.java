package com.hatedero.compendiummod.mana.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public abstract class Spell{
    protected float costPerTick;
    protected String name;

    public Spell(String name, float costPerTick) {
        this.costPerTick = costPerTick;
        this.name = name;
    }

    public abstract int getUseDuration();

    public String getName() {
        return name;
    }

    public abstract boolean canStart (Player player);

    public abstract void chargeTick (Level level, LivingEntity livingEntity, int remainingUseDuration);

    public abstract void release (Level level, LivingEntity livingEntity, int remainingUseDuration);

    public abstract boolean canUseMana (LivingEntity livingEntity);
}
