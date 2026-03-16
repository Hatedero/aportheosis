package com.hatedero.compendiummod.network.SpellData;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.CURRENT_SPELL_ID;
import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellDataUpdateServerPayloadHandler {
    public static void handleDataOnMain(final SpellDataUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SPELL_DATA, data.data());
    }
}
