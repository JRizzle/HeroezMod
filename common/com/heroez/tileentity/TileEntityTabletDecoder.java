package com.heroez.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.heroez.recipes.RecipesTabletDecoder;

public class TileEntityTabletDecoder extends TileEntity implements IInventory {
    private ItemStack tabletItemStacks[];

    /** The number of ticks that the furnace will keep burning */
    public int tabletBurnTime;

    private boolean isActive;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    public int tabletItemBurnTime;

    /** The number of ticks that the current item has been cooking for */
    public int tabletCookTime;

    public int front;

    public TileEntityTabletDecoder() {
	tabletItemStacks = new ItemStack[3];
	tabletBurnTime = 0;
	tabletItemBurnTime = 0;
	tabletCookTime = 0;
    }

    public void setFrontDirection(int f) {
	this.front = f;
    }

    public int getFrontDirection() {
	return this.front;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
	return tabletItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1) {
	return tabletItemStacks[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of
     * the second int arg. Returns the new stack.
     */
    public ItemStack decrStackSize(int par1, int par2) {
	if (tabletItemStacks[par1] != null) {
	    if (tabletItemStacks[par1].stackSize <= par2) {
		ItemStack itemstack = tabletItemStacks[par1];
		tabletItemStacks[par1] = null;
		return itemstack;
	    }

	    ItemStack itemstack1 = tabletItemStacks[par1].splitStack(par2);

	    if (tabletItemStacks[par1].stackSize == 0) {
		tabletItemStacks[par1] = null;
	    }

	    return itemstack1;
	} else {
	    return null;
	}
    }

    /**
     * When some Containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1) {
	if (tabletItemStacks[par1] != null) {
	    ItemStack itemstack = tabletItemStacks[par1];
	    tabletItemStacks[par1] = null;
	    return itemstack;
	} else {
	    return null;
	}
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
	tabletItemStacks[par1] = par2ItemStack;

	if (par2ItemStack != null
		&& par2ItemStack.stackSize > getInventoryStackLimit()) {
	    par2ItemStack.stackSize = getInventoryStackLimit();
	}
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName() {
	return "container.tabletDecoder";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
	super.readFromNBT(par1NBTTagCompound);
	NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
	tabletItemStacks = new ItemStack[getSizeInventory()];

	for (int i = 0; i < nbttaglist.tagCount(); i++) {
	    NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist
		    .tagAt(i);
	    byte byte0 = nbttagcompound.getByte("Slot");

	    if (byte0 >= 0 && byte0 < tabletItemStacks.length) {
		tabletItemStacks[byte0] = ItemStack
			.loadItemStackFromNBT(nbttagcompound);
	    }
	}

	front = par1NBTTagCompound.getInteger("FrontDirection");
	tabletBurnTime = par1NBTTagCompound.getShort("BurnTime");
	tabletCookTime = par1NBTTagCompound.getShort("CookTime");
	tabletItemBurnTime = getItemBurnTime(tabletItemStacks[1]);

	// System.out.println("front:" + front);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
	super.writeToNBT(par1NBTTagCompound);
	par1NBTTagCompound.setInteger("FrontDirection", (int) front);
	par1NBTTagCompound.setShort("BurnTime", (short) tabletBurnTime);
	par1NBTTagCompound.setShort("CookTime", (short) tabletCookTime);
	NBTTagList nbttaglist = new NBTTagList();

	for (int i = 0; i < tabletItemStacks.length; i++) {
	    if (tabletItemStacks[i] != null) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("Slot", (byte) i);
		tabletItemStacks[i].writeToNBT(nbttagcompound);
		nbttaglist.appendTag(nbttagcompound);
	    }
	}

	par1NBTTagCompound.setTag("Items", nbttaglist);
	System.out.println("write:" + front);
	System.out.println("burn:" + tabletBurnTime);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    public int getInventoryStackLimit() {
	return 64;
    }

    /**
     * Returns an integer between 0 and the passed value representing how close
     * the current item is to being completely cooked
     */
    public int getCookProgressScaled(int par1) {
	return (tabletCookTime * par1) / 200;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much
     * burn time is left on the current fuel item, where 0 means that the item
     * is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1) {
	if (tabletItemBurnTime == 0) {
	    tabletItemBurnTime = 200;
	}

	return (tabletBurnTime * par1) / tabletItemBurnTime;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning() {
	return tabletBurnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    public void updateEntity() {
	boolean var1 = this.tabletBurnTime > 0;
	boolean var2 = false;
	if (this.tabletBurnTime > 0) {
	    --this.tabletBurnTime;
	}
	if (!this.worldObj.isRemote) {
	    if (this.tabletBurnTime == 0 && this.canSmelt()) {
		this.tabletItemBurnTime = this.tabletBurnTime = getItemBurnTime(this.tabletItemStacks[1]);
		if (this.tabletBurnTime > 0) {
		    var2 = true;
		    if (this.tabletItemStacks[1] != null) {
			--this.tabletItemStacks[1].stackSize;
			if (this.tabletItemStacks[1].stackSize == 0) {
			    Item var3 = this.tabletItemStacks[1].getItem()
				    .getContainerItem();
			    this.tabletItemStacks[1] = var3 == null ? null
				    : new ItemStack(var3);
			}
		    }
		}
	    }
	    if (this.isBurning() && this.canSmelt()) {
		++this.tabletCookTime;
		if (this.tabletCookTime == 200) {
		    this.tabletCookTime = 0;
		    this.smeltItem();
		    var2 = true;
		}
	    } else {
		this.tabletCookTime = 0;
	    }
	    if (var1 != this.tabletBurnTime > 0) {
		var2 = true;
		this.validate();
	    }
	}
	boolean check = isActive;
	isActive = isBurning();
	if (isActive != check) {
	    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
		    this.zCoord);
	}
	if (var2) {
	    this.onInventoryChanged();
	}
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canSmelt() {
	if (tabletItemStacks[0] == null) {
	    return false;
	}

	ItemStack itemstack = RecipesTabletDecoder.decoding()
		.getSmeltingResult(tabletItemStacks[0].getItem().itemID);

	if (itemstack == null) {
	    return false;
	}

	if (tabletItemStacks[2] == null) {
	    return true;
	}

	if (!tabletItemStacks[2].isItemEqual(itemstack)) {
	    return false;
	}

	if (tabletItemStacks[2].stackSize < getInventoryStackLimit()
		&& tabletItemStacks[2].stackSize < tabletItemStacks[2]
			.getMaxStackSize()) {
	    return true;
	}

	return tabletItemStacks[2].stackSize < itemstack.getMaxStackSize();
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem() {
	if (this.canSmelt()) {
	    ItemStack var1 = RecipesTabletDecoder.decoding().getSmeltingResult(
		    this.tabletItemStacks[0].getItem().itemID);
	    if (this.tabletItemStacks[2] == null) {
		this.tabletItemStacks[2] = var1.copy();
	    } else if (this.tabletItemStacks[2].itemID == var1.itemID) {
		++this.tabletItemStacks[2].stackSize;
	    }
	    --this.tabletItemStacks[0].stackSize;
	    if (this.tabletItemStacks[0].stackSize == 0) {
		Item var2 = this.tabletItemStacks[0].getItem()
			.getContainerItem();
		this.tabletItemStacks[0] = var2 == null ? null : new ItemStack(
			var2);
	    }
	}
    }

    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */
    public static boolean isItemFuel(ItemStack par0ItemStack) {
	return getItemBurnTime(par0ItemStack) > 0;
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the
     * furnace burning, or 0 if the item isn't fuel
     */
    public static int getItemBurnTime(ItemStack par1ItemStack) {
	if (par1ItemStack == null) {
	    return 0;
	}

	int i = par1ItemStack.getItem().itemID;

	if (i == Item.redstone.itemID) {
	    return 1600;
	} else {
	    return 0;
	}
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
	if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
	    return false;
	}

	return par1EntityPlayer.getDistanceSq((double) xCoord + 0.5D,
		(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
    }

    public void openChest() {
    }

    public void closeChest() {
    }

    public boolean isActive() {
	return this.isActive;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized
     * name, and translated into the player's language. Otherwise it will be
     * used directly.
     */
    @Override
    public boolean isInvNameLocalized() {
	// return this.field_94130_e != null && this.field_94130_e.length() > 0;
	return false;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
	return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack)
		: true);
    }
}