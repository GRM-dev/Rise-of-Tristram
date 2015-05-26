package ca.grm.rot.comms;

import java.lang.reflect.Field;

import ca.grm.rot.extendprops.ExtendPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TNTPacket implements IMessage
{

	public static class TNTPacketHandler implements IMessageHandler<TNTPacket, IMessage>
	{

		@Override
		public IMessage onMessage(TNTPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			/*
			 * InventoryPlayer ip = player.inventory;
			 * ip.setInventorySlotContents(ip.currentItem, message.item);
			 */
			if (ExtendPlayer.get(player).consumeStam(10f))
			{
				// TODO Miner Skill?, also make it require and consume TNT
				EntityArrow arrow = new EntityArrow(player.worldObj, player, 1f);
				EntityTNTPrimed tnt = new EntityTNTPrimed(player.worldObj);
				tnt.motionX = arrow.motionX;
				tnt.motionY = arrow.motionY;
				tnt.motionZ = arrow.motionZ;
				tnt.setPosition(arrow.posX, arrow.posY, arrow.posZ);
				tnt.fuse = 80;
				tnt.forceSpawn=true;
				player.worldObj.spawnEntityInWorld(tnt);
			}
			return null;
		}

	}

	public ItemStack item;

	public TNTPacket()
	{

	}

	public TNTPacket(ItemStack item)
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
