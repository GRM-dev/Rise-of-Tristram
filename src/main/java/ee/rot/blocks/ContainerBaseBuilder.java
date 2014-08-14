package ee.rot.blocks;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBaseBuilder extends Container 
{
	private TileEntityBaseBuilder bb;
	
	public ContainerBaseBuilder(InventoryPlayer invPlayer, TileEntityBaseBuilder bb)
	{
		this.bb = bb;
		
		for(int x = 0; x < 9; x++) 
		{
			  this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}
		
		for(int y = 0; y < 3; y++) 
		{
			for(int x = 0; x < 9; x++) 
			{
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		/*for(int y = 0; y < 3; y++) 
		{
			  for(int x = 0; x < 3; x++) 
			  {
			        addSlotToContainer(new Slot(bb, x + y * 3, 62 + x * 18, 17 + y * 18));
			  }
		}*/
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) 
	{
		Slot slot = getSlot(par2);

		  if(slot != null && slot.getHasStack()) {
		        ItemStack itemstack = slot.getStack();
		        ItemStack result = itemstack.copy();

		        if(par2 >= 36) {
		          if(!mergeItemStack(itemstack, 0, 36, false)) {
		                return null;
		          }
		        } else if(!mergeItemStack(itemstack, 36, 36 + bb.getSizeInventory(), false)) {
		          return null;
		        }

		        if(itemstack.stackSize == 0) {
		          slot.putStack(null);
		        } else {
		          slot.onSlotChanged();
		        }
		        slot.onPickupFromSlot(par1EntityPlayer, itemstack); 
		        return result;
		  }
		  return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return bb.isUseableByPlayer(entityplayer);
	}
}
