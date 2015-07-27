package ca.grm.rot.events;

import java.util.Random;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.UtilItemStats;
import ca.grm.rot.managers.RotClassManager;

public class FMLEventItems
{
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent e)
	{
		Random random = new Random();
		if (ExtendPlayer.get(e.player).pickedProfession.professionName == RotClassManager.professionBlacksmith) UtilItemStats
				.applyItemStats(e.crafting, random,new int[]{1,3},new int[]{3,6});
		else UtilItemStats.applyItemStats(e.crafting, random,new int[]{1,2},new int[]{2,4});
	}
	
	@SubscribeEvent
	public void onEventPlayerPickup(ItemPickupEvent event)
	{
		if (event.pickedUp.getEntityItem().getItem() == RotItems.gold)
		{
			
		}
	}
}
