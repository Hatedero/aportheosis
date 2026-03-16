package com.hatedero.compendiummod.mana.packets;

import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;

public class SpellDataSyncHandler implements AttachmentSyncHandler<PlayerSpellData> {

    private static final StreamCodec<RegistryFriendlyByteBuf, PlayerSpellData> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(PlayerSpellData.CODEC);

    @Override
    public void write(RegistryFriendlyByteBuf buf, PlayerSpellData attachment, boolean initialSync) {
        STREAM_CODEC.encode(buf, attachment);
    }

    @Override
    public PlayerSpellData read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable PlayerSpellData previousValue) {
        return STREAM_CODEC.decode(buf);
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}