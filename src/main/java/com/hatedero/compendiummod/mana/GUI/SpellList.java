package com.hatedero.compendiummod.mana.GUI;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;

public class SpellList extends ObjectSelectionList<SpellList.MySpellEntry> {

    public SpellList(Minecraft minecraft, int width, int height, int top, int itemHeight) {
        super(minecraft, width, height, top, itemHeight);
    }

    @Override
    public void clearEntries() {
        super.clearEntries();
    }

    @Override
    public int addEntry(MySpellEntry entry) {
        return super.addEntry(entry);
    }

    public class MySpellEntry extends ObjectSelectionList.Entry<MySpellEntry> {
        private final Spell spell;

        private static final int ICON_SIZE = 18;
        private static final int ICON_SPACING = 2;
        private static final int BUTTON_START_X = 140;
        private static final List<String> SLOTS = List.of("ultimate", "ability_1", "ability_2", "ability_3");

        public MySpellEntry(Spell spell) {
            this.spell = spell;
        }

        public MutableComponent getTranslatedName() {
            return Component.translatable("spell." + SPELLS.getRegistry().get().getKey(spell).toLanguageKey());
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            guiGraphics.drawString(minecraft.font, getTranslatedName(), left + 5, top + (height / 2 - 4), 0xFFFFFF);

            for (int i = 0; i < SLOTS.size(); i++) {
                String slotName = SLOTS.get(i);

                int bx = left + BUTTON_START_X + (i * (ICON_SIZE + ICON_SPACING));
                int by = top + (height / 2 - (ICON_SIZE / 2));

                boolean hovered = mouseX >= bx && mouseX < bx + ICON_SIZE && mouseY >= by && mouseY < by + ICON_SIZE;

                ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "textures/gui/" + slotName + ".png");
                guiGraphics.blit(texture, bx, by, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

                if (hovered) {
                    guiGraphics.fill(bx, by, bx + ICON_SIZE, by + ICON_SIZE, 0x80FFFFFF);

                    guiGraphics.renderOutline(bx, by, ICON_SIZE, ICON_SIZE, 0xFFFFFFFF);
                    String translatedSlotName = Component.translatable("slot." + CompendiumMod.MODID + "." + slotName).getString();

                    guiGraphics.renderTooltip(minecraft.font, Component.literal("Slot: " + translatedSlotName), mouseX, mouseY);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int top = SpellList.this.getRowTop(SpellList.this.children().indexOf(this));
            int left = SpellList.this.getRowLeft();

            for (int i = 0; i < 4; i++) {
                int bx = left + BUTTON_START_X + (i * (ICON_SIZE + ICON_SPACING));
                int by = top + (getHeight() / 2 - (ICON_SIZE / 2));

                if (mouseX >= bx && mouseX < bx + ICON_SIZE && mouseY >= by && mouseY < by + ICON_SIZE) {
                    onMagicSlotClicked(i);
                    return true;
                }
            }

            //PacketDistributor.sendToServer(new CurrentSpellIdUpdatePayload(SpellRegistry.REGISTRY.getKey(spell).getPath()));
            return super.mouseClicked(mouseX, mouseY, button);
        }

        private void onMagicSlotClicked(int slotIndex) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("CLICKED " + SLOTS.get(slotIndex)));

            // Example: PacketDistributor.sendToServer(new BindSpellPayload(spellId, slotIndex));
        }

        @Override
        public Component getNarration() {
            return getTranslatedName();
        }
    }
}