package ee.rot.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemGen extends Container 
{
	private TileEntityItemGenerator ig;
	
	public ContainerItemGen(TileEntityItemGenerator ig)
	{
		System.out.println("container made");
		this.ig = ig;
		
		/*for(int x = 0; x < 9; x++) 
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
		this.addSlotToContainer(new Slot(this.ig, 0, 62, 17 + 18));
		this.addSlotToContainer(new Slot(this.ig, 1, 62 + 18, 17 + 18));
		this.addSlotToContainer(new Slot(this.ig, 2, 62 + 18 * 2, 17 + 18));
		this.addSlotToContainer(new Slot(this.ig, 3, 62 + 18 * 4, 17));
		this.addSlotToContainer(new Slot(this.ig, 4, 62 + 18 * 4, 17 + 18));
		this.addSlotToContainer(new Slot(this.ig, 5, 62 + 18 * 4, 17 + 18 * 2));*/
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
		        } else if(!mergeItemStack(itemstack, 36, 36 + ig.getSizeInventory(), false)) {
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
		return ig.isUseableByPlayer(entityplayer);
	}
}
