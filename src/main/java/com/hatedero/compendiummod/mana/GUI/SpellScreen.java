package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.mana.packets.CurrentSpellIdSyncHandler;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpellScreen extends Screen {
    public SpellScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        int bw = 100;
        int bh = 20;
        int gap = 10;

        this.addRenderableWidget(Button.builder(
                        Component.literal("Reverse Cursed Technique"),
                        button -> {
                            PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload("reverse_cursed_technique"));
                        })
                .bounds(this.width / 2 - bw - (gap / 2), this.height / 2, bw, bh)
                .tooltip(Tooltip.create(Component.literal("Change Spell")))
                .build());

        this.addRenderableWidget(Button.builder(
                        Component.literal("Dismantle"),
                        button -> {
                            PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload("dismantle"));
                        })
                .bounds(this.width / 2 + (gap / 2), this.height / 2, bw, bh)
                .tooltip(Tooltip.create(Component.literal("Change Spell")))
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
