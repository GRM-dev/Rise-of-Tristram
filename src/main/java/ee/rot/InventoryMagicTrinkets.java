package ee.rot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryMagicTrinkets implements IInventory
{
	/** The name your custom inventory will display in the GUI, possibly just "Inventory" */
	private final String name = "Tinkets Inventory";

	/** The key used to store and retrieve the inventory from NBT */
	private final String tagName = "trinkInv";

	/** Define the inventory size here for easy reference */
	// This is also the place to define which slot is which if you have different types,
	// for example SLOT_SHIELD = 0, SLOT_AMULET = 1;
	public static final int INV_SIZE = 4;

	/** Inventory's size must be same as number of slots you add to the Container class */
	private ItemStack[] inventory = new ItemStack[INV_SIZE];

	public InventoryMagicTrinkets()
	{
		
	}
	
	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		// TODO Auto-generated method stub
		return inventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		ItemStack stack = getStackInSlot(var1);
		if (stack != null)
		{
			if (stack.stackSize > var2)
			{
				stack = stack.splitStack(var2);
				if (stack.stackSize == 0)
				{
					setInventorySlotContents(var1, null);
				}
			}
			else
			{
				setInventorySlotContents(var1, null);
			}
	
			this.markDirty();
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		ItemStack stack = getStackInSlot(var1);
		if (stack != null)
		{
			setInventorySlotContents(var1, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		this.inventory[var1] = var2;

		if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
		{
			var2.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public String getInventoryName()
	{
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void markDirty()
	{
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null && this.getStackInSlot(i).stackSize == 0)
			this.setInventorySlotContents(i, null);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	public void writeToNBT(NBTTagCompound compound)
	{
		NBTTagList items = new NBTTagList();
	
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}

	// We're storing our items in a custom tag list using our 'tagName' from above
	// to prevent potential conflicts
	compound.setTag(tagName, items);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		NBTTagList items = compound.getTagList(tagName,compound.getId());
		for (int i = 0; i < items.tagCount(); ++i) 
		{
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < getSizeInventory()) 
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

}
