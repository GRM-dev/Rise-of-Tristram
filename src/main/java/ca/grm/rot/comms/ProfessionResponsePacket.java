package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.Rot;

public class ProfessionResponsePacket implements IMessage
{

	public static class ProfessionResponsePacketHandler implements IMessageHandler<ProfessionResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(ProfessionResponsePacket message, MessageContext ctx)
		{
			Rot.proxy.handleProfessionMessage(message, ctx);
			return null;
		}

	}

	public int professionID;

	public ProfessionResponsePacket()
	{

	}

	public ProfessionResponsePacket(int professionID)
	{
		this.professionID = professionID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.professionID = buf.readInt(); // this class is very
											// useful in general
											// for writing more
											// complex objects
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.professionID);
	}

}
