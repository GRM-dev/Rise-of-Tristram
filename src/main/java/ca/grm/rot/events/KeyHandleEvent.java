package ca.grm.rot.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClientProxy;
import ca.grm.rot.comms.EnderPearlPacket;
import ca.grm.rot.comms.TNTPacket;
import ca.grm.rot.extendprops.ExtendPlayer;

public class KeyHandleEvent
{

	@SubscribeEvent
	public void keyHandler(InputEvent.KeyInputEvent e)
	{
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		ExtendPlayer props = ExtendPlayer.get(player);
		if (ClientProxy.classKey.isPressed())
		{
			FMLNetworkHandler.openGui(player, Rot.instance, 1, player.worldObj, (int) player.posX,
					(int) player.posY, (int) player.posZ);
		}
		if (ClientProxy.skill1.isPressed())
		{
			Rot.net.sendToServer(new EnderPearlPacket());
		}
		if (ClientProxy.skill2.isPressed())
		{
			Rot.net.sendToServer(new TNTPacket());
		}
	}

}
