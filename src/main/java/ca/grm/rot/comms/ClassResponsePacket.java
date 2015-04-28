package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.Rot;

public class ClassResponsePacket implements IMessage {

	public static class ClassResponsePacketHandler implements IMessageHandler<ClassResponsePacket, IMessage> {
		
		@Override
		public IMessage onMessage(ClassResponsePacket message, MessageContext ctx) {
			Rot.proxy.handleClassMessage(message, ctx);
			return null;
		}
		
	}

	public String	className;

	public ClassResponsePacket() {

	}
	
	public ClassResponsePacket(String className) {
		this.className = className;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.className = ByteBufUtils.readUTF8String(buf); // this class is very
															// useful in general
															// for writing more
															// complex objects
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.className);
	}
	
}
