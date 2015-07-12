package ca.grm.rot.events.entity.damage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.extendprops.ExtendPlayer;

public class EventEntityFall
{
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			ExtendPlayer props = ExtendPlayer.get((EntityPlayer) event.entity);
			event.distance -= (props.getAthleticScore() / 10);
		}
	}
}
