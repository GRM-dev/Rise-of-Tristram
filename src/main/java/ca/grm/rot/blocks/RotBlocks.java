package ca.grm.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.Rot;

public class RotBlocks {
	
	// These are the metal-blocks
	public static Block silver_block;
	public static Block bronze_block;
	public static Block copper_block;
	public static Block lead_block;
	public static Block platinum_block;
	public static Block steel_block;
	public static Block tin_block;
	
	// These are the ore blocks
	public static Block silverOre;
	public static Block copperOre;
	public static Block leadOre;
	public static Block platinumOre;
	public static Block tinOre;
	public static Block galenaOre;
	
	public static void init()
	{
		// Metals
		silver_block = new MetalBlocks(Material.iron).setUnlocalizedName("silver_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		bronze_block = new MetalBlocks(Material.iron).setUnlocalizedName("bronze_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		copper_block = new MetalBlocks(Material.iron).setUnlocalizedName("copper_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		lead_block = new MetalBlocks(Material.iron).setUnlocalizedName("lead_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		platinum_block = new MetalBlocks(Material.iron).setUnlocalizedName("platinum_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		steel_block = new MetalBlocks(Material.iron).setUnlocalizedName("steel_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		tin_block = new MetalBlocks(Material.iron).setUnlocalizedName("tin_block").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		
		// Ores
		silverOre = new OreBlocks(Material.rock).setUnlocalizedName("silverOre").setCreativeTab(Rot.tabRot).setHardness(6).setResistance(5);
		copperOre = new OreBlocks(Material.rock).setUnlocalizedName("copperOre").setCreativeTab(Rot.tabRot).setHardness(5).setResistance(5);
		leadOre = new OreBlocks(Material.rock).setUnlocalizedName("leadOre").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(6);
		platinumOre = new OreBlocks(Material.rock).setUnlocalizedName("platinumOre").setCreativeTab(Rot.tabRot).setHardness(7).setResistance(5);
		tinOre = new OreBlocks(Material.rock).setUnlocalizedName("tinOre").setCreativeTab(Rot.tabRot).setHardness(5).setResistance(5);
		galenaOre = new GalenaOreBlock(Material.rock).setUnlocalizedName("galenaOre").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(4);
	}
	
	public static void register()
	{
		// Metals
		GameRegistry.registerBlock(silver_block, silver_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bronze_block, bronze_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(copper_block, copper_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(lead_block, lead_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(platinum_block, platinum_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(steel_block, steel_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tin_block, tin_block.getUnlocalizedName().substring(5));
		
		// Ores
		GameRegistry.registerBlock(silverOre, silverOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(copperOre, copperOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(leadOre, leadOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(platinumOre, platinumOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tinOre, tinOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(galenaOre, galenaOre.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		// Metals
		registerRender(silver_block);
		registerRender(bronze_block);
		registerRender(copper_block);
		registerRender(lead_block);
		registerRender(platinum_block);
		registerRender(steel_block);
		registerRender(tin_block);
		
		// Ores
		registerRender(silverOre);
		registerRender(copperOre);
		registerRender(leadOre);
		registerRender(platinumOre);
		registerRender(tinOre);
		registerRender(galenaOre);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void registerRenderGalena(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
