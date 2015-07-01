package ca.grm.rot.gui;

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

public class ContainerPseudoPlayer extends Container{
	private IInventory pseudoPlayerInventory;
	private static final int ARMOR_START = 3, ARMOR_END = ARMOR_START+3,
			INV_START = ARMOR_END+1, INV_END = INV_START+26, HOTBAR_START = INV_END+1,
			HOTBAR_END = HOTBAR_START+8;
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	public ContainerPseudoPlayer(InventoryPlayer realPlayerInventory, EntityPlayer player)
	{
		int i;
        int j;
        
        // Inventory
		for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(realPlayerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
		
		// Hotbar
		for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(realPlayerInventory, i, 8 + i * 18, 142));
        }
		
		// Trinkets
		for (i = 0; i < 3; i++)
		{
			this.addSlotToContainer(new Slot(new RotTrinketInventory(), i, 26, 8 + i * 18));
		}
		
		// Armor, literally copy-pasted from container-player
		 for (i = 0; i < 4; ++i)
	        {
	            final int k = i;
	            this.addSlotToContainer(new Slot(realPlayerInventory, realPlayerInventory.getSizeInventory() - 1 - i, 8, 8 + i * 18)
	            {
	                public int getSlotStackLimit()
	                {
	                    return 1;
	                }
	                public boolean isItemValid(ItemStack stack)
	                {
	                    if (stack == null) return false;
	                    return stack.getItem().isValidArmor(stack, k, player);
	                }
	                @SideOnly(Side.CLIENT)
	                public String getSlotTexture()
	                {
	                    return ItemArmor.EMPTY_SLOT_NAMES[k];
	                }
	            });
	        }
	}

	// This is the method that prevents you from crashing when you shift click.
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
				// if item is armor
				else if (itemstack1.getItem() instanceof ItemArmor)
				{
					int type = ((ItemArmor) itemstack1.getItem()).armorType;
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type,ARMOR_START + type + 1, false))
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
