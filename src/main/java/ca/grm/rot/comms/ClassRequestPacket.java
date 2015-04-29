package ca.grm.rot.comms;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityFunctions;

public class ClassRequestPacket implements IMessage {

	public static class ClassRequestPacketHandler implements IMessageHandler<ClassRequestPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ClassRequestPacket message, MessageContext ctx) {
			if (message.className.equals("whatAmI")) { return new ClassResponsePacket(
					ExtendPlayer.get(ctx.getServerHandler().playerEntity)
							.getCurrentClassName()); }

			System.out.println("got a request to change to: " + message.className);
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (ExtendPlayer.get(player).getCurrentClassName()
					.equals(ExtendPlayer.classNames[0])) {
				System.out.println("Yeah player has no class, free change");
				ExtendPlayer.get(player).setCurrentClass(message.className);
				return new ClassResponsePacket(message.className);
			} else {
				System.out.println("Player has a class, gonna cost him/her 27g");
				System.out.println("Current Class is: "
						+ ExtendPlayer.get(player).getCurrentClassName());
				if (UtilityFunctions.checkForItemAndAmount(Items.gold_ingot, 3, player.inventory)) {
					System.out.println("Player has 3 ingots");
					for (int i = 0; i < 3; i++) {
						player.inventory.consumeInventoryItem(Items.gold_ingot);
					}
					ExtendPlayer.get(player).setCurrentClass(message.className);
					return new ClassResponsePacket(message.className);
				} else if (UtilityFunctions.checkForItemAndAmount(Items.gold_nugget, 27,
						player.inventory)) {
					System.out.println("Player has 27 nuggets");
					for (int i = 0; i < 27; i++) {
						player.inventory.consumeInventoryItem(Items.gold_nugget);
					}
					ExtendPlayer.get(player).setCurrentClass(message.className);
					return new ClassResponsePacket(message.className);
				}
			}
			return null;
		}
		
	}

	//TODO Somehow use this to use bytes
	public String	className;

	public ClassRequestPacket() {

	}
	
	public ClassRequestPacket(String className) {
		this.className = className;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.className = ByteBufUtils.readUTF8String(buf); // this class is very
															// useful in general
															// for writing more
															// complex objects
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.className);
	}
	
}
