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

public class BaseNodeResponsePacket implements IMessage 
{
	public int actionType;//0 is add, 1 is clear, 2 is build
	public int xTe,yTe,zTe;
	public int xB,yB,zB;
	public int blockId;
	
	public BaseNodeResponsePacket()
	{
		
	}
	
	public BaseNodeResponsePacket(int action,int xTe, int yTe, int zTe, int xB, int yB, int zB, int blockId)
	{
		this.actionType = action;
		this.xTe = xTe;
		this.yTe = yTe;
		this.zTe = zTe;
		this.xB = xB;
		this.yB = yB;
		this.zB = zB;
		this.blockId = blockId;
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
		blockId = buf.readInt();
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
		buf.writeInt(blockId);
	}
	
	public static class BaseNodeResponsePacketHandler implements IMessageHandler<BaseNodeResponsePacket, IMessage> 
	{

		@Override
		public IMessage onMessage(BaseNodeResponsePacket message, MessageContext ctx) 
		{			
			TileEntityBaseNode te;
			// 0 = add, 1 = clear, 2 = start, 3 = update client
			switch (message.actionType)
			{			
				case 0:
					te = (TileEntityBaseNode)Minecraft.getMinecraft().thePlayer.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.addLocation(message.xB, message.yB, message.zB, Block.getBlockById(message.blockId));
					break;
				case 1:
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.clearLocations();
					break;
				case 2:
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.startBuilding();
					break;
				case 3:
					te = (TileEntityBaseNode)ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.xTe, message.yTe, message.zTe);
					te.updateClient(ctx.getServerHandler().playerEntity);
					break;
			}
			return null;
		}

	}

}
