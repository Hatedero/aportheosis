package com.hatedero.compendiummod.mana.spell.spellslot;

import com.hatedero.compendiummod.network.SpellData.SpellDataUpdatePayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

import static com.hatedero.compendiummod.mana.ModAttachments.SPELL_DATA;

public class SpellSlotDataHelper {
    private SpellSlotDataHelper() {
    }

    public static void cooldownHandler(Player player, int cooldown) {
        player.sendSystemMessage(Component.literal("APPLY COOLDOWN TO "));
        PlayerSpellData data = player.getData(SPELL_DATA);
        PlayerSpellData newData = new PlayerSpellData(
                data.slots().stream().map(s -> {
                    if (Objects.equals(s.slotName(), data.chargingSlotName())) {
                        player.sendSystemMessage(Component.literal("CHANGED " + s.slotName()));
                        return new SpellSlotData(
                                s.slotName(),
                                s.spellId(),
                                0,
                                cooldown
                        );
                    }
                    return s;
                }).toList(),
                ""
        );
        player.setData(SPELL_DATA, newData);
    }
}
