package com.hatedero.compendiummod.network.isCharging;

import com.hatedero.compendiummod.network.showMana.ShowManaUpdatePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.hatedero.compendiummod.mana.ModAttachments.*;

public class IsChargingUpdateServerPayloadHandler {
    public static void handleDataOnMain(final IsChargingUpdatePayload data, final IPayloadContext context) {
        if (data.value()) {
            Player player = context.player();

            if (player != null && !player.getData(IS_CHARGING) && player.getData(CAST_COOLDOWN) <= 0)
                player.setData(IS_CHARGING, true);
        } else {
            context.player().setData(IS_CHARGING, false);
        }
    }
}
