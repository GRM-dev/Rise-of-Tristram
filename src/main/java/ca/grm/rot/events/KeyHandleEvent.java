package ca.grm.rot.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import org.lwjgl.input.Keyboard;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassGUIPacket;
import ca.grm.rot.comms.ClassProfessionInfoGUIPacket;
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
			//FMLNetworkHandler.openGui(player, Rot.instance, 1, player.worldObj, (int) player.posX,(int) player.posY, (int) player.posZ);
			Rot.net.sendToServer(new ClassGUIPacket());
		}
		if (ClientProxy.skill1.isPressed())
		{
			Rot.net.sendToServer(new EnderPearlPacket());
		}
		if (ClientProxy.skill2.isPressed())
		{
			Rot.net.sendToServer(new TNTPacket());
		}
		if (Minecraft.getMinecraft().thePlayer.getClientBrand() == "ToxicShad0w" || Minecraft.getMinecraft().thePlayer.getClientBrand() == "Hugo_the_Dwarf")
		{
			if (new KeyBinding("", Keyboard.KEY_C, "").isPressed())
			{
				// This is where the super secret give-player-OP-gear code goes.
				// TODO write the super secret give-player-OP-gear code.
				// For testing I'm gonna open a gui
				Rot.net.sendToServer(new ClassProfessionInfoGUIPacket());
			}
		}
	}

}
