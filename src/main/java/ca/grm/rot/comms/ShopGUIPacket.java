package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import ca.grm.rot.gui.ContainerPseudoPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ShopGUIPacket implements IMessage {

	public static class ShopGUIPacketHandler implements IMessageHandler<ShopGUIPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ShopGUIPacket message, MessageContext ctx) {
			// Open the gui on the server so we can actually do stuff.
			// TODO Add in a feature that checks if the player is near the entity they clicked on to open the GUI, as an anti-cheat feature.
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			// TODO Actually make a gui to open.
			// TODO Make this GUI openable, see KeyHandleEvent.
			//player.openGui(Rot.instance, 1, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}
	}

	public ShopGUIPacket() {

	}
	
	public ShopGUIPacket(String text) {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	

}
