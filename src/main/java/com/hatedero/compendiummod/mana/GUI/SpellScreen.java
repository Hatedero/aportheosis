package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.mana.spell.Spell;
import com.hatedero.compendiummod.mana.spell.SpellRegistry;
import com.hatedero.compendiummod.network.CurrentSpellId.CurrentSpellIdUpdatePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellScreen extends Screen {
    private SpellList list;
    private EditBox searchBox;

    public SpellScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        list = new SpellList(this.minecraft, this.width, this.height - 80, 40, 25);

        SpellRegistry.REGISTRY.entrySet().forEach(entry -> {
            Spell spell = entry.getValue();

            list.addEntry(list.new MySpellEntry(spell));
        });

        this.addWidget(list);

        this.searchBox = new EditBox(this.font, this.width / 2 - 80, 10, 160, 20, Component.literal("Search"));

        this.searchBox.setResponder(this::filterList);

        this.searchBox.setHint(Component.literal("Search spells..."));

        this.addRenderableWidget(this.searchBox);

        filterList("");
    }

    public MutableComponent getTranslatedName(Spell spell) {
        return Component.translatable("spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey());
    }

    private void filterList(String searchTerm) {
        this.list.clearEntries();

        String search = searchTerm.toLowerCase();

        SpellRegistry.REGISTRY.entrySet().forEach(entry -> {
            Spell spell = entry.getValue();

            Component spellComponent = getTranslatedName(spell);

            String plainName = spellComponent.getString().toLowerCase(Locale.ROOT);

            if (plainName.contains(search)) {
                this.list.addEntry(this.list.new MySpellEntry(spell));
            }

        });

        this.list.setScrollAmount(0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        list.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}