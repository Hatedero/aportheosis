package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ReverseCursedTechniqueSpell extends LimitedTimeSpell {
    public ReverseCursedTechniqueSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, int maxTime) {
        super(minManaCostPerTick, maxManaCharge, cooldown, maxTime);
    }

    @Override
    public void chargeEffect(Level level, Player player, int manaLevel) {
        if (!level.isClientSide) {
            player.heal(SpellSlotDataHelper.calculateManaCost(player)/20f);
        }
    }

    @Override
    public void releaseEffect(Level level, Player player, int manaLevel) {
    }
}
