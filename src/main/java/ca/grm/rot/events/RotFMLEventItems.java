package ca.grm.rot.events;

import java.util.Random;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.RotClassManager;

public class RotFMLEventItems
{
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent e)
	{
		Random random = new Random();
		if (ExtendPlayer.get(e.player).pickedProfession.professionName == RotClassManager.professionBlacksmith) RotEventItems
				.applyItemStats(e.crafting, random,new int[]{1,3},new int[]{2,6});
		else RotEventItems.applyItemStats(e.crafting, random,new int[]{1,3},new int[]{1,4});
	}
}
