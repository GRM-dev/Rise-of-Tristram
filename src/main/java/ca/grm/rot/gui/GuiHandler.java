package ca.grm.rot.gui;

import ca.grm.rot.blocks.TileEntityBaseNode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x,
			int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		switch (guiId) {
			case 0 :
				if (entity instanceof TileEntityBaseNode) {
					return new GuiBaseNode((TileEntityBaseNode) entity, player);
				} else {
					return null;
				}
			case 1 : {
				return new GuiClassSelection(player);
			}
			case 2 : {
				return new GuiItemCustom(player);
			}
			default :
				return null;
		}
	}

	// 0 is base builder
	// 1 is class Menu
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x,
			int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch (guiId) {
			case 0 :
				if (entity instanceof TileEntityBaseNode) {
					return null;
					// ContainerPedestal(player.inventory, (TileEntityPedestal)
					// te);
				} else {
					return null;
				}
			default :
				return null;
		}
	}
	
}
