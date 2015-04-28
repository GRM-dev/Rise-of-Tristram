package ca.grm.rot.libs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.blocks.RotBlocks;

public class CreativeTabsRoT extends CreativeTabs {
	
	public CreativeTabsRoT(String tabLabel) {
		super(tabLabel);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(RotBlocks.itemModIcon);
	}
	
}