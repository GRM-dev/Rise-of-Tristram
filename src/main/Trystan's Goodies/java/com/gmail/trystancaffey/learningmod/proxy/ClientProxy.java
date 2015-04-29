package com.gmail.trystancaffey.learningmod.proxy;

import com.gmail.trystancaffey.learningmod.init.TutorialBlocks;
import com.gmail.trystancaffey.learningmod.init.TutorialItems;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders()
	{
		TutorialBlocks.registerRenders();
		TutorialItems.registerRenders();
	}
}
