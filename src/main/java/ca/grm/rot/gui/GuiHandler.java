package ca.grm.rot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ca.grm.rot.blocks.TileEntityBaseBuilder;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x,
			int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		switch (guiId) {
			case 0 :
				if (entity instanceof TileEntityBaseBuilder) {
					return new GuiBaseNode((TileEntityBaseBuilder) entity, player);
				} else {
					return null;
				}
			case 1 : {
				return new GuiClassSelection(player);
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
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (guiId) {
			case 0 :
				if (entity instanceof TileEntityBaseBuilder) {
					return null;
					//ContainerPedestal(player.inventory, (TileEntityPedestal)te);
				} else {
					return null;
				}
			default :
				return null;
		}
	}
	
}
