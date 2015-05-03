package ca.grm.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.Rot;
import ca.grm.rot.items.RotItems;

public class RotBlocks {
	
	// TileEntity Blocks
	public static Block baseBuilder;
	
	// These are the metal-blocks
	public static Block silverBlock;
	public static Block bronzeBlock;
	public static Block copperBlock;
	public static Block leadBlock;
	public static Block platinumBlock;
	public static Block steelBlock;
	public static Block tinBlock;
	
	// These are the ore blocks
	public static Block silverOre;
	public static Block copperOre;
	public static Block leadOre;
	public static Block platinumOre;
	public static Block tinOre;
	public static Block galenaOre;
	
	// These are the sand blocks
	public static Block amethystSand;
	public static Block diamondSand;
	public static Block emeraldSand;
	public static Block rubySand;
	public static Block topazSand;
	
	public static void init()
	{
		// Tiles
		baseBuilder = new BlockBaseBuilder().setUnlocalizedName("baseBuilder").setCreativeTab(Rot.tabRot);
		
		// Metals
		silverBlock = new MetalBlocks(Material.iron).setUnlocalizedName("silverBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		bronzeBlock = new MetalBlocks(Material.iron).setUnlocalizedName("bronzeBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		copperBlock = new MetalBlocks(Material.iron).setUnlocalizedName("copperBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		leadBlock = new MetalBlocks(Material.iron).setUnlocalizedName("leadBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		platinumBlock = new MetalBlocks(Material.iron).setUnlocalizedName("platinumBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		steelBlock = new MetalBlocks(Material.iron).setUnlocalizedName("steelBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		tinBlock = new MetalBlocks(Material.iron).setUnlocalizedName("tinBlock").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(10);
		
		// Ores
		silverOre = new OreBlocks(Material.rock).setUnlocalizedName("silverOre").setCreativeTab(Rot.tabRot).setHardness(6).setResistance(5);
		copperOre = new OreBlocks(Material.rock).setUnlocalizedName("copperOre").setCreativeTab(Rot.tabRot).setHardness(5).setResistance(5);
		leadOre = new OreBlocks(Material.rock).setUnlocalizedName("leadOre").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(6);
		platinumOre = new OreBlocks(Material.rock).setUnlocalizedName("platinumOre").setCreativeTab(Rot.tabRot).setHardness(7).setResistance(5);
		tinOre = new OreBlocks(Material.rock).setUnlocalizedName("tinOre").setCreativeTab(Rot.tabRot).setHardness(5).setResistance(5);
		galenaOre = new GalenaOreBlock(Material.rock).setUnlocalizedName("galenaOre").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(4);
		
		// Sands
		amethystSand = new SandBlocks(Material.sand, RotItems.rawAmethyst).setUnlocalizedName("amethystSand").setCreativeTab(Rot.tabRot).setHardness(3).setResistance(3);
		diamondSand = new SandBlocks(Material.sand, RotItems.rawDiamond).setUnlocalizedName("diamondSand").setCreativeTab(Rot.tabRot).setHardness(3).setResistance(3);
		emeraldSand = new SandBlocks(Material.sand, RotItems.rawEmerald).setUnlocalizedName("emeraldSand").setCreativeTab(Rot.tabRot).setHardness(3).setResistance(3);
		rubySand = new SandBlocks(Material.sand, RotItems.rawRuby).setUnlocalizedName("rubySand").setCreativeTab(Rot.tabRot).setHardness(3).setResistance(3);
		topazSand = new SandBlocks(Material.sand, RotItems.rawTopaz).setUnlocalizedName("topazSand").setCreativeTab(Rot.tabRot).setHardness(3).setResistance(3);
	}
	
	public static void register()
	{
		// Tiles
		GameRegistry.registerBlock(baseBuilder, baseBuilder.getUnlocalizedName().substring(5));
		
		// Metals
		GameRegistry.registerBlock(silverBlock, silverBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bronzeBlock, bronzeBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(copperBlock, copperBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(leadBlock, leadBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(platinumBlock, platinumBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(steelBlock, steelBlock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tinBlock, tinBlock.getUnlocalizedName().substring(5));
		
		// Ores
		GameRegistry.registerBlock(silverOre, silverOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(copperOre, copperOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(leadOre, leadOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(platinumOre, platinumOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tinOre, tinOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(galenaOre, galenaOre.getUnlocalizedName().substring(5));
		
		// Sands
		GameRegistry.registerBlock(amethystSand, amethystSand.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(diamondSand, diamondSand.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(emeraldSand, emeraldSand.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(rubySand, rubySand.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(topazSand, topazSand.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		// Tiles
		registerRender(baseBuilder);
		
		// Metals
		registerRender(silverBlock);
		registerRender(bronzeBlock);
		registerRender(copperBlock);
		registerRender(leadBlock);
		registerRender(platinumBlock);
		registerRender(steelBlock);
		registerRender(tinBlock);
		
		// Ores
		registerRender(silverOre);
		registerRender(copperOre);
		registerRender(leadOre);
		registerRender(platinumOre);
		registerRender(tinOre);
		registerRender(galenaOre);
		
		// Sands
		registerRender(amethystSand);
		registerRender(diamondSand);
		registerRender(emeraldSand);
		registerRender(rubySand);
		registerRender(topazSand);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
