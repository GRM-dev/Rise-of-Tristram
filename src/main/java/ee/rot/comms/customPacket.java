package ee.rot.comms;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class customPacket implements IMessage 
{
	
	public String text;
	
	public customPacket()
	{
		
	}
	
	public customPacket(String text)
	{
		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		 ByteBufUtils.writeUTF8String(buf, text);
	}
	
	public static class customPacketHandler implements IMessageHandler<customPacket, IMessage> 
	{

		@Override
		public IMessage onMessage(customPacket message, MessageContext ctx) 
		{
			System.out.println(String.format("Received %s from %s", 
					message.text, 
					ctx.getServerHandler().playerEntity.getDisplayName()));
			return null;
		}

	}

}
