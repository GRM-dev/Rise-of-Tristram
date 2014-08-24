package ee.rot.items;

import java.util.Set;

import ee.rot.ExtendPlayerRotManaStam;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemWandManaSteal extends ItemSword
{

	public ItemWandManaSteal(ToolMaterial mat) 
	{
		super(mat);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) 
	{
		
		if (par3EntityLivingBase instanceof EntityPlayer)
		{
			ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get((EntityPlayer)par3EntityLivingBase);
			props.regenMana(1.3f);
		}
		
		return false;
		
		/*return super.hitEntity(par1ItemStack, par2EntityLivingBase,
				par3EntityLivingBase);*/
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) 
	{
		
		/*ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(par3EntityPlayer);
		if (props.consumeMana(5f))
		{
			EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, 3f);
			entityarrow.canBePickedUp = 2;
			entityarrow.setDamage(3);
			if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
            }
		}*/

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
}
