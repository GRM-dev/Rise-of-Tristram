package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.extendprops.ExtendVillager;
import ca.grm.rot.libs.RotShopType;

public class VillagerRequestPacket implements IMessage {

	public static class VillagerRequestHandler implements IMessageHandler<VillagerRequestPacket, IMessage> {
		
		@Override
		public IMessage onMessage(VillagerRequestPacket message, MessageContext ctx) {
			// This happens when the server gets the packet
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Entity o = world.getEntityByID(message.entityID);                   
        	if (o instanceof EntityLiving)
        	{
        		ExtendVillager e = ExtendVillager.get((EntityLiving)o);
        		int villagerShopType = e.shopType.index;
        		String firstName = e.firstName;
        		String lastName = e.lastName;
        		e.needsUpdate = false;
        		return new VillagerResponsePacket(villagerShopType, ((Entity)o).getEntityId(), firstName, lastName);
        	}
			return null;
		}
		
	}

	public int entityID;
	
	public VillagerRequestPacket() {

	}
	
	public VillagerRequestPacket(int entityID) {
		this.entityID = entityID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}
}
