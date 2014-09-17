package ee.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.Rot;
import ee.rot.items.ItemBlockMultiTexWithSubtypes;
import ee.rot.items.ItemModIcon;

public class RotBlocks 
{
	//Block Types that are considered placeable by a base node
	public static String[] blockTypes = 
		{"planks","cobble",
		"stone","stonebrick","glass",
		"glasspane","redbrick"};
	public static Block[] blockTypeObjects = 
		{Blocks.planks,Blocks.cobblestone,
		Blocks.stone,Blocks.stonebrick,Blocks.glass,
		Blocks.glass_pane,Blocks.brick_block};
	public static String[] blockTypeLetters =
		{"P","C",
		"S","SB","G",
		"GP","BR"};
	public static int[] blockTypeColors =
		{0xFFBB00,0xAAAAAA,
		0xBBBBBB,0xBBBBBB,0xFFFFFF,
		0xFFFFFF,0xAA0000};
	
	//Blocks to be registered
	public static Block SB;
	public static Block baseBlock;
	public static Block itemGen;
	public static Block myPane;
	
	public static Block itemModIcon;
	
	
	public static void init()
	{
		itemModIcon = new ItemModIcon().setBlockName("rotmodicon").setCreativeTab(Rot.tabRoT).setBlockTextureName(Rot.MODID +":" + "rotDiablo2");
		
		SB = new SpawnerBlock(Material.rock,"netherrack_hot",Blocks.flowing_lava)
			.setCreativeTab(Rot.tabRoT)
			.setBlockName("spawnBlock");
		
		baseBlock = new BlockMultiTexWithSubSets(Material.rock, 
				new String[]{"cobblestone","stone"}, 
				"stonebrick","Builder's Block").setCreativeTab(Rot.tabRoT).
				setBlockName("baseBlock");
		
		
		itemGen = new BlockBaseNode().setBlockName("itemGen").setBlockTextureName("cauldron_inner").setCreativeTab(Rot.tabRoT);
		
		myPane = new BlockPaneRot("stone", "cobblestone", Material.rock, false).setHardness(1.5F).setResistance(10.0F).setBlockName("stoneSheet");
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(SB, 
				SB.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(baseBlock, 
				ItemBlockMultiTexWithSubtypes.class, 
				baseBlock.getUnlocalizedName().substring(5));

		GameRegistry.registerBlock(itemGen, 
				itemGen.getUnlocalizedName().substring(5));
		
		GameRegistry.registerBlock(myPane, 
				myPane.getUnlocalizedName().substring(5));
		
		GameRegistry.registerBlock(itemModIcon, 
				itemModIcon.getUnlocalizedName().substring(5));
	}
}
