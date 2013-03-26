package com.heroez.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.heroez.recipes.RecipesTabletDecoder;
import com.heroez.tileentity.TileEntityTabletDecoder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTabletDecoder extends Container {
    private TileEntityTabletDecoder tabletDecoder;
    private int lastTabletDecoderCookTime;
    private int lastTabletDecoderBurnTime;
    private int lastTabletDecoderItemBurnTime;

    public ContainerTabletDecoder(InventoryPlayer par1InventoryPlayer,
	    TileEntityTabletDecoder par2TileEntityTabletDecoder) {
	lastTabletDecoderCookTime = 0;
	lastTabletDecoderBurnTime = 0;
	lastTabletDecoderItemBurnTime = 0;
	tabletDecoder = par2TileEntityTabletDecoder;
	addSlotToContainer(new Slot(par2TileEntityTabletDecoder, 0, 90, 56));
	addSlotToContainer(new Slot(par2TileEntityTabletDecoder, 1, 54, 56));
	addSlotToContainer(new SlotTabletDecoder(par1InventoryPlayer.player,
		par2TileEntityTabletDecoder, 2, 51, 17));

	for (int i = 0; i < 3; i++) {
	    for (int k = 0; k < 9; k++) {
		addSlotToContainer(new Slot(par1InventoryPlayer, k + i * 9 + 9,
			8 + k * 18, 84 + i * 18));
	    }
	}

	for (int j = 0; j < 9; j++) {
	    addSlotToContainer(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
	}
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
    public void detectAndSendChanges() {
	super.detectAndSendChanges();
	for (int i = 0; i < this.crafters.size(); ++i) {
	    ICrafting icrafting = (ICrafting) this.crafters.get(i);

	    if (this.lastTabletDecoderCookTime != this.tabletDecoder.tabletCookTime) {
		icrafting.sendProgressBarUpdate(this, 0,
			this.tabletDecoder.tabletCookTime);
	    }

	    if (this.lastTabletDecoderBurnTime != this.tabletDecoder.tabletBurnTime) {
		icrafting.sendProgressBarUpdate(this, 1,
			this.tabletDecoder.tabletBurnTime);
	    }

	    if (this.lastTabletDecoderItemBurnTime != this.tabletDecoder.tabletItemBurnTime) {
		icrafting.sendProgressBarUpdate(this, 2,
			this.tabletDecoder.tabletItemBurnTime);
	    }
	}
	this.lastTabletDecoderCookTime = this.tabletDecoder.tabletCookTime;
	this.lastTabletDecoderBurnTime = this.tabletDecoder.tabletBurnTime;
	this.lastTabletDecoderItemBurnTime = this.tabletDecoder.tabletItemBurnTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
	if (par1 == 0) {
	    tabletDecoder.tabletCookTime = par2;
	}

	if (par1 == 1) {
	    tabletDecoder.tabletBurnTime = par2;
	}

	if (par1 == 2) {
	    tabletDecoder.tabletItemBurnTime = par2;
	}
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
	return tabletDecoder.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber) {
	ItemStack itemstack = null;
	Slot slot = (Slot) inventorySlots.get(slotnumber);

	if (slot != null && slot.getHasStack()) {
	    ItemStack itemstack1 = slot.getStack();
	    itemstack = itemstack1.copy();

	    if (slotnumber == 2) {
		if (!mergeItemStack(itemstack1, 3, 39, true)) {
		    return null;
		}

		slot.onSlotChange(itemstack1, itemstack);
	    } else if (slotnumber == 1 || slotnumber == 0) {
		if (!mergeItemStack(itemstack1, 3, 39, false)) {
		    return null;
		}
	    } else if (RecipesTabletDecoder.decoding().getSmeltingResult(
		    itemstack1.getItem().itemID) != null) {
		if (!mergeItemStack(itemstack1, 0, 1, false)) {
		    return null;
		}
	    } else if (TileEntityTabletDecoder.isItemFuel(itemstack1)) {
		if (!mergeItemStack(itemstack1, 1, 2, false)) {
		    return null;
		}
	    } else if (slotnumber >= 3 && slotnumber < 30) {
		if (!mergeItemStack(itemstack1, 30, 39, false)) {
		    return null;
		}
	    } else if (slotnumber >= 30 && slotnumber < 39
		    && !mergeItemStack(itemstack1, 3, 30, false)) {
		return null;
	    }

	    if (itemstack1.stackSize == 0) {
		slot.putStack(null);
	    } else {
		slot.onSlotChanged();
	    }

	    if (itemstack1.stackSize == itemstack.stackSize) {
		return null;
	    }

	    slot.onPickupFromSlot(player, itemstack);
	}

	return itemstack;
    }

}
