package com.heroez.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class RecipesTabletDecoder {
    private static final RecipesTabletDecoder tabletDecoderBase = new RecipesTabletDecoder();

    /** The list of decoding results. */
    @SuppressWarnings("rawtypes")
    private Map decoderList = new HashMap();
    @SuppressWarnings("rawtypes")
    private Map decoderExperience = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final RecipesTabletDecoder decoding() {
	return tabletDecoderBase;
    }

    private RecipesTabletDecoder() {
	addSmelting(Block.dirt.blockID, new ItemStack(Block.cobblestone, 1, 0),
		0.7F);
    }

    /**
     * Adds a smelting recipe.
     */
    @SuppressWarnings("unchecked")
    public void addSmelting(int id, ItemStack itemStack, float experience) {
	decoderList.put(Integer.valueOf(id), itemStack);
	this.decoderExperience.put(Integer.valueOf(itemStack.itemID),
		Float.valueOf(experience));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(int id) {
	return (ItemStack) decoderList.get(Integer.valueOf(id));
    }

    @SuppressWarnings("rawtypes")
    public Map getSmeltingList() {
	return decoderList;
    }

    public float getExperience(int par1) {
	return this.decoderExperience.containsKey(Integer.valueOf(par1)) ? ((Float) this.decoderExperience
		.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }
}