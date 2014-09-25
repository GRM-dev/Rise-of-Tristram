package ca.grm.rot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNull extends Container {
	public ContainerNull() {}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public Slot getSlot(int par1) {
		return null;
	}

	@Override
	public void putStackInSlot(int par1, ItemStack par2ItemStack) {

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}
}
