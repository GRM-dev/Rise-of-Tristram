package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendMob;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MobRequestPacket implements IMessage {

	public static class MobRequestHandler implements IMessageHandler<MobRequestPacket, IMessage> {
		
		@Override
		public IMessage onMessage(MobRequestPacket message, MessageContext ctx) {
			// This happens when the server gets the packet
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Entity o = world.getEntityByID(message.entityID);                   
        	if (o instanceof EntityLiving)
        	{
        		ExtendMob e = ExtendMob.get((EntityLiving)o);
        		int isBossInt;
        		if (e.isBoss())
        		{
        			isBossInt = 1;
        		}
        		else
        		{
        			isBossInt = 0;
        		}
        		int[] responseIntArray = new int[]{e.monsterLevel, e.strength, e.agility, e.dexterity, e.vitality, e.minDmg, e.maxDmg, e.defBonus, e.gold, isBossInt};
        		String[] responseStringArray = new String[] {e.prefix, e.bossPrefix2, e.bossPrefix3, e.bossPrefix4, e.suffix};
        		return new MobResponsePacket(responseIntArray, responseStringArray, e.getHpRegenBonusPercent(), ((Entity)o).getEntityId());
        	}
			return null;
		}
		
	}

	public int entityID;
	
	public MobRequestPacket() {

	}
	
	public MobRequestPacket(int entityID) {
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
