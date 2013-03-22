package com.heroez.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.heroez.lib.ItemIds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTablet extends Item {

    public ItemTablet() {
	super(ItemIds.TABLET_DEFAULT);
	setMaxStackSize(64);
	setCreativeTab(CreativeTabs.tabMisc);
	setUnlocalizedName("itemTablet");

    }

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister) {
	this.iconIndex = iconRegister.registerIcon("heroez:itemTablet");
    }

}