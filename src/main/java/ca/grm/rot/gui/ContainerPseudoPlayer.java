package ca.grm.rot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerPseudoPlayer extends Container{
	private IInventory pseudoPlayerInventory;
    private static final String __OBFID = "RT_00009666"; // Idk what this does, hopefully it doesn't break stuff.
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	public ContainerPseudoPlayer(final InventoryPlayer realPlayerInventory, boolean localWorld, EntityPlayer player)
	{
		int i;
        int j;
		for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(realPlayerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
		
		for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(realPlayerInventory, i, 8 + i * 18, 142));
        }
	}
	
}
