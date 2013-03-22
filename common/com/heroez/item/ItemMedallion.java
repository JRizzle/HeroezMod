package com.heroez.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.heroez.lib.ItemIds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMedallion extends Item {

    public ItemMedallion() {
	super(ItemIds.MEDALLION_DEFAULT);
	setMaxStackSize(1);
	setCreativeTab(CreativeTabs.tabMisc);
	// setItemName("itemMedallion");

    }

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister) {
	this.iconIndex = iconRegister.registerIcon("heroez:Medallion");
    }

}