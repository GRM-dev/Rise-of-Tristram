package ca.grm.rot.events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;

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
		FMLCommonHandler.instance().bus().register(new RotFMLEventItems());
	}
}
