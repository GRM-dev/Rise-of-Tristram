package ee.rot.libs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.blocks.RotBlocks;

public class CreativeTabsRoT extends CreativeTabs 
{

	public CreativeTabsRoT(String tabLabel)
	{
		super(tabLabel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(RotBlocks.itemModIcon);
	}

}