package ee.rot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerMagicTrinkets extends Container
{
	/** Avoid magic numbers! This will greatly reduce the chance of you making errors in 'transferStackInSlot' method */
	private static final int ARMOR_START = InventoryMagicTrinkets.INV_SIZE, ARMOR_END = ARMOR_START+3,
	INV_START = ARMOR_END+1, INV_END = INV_START+26, HOTBAR_START = INV_END+1,
	HOTBAR_END = HOTBAR_START+8;
	
	public ContainerMagicTrinkets (EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryMagicTrinkets inventoryTrinket)
	{
		int i;

		// Add CUSTOM slots - we'll just add two for now, both of the same type.
		// Make a new Slot class for each different item type you want to add
		this.addSlotToContainer(new Slot(inventoryTrinket, 0, 80, 8));
		this.addSlotToContainer(new Slot(inventoryTrinket, 1, 80, 26));

		// Add ARMOR slots; note you need to make a public version of SlotArmor
		// just copy and paste the vanilla code into a new class and change what you need
		for (i = 0; i < 4; ++i)
		{
		this.addSlotToContainer(new Slot(inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18));
		}

		// Add vanilla PLAYER INVENTORY - just copied/pasted from vanilla classes
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Add ACTION BAR - just copied/pasted from vanilla classes
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		// TODO Auto-generated method stub
		return true;
	}
}
	
