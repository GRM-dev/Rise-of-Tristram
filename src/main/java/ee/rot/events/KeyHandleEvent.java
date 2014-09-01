package ee.rot.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import ee.rot.Rot;

public class KeyHandleEvent
{

	@SubscribeEvent
	public void keyHandler(InputEvent.KeyInputEvent e)
	{
		if (Rot.classKey.isPressed())
		{
			System.out.println("open the class menu!");
		}
	}
	
}
