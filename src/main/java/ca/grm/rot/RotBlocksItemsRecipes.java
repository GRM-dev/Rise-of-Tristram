package ca.grm.rot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.blocks.RotBlocks;
import ca.grm.rot.items.RotItems;

// This is the new good stuff.

public class RotBlocksItemsRecipes {
	public static ItemStack galenaDrops;
	
	public static void crafting() {
		GameRegistry.addRecipe(new ItemStack(RotItems.bronzeIngot), new Object[]{"x ", "y ", 'x', RotItems.copperIngot, 'y', RotItems.tinIngot});
		GameRegistry.addRecipe(new ItemStack(RotItems.steelIngot), new Object[]{"x ", "y ", 'x', Items.iron_ingot, 'y', Items.coal});
		
		GameRegistry.addRecipe(new ItemStack(RotBlocks.silverBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.silverIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.bronzeBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.bronzeIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.copperBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.copperIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.leadBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.leadIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.platinumBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.platinumIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.steelBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.steelIngot});
		GameRegistry.addRecipe(new ItemStack(RotBlocks.tinBlock), new Object[]{"xxx", "xxx", "xxx", 'x', RotItems.tinIngot});
	}

	public static void init() {
		crafting();
		smelting();
	}

	public static void smelting() {
		GameRegistry.addSmelting(new ItemStack(Items.rotten_flesh), new ItemStack(
				Items.leather), 1f); // idk if I should get rid of this, it seems nice.		
		GameRegistry.addSmelting(new ItemStack(RotBlocks.copperOre), new ItemStack(RotItems.copperIngot), 0.8f);
		GameRegistry.addSmelting(new ItemStack(RotBlocks.leadOre), new ItemStack(RotItems.leadIngot), 0.8f);
		GameRegistry.addSmelting(new ItemStack(RotBlocks.platinumOre), new ItemStack(RotItems.platinumIngot), 0.8f);
		GameRegistry.addSmelting(new ItemStack(RotBlocks.silverOre), new ItemStack(RotItems.silverIngot), 0.8f);
		GameRegistry.addSmelting(new ItemStack(RotBlocks.tinOre), new ItemStack(RotItems.tinIngot), 0.8f);
		
		// Galena ore is special and can return lead w/ 50% chance of silver too.
		// This exists incase anyone mines Galena with a silk touch pickaxe
		ArrayList <ItemStack> galenaDrops = new ArrayList<ItemStack>();
		galenaDrops.add(new ItemStack(RotItems.leadIngot));
		Random rand = new Random();
		if (rand.nextFloat() < 0.5F)
	        galenaDrops.add(new ItemStack(RotItems.silverIngot));
		GameRegistry.addSmelting(new ItemStack(RotBlocks.galenaOre), RotBlocksItemsRecipes.galenaDrops, 0.8f);
	}
}
