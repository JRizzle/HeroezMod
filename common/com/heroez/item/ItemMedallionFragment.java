package com.heroez.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.heroez.lib.ItemIds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMedallionFragment extends Item {

    public ItemMedallionFragment() {
	super(ItemIds.MEDALLION_FRAGMENT_DEFAULT);
	setMaxStackSize(64);
	setCreativeTab(CreativeTabs.tabMisc);
	setUnlocalizedName("itemMedallionFragment");
    }

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister) {
	this.iconIndex = iconRegister.registerIcon("heroez:MedallionFragment");
    }
}