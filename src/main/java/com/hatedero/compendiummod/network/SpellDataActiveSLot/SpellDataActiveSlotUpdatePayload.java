package com.hatedero.compendiummod.network.SpellDataActiveSLot;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellDataActiveSlotUpdatePayload(String activeSlot, Long time) implements CustomPacketPayload {
    public static final Type<SpellDataActiveSlotUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "spell_data_active_slot_update"));

    public static final StreamCodec<ByteBuf, SpellDataActiveSlotUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.STRING), SpellDataActiveSlotUpdatePayload::activeSlot,
            ByteBufCodecs.fromCodec(Codec.LONG), SpellDataActiveSlotUpdatePayload::time,
            SpellDataActiveSlotUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}