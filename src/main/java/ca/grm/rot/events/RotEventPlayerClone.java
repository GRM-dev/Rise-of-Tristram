package ca.grm.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.libs.ExtendMob;
import ca.grm.rot.libs.ExtendPlayer;

public class RotEventPlayerClone
{
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if (event.wasDeath)
		{
			if (!event.entityPlayer.worldObj.isRemote)
			{
				ExtendPlayer old = ExtendPlayer.get(event.original);
				ExtendPlayer properties = ExtendPlayer.get(event.entityPlayer);
				properties.setValues(old.getValues());
				Rot.net.sendToServer(new ClassRequestPacket(0));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityPlayer)
		{
			if (!e.entity.worldObj.isRemote)
			{
				ExtendPlayer props = ExtendPlayer.get((EntityPlayer) e.entity);
				props.needsUpdate = true;
				/*
				 * if (props != null) Rot.net.sendTo(new
				 * ClassResponsePacket(props.getCurrentClassIndex()),
				 * (EntityPlayerMP)e.entity); else
				 * System.out.println("something fucked up getting the props");
				 */
			}
			// Rot.net.sendToServer(new ClassRequestPacket("whatAmI"));
		}
	}

	// In your TutEventHandler class - the name of the method doesn't matter
	// Only the Event type parameter is what's important (see below for
	// explanations of some types)
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{

		if ((event.entity instanceof EntityPlayer)
				&& (ExtendPlayer.get((EntityPlayer) event.entity) == null))
		{
			ExtendPlayer.register((EntityPlayer) event.entity);
		}
		if ((event.entity instanceof EntityZombie)
				&& (ExtendMob.get((EntityZombie) event.entity) == null))
		{
			ExtendMob.register((EntityZombie) event.entity);
		}
		if ((event.entity instanceof EntitySkeleton)
				&& (ExtendMob.get((EntitySkeleton) event.entity) == null))
		{
			ExtendMob.register((EntitySkeleton) event.entity);
		}
		if ((event.entity instanceof EntityEnderman)
				&& (ExtendMob.get((EntityEnderman) event.entity) == null))
		{
			ExtendMob.register((EntityEnderman) event.entity);
		}
	}
}
