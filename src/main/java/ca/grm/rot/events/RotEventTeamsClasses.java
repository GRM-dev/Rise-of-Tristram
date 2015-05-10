package ca.grm.rot.events;

import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.libs.ExtendPlayer;

public class RotEventTeamsClasses 
{
	@SubscribeEvent
	public void EntityBlockBreakSpeed(BreakSpeed e) {
		if (ExtendPlayer.get(e.entityPlayer).getCurrentClassName()
				.equals("tony")) {
			e.newSpeed += (MathHelper.clamp_float((ExtendPlayer.get(e.entityPlayer)
					.getStrength() * 2), 0f, 30f));
		} else {
			e.newSpeed += (MathHelper.clamp_float((ExtendPlayer.get(e.entityPlayer)
					.getStrength() / 3), -0.2f, 9f));
		}
	}
}
