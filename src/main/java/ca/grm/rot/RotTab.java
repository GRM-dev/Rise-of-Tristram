package ca.grm.rot;

import ca.grm.rot.items.TutorialItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RotTab extends CreativeTabs{

	public RotTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return TutorialItems.bronze_nugget;
	}
	
}
