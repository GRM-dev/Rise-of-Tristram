package ee.rot.items;

import java.util.List;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.RotOld;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunpowderInfuser extends Item 
{
	private int delayTime = 60;
	private int coolDown = 0;
	
	public ItemGunpowderInfuser() {
		super();
		
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		
		ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(par3EntityPlayer);
		if (par3EntityPlayer.inventory.hasItem(Items.gunpowder) && !par3EntityPlayer.isSneaking())
		{
			if (props.consumeMana(75f))
			{
				par3EntityPlayer.inventory.consumeInventoryItem(Items.gunpowder);
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Blocks.tnt));
			}
		}
		else if (par3EntityPlayer.inventory.hasItem(Items.gunpowder) && par3EntityPlayer.isSneaking())
		{
			if (props.consumeMana(25f))
			{
				par3EntityPlayer.inventory.consumeInventoryItem(Items.gunpowder);
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.coal,4));
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
		par3List.add("The infuser can take");
		par3List.add("gunpowder and infuse it");
		par3List.add("with mana to create a");
		par3List.add("block of TNT.");
		par3List.add("Costs 75MP.");
		par3List.add("Sneak to turn 1");
		par3List.add("gunpowder into 4");
		par3List.add("coal.");
		par3List.add("Costs 25MP.");
	}
}
