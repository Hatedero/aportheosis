package com.hatedero.compendiummod.util;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.GUI.SpellScreen;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import com.hatedero.compendiummod.network.isCharging.IsChargingUpdatePayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = CompendiumMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().screen != null)
            return;
        if (event.getAction() == GLFW.GLFW_PRESS) {
            if (ModKeybinds.CHARGE_SPELL_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    PacketDistributor.sendToServer(new IsChargingUpdatePayload(true));
                }
            } else if (ModKeybinds.OPEN_SPELL_MENU.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                Minecraft.getInstance().setScreen(new SpellScreen(Component.literal("Spell List")));
            } else if (ModKeybinds.ULTIMATE_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                Player player = Minecraft.getInstance().player;
                assert player != null;
                for (SpellSlotData slot : player.getData(ModAttachments.SPELL_DATA).slots()) {
                    player.sendSystemMessage(Component.translatable("slot."+CompendiumMod.MODID+"."+slot.slotName()));
                }
            }
        } else if (event.getAction() == GLFW.GLFW_RELEASE) {
            if (ModKeybinds.CHARGE_SPELL_KEY.isActiveAndMatches(InputConstants.getKey(event.getKey(), event.getScanCode()))) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    PacketDistributor.sendToServer(new IsChargingUpdatePayload(false));
                }
            }
        }
    }
}