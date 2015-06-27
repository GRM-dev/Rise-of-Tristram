package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TextPacket implements IMessage {

	public static class TextPacketHandler implements IMessageHandler<TextPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TextPacket message, MessageContext ctx) {
//			System.out.println(String.format("Received %s from %s", message.text,
//					ctx.getServerHandler().playerEntity.getDisplayName()));
			return null;
		}
		
	}

	public String	text;

	public TextPacket() {

	}
	
	public TextPacket(String text) {
		this.text = text;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.text = ByteBufUtils.readUTF8String(buf); // this class is very
														// useful in general for
														// writing more complex
														// objects
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.text);
	}
	
}
