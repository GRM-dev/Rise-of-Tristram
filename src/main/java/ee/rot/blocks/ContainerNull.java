package ee.rot.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNull extends Container 
{	
	public ContainerNull(){}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) 
	{
		  return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) 
	{
		return true;
	}
}
