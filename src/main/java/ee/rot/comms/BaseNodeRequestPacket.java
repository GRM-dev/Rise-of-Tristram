package ee.rot.comms;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.rot.blocks.RotBlocks;
import ee.rot.blocks.TileEntityBaseNode;

public class BaseNodeRequestPacket implements IMessage 
{
	public int actionType;//0 is add, 1 is clear, 2 is build
	public int xTe,yTe,zTe;
	public int xB,yB,zB;
	public ItemStack is;
	
	public BaseNodeRequestPacket()
	{
		
	}
	
	public BaseNodeRequestPacket(int action,int xTe, int yTe, int zTe, int xB, int yB, int zB, ItemStack is)
	{
		this.actionType = action;
		this.xTe = xTe;
		this.yTe = yTe;
		this.zTe = zTe;
		this.xB = xB;
		this.yB = yB;
		this.zB = zB;
		this.is = is;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		actionType = buf.readInt();
		xTe = buf.readInt();
		yTe = buf.readInt();
		zTe = buf.readInt();
		xB = buf.readInt();
		yB = buf.readInt();
		zB = buf.readInt();
		is = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(actionType);
		buf.writeInt(xTe);
		buf.writeInt(yTe);
		buf.writeInt(zTe);
		buf.writeInt(xB);
		buf.writeInt(yB);
		buf.writeInt(zB);
		ByteBufUtils.writeItemStack(buf, is);
	}
	
	public static class BaseNodeRequestPacketHandler implements IMessageHandler<BaseNodeRequestPacket, IMessage> 
	{

		@Override
		public IMessage onMessage(BaseNodeRequestPacket message, MessageContext ctx) 
		{			
			TileEntityBaseNode te;
			// 0 = add, 1 = clear, 2 = start
			switch (message.actionType)
			{			
				case 0:
					Block bl = Block.getBlockFromItem(message.is.getItem());
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.addLocation(message.xB, message.yB, message.zB, Block.getBlockFromItem(message.is.getItem()));
					break;
				case 1:
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.clearLocations();
					break;
				case 2:
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.startBuilding();
					break;
			}
			return null;
		}

	}

}
