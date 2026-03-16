package com.hatedero.compendiummod.mana.spell.spellslot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpellSlotData(
        String slotName,
        String spellId,
        int chargeLevel,
        int cooldown
) {
    public static final Codec<SpellSlotData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.fieldOf("slot_name").forGetter(SpellSlotData::slotName),
            Codec.STRING.fieldOf("spell_id").forGetter(SpellSlotData::spellId),
            Codec.INT.fieldOf("charge").forGetter(SpellSlotData::chargeLevel),
            Codec.INT.fieldOf("cooldown").forGetter(SpellSlotData::cooldown)
    ).apply(inst, SpellSlotData::new));
}