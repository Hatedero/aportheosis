package com.hatedero.compendiummod.network.isCharging;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.IS_CHARGING;

public class IsChargingUpdateClientPayloadHandler {
    public static void handleDataOnMain(final IsChargingUpdatePayload data, final IPayloadContext context) {
        context.player().setData(IS_CHARGING, data.value());
    }
}
