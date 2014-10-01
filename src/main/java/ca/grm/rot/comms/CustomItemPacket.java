package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CustomItemPacket implements IMessage {

	public static class CustomItemPacketHandler implements IMessageHandler<CustomItemPacket, IMessage> {
		
		@Override
		public IMessage onMessage(CustomItemPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			InventoryPlayer ip = player.inventory;
			ip.setInventorySlotContents(ip.currentItem, message.item);
			return null;
		}
		
	}

	public ItemStack item;

	public CustomItemPacket() {

	}
	
	public CustomItemPacket(ItemStack item) {
		this.item = item;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.item = ByteBufUtils.readItemStack(buf); // this class is very
														// useful in general for
														// writing more complex
														// objects
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, this.item);
	}
	
}
