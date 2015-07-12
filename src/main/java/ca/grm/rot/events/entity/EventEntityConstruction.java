package ca.grm.rot.events.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.extendprops.ExtendVillager;

public class EventEntityConstruction
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		// Players
		if ((event.entity instanceof EntityPlayer) && (ExtendPlayer
				.get((EntityPlayer) event.entity) == null))
		{
			ExtendPlayer.register((EntityPlayer) event.entity);
		}
		// Mobs to get extended props
		if ((event.entity instanceof EntityZombie) && (ExtendMob.get((EntityZombie) event.entity) == null) || (event.entity instanceof EntitySkeleton) && (ExtendMob
				.get((EntitySkeleton) event.entity) == null) || (event.entity instanceof EntityEnderman) && (ExtendMob
				.get((EntityEnderman) event.entity) == null) || (event.entity instanceof EntitySpider) && (ExtendMob
				.get((EntitySpider) event.entity) == null) || (event.entity instanceof EntityCaveSpider) && (ExtendMob
				.get((EntityCaveSpider) event.entity) == null))
		{
			ExtendMob.register((EntityLiving) event.entity);
		}
		// Villagers
		if ((event.entity instanceof EntityVillager) && (ExtendVillager
				.get((EntityVillager) event.entity) == null))
		{
			ExtendVillager.register((EntityVillager) event.entity);
		}

	}
}
