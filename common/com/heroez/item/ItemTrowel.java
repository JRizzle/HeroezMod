package com.heroez.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemTool;

import com.heroez.Heroez;

public class ItemTrowel extends ItemTool {
    public static final Block[] blocksEffectiveAgainst = new Block[] { Heroez.blockTabletOre };
    public float efficiencyOnProperMaterial = 10.0F;

    public ItemTrowel(int itemId, EnumToolMaterial enumToolMaterial) {
	super(itemId, 2, enumToolMaterial, blocksEffectiveAgainst);
    }
}
