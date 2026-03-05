package com.hatedero.compendiummod.network.CurrentSpellId;

import com.hatedero.compendiummod.CompendiumMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CurrentSpellIdUpdatePayload(String value) implements CustomPacketPayload {

    public static final Type<CurrentSpellIdUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "current_spell_id_update"));

    public static final StreamCodec<ByteBuf, CurrentSpellIdUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CurrentSpellIdUpdatePayload::value,
            CurrentSpellIdUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}