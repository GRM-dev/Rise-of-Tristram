package ca.grm.rot.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClientProxy;
import ca.grm.rot.items.WeaponCustom;
import ca.grm.rot.libs.ExtendPlayer;

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
