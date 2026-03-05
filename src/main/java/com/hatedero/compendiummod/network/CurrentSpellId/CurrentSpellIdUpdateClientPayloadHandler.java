package com.hatedero.compendiummod.network.CurrentSpellId;

import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.CURRENT_SPELL_ID;
import static com.hatedero.compendiummod.mana.ModAttachments.IS_CHARGING;

public class CurrentSpellIdUpdateClientPayloadHandler {
    public static void handleDataOnMain(final CurrentSpellIdUpdatePayload data, final IPayloadContext context) {
        context.player().setData(CURRENT_SPELL_ID, data.value());
    }
}
