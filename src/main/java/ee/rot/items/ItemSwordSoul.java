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
					if (props.consumeMana(4f))
					{
						target.setFire(4);
					}
					if (props.consumeStam(4f))
					{						
						target.heal(-4);
						attacker.heal(2);
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
					props.regenMana(target.getMaxHealth() * 0.15f);
					props.regenStam(target.getMaxHealth() * 0.15f);
				}
				attacker.heal(target.getMaxHealth() * 0.05f);				
			}	
			else
			{
				if (is.getItemDamage() > 9)is.setItemDamage(is.getItemDamage() - 9);
				else is.setItemDamage(0);
			}
		}		
		return true;
	}

}
