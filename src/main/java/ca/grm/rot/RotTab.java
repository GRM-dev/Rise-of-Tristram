package ca.grm.rot;

import ca.grm.rot.items.RotItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RotTab extends CreativeTabs{

	public RotTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		return RotItems.bronzeNugget;
	}
	
}
