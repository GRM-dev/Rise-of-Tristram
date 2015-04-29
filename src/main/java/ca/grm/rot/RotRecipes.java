package ca.grm.rot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.blocks.RotBlocksOld;
import ca.grm.rot.items.RotItemsOld;

public class RotRecipes {
	
	public static void crafting() {
		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemRelicHeal), new Object[]{
				"yyy", "yxy", "yyy", 'y', Items.gold_ingot, 'x', Items.speckled_melon});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemRelicRepair), new Object[]{
				"yyy", "yxy", "yyy", 'y', Items.iron_ingot, 'x', Blocks.anvil});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemRelicLife), new Object[]{
				"xyz", 'z', RotItemsOld.itemRelicHeal, 'y', Items.diamond, 'x',
				RotItemsOld.itemRelicRepair});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemMana, 1, 0), new Object[]{
				"xx", "xx", 'x', new ItemStack(RotItemsOld.itemMana, 1, 1)});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemMana, 1, 2), new Object[]{
				"xyx", "yxy", "xyx", 'x', new ItemStack(RotItemsOld.itemMana, 1, 0), 'y',
				Items.diamond});

		GameRegistry.addRecipe(new ItemStack(RotBlocksOld.itemGen), new Object[]{
				"xxx", "xyx", "xxx", 'x', Items.emerald, 'y',
				new ItemStack(RotItemsOld.itemMana, 1, 2)});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.weaponSwordSoul), new Object[]{
				"  x", "ay ", "za ", 'x', Items.ender_eye, 'y', Blocks.soul_sand, 'z',
				Items.diamond, 'a', Blocks.obsidian});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.itemManaConverter), new Object[]{
				"yxy", 'x', Blocks.planks, 'y', Items.emerald});

		GameRegistry.addRecipe(new ItemStack(RotItemsOld.weaponCBR, 1, 0), new Object[]{
				"zz ", "zx ", "  y", 'x', Items.bow, 'y', Items.redstone, 'z',
				Items.iron_ingot});

		GameRegistry.addRecipe(new ItemStack(RotBlocksOld.SB), new Object[]{
				"xxx", "xyx", "xxx", 'x', Items.blaze_rod, 'y', Blocks.netherrack});
	}

	public static void init() {
		crafting();
		smelting();
	}

	public static void smelting() {
		GameRegistry.addSmelting(new ItemStack(Items.rotten_flesh), new ItemStack(
				Items.leather), 1f);
	}
}
