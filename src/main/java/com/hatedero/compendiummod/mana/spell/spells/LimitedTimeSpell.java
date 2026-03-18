package com.hatedero.compendiummod.mana.spell.spells;

import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotDataHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.hatedero.compendiummod.mana.ModAttachments.MANA;
import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public abstract class LimitedTimeSpell extends Spell {
    protected int maxTime;

    public LimitedTimeSpell(int minManaCostPerTick, int maxManaCharge, int cooldown, int maxTime) {
        super(minManaCostPerTick, maxManaCharge, cooldown);
        this.maxTime = maxTime;
    }

    @Override
    public boolean canUseMana (Player player, int manaLevel) {
        return SpellSlotDataHelper.canUseMana(player) && (player.level().getGameTime() - player.getData(SPELL_DATA).chargeStartTime()) < maxTime;
    }
}
