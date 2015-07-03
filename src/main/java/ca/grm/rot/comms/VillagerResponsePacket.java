package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendVillager;
import ca.grm.rot.libs.RotShopType;

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
	
	public VillagerResponsePacket() {
		
	}
	
	public VillagerResponsePacket(int villagerShopType, int entityID) {
		this.entityID = entityID;
		this.villagerShopType = villagerShopType;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		villagerShopType = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(villagerShopType);
	}

}
