package com.hatedero.compendiummod.mana.packets.deprecated;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;

public class CurrentSpellIdSyncHandler implements AttachmentSyncHandler<String> {

    @Override
    public void write(RegistryFriendlyByteBuf buf, String attachment, boolean initialSync) {
        buf.writeUtf(attachment, 32767);
    }

    @Override
    public String read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable String previousValue) {
        return buf.readUtf(32767);
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}