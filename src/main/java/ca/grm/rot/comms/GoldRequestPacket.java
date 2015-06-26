package ca.grm.rot.comms;

import ca.grm.rot.extendprops.ExtendPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GoldRequestPacket implements IMessage {

	public static class GoldRequestHandler implements IMessageHandler<GoldRequestPacket, IMessage> {
		
		@Override
		public IMessage onMessage(GoldRequestPacket message, MessageContext ctx) {
			// This happens when the server gets the packet
			System.out.println("Server has recieved gold request packet. " + ExtendPlayer.get(ctx.getServerHandler().playerEntity).getGold());
			return new GoldResponsePacket(ExtendPlayer.get(ctx.getServerHandler().playerEntity).getGold());
		}
		
	}

	public GoldRequestPacket() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {}
	
}
