package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MerchantGUIPacket implements IMessage {
	
	public static class MerchantGUIPacketHandler implements IMessageHandler<MerchantGUIPacket, IMessage> 
	{
		@Override
		public IMessage onMessage(MerchantGUIPacket message, MessageContext ctx) 
		{
			// Open the gui on the server so we can actually do stuff.
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			player.openGui(Rot.instance, 2, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}
	}
	
	public MerchantGUIPacket() {

	}
	
	public MerchantGUIPacket(String text) {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	

}
