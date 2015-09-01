package ca.grm.rot.managers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ca.grm.rot.events.EventItemToolTip;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.UtilItemStats;
import ca.grm.rot.libs.UtilityFunctions;

public class RotLootManager
{
	public RotLootManager()
	{

	}

	public static final int lootRange = 5;
	private static int numberOfLootDrops = 7;
	private static int stackDropSize = 8;
	private static float materialDropChance = 0.875f;
	private static Random rand = new Random();
	//private static int lowLevel = 7, midLevel = 14, midHighLevel = 21, highLevel = 30;
	
	private static int generateLootAmount(int level, int lootRange)
	{
		float leftPoint = 0.55f,degrade = 0.1f,offsetAmount = 0.035f;
		return 1 
				+ (UtilityFunctions.recursiveRandom(stackDropSize, leftPoint, degrade, level + lootRange, offsetAmount) 
				* UtilityFunctions.recursiveRandom(stackDropSize, leftPoint, degrade, level + lootRange, offsetAmount));
	}

	public static ItemStack getMaterialLoot(int level, int lootRange)
	{
		ItemStack[] lootMaterialsLow = new ItemStack[] {
				new ItemStack(Items.stick, generateLootAmount(level,lootRange)),
				new ItemStack(Items.flint, generateLootAmount(level,lootRange)),
				new ItemStack(Items.clay_ball, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.copperNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.tinNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.leadNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.bronzeNugget, generateLootAmount(level,lootRange)) };
		ItemStack[] lootMaterialsMid = new ItemStack[] {
				new ItemStack(Items.coal, generateLootAmount(level,lootRange)),
				new ItemStack(Items.saddle),
				new ItemStack(Items.iron_ingot, generateLootAmount(level,lootRange)),
				new ItemStack(Items.redstone, generateLootAmount(level,lootRange)),
				new ItemStack(Items.glowstone_dust, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.silverNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.platinumNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.steelNugget, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.copperIngot, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.tinIngot, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.leadIngot, generateLootAmount(level,lootRange)),
				new ItemStack(RotItems.bronzeIngot, generateLootAmount(level,lootRange)) };
		ItemStack[] lootMaterialsHigh = new ItemStack[] {
				new ItemStack(Items.emerald, generateLootAmount(level,lootRange)),
				new ItemStack(Items.diamond),
				new ItemStack(RotItems.rawAmethyst),
				new ItemStack(RotItems.rawDiamond),
				new ItemStack(RotItems.rawEmerald),
				new ItemStack(RotItems.rawRuby),
				new ItemStack(RotItems.rawTopaz),
				new ItemStack(RotItems.silverIngot, (1)),
				new ItemStack(RotItems.platinumIngot),
				new ItemStack(RotItems.steelIngot, (1)), };		
		ItemStack[] levelsOfLoot [] = {lootMaterialsLow,lootMaterialsMid,lootMaterialsHigh};
		ItemStack[] returnLoot = levelsOfLoot[UtilityFunctions.recursiveRandom(
				MathHelper.clamp_int(levelsOfLoot.length - 1, 0, levelsOfLoot.length - 1), 
				(0.4f - ((0.02f * level) + (0.03f *lootRange))), 
				(0.7f + ((0.02f * level) + (0.03f *lootRange))), 
				0.1f, 
				level + lootRange, 
				0.01f)];
		//return null; // lootMaterials[random.nextInt(lootMaterials.length)];
		return returnLoot[rand.nextInt(returnLoot.length)];
	}

	public static ItemStack getWeaponLoot(int level, int lootRange)
	{
		ItemStack[] lootWeaponsLow = new ItemStack[] {
				new ItemStack(Items.wooden_sword),
				new ItemStack(Items.stone_sword),
				new ItemStack(Items.bow) };
		ItemStack[] lootWeaponsMid = new ItemStack[] {
				new ItemStack(Items.iron_sword),
				new ItemStack(Items.golden_sword),
				new ItemStack(Items.bow) };
		ItemStack[] lootWeaponsHigh = new ItemStack[] {
				new ItemStack(Items.diamond_sword),
				new ItemStack(Items.bow) };
		ItemStack[] levelsOfLoot [] = {lootWeaponsLow,lootWeaponsMid,lootWeaponsHigh};
		ItemStack[] returnLoot = levelsOfLoot[UtilityFunctions.recursiveRandom(
				MathHelper.clamp_int(levelsOfLoot.length - 1, 0, levelsOfLoot.length - 1), 
				(0.4f - ((0.02f * level) + (0.03f *lootRange))), 
				(0.7f + ((0.02f * level) + (0.03f *lootRange))), 
				0.1f, 
				level + lootRange, 
				0.01f)];
		return returnLoot[rand.nextInt(returnLoot.length)];
	}

	public static ItemStack getToolLoot(int level, int lootRange)
	{
		ItemStack[] lootToolsLow = new ItemStack[] {
				new ItemStack(Items.wooden_axe),
				new ItemStack(Items.wooden_pickaxe),
				new ItemStack(Items.wooden_shovel),
				new ItemStack(Items.stone_axe),
				new ItemStack(Items.stone_pickaxe),
				new ItemStack(Items.stone_shovel) };
		ItemStack[] lootToolsMid = new ItemStack[] {
				new ItemStack(Items.iron_axe),
				new ItemStack(Items.iron_pickaxe),
				new ItemStack(Items.iron_shovel),
				new ItemStack(Items.golden_axe),
				new ItemStack(Items.golden_pickaxe),
				new ItemStack(Items.golden_shovel) };
		ItemStack[] lootToolsHigh = new ItemStack[] {
				new ItemStack(Items.diamond_axe),
				new ItemStack(Items.diamond_pickaxe),
				new ItemStack(Items.diamond_shovel) };
		ItemStack[] levelsOfLoot [] = {lootToolsLow,lootToolsMid,lootToolsHigh};
		ItemStack[] returnLoot = levelsOfLoot[UtilityFunctions.recursiveRandom(
				MathHelper.clamp_int(levelsOfLoot.length - 1, 0, levelsOfLoot.length - 1), 
				(0.4f - ((0.02f * level) + (0.03f *lootRange))), 
				(0.7f + ((0.02f * level) + (0.03f *lootRange))), 
				0.1f, 
				level + lootRange, 
				0.01f)];
		return returnLoot[rand.nextInt(returnLoot.length)];
	}

	public static ItemStack getArmorLoot(int level, int lootRange)
	{
		ItemStack[] lootArmorsLow = new ItemStack[] {
				new ItemStack(Items.leather_boots),
				new ItemStack(Items.leather_chestplate),
				new ItemStack(Items.leather_helmet),
				new ItemStack(Items.leather_leggings),
				new ItemStack(RotItems.quiltedArmorBoots),
				new ItemStack(RotItems.quiltedArmorChestplate),
				new ItemStack(RotItems.quiltedArmorHelm),
				new ItemStack(RotItems.quiltedArmorLeggings),
				new ItemStack(RotItems.hardLeatherArmorBoots),
				new ItemStack(RotItems.hardLeatherArmorChestplate),
				new ItemStack(RotItems.hardLeatherArmorHelm),
				new ItemStack(RotItems.hardLeatherArmorLeggings),
				new ItemStack(RotItems.studdedArmorBoots),
				new ItemStack(RotItems.studdedArmorChestplate),
				new ItemStack(RotItems.studdedArmorHelm),
				new ItemStack(RotItems.studdedArmorLeggings) };
		ItemStack[] lootArmorsMid = new ItemStack[] {
				new ItemStack(Items.chainmail_helmet),
				new ItemStack(Items.chainmail_chestplate),
				new ItemStack(Items.chainmail_leggings),
				new ItemStack(Items.chainmail_boots),
				new ItemStack(Items.iron_horse_armor),
				new ItemStack(Items.iron_helmet),
				new ItemStack(Items.iron_chestplate),
				new ItemStack(Items.iron_leggings),
				new ItemStack(Items.iron_boots),
				new ItemStack(RotItems.ringMailArmorBoots),
				new ItemStack(RotItems.ringMailArmorChestplate),
				new ItemStack(RotItems.ringMailArmorHelm),
				new ItemStack(RotItems.ringMailArmorLeggings),
				new ItemStack(RotItems.scaleMailArmorBoots),
				new ItemStack(RotItems.scaleMailArmorChestplate),
				new ItemStack(RotItems.scaleMailArmorHelm),
				new ItemStack(RotItems.scaleMailArmorLeggings) };
		ItemStack[] lootArmorsMidHigh = new ItemStack[] {
				new ItemStack(RotItems.breastPlateArmorBoots),
				new ItemStack(RotItems.breastPlateArmorChestplate),
				new ItemStack(RotItems.breastPlateArmorHelm),
				new ItemStack(RotItems.breastPlateArmorLeggings),
				new ItemStack(RotItems.splintMailArmorBoots),
				new ItemStack(RotItems.splintMailArmorChestplate),
				new ItemStack(RotItems.splintMailArmorHelm),
				new ItemStack(RotItems.splintMailArmorLeggings),
				new ItemStack(RotItems.lightPlateArmorBoots),
				new ItemStack(RotItems.lightPlateArmorChestplate),
				new ItemStack(RotItems.lightPlateArmorHelm),
				new ItemStack(RotItems.lightPlateArmorLeggings),
				new ItemStack(RotItems.fieldPlateArmorBoots),
				new ItemStack(RotItems.fieldPlateArmorChestplate),
				new ItemStack(RotItems.fieldPlateArmorHelm),
				new ItemStack(RotItems.fieldPlateArmorLeggings), };
		ItemStack[] lootArmorsHigh = new ItemStack[] {
				new ItemStack(Items.golden_helmet),
				new ItemStack(Items.golden_chestplate),
				new ItemStack(Items.golden_leggings),
				new ItemStack(Items.golden_boots),
				new ItemStack(Items.diamond_helmet),
				new ItemStack(Items.diamond_chestplate),
				new ItemStack(Items.diamond_leggings),
				new ItemStack(Items.diamond_boots),
				new ItemStack(RotItems.plateMailArmorBoots),
				new ItemStack(RotItems.plateMailArmorChestplate),
				new ItemStack(RotItems.plateMailArmorHelm),
				new ItemStack(RotItems.plateMailArmorLeggings),
				new ItemStack(RotItems.gothicPlateArmorBoots),
				new ItemStack(RotItems.gothicPlateArmorChestplate),
				new ItemStack(RotItems.gothicPlateArmorHelm),
				new ItemStack(RotItems.gothicPlateArmorLeggings),
				new ItemStack(RotItems.fullPlateArmorBoots),
				new ItemStack(RotItems.fullPlateArmorChestplate),
				new ItemStack(RotItems.fullPlateArmorHelm),
				new ItemStack(RotItems.fullPlateArmorLeggings) }; // Note:
																	// Ancient
																	// Armor is
																	// not
																	// included
																	// in this
																	// list, it
																	// should
																	// *only* be
																	// obtained
																	// via
																	// special
																	// stuff.
		ItemStack[] levelsOfLoot [] = {lootArmorsLow,lootArmorsMid,lootArmorsMidHigh,lootArmorsHigh};
		ItemStack[] returnLoot = levelsOfLoot[UtilityFunctions.recursiveRandom(
				MathHelper.clamp_int(levelsOfLoot.length - 1, 0, levelsOfLoot.length - 1), 
				(0.4f - ((0.02f * level) + (0.03f *lootRange))), 
				(0.7f + ((0.02f * level) + (0.03f *lootRange))), 
				0.1f, 
				level + lootRange, 
				0.01f)];
		return returnLoot[rand.nextInt(returnLoot.length)];
	}

	/**
	 * Will return a random loot based on if it's material or equipment and the
	 * monsters level/rank
	 **/
	public static ItemStack getLoot(int rank,int lootRange, boolean isEquipment)
	{
		Random random = new Random();
		if (isEquipment)
		{
			int itemType = random.nextInt(3);
			switch (itemType)
			{
			case 0:
				return getWeaponLoot(rank, lootRange);
			case 1:
				return getToolLoot(rank, lootRange);
			case 2:
				return getArmorLoot(rank, lootRange);
			}
		}
		else return getMaterialLoot(rank, lootRange);

		return null;
	}

	public static EntityItem[] addLoot(int range, int rank, EntityLiving entity)
	{
		EntityItem[] newList = new EntityItem[0];
		int numOfItems = 0;
		int numOfMaterialDrops = 0;
		float leftPointer = 0.2f;
		numOfItems = UtilityFunctions.recursiveRandom(numberOfLootDrops, leftPointer, 0.1f, rank + range, 0.00573f);
		numOfMaterialDrops = UtilityFunctions.recursiveRandom(numOfItems, leftPointer, 0.05f, 0, 0.01f);
		//TODO search for vanilla item drops and do some math to remove numberofItems if it's able to have RotStats on it
		if (numOfItems != 0)
		{
			newList = new EntityItem[numOfItems];
			ItemStack item = null;
			for (int i = 0; i < numOfItems; i++)
			{
				if (numOfMaterialDrops > 0)
				{
					item = getLoot(rank,range, false);
					numOfMaterialDrops--;
				}
				else item = getLoot(rank,range, true);
				if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemTool || item
						.getItem() instanceof ItemArmor || item.getItem() instanceof ItemBow) UtilItemStats
						.applyItemStats(item, rand, new int[] { rank, rank + 2 },
								new int[] { rank, rank + 2 });
				newList[i] = new EntityItem(entity.worldObj, entity.getPosition().getX(), entity
						.getPosition().getY(), entity.getPosition().getZ(), item);
			}
		}
		if (numOfItems == 0) return null;
		else return newList;
	}
}
