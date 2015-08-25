package ca.grm.rot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ca.grm.rot.blocks.TileEntityBaseBuilder;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.gui.basebuilder.GuiBaseNode;
import ca.grm.rot.gui.shop.ContainerMerchantPlayer;
import ca.grm.rot.gui.shop.GuiMerchantRot;
import ca.grm.rot.gui.skills.ContainerPseudoPlayer;
import ca.grm.rot.gui.skills.GuiClassSelection;

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
			case 2 : {
				return new GuiMerchantRot(player);
			}
			default :
				return null;
		}
	}

	// 0 is base builder
	// 1 is class Menu
	// 2 is merchant gui
	// 3 is class profession info gui
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
			case 1 : 
				return new ContainerPseudoPlayer(player.inventory, player, ExtendPlayer.get(player).inventory);
			case 2 :
				return new ContainerMerchantPlayer(player.inventory, player); // Gotta change this eventually. 
			case 3 :
				return null; //TODO make this actually open "ClassProfessionInfoGUI"
			default :
				return null;
		}
	}
	
}
