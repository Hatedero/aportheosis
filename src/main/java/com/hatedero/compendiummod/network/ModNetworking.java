package com.hatedero.compendiummod.network;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdateServerPayloadHandler;
import com.hatedero.compendiummod.network.showMana.ShowManaUpdateClientPayloadHandler;
import com.hatedero.compendiummod.network.showMana.ShowManaUpdatePayload;
import com.hatedero.compendiummod.network.showMana.ShowManaUpdateServerPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                ShowManaUpdatePayload.TYPE,
                ShowManaUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ShowManaUpdateClientPayloadHandler::handleDataOnMain,
                        ShowManaUpdateServerPayloadHandler::handleDataOnMain
                )
        );
        registrar.playBidirectional(
                IsChargingUpdatePayload.TYPE,
                IsChargingUpdatePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        IsChargingUpdateClientPayloadHandler::handleDataOnMain,
                        IsChargingUpdateServerPayloadHandler::handleDataOnMain
                )
        );
    }
}