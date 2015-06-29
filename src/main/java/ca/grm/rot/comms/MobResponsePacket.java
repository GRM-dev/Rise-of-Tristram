package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendMob;

public class MobResponsePacket implements IMessage {

	public static class MobResponsePacketHandler implements IMessageHandler<MobResponsePacket, IMessage> {
		
		@Override
		public IMessage onMessage(MobResponsePacket message, MessageContext ctx) {
			// This happens when the client gets the server packet
			Rot.proxy.handleMobData(message, ctx);
			return null;
		}
		
	}

	public int monsterLevel, strength, agility, dexterity, vitality, minDmg, maxDmg, defBonus, gold, isBoss;
	public String prefix, bossPrefix2, bossPrefix3, bossPrefix4, suffix;
	public float hpRegenBonusPercent;
	public int entityID;
	
	public MobResponsePacket() {
		
	}
	
	/** Int array takes: monsterLevel, Str, Agi, Dex, Vit, minDmg, maxDmg, defBonus, gold, isBoss.
	 * String Array takes: prefix, bossPrefix2, bossPrefix3, bossPrefix4, suffix.
	 * Float is hpRegenBonusPercent.
	 * @param intArray
	 * @param stringArray
	 * @param floatParameter
	 */
	public MobResponsePacket(int[] intArray, String[] stringArray, float floatParameter, int entityID) {
		monsterLevel = intArray[0];
		strength = intArray[1];
		agility = intArray[2];
		dexterity = intArray[3];
		vitality = intArray[4];
		minDmg = intArray[5];
		maxDmg = intArray[6];
		defBonus = intArray[7];
		gold = intArray[8];
		isBoss = intArray[9];
		this.entityID = entityID;
		
		prefix = stringArray[0];
		bossPrefix2 = stringArray[1];
		bossPrefix3 = stringArray[2];
		bossPrefix4 = stringArray[3];
		suffix = stringArray[4];
		
		hpRegenBonusPercent = floatParameter;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		monsterLevel = buf.readInt();
		strength = buf.readInt();
		agility = buf.readInt();
		dexterity = buf.readInt();
		vitality = buf.readInt();
		minDmg = buf.readInt();
		maxDmg = buf.readInt();
		defBonus = buf.readInt();
		gold = buf.readInt();
		isBoss = buf.readInt();
		entityID = buf.readInt();
		
		prefix = ByteBufUtils.readUTF8String(buf);
		bossPrefix2 = ByteBufUtils.readUTF8String(buf);
		bossPrefix3 = ByteBufUtils.readUTF8String(buf);
		bossPrefix4 = ByteBufUtils.readUTF8String(buf);
		suffix = ByteBufUtils.readUTF8String(buf);
		
		hpRegenBonusPercent = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(monsterLevel);
		buf.writeInt(strength);
		buf.writeInt(agility);
		buf.writeInt(dexterity);
		buf.writeInt(vitality);
		buf.writeInt(minDmg);
		buf.writeInt(maxDmg);
		buf.writeInt(defBonus);
		buf.writeInt(gold);
		buf.writeInt(isBoss);
		buf.writeInt(entityID);
		
		ByteBufUtils.writeUTF8String(buf, prefix);
		ByteBufUtils.writeUTF8String(buf, bossPrefix2);
		ByteBufUtils.writeUTF8String(buf, bossPrefix3);
		ByteBufUtils.writeUTF8String(buf, bossPrefix4);
		ByteBufUtils.writeUTF8String(buf, suffix);
		
		buf.writeFloat(hpRegenBonusPercent);
	}

}
