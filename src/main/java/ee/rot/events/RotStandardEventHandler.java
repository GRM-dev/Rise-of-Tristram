package ee.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.items.ItemRelicLife;
import ee.rot.items.RotItems;

public class RotStandardEventHandler 
{
	// In your TutEventHandler class - the name of the method doesn't matter
	// Only the Event type parameter is what's important (see below for explanations of some types)	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		// This event has an Entity variable, access it like this:
		//event.entity;
	
		// do something to player every update tick:
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
			float timeMath = 3* 60 * 10;
			if (player.isPlayerFullyAsleep())
			{
				props.replenishMana();
				props.replenishStam();
			}
			if (player.isInWater())
			{
				props.regenStam(30f/60 + ((player.experienceLevel)*2)/timeMath);
			}
			else
			{
				props.regenMana(10f/timeMath + (player.experienceLevel)/timeMath);
				props.regenStam(30f/timeMath + ((player.experienceLevel)*2)/timeMath);
			}			
		}
		else if (event.entity instanceof EntityIronGolem)
		{
			EntityIronGolem golem = (EntityIronGolem) event.entity;
			golem.heal(0.05f);
		}
		else if (event.entity instanceof EntityVillager)
		{
			EntityVillager villager = (EntityVillager) event.entity;
			villager.heal(0.075f);
		}
		else if (event.entity instanceof EntityWolf)
		{
			EntityWolf wolf = (EntityWolf) event.entity;
			wolf.heal(0.05f);
		}
		else if (event.entity instanceof EntityHorse)
		{
			EntityHorse horse = (EntityHorse) event.entity;
			horse.heal(0.05f);
		}
		else
		{
			EntityLiving e = (EntityLiving) event.entity;
			if(e.hurtResistantTime != 5)
			{
				e.hurtResistantTime = 5;
				e.arrowHitTimer = 1;
			}
			e.heal(0.0025f);
		}
	}
}
