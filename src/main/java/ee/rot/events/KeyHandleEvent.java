package ee.rot.events;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import ee.rot.Rot;
import ee.rot.comms.ClassRequestPacket;
import ee.rot.libs.ExtendPlayer;

public class KeyHandleEvent
{

	@SubscribeEvent
	public void keyHandler(InputEvent.KeyInputEvent e)
	{
		if (Rot.classKey.isPressed())
		{
			//Random r = new Random();
			//Rot.net.sendToServer(new ClassRequestPacket(ExtendPlayer.classNames[r.nextInt(ExtendPlayer.classNames.length)]));
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			FMLNetworkHandler.openGui(player, 
					Rot.instance, 
					1, 
					player.worldObj, 
					(int) player.posX, 
					(int) player.posY,
					(int) player.posZ);
		}
	}
	
}
