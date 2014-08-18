package ee.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.Rot;
import ee.rot.items.ItemBlockMultiTexWithSubtypes;

public class RotBlocks 
{
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
