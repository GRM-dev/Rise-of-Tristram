package ee.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.Rot;
import ee.rot.items.ItemBlockMultiTexWithSubtypes;

public class RotBlocks 
{
	//Block Types that are considered placeable by a base node
	public static String[] blockTypes = 
		{"air","planks","cobble",
		"stone","stonebrick","glass",
		"glasspane","redbrick"};
	public static Block[] blockTypeObjects = 
		{Blocks.air,Blocks.planks,Blocks.cobblestone,
		Blocks.stone,Blocks.stonebrick,Blocks.glass,
		Blocks.glass_pane,Blocks.brick_block};
	public static String[] blockTypeLetters =
		{"A","P","C",
		"S","SB","G",
		"GP","BR"};
	public static int[] blockTypeColors =
		{0x00DDFF,0xFFBB00,0xAAAAAA,
		0xBBBBBB,0xBBBBBB,0xFFFFFF,
		0xFFFFFF,0xAA0000};
	
	//blocks that are natural but need to be rendered in a gui
	public static Block[] naturalBlockTypeObjects = 
		{Blocks.dirt,Blocks.grass,Blocks.leaves,
		Blocks.leaves2,Blocks.log,Blocks.log2,
		Blocks.sand,Blocks.sandstone,Blocks.gravel};
	public static String[] naturalBlockTypeLetters =
		{"d","g","l",
		"l","t","t",
		"s","s","g"};
	public static int[] naturalBlockTypeColors =
		{0x885500,0x008800,0x00AA00,
		0x00AA00,0x997700,0x997700,
		0xAAAA00,0xAAAA00,0xAAAAAA};
	
	//Blocks to be registered
	public static Block SB;
	public static Block baseBlock;
	public static Block itemGen;
	public static Block myPane;
	
	public static void init()
	{
		
		SB = new SpawnerBlock(Material.rock,"netherrack_hot",Blocks.flowing_lava)
			.setCreativeTab(Rot.tabRoT)
			.setBlockName("spawnBlock");
		
		baseBlock = new BlockMultiTexWithSubSets(Material.rock, 
				new String[]{"cobblestone","stone"}, 
				"stonebrick","Builder's Block").setCreativeTab(Rot.tabRoT).
				setBlockName("baseBlock");
		
		
		itemGen = new BlockMagicBase().setBlockName("itemGen").setBlockTextureName("cauldron_inner").setCreativeTab(Rot.tabRoT);
		
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
	}
}
