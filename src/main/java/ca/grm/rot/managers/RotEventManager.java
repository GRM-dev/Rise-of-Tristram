package ca.grm.rot.managers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ca.grm.rot.events.EventItemToolTip;
import ca.grm.rot.events.FMLEventItems;
import ca.grm.rot.events.RotEventGuiOpen;
import ca.grm.rot.events.RotEventTeamsClasses;
import ca.grm.rot.events.entity.EventEntityConstruction;
import ca.grm.rot.events.entity.EventEntityJoin;
import ca.grm.rot.events.entity.EventEntityJump;
import ca.grm.rot.events.entity.EventEntityLivingUpdate;
import ca.grm.rot.events.entity.EventPlayerClone;
import ca.grm.rot.events.entity.FMLEventPlayerTick;
import ca.grm.rot.events.entity.damage.EventEntityDamage;
import ca.grm.rot.events.entity.damage.EventEntityDeath;
import ca.grm.rot.events.entity.damage.EventEntityFall;

public class RotEventManager
{
	public RotEventManager()
	{

	}

	public static void registerEvents()
	{
		FMLCommonHandler.instance().bus().register(new FMLEventPlayerTick());
		MinecraftForge.EVENT_BUS.register(new EventEntityLivingUpdate());

		MinecraftForge.EVENT_BUS.register(new EventPlayerClone());
		MinecraftForge.EVENT_BUS.register(new EventEntityDamage());
		MinecraftForge.EVENT_BUS.register(new EventEntityDeath());
		MinecraftForge.EVENT_BUS.register(new EventEntityFall());

		MinecraftForge.EVENT_BUS.register(new EventEntityJump());

		FMLCommonHandler.instance().bus().register(new FMLEventItems());
		MinecraftForge.EVENT_BUS.register(new EventItemToolTip());

		MinecraftForge.EVENT_BUS.register(new EventEntityConstruction());
		MinecraftForge.EVENT_BUS.register(new EventEntityJoin());

		MinecraftForge.EVENT_BUS.register(new RotEventTeamsClasses());
		
		MinecraftForge.EVENT_BUS.register(new RotEventGuiOpen());
		// MinecraftForge.EVENT_BUS.register(new RotEventRenderLiving());
		// //moved to client proxy like keyhandler

	}
}
