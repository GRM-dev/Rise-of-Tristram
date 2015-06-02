package ca.grm.rot.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.items.RotItems;

public class RotEventPlayerPickup {
	@SubscribeEvent
	public void onEventPlayerPickup(ItemPickupEvent event)
	{
		// TODO make this actually work. But right now fuck this im going to fucking bed im so fucking mad rn why the fuck wont this work?
		if (event.pickedUp.getEntityItem() == new ItemStack(RotItems.gold, event.pickedUp.getEntityItem().stackSize))
		{
			System.out.println("Fuck you.");
			ExtendPlayer.get(event.player).addGold(event.pickedUp.getEntityItem().stackSize);
		}
	}
}
