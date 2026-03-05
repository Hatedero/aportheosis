package com.hatedero.compendiummod.network.CurrentSpellId;

import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.*;

public class CurrentSpellIdUpdateServerPayloadHandler {
    public static void handleDataOnMain(final CurrentSpellIdUpdatePayload data, final IPayloadContext context) {
        context.player().setData(CURRENT_SPELL_ID, data.value());
    }
}
