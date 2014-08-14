package ee.rot.items;

import ee.rot.ExtendPlayerRotManaStam;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemSwordSoul extends ItemSword
{

	public ItemSwordSoul(ToolMaterial mat) 
	{
		super(mat);
	}
	
	@Override
	public boolean hitEntity(ItemStack is,EntityLivingBase target,EntityLivingBase attacker) 
	{
		
		if (target.isEntityAlive())
			{
				if (attacker instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)attacker;
					ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
					if (props.consumeStam(4f))
					{
						target.setFire(4);
						target.heal(-1);
					}
				}
				is.damageItem(1, attacker);
				
			}
		
		
		if (!target.isEntityAlive())
		{	
			if (attacker.getHealth() < attacker.getMaxHealth())
			{				
				if (attacker instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)attacker;
					ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
					props.regenMana(target.getMaxHealth() * 0.05f);
					props.regenStam(target.getMaxHealth() * 0.05f);
				}
				attacker.heal(target.getMaxHealth() * 0.05f);				
			}	
			else
			{
				if (is.getItemDamage() > 5)is.setItemDamage(is.getItemDamage() - 5);
				else is.setItemDamage(0);
			}
		}		
		return true;
	}

}
