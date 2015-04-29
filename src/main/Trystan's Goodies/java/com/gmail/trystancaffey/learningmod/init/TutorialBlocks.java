package com.gmail.trystancaffey.learningmod.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gmail.trystancaffey.learningmod.Reference;
import com.gmail.trystancaffey.learningmod.TutorialMod;
import com.gmail.trystancaffey.learningmod.blocks.MetalBlocks;

public class TutorialBlocks {
	
	public static Block silver_block;
	public static Block bronze_block;
	public static Block copper_block;
	public static Block lead_block;
	public static Block platinum_block;
	public static Block steel_block;
	public static Block tin_block;
	
	public static void init()
	{
		silver_block = new MetalBlocks(Material.iron).setUnlocalizedName("silver_block").setCreativeTab(TutorialMod.tabRot);
		bronze_block = new MetalBlocks(Material.iron).setUnlocalizedName("bronze_block").setCreativeTab(TutorialMod.tabRot);
		copper_block = new MetalBlocks(Material.iron).setUnlocalizedName("copper_block").setCreativeTab(TutorialMod.tabRot);
		lead_block = new MetalBlocks(Material.iron).setUnlocalizedName("lead_block").setCreativeTab(TutorialMod.tabRot);
		platinum_block = new MetalBlocks(Material.iron).setUnlocalizedName("platinum_block").setCreativeTab(TutorialMod.tabRot);
		steel_block = new MetalBlocks(Material.iron).setUnlocalizedName("steel_block").setCreativeTab(TutorialMod.tabRot);
		tin_block = new MetalBlocks(Material.iron).setUnlocalizedName("tin_block").setCreativeTab(TutorialMod.tabRot);
	}
	
	public static void register()
	{
		GameRegistry.registerBlock(silver_block, silver_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bronze_block, bronze_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(copper_block, copper_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(lead_block, lead_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(platinum_block, platinum_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(steel_block, steel_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tin_block, tin_block.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(silver_block);
		registerRender(bronze_block);
		registerRender(copper_block);
		registerRender(lead_block);
		registerRender(platinum_block);
		registerRender(steel_block);
		registerRender(tin_block);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
