package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.libs.UtilityFunctions;
import ca.grm.rot.managers.RotClassManager;

public class ClassRequestPacket implements IMessage
{

	public static class ClassRequestPacketHandler implements IMessageHandler<ClassRequestPacket, IMessage>
	{

		@Override
		public IMessage onMessage(ClassRequestPacket message, MessageContext ctx)
		{
			final int goldCost = 500;
			
			if (message.classID == 0) { return new ClassResponsePacket(ExtendPlayer.get(
					ctx.getServerHandler().playerEntity).getCurrentClassIndex()); }
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			ExtendPlayer props = ExtendPlayer.get(player);
			if (props.pickedClass == RotClassManager.classes[0])
			{
				props.setCurrentClass(message.classID);
				return new ClassResponsePacket(message.classID);
			}
			else
			{
				
//				if (UtilityFunctions.checkForItemAndAmount(Items.gold_ingot, 3, player.inventory))
//				{
//					for (int i = 0; i < 3; i++)
//					{
//						player.inventory.consumeInventoryItem(Items.gold_ingot);
//						player.inventory.markDirty();
//					}
//					ExtendPlayer.get(player).setCurrentClass(message.classID);
//					return new ClassResponsePacket(message.classID);
//				}
//				else if (UtilityFunctions.checkForItemAndAmount(Items.gold_nugget, 27,
//						player.inventory))
//				{
//					for (int i = 0; i < 27; i++)
//					{
//						player.inventory.consumeInventoryItem(Items.gold_nugget);
//						player.inventory.markDirty();
//					}
//					ExtendPlayer.get(player).setCurrentClass(message.classID);
//					return new ClassResponsePacket(message.classID);
//				}
				if (props.getGold() >= goldCost)
				{
					props.setGold(props.getGold() - goldCost);
					return new ClassResponsePacket(message.classID);
				}
			}
			return null;
		}

	}

	public int classID;

	public ClassRequestPacket()
	{

	}

	public ClassRequestPacket(int className)
	{
		this.classID = className;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.classID = buf.readInt(); 
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.classID);
	}

}
