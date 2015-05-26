package ca.grm.rot.comms;

import ca.grm.rot.extendprops.ExtendPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EnderPearlPacket implements IMessage
{

	public static class EnderPearlPacketHandler implements
			IMessageHandler<EnderPearlPacket, IMessage>
	{

		@Override
		public IMessage onMessage(EnderPearlPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			/*
			 * InventoryPlayer ip = player.inventory;
			 * ip.setInventorySlotContents(ip.currentItem, message.item);
			 */
			if (ExtendPlayer.get(player).consumeMana(10f))
			{
				EntityArrow arrow = new EntityArrow(player.worldObj, player, 1f);
				EntityEnderPearl ep = new EntityEnderPearl(player.worldObj, player);
//				EntityEnderPearl ep = new EntityEnderPearl(player.worldObj,
//						arrow.motionX, arrow.motionY, arrow.motionZ);
				player.worldObj.spawnEntityInWorld(ep);
			}
			return null;
		}

	}

	public ItemStack item;

	public EnderPearlPacket()
	{

	}

	public EnderPearlPacket(ItemStack item)
	{
		this.item = item;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.item = ByteBufUtils.readItemStack(buf); // this class is very
														// useful in general for
														// writing more complex
														// objects
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, this.item);
	}

}
