package ca.grm.rot.events;

import net.minecraftforge.common.MinecraftForge;

public class RotEventManager 
{
	public RotEventManager()
	{
		
	}
	
	public static void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RotEventLivingUpdate());
		MinecraftForge.EVENT_BUS.register(new RotEventDamage());
		MinecraftForge.EVENT_BUS.register(new RotEventItems());
		MinecraftForge.EVENT_BUS.register(new RotEventPlayerClone());
		MinecraftForge.EVENT_BUS.register(new RotEventTeamsClasses());
	}
}
