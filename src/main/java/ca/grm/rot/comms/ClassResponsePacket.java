package ca.grm.rot.comms;

import ca.grm.rot.libs.ExtendPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ClassResponsePacket implements IMessage {

	public static class ClassResponsePacketHandler implements IMessageHandler<ClassResponsePacket, IMessage> {
		
		@Override
		public IMessage onMessage(ClassResponsePacket message, MessageContext ctx) {
			System.out.println("got a response about changing to: " + message.className);
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setCurrentClass(
					message.className);
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
