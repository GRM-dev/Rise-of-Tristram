package com.gmail.trystancaffey.learningmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gmail.trystancaffey.learningmod.init.TutorialBlocks;
import com.gmail.trystancaffey.learningmod.init.TutorialItems;
import com.gmail.trystancaffey.learningmod.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class TutorialMod {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final rotTab tabRot = new rotTab("tabRot");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		TutorialBlocks.init();
		TutorialBlocks.register();
		TutorialItems.init();
		TutorialItems.register();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
}
