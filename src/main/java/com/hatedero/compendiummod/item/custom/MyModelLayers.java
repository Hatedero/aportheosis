package com.hatedero.compendiummod.item.custom;

import com.hatedero.compendiummod.CompendiumMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class MyModelLayers {
    public static final ModelLayerLocation MASK_LAYER = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "mask"),
        "main"
    );

    public static final ModelLayerLocation BOOTS_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CompendiumMod.MODID, "boots"),
            "main"
    );
}