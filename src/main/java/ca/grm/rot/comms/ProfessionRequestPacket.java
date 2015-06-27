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

			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (ExtendPlayer.get(player).pickedProfession == RotClassManager.professions[0])
			{
				ExtendPlayer.get(player).setCurrentProfession(message.professionID);
				return new ProfessionResponsePacket(message.professionID);
			}
			else
			{
				if (UtilityFunctions.checkForItemAndAmount(Items.gold_ingot, 3, player.inventory))
				{
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
