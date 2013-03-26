package com.heroez.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.heroez.client.gui.inventory.GuiTabletDecoder;
import com.heroez.container.ContainerTabletDecoder;
import com.heroez.tileentity.TileEntityTabletDecoder;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
	    int x, int y, int z) {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	if (ID == 0) {
	    return new GuiTabletDecoder(player.inventory,
		    (TileEntityTabletDecoder) tileEntity);
	}

	return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
	    int x, int y, int z) {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	if (ID == 0) {
	    return new ContainerTabletDecoder(player.inventory,
		    (TileEntityTabletDecoder) tileEntity);
	}

	return null;
    }
}