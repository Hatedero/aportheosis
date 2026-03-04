package com.hatedero.compendiummod.network.showMana;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.SHOW_MANA;

public class ShowManaUpdateServerPayloadHandler {
    public static void handleDataOnMain(final ShowManaUpdatePayload data, final IPayloadContext context) {
        context.player().setData(SHOW_MANA, data.value());
    }
}
