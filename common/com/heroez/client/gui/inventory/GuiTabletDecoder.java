package com.heroez.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.heroez.container.ContainerTabletDecoder;
import com.heroez.tileentity.TileEntityTabletDecoder;

public class GuiTabletDecoder extends GuiContainer {

    private TileEntityTabletDecoder tabletInventory;

    public GuiTabletDecoder(InventoryPlayer inventory,
	    TileEntityTabletDecoder tablet) {
	super(new ContainerTabletDecoder(inventory, tablet));
	tabletInventory = tablet;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
	fontRenderer.drawString(
		StatCollector.translateToLocal("container.inventory"), 8,
		(ySize - 96) + 2, 0xffffff);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
	    int par3) {
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	mc.renderEngine
		.bindTexture("/mods/heroez/textures/gui/tabletDecoder.png");
	int j = (width - xSize) / 2;
	int k = (height - ySize) / 2;
	drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

	if (tabletInventory.isBurning()) {
	    int burn = tabletInventory.getBurnTimeRemainingScaled(14);
	    drawTexturedModalRect(j + 73, k + 59, 176, 16, burn, 10);
	}

	int update = tabletInventory.getCookProgressScaled(16);
	drawTexturedModalRect(j + 89, k + 55, 191, 15, -update, -update);
    }

}
