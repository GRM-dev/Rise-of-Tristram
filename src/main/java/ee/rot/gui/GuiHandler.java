package ee.rot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ee.rot.blocks.ContainerNull;
import ee.rot.blocks.TileEntityMagicBase;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity entity = world.getTileEntity(x, y, z);

		switch(guiId) {
		case 0:
		if(entity instanceof TileEntityMagicBase) 
		{
			return null;
		} 
		else 
		{
			return null;
		}
		default:
			return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		switch(guiId) {
		case 0:
		if(entity instanceof TileEntityMagicBase) 
		{
			return new GuiMagicBase((TileEntityMagicBase) entity);
		}  
		else 
		{
			return null;
		}
		default:
			return null;
		}
	}

}
