package ca.grm.rot.events;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClientProxy;
import ca.grm.rot.items.WeaponCustom;
import ca.grm.rot.libs.ExtendPlayer;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class KeyHandleEvent {
	
	@SubscribeEvent
	public void keyHandler(InputEvent.KeyInputEvent e) {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		ExtendPlayer props = ExtendPlayer.get(player);
		if (ClientProxy.classKey.isPressed()) {
			FMLNetworkHandler.openGui(player, Rot.instance, 1, player.worldObj,
					(int) player.posX, (int) player.posY, (int) player.posZ);
		}
		if (ClientProxy.customizeItemKey.isPressed()) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() instanceof WeaponCustom) {
					FMLNetworkHandler.openGui(player, Rot.instance, 2, player.worldObj,
							(int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
		}
	}

}
