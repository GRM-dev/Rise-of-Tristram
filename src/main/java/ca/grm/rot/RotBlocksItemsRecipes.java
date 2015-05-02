package ca.grm.rot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.blocks.RotBlocks;
import ca.grm.rot.items.RotItems;

// This is the new good stuff.
public class RotBlocksItemsRecipes {
	
	public static void crafting() {
		GameRegistry.addRecipe(new ItemStack(RotItems.bronzeIngot), new Object[]{"x ", "y ", 'x', RotItems.copperIngot, 'y', RotItems.tinIngot});
		GameRegistry.addRecipe(new ItemStack(RotItems.steelIngot), new Object[]{"x ", "y ", 'x', Items.iron_ingot, 'y', Items.coal});
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
	}
}
