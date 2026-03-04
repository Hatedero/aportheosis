package com.hatedero.compendiummod.network.showMana;

import com.hatedero.compendiummod.CompendiumMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ShowManaUpdatePayload(Boolean value) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ShowManaUpdatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "show_mana_update"));

    public static final StreamCodec<ByteBuf, ShowManaUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ShowManaUpdatePayload::value,
            ShowManaUpdatePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}