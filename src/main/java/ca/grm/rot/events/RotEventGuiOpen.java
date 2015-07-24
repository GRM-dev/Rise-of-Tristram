package ca.grm.rot.events;

import net.minecraft.client.gui.GuiMerchant;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.MerchantGUIPacket;

public class RotEventGuiOpen {
	@SubscribeEvent
	public void RotGuiOpenHandler(GuiOpenEvent event)
	{
		if (event.gui instanceof GuiMerchant)
		{
			event.setCanceled(true); // Cancel the event so we prevent the old GUI from opening.
			
			//System.out.println("Shouldn't have opened the GuiMerchant gui but you should be able to read this.");
			
			//Event is fired properly, cancels properly, and the code after the cancel is ran. GUI time!!
			
			Rot.net.sendToServer(new MerchantGUIPacket());
		}
	}
}
