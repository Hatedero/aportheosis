package com.hatedero.compendiummod.item;

import com.hatedero.compendiummod.CompendiumMod;
import com.hatedero.compendiummod.item.custom.DawnbreakerItem;
import com.hatedero.compendiummod.item.custom.FuelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CompendiumMod.MODID);

    public static final DeferredItem<Item> DAWNBREAKER_HILT = ITEMS.register("dawnbreaker_hilt",
            () -> new FuelItem(new Item.Properties(), 90000));

    public static final DeferredItem<Item> DAWNBREAKER_BLADE = ITEMS.register("dawnbreaker_blade",
            () -> new FuelItem(new Item.Properties(), 100000));

    public static final DeferredItem<Item> DAWNBREAKER_TIP = ITEMS.register("dawnbreaker_tip",
            () -> new FuelItem(new Item.Properties(), 90000));

    public static final DeferredItem<Item> DAWNBREAKER = ITEMS.register("dawnbreaker",
            () -> new DawnbreakerItem(Tiers.NETHERITE,
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 5, 1F))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
