package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.spellslot.PlayerSpellData;
import com.hatedero.compendiummod.mana.spell.spellslot.SpellSlotData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.Mod;

import java.text.DecimalFormat;

import static com.hatedero.compendiummod.mana.ModAttachments.CAST_COOLDOWN;

public class ManaHudOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player == null || player.isSpectator()) return;

        DecimalFormat df = new DecimalFormat("#.#");

        double mana = player.getData(ModAttachments.MANA);
        double max_mana = player.getAttributeValue(ModAttributes.MAX_MANA);
        double mana_regen = player.getAttributeValue(ModAttributes.MANA_REGEN);
        String text = df.format(mana) + "/" + df.format(max_mana);
        String secondText = (mana_regen > 0 ? "+ " : "- ") + df.format(mana_regen);

        int x = guiGraphics.guiWidth() - 10 - minecraft.font.width(text);
        int x2 = guiGraphics.guiWidth() - 10 - minecraft.font.width(secondText);
        int y = guiGraphics.guiHeight() - 20;

        guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF);
        guiGraphics.drawString(minecraft.font, secondText, x2, y-20, 0xFFFFFF);

        PlayerSpellData data = player.getData(ModAttachments.SPELL_DATA);
        if (data.slots().isEmpty()) return;

        x = 2;
        y = guiGraphics.guiHeight() / 2;
        int lineHeight = 12;

        y -= (data.slots().size() * lineHeight) / 2;

        guiGraphics.drawString(minecraft.font, String.valueOf(data.chargeStartTime()), 0, 0, 0xFFFFFF);

        for (SpellSlotData slot : data.slots()) {
            String slotName = Component.translatable("slot." + CompendiumMod.MODID + "." + slot.slotName()).getString();
            String spellName = Component.translatable("spell." + CompendiumMod.MODID + "." + slot.spellId()).getString();

            text = String.format("%s : %s| %d | %d", slotName, spellName, slot.cooldown(), slot.chargeLevel());

            guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF);

            y += lineHeight;
        }
    }
}