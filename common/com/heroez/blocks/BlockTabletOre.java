package com.heroez.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;

import com.heroez.Heroez;
import com.heroez.lib.BlockIds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTabletOre extends BlockOre {

    public BlockTabletOre() {
	super(BlockIds.TABLET_ORE_DEFAULT);
	setHardness(3.0F);
	setResistance(2.0F);
	setStepSound(Block.soundStoneFootstep);
	setUnlocalizedName("tabletOre");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
	this.blockIcon = par1IconRegister.registerIcon("heroez:tabletOre");
    }

    public int idDropped(int par1, Random random, int par2) {
	return Heroez.itemTablet.itemID;
    }

    public int quantityDropped(Random rand) {
	return rand.nextInt(3);
    }
}
