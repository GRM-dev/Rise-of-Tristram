package ee.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.Rot;

//First item I went out on a limb to make
public class ItemSpellJump extends Item {
	
	private float spellCost = 10f;
	
	public ItemSpellJump() 
	{
		super();
		
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    	{
		if (!world.isRemote)
		{
			ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
			
			if (props.consumeMana(spellCost))
			{
				//player.setPosition(player.posX, player.posY + 5, player.posZ);
				player.posY += 5;
			}			
		}
		
        return itemstack;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		
	}
}
