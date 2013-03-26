package com.heroez.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.heroez.Heroez;
import com.heroez.lib.BlockIds;
import com.heroez.tileentity.TileEntityTabletDecoder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTabletDecoder extends BlockContainer {

    private Icon decoderTextures[] = new Icon[7];
    private static boolean keepInventory = false;
    private Random tabletDecoderRand;

    public BlockTabletDecoder() {
	super(BlockIds.TABLET_DECODER_DEFAULT, Material.iron);
	setHardness(1.0F);
	setResistance(0.0F);
	setStepSound(Block.soundStoneFootstep);
	setUnlocalizedName("tabletDecoder");
	setCreativeTab(CreativeTabs.tabDecorations);
	tabletDecoderRand = new Random();
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
	return new TileEntityTabletDecoder();
    }

    public int idDropped(int par1, Random random, int xCoord) {
	return Heroez.blockTabletDecoder.blockID;
    }

    public int quantityDropped(Random random) {
	return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
	decoderTextures[0] = iconRegister.registerIcon("heroez:decoderBottom");
	decoderTextures[1] = iconRegister.registerIcon("heroez:decoderTop");
	decoderTextures[2] = iconRegister.registerIcon("heroez:decoderSides");
	decoderTextures[3] = iconRegister.registerIcon("heroez:decoderFront");
	decoderTextures[4] = iconRegister.registerIcon("heroez:decoderSides");
	decoderTextures[5] = iconRegister.registerIcon("heroez:decoderSides");
	decoderTextures[6] = iconRegister
		.registerIcon("heroez:decoderFrontActive");
    }

    // /**
    // * Retrieves the block texture to use based on the display side. Args:
    // * iBlockAccess, x, y, z, side
    // */
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z,
	    int side) {
	TileEntity te = world.getBlockTileEntity(x, y, z);
	int front = 0;

	if (te != null) {
	    front = ((TileEntityTabletDecoder) te).getFrontDirection();
	}

	switch (side) {
	case 0:
	    return decoderTextures[0]; // bottom
	case 1:
	    return decoderTextures[1]; // top
	default:
	    if (side == front) {
		return ((TileEntityTabletDecoder) te).isActive() ? decoderTextures[6]
			: decoderTextures[3];

	    } else {
		return decoderTextures[2]; // sides
	    }
	}
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
	return decoderTextures[side];
    }

    /**
     * Called whenever the block is added into the world.
     */
    public void onBlockAdded(World world, int xCoord, int yCoord, int zCoord) {
	super.onBlockAdded(world, xCoord, yCoord, zCoord);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int xCoord, int yCoord,
	    int zCoord, EntityLiving entity, ItemStack stack) {

	int facing = MathHelper
		.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	TileEntityTabletDecoder blockEntity = (TileEntityTabletDecoder) world
		.getBlockTileEntity(xCoord, yCoord, zCoord);

	switch (facing) {
	case 0:
	    blockEntity.setFrontDirection(2);
	    break;

	case 1:
	    blockEntity.setFrontDirection(5);
	    break;

	case 2:
	    blockEntity.setFrontDirection(3);
	    break;

	case 3:
	    blockEntity.setFrontDirection(4);
	    break;
	}
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
	    EntityPlayer player, int i, float a, float b, float c) {

	// TileEntityTabletDecoder tileEntityTabletDecoder =
	// (TileEntityTabletDecoder) world
	// .getBlockTileEntity(x, y, z);

	player.openGui(Heroez.instance, 0, world, x, y, z);

	return true;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an
     * update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4,
	    int par5, int par6) {
	if (!keepInventory) {
	    TileEntityTabletDecoder decoder = (TileEntityTabletDecoder) par1World
		    .getBlockTileEntity(par2, par3, par4);
	    if (decoder != null) {
		for (int var8 = 0; var8 < decoder.getSizeInventory(); ++var8) {
		    ItemStack item = decoder.getStackInSlot(var8);
		    if (item != null) {
			float var10 = this.tabletDecoderRand.nextFloat() * 0.8F + 0.1F;
			float var11 = this.tabletDecoderRand.nextFloat() * 0.8F + 0.1F;
			float var12 = this.tabletDecoderRand.nextFloat() * 0.8F + 0.1F;
			while (item.stackSize > 0) {
			    int var13 = this.tabletDecoderRand.nextInt(21) + 10;
			    if (var13 > item.stackSize) {
				var13 = item.stackSize;
			    }
			    item.stackSize -= var13;
			    EntityItem var14 = new EntityItem(par1World,
				    (double) ((float) par2 + var10),
				    (double) ((float) par3 + var11),
				    (double) ((float) par4 + var12),
				    new ItemStack(item.itemID, var13,
					    item.getItemDamage()));
			    if (item.hasTagCompound()) {
				var14.getEntityItem().setTagCompound(
					(NBTTagCompound) item.getTagCompound()
						.copy());
			    }
			    float var15 = 0.05F;
			    var14.motionX = (double) ((float) this.tabletDecoderRand
				    .nextGaussian() * var15);
			    var14.motionY = (double) ((float) this.tabletDecoderRand
				    .nextGaussian() * var15 + 0.2F);
			    var14.motionZ = (double) ((float) this.tabletDecoderRand
				    .nextGaussian() * var15);
			    par1World.spawnEntityInWorld(var14);
			}
		    }
		}
	    }
	}
	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
