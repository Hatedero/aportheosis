package com.hatedero.compendiummod.event;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.mana.ModAttachments;
import com.hatedero.compendiummod.mana.ModAttributes;
import com.hatedero.compendiummod.mana.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.hatedero.compendiummod.mana.ModAttachments.CAST_COOLDOWN;
import static com.hatedero.compendiummod.mana.ModAttachments.CURRENT_SPELL_ID;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.SPELLS;
import static com.hatedero.compendiummod.mana.spell.SpellRegistry.getSpell;

@EventBusSubscriber(modid = CompendiumMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        ModAttributes.ATTRIBUTES.getEntries().forEach(entry -> {
            event.add(
                    EntityType.PLAYER,
                    entry
            );
        });
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (!level.isClientSide) {
            boolean isCharging = player.getData(ModAttachments.IS_CHARGING);
            int cooldown = player.getData(CAST_COOLDOWN);

            if (isCharging) {
                player.setData(ModAttachments.CHARGE_TIME, player.getData(ModAttachments.CHARGE_TIME) + 1 );
                Spell spell = getSpell(player.level(), player.getData(CURRENT_SPELL_ID));
                spell.chargeTick(level, player, player.getData(ModAttachments.CHARGE_TIME));
            } else if (cooldown > 0) {
                player.setData(CAST_COOLDOWN,  cooldown - 1);
            }
        }
    }
}
