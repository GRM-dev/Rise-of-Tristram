package ee.rot.items;

import scala.Console;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.Rot;

//First item I went out on a limb to make
public class ItemRelicRepair extends Item {

	private int CD = 60;
	private int coolDown = 0;
	private float manaCost = 1.5f;
	
	public ItemRelicRepair() 
	{
		super();		
		setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if (coolDown != 0)coolDown--;
		
		if (coolDown == 0 && !par2World.isRemote)
		{
			EntityPlayer player = (EntityPlayer)par3Entity;	
			ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
			coolDown = CD / 4;
			for (int i = 9; i < player.inventory.getSizeInventory() - 4;i++)
			{
				ItemStack tool = player.inventory.getStackInSlot(i);
				if (tool != null)
				{
					if (tool.isItemDamaged())
					{							
						if (props.consumeMana(manaCost))
						{
							coolDown = CD;
							if (tool.getItemDamage() > 1) 
								tool.setItemDamage(tool.getItemDamage() - 1);
							else 
							{
								tool.setItemDamage(0);
								break;
							}
						}
						else break;
					}
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, java.util.List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will slowly repair");
		par3List.add("non worn, non hotbar items");
		par3List.add("at the cost of "+manaCost+" mana.");
	}
}
