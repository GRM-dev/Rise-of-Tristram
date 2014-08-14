package ee.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.Rot;
import ee.rot.comms.TextPacket;

public class ItemManaConverter extends Item {

	private int delayTime = 60;
	private int coolDown = 0;
	
	public ItemManaConverter() {
		super();
		
		setMaxStackSize(1);
	}
		
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub		
		
		Rot.net.sendToServer(new TextPacket("testing"));
		ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(par3EntityPlayer);
		if (par3EntityPlayer.inventory.getFirstEmptyStack() > 0)
		{
			if (props.consumeMana(60f))
			{
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(RotItems.itemMana,1,1));
			}
		}
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("");
		par3List.add("");
		par3List.add("");
		par3List.add("");
		par3List.add("");
	}
	
	/*public boolean checkForItemsAndAmounts(ItemStack[] items, int[] amounts, InventoryPlayer invPlayer)
	{
		if (items.length != amounts.length)//Just to make sure you checked right
		{
			System.out.println("items and amounts do not match up in lengths.");
			return false;
		}
		boolean[] correctAmount = new boolean[items.length];
		int[] amountCounter = new int[amounts.length];
		for (int i = 0; i < correctAmount.length; i++)
		{
			correctAmount[i] = false;
			amountCounter[i] = 0;
		}
		
		//Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory();slot++)
		{
			if (invPlayer.getStackInSlot(slot) != null)
			{
				//If something is in the slot check to see if it matches one of the given items
				for (int item = 0; item < items.length; item++)
				{
					if (invPlayer.getStackInSlot(slot).equals(items[item]) && !correctAmount[item])
					{
						//If the found stack matches the item and don't have the correct amount
						amountCounter[item] += invPlayer.getStackInSlot(slot).stackSize;
						if (amountCounter[item] >= amounts[item])
						{
							correctAmount[item] = true;
						}
						break;//no need to go through the rest of the loop, found the item.
					}
				}
			}
		}
		
		//make sure that all the amounts were found
		for (int i = 0; i < correctAmount.length; i++)
		{
			if (!correctAmount[i])return false;
			//If one of the amounts didn't add up, return false.
		}
		return true;
	}*/
}
