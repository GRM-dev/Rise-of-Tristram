package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import ca.grm.rot.gui.skills.ContainerPseudoPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClassGUIPacket implements IMessage {

	public static class ClassGUIPacketHandler implements IMessageHandler<ClassGUIPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ClassGUIPacket message, MessageContext ctx) {
			// Open the gui on the server so we can actually do stuff.
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			player.openGui(Rot.instance, 1, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}
		
	}

	public ClassGUIPacket() {

	}
	
	public ClassGUIPacket(String text) {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	

}
