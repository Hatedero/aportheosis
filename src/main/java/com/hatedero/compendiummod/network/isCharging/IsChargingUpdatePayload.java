package com.hatedero.compendiummod.network.isCharging;

import com.hatedero.compendiummod.CompendiumMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record IsChargingUpdatePayload(Boolean value) implements CustomPacketPayload {

    public static final Type<IsChargingUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "is_charging_update"));

    public static final StreamCodec<ByteBuf, IsChargingUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            IsChargingUpdatePayload::value,
            IsChargingUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}