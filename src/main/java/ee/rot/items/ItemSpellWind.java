package ee.rot.items;

import ibxm.Player;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;

public class ItemSpellWind extends Item
{
	private float spellCost = 15f;
	
	public ItemSpellWind() 
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
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 3, 5));
			}			
		}
		
        return itemstack;
    }
}
