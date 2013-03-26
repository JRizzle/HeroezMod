package com.heroez.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.heroez.Heroez;

public class RecipesTabletDecoder {
    private static final RecipesTabletDecoder tabletDecoderBase = new RecipesTabletDecoder();

    /** The list of decoding results. */
    private Map<Integer, ItemStack> decoderList = new HashMap<Integer, ItemStack>();
    private Map<Integer, Float> decoderExperience = new HashMap<Integer, Float>();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final RecipesTabletDecoder decoding() {
	return tabletDecoderBase;
    }

    private RecipesTabletDecoder() {
	addSmelting(Heroez.itemTablet.itemID, new ItemStack(Block.cobblestone,
		1, 0), 0.7F);
    }

    /**
     * Adds a smelting recipe.
     */
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

    public Map<Integer, ItemStack> getSmeltingList() {
	return decoderList;
    }

    public float getExperience(int par1) {
	return this.decoderExperience.containsKey(Integer.valueOf(par1)) ? ((Float) this.decoderExperience
		.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }
}