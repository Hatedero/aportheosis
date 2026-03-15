package com.hatedero.compendiummod.mana;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.server.level.ServerPlayer;

import static com.hatedero.compendiummod.mana.ModAttachments.*;
import static com.hatedero.compendiummod.mana.ModAttachments.CAST_COOLDOWN;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ManaEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Double currentMana = player.getData(ModAttachments.MANA);
            Double maxMana = player.getAttributeValue(ModAttributes.MAX_MANA);
            Double manaRegen = player.getAttributeValue(ModAttributes.MANA_REGEN) / 20;
            Double manaInput = player.getAttributeValue(ModAttributes.MANA_INPUT);

            if (currentMana < maxMana)
                player.setData(ModAttachments.MANA, currentMana + (manaRegen * manaInput));

            if (currentMana > maxMana)
                player.setData(ModAttachments.MANA, maxMana);

            Level level = player.level();

            if (!level.isClientSide) {
                boolean isCharging = player.getData(ModAttachments.IS_CHARGING);
                int manaCharged = player.getData(CHARGE_TIME);
                int cooldown = player.getData(CAST_COOLDOWN);
                Spell spell = getSpell(player.level(), player.getData(CURRENT_SPELL_ID));

                if (spell == null) {
                    player.setData(IS_CHARGING, false);
                    player.setData(CHARGE_TIME, 0);
                    player.setData(CAST_COOLDOWN, 0);
                    return;
                }

                if (isCharging) {
                    player.setData(ModAttachments.CHARGE_TIME, player.getData(ModAttachments.CHARGE_TIME) + 1);
                    spell.chargeTick(level, player, player.getData(ModAttachments.CHARGE_TIME));
                } else if (cooldown > 0) {
                    player.setData(CAST_COOLDOWN, cooldown - 1);
                } else if (!isCharging && manaCharged > 0) {
                    spell.release(level, player, player.getData(ModAttachments.CHARGE_TIME));
                }
            }
        }
    }

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        ModAttributes.ATTRIBUTES.getEntries().forEach(entry -> {
            event.add(
                    EntityType.PLAYER,
                    entry
            );
        });
    }
}