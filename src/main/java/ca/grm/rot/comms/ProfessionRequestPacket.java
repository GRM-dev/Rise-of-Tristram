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

public class ProfessionRequestPacket implements IMessage
{

	public static class ProfessionRequestPacketHandler implements IMessageHandler<ProfessionRequestPacket, IMessage>
	{

		@Override
		public IMessage onMessage(ProfessionRequestPacket message, MessageContext ctx)
		{
			if (message.professionID == 0) { return new ProfessionResponsePacket(ExtendPlayer.get(
					ctx.getServerHandler().playerEntity).getCurrentProfessionIndex()); }

			System.out.println("got a request to change to: " + message.professionID);
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (ExtendPlayer.get(player).pickedProfession == RotClassManager.professions[0])
			{
				System.out.println("Yeah player has no profession, free change");
				ExtendPlayer.get(player).setCurrentProfession(message.professionID);
				return new ProfessionResponsePacket(message.professionID);
			}
			else
			{
				System.out.println("Player has a profession, gonna cost 27g");
				System.out.println("Current Profession is: " + ExtendPlayer.get(player)
						.getCurrentProfessionName());
				if (UtilityFunctions.checkForItemAndAmount(Items.gold_ingot, 3, player.inventory))
				{
					System.out.println("Player has 3 ingots");
					for (int i = 0; i < 3; i++)
					{
						player.inventory.consumeInventoryItem(Items.gold_ingot);
						player.inventory.markDirty();
					}
					ExtendPlayer.get(player).setCurrentProfession(message.professionID);
					return new ProfessionResponsePacket(message.professionID);
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
					ExtendPlayer.get(player).setCurrentProfession(message.professionID);
					return new ProfessionResponsePacket(message.professionID);
				}
			}
			return null;
		}

	}

	public int professionID;

	public ProfessionRequestPacket()
	{

	}

	public ProfessionRequestPacket(int className)
	{
		this.professionID = className;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.professionID = buf.readInt(); 
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.professionID);
	}

}
