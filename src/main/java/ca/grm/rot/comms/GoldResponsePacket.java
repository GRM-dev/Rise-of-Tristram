package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GoldResponsePacket implements IMessage {

	public static class GoldResponseHandler implements IMessageHandler<GoldResponsePacket, IMessage> {
		
		@Override
		public IMessage onMessage(GoldResponsePacket message, MessageContext ctx) {
			Rot.proxy.handleGoldMessage(message, ctx);
			return null;
		}
		
	}

	public int gold;

	public GoldResponsePacket() {
		
	}
	
	public GoldResponsePacket(int gold) {
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.gold = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(gold);
	}
}