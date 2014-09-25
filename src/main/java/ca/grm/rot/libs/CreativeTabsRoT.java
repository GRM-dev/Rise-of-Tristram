package ca.grm.rot.libs;

import ca.grm.rot.blocks.RotBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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