package com.hatedero.compendiummod.network.isCharging;

import com.hatedero.compendiummod.network.showMana.ShowManaUpdatePayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.IS_CHARGING;
import static com.hatedero.compendiummod.mana.ModAttachments.SHOW_MANA;

public class IsChargingUpdateServerPayloadHandler {
    public static void handleDataOnMain(final IsChargingUpdatePayload data, final IPayloadContext context) {
        context.player().setData(IS_CHARGING, data.value());
    }
}
