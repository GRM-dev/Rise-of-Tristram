package com.gmail.trystancaffey.learningmod;

import com.gmail.trystancaffey.learningmod.init.TutorialItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class rotTab extends CreativeTabs{

	public rotTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return TutorialItems.bronze_nugget;
	}
	
}
