package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.RotClassManager;
import ca.grm.rot.libs.UtilityFunctions;

public class ClassRequestPacket implements IMessage
{

	public static class ClassRequestPacketHandler implements IMessageHandler<ClassRequestPacket, IMessage>
	{

		@Override
		public IMessage onMessage(ClassRequestPacket message, MessageContext ctx)
		{
			if (message.classID == 0) { return new ClassResponsePacket(ExtendPlayer.get(
					ctx.getServerHandler().playerEntity).getCurrentClassIndex()); }

			System.out.println("got a request to change to: " + message.classID);
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (ExtendPlayer.get(player).pickedClass == RotClassManager.classes[0])
			{
				System.out.println("Yeah player has no class, free change");
				ExtendPlayer.get(player).setCurrentClass(message.classID);
				return new ClassResponsePacket(message.classID);
			}
			else
			{
				System.out.println("Player has a class, gonna cost 27g");
				System.out.println("Current Class is: " + ExtendPlayer.get(player)
						.getCurrentClassName());
				if (UtilityFunctions.checkForItemAndAmount(Items.gold_ingot, 3, player.inventory))
				{
					System.out.println("Player has 3 ingots");
					for (int i = 0; i < 3; i++)
					{
						player.inventory.consumeInventoryItem(Items.gold_ingot);
						player.inventory.markDirty();
					}
					ExtendPlayer.get(player).setCurrentClass(message.classID);
					return new ClassResponsePacket(message.classID);
				}
				else if (UtilityFunctions.checkForItemAndAmount(Items.gold_nugget, 27,
						player.inventory))
				{
					System.out.println("Player has 27 nuggets");
					for (int i = 0; i < 27; i++)
					{
						player.inventory.consumeInventoryItem(Items.gold_nugget);
						player.inventory.markDirty();
					}
					ExtendPlayer.get(player).setCurrentClass(message.classID);
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
