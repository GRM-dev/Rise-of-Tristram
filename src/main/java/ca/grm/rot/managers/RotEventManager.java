package ca.grm.rot.managers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ca.grm.rot.events.RotEventDamage;
import ca.grm.rot.events.RotEventItems;
import ca.grm.rot.events.RotEventLivingUpdate;
import ca.grm.rot.events.RotEventPlayerClone;
import ca.grm.rot.events.RotEventTeamsClasses;
import ca.grm.rot.events.RotFMLEventItems;

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
		//MinecraftForge.EVENT_BUS.register(new RotEventRenderLiving());
		FMLCommonHandler.instance().bus().register(new RotFMLEventItems());
	}
}
