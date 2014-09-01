package ee.rot.items;

import ee.rot.ExtendPlayerRot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSwordSoul extends ItemSword
{

	public ItemSwordSoul(ToolMaterial mat) 
	{
		super(mat);
	}
	
	/*@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5)
	{
		if (par3Entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)par3Entity;
			if (player.getHeldItem() != null && player.getHeldItem() == par1ItemStack)
			{
				ExtendPlayerRot props = ExtendPlayerRot.get(player);
				props.setIntelligence(20);
				props.setStrength(20);
				props.setVitality(20);
				if (player.isSprinting())
				{
					if (props.consumeStam(.25f))
					{
						player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 0, true));
						player.addPotionEffect(new PotionEffect(Potion.jump.id, 1, 0, true));
					}
				}
				if (player.isInWater())
				{
					if (props.consumeStam(.25f))
					{
						player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 1, 0, true));
					}
				}
				if (player.isSneaking())
				{
					if (props.consumeStam(.25f))
					{
						player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 3, 1, true));
					}
				}
			}
		}
	}*/
	
	@Override
	public boolean hitEntity(ItemStack is,EntityLivingBase target,EntityLivingBase attacker) 
	{
		
		if (target.isEntityAlive())
			{
				if (attacker instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)attacker;
					ExtendPlayerRot props = ExtendPlayerRot.get(player);
					if (props.consumeMana(4f))
					{
						//target.setFire(4);
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
					ExtendPlayerRot props = ExtendPlayerRot.get(player);
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
