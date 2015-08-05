package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.Rot;

public class VillagerResponsePacket implements IMessage {

	public static class VillagerResponsePacketHandler implements IMessageHandler<VillagerResponsePacket, IMessage> {
		
		@Override
		public IMessage onMessage(VillagerResponsePacket message, MessageContext ctx) {
			// This happens when the client gets the server packet
			Rot.proxy.handleVillagerData(message, ctx);
			return null;
		}
		
	}
	
	public int villagerShopType;
	public int entityID;
	public String firstName;
	public String lastName;
	
	public VillagerResponsePacket() {
		
	}
	
	public VillagerResponsePacket(int villagerShopType, int entityID, String firstName, String lastName) {
		this.entityID = entityID;
		this.villagerShopType = villagerShopType;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		villagerShopType = buf.readInt();
		firstName = ByteBufUtils.readUTF8String(buf);
		lastName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(villagerShopType);
		ByteBufUtils.writeUTF8String(buf, firstName);
		ByteBufUtils.writeUTF8String(buf, lastName);
	}

}
