package ca.grm.rot.gui.shop;

import ca.grm.rot.inventory.RotTrinketInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMerchantPlayer extends Container{
	private IInventory playerInventoryClone;
	private static final int INV_START = 3, INV_END = INV_START+26, HOTBAR_START = INV_END+1,
			HOTBAR_END = HOTBAR_START+8;
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	public ContainerMerchantPlayer(InventoryPlayer realPlayerInventory, EntityPlayer player)
	{
		int i;
        int j;
        
        // Inventory
		for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            { 
                this.addSlotToContainer(new Slot(realPlayerInventory, j + (i + 1) * 9, 6 + j * 18, 86 + i * 18));
            }
        }
		
		// Hotbar
		for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(realPlayerInventory, i, 6+ i * 18, 142));
        }
	}

	// This is the method that prevents you from crashing when you shift click.
	// Just saying I copy/pasted this, and removed a tiny bit, it might behave really badly. Lets just hope we don't crash.
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			// Either armor slot or custom item slot was clicked
			if (par2 < INV_START)
			{
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1,
						true))
				{
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place either in custom or
			// armor slots
			else
			{
				// if item is our custom item
				if (itemstack1.getItem() instanceof Item) // Change this later to be the amulet or whatever.
				{
					if (!this.mergeItemStack(itemstack1, 0,3, false))
					{
						return null;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= INV_START && par2 < HOTBAR_START)
				{
					// place in action bar
					if (!this.mergeItemStack(itemstack1, HOTBAR_START,HOTBAR_START + 1, false))
					{
						return null;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1)
				{
					if (!this.mergeItemStack(itemstack1, INV_START,INV_END + 1, false))
					{
						return null;
					}
				}
			}
			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
}
