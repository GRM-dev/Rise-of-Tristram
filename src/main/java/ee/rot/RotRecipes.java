package ee.rot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.blocks.RotBlocks;
import ee.rot.items.RotItems;

public class RotRecipes {

	
	public static void init()
	{
		crafting();
		smelting();    	
	}
	
	public static void crafting()
	{
		GameRegistry.addRecipe(new ItemStack(RotItems.itemRelicHeal), 
    			new Object[]{
    				"yyy",
    				"yxy",
    				"yyy",
    				'y', Items.gold_ingot, 
    				'x', Items.speckled_melon});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemRelicRepair), 
    			new Object[]{
    				"yyy",
    				"yxy",
    				"yyy",
    				'y', Items.iron_ingot, 
    				'x', Blocks.anvil});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemRelicLife), 
    			new Object[]{
    				"xyz",
    				'z', RotItems.itemRelicHeal,
    				'y', Items.diamond,
    				'x', RotItems.itemRelicRepair});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemMana, 1, 0), 
    			new Object[]{
					"xx",
					"xx",
    				'x', new ItemStack(RotItems.itemMana,1,1)});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemMana, 1, 2), 
    			new Object[]{
					"xyx",
					"yxy",
					"xyx",
    				'x', 
    				new ItemStack(RotItems.itemMana,1,0),
    				'y',
    				Items.diamond});
		
		GameRegistry.addRecipe(new ItemStack(RotBlocks.itemGen), 
    			new Object[]{
					"xxx",
					"xyx",
					"xxx",
    				'x', 
    				Items.emerald,
    				'y',
    				new ItemStack(RotItems.itemMana,1,2)});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemSwordSoul), 
    			new Object[]{
					"  x",
					"ay ",
					"za ",
    				'x', 
    				Items.ender_eye,
    				'y',
    				Blocks.soul_sand,
    				'z',
    				Items.diamond,
    				'a',
    				Blocks.obsidian});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemManaConverter), 
    			new Object[]{
					"yxy",
    				'x', Blocks.planks,
    				'y', Items.emerald});
		
		GameRegistry.addRecipe(new ItemStack(RotItems.itemCBR,1,0), 
    			new Object[]{
					"zz ",
					"zx ",
					"  y",
    				'x', Items.bow,
    				'y', Items.redstone,
    				'z', Items.iron_ingot});
		
		GameRegistry.addRecipe(new ItemStack(RotBlocks.SB), 
    			new Object[]{
					"xxx",
					"xyx",
					"xxx",
    				'x', Items.blaze_rod,
    				'y', Blocks.netherrack});
	}
	
	public static void smelting()
	{
		GameRegistry.addSmelting(new ItemStack(Items.rotten_flesh), new ItemStack(Items.leather), 1f);
	}
}
