package ca.grm.rot.managers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import ca.grm.rot.events.EventItemToolTip;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.UtilItemStats;

public class RotLootManager
{
	public RotLootManager()
	{

	}

	public static final int lootRange = 5;
	private static int lowLevel = 7, midLevel = 14, midHighLevel = 21, highLevel = 30;

	public static ItemStack getMaterialLoot(int level, Random random)
	{
		ItemStack[] lootMaterialsLow = new ItemStack[] {new ItemStack(Items.stick, (random.nextInt(5) + 1)), new ItemStack(Items.flint, (random.nextInt(1) + 1)), 
				new ItemStack(Items.clay_ball, (random.nextInt(5) + 1)), new ItemStack(RotItems.copperNugget, (random.nextInt(2) + 1)), 
				new ItemStack(RotItems.tinNugget, (random.nextInt(2) + 1)), new ItemStack(RotItems.leadNugget, (random.nextInt(2) + 1)), 
				new ItemStack(RotItems.bronzeNugget, (random.nextInt(1) + 1))};
		ItemStack[] lootMaterialsMid = new ItemStack[] {new ItemStack(Items.coal,(random.nextInt(2) + 1)), new ItemStack(Items.saddle), 
				new ItemStack(Items.iron_ingot, (random.nextInt(1) + 1)), new ItemStack(Items.redstone, (random.nextInt(2) + 1)), 
				new ItemStack(Items.glowstone_dust, (random.nextInt(2) + 1)), new ItemStack(RotItems.silverNugget, (random.nextInt(2) + 1)),
				new ItemStack(RotItems.platinumNugget, (random.nextInt(2) + 1)), new ItemStack(RotItems.steelNugget, (random.nextInt(2) + 1)), 
				new ItemStack(RotItems.copperIngot, (random.nextInt(1) + 1)), new ItemStack(RotItems.tinIngot, (random.nextInt(1) + 1)),
				new ItemStack(RotItems.leadIngot, (random.nextInt(1) + 1)), new ItemStack(RotItems.bronzeIngot, (random.nextInt(1) + 1))};
		ItemStack[] lootMaterialsHigh = new ItemStack[] {new ItemStack(Items.emerald, (random.nextInt(2) + 1)), new ItemStack(Items.diamond), 
				new ItemStack(RotItems.rawAmethyst), new ItemStack(RotItems.rawDiamond), new ItemStack(RotItems.rawEmerald), 
				new ItemStack(RotItems.rawRuby), new ItemStack(RotItems.rawTopaz), new ItemStack(RotItems.silverIngot, (random.nextInt(1) + 1)),
				new ItemStack(RotItems.platinumIngot), new ItemStack(RotItems.steelIngot, (random.nextInt(1) + 1)), 
				};
		if (level <= lowLevel) return lootMaterialsLow[random.nextInt(lootMaterialsLow.length)];
		else if (level <= midLevel) return lootMaterialsMid[random.nextInt(lootMaterialsMid.length)];
		else if (level <= highLevel) return lootMaterialsHigh[random.nextInt(lootMaterialsHigh.length)];
		return null; //lootMaterials[random.nextInt(lootMaterials.length)];
	}

	public static ItemStack getWeaponLoot(int level, Random random)
	{
		ItemStack[] lootWeaponsLow = new ItemStack[] { new ItemStack(Items.wooden_sword), new ItemStack(
				Items.stone_sword), new ItemStack(Items.bow) };
		ItemStack[] lootWeaponsMid = new ItemStack[] { new ItemStack(Items.iron_sword), new ItemStack(
				Items.golden_sword), new ItemStack(Items.bow) };
		ItemStack[] lootWeaponsHigh = new ItemStack[] { new ItemStack(Items.diamond_sword), new ItemStack(
				Items.bow) };
		if (level <= lowLevel) return lootWeaponsLow[random.nextInt(lootWeaponsLow.length)];
		else if (level <= midLevel) return lootWeaponsMid[random.nextInt(lootWeaponsMid.length)];
		else if (level <= highLevel) return lootWeaponsHigh[random.nextInt(lootWeaponsHigh.length)];
		else return getMaterialLoot(level, random);
	}

	public static ItemStack getToolLoot(int level, Random random)
	{
		ItemStack[] lootToolsLow = new ItemStack[] { new ItemStack(Items.wooden_axe), new ItemStack(
				Items.wooden_pickaxe), new ItemStack(Items.wooden_shovel), new ItemStack(
				Items.stone_axe), new ItemStack(Items.stone_pickaxe), new ItemStack(
				Items.stone_shovel) };
		ItemStack[] lootToolsMid = new ItemStack[] { new ItemStack(Items.iron_axe), new ItemStack(
				Items.iron_pickaxe), new ItemStack(Items.iron_shovel), new ItemStack(
				Items.golden_axe), new ItemStack(Items.golden_pickaxe), new ItemStack(
				Items.golden_shovel) };
		ItemStack[] lootToolsHigh = new ItemStack[] { new ItemStack(Items.diamond_axe), new ItemStack(
				Items.diamond_pickaxe), new ItemStack(Items.diamond_shovel) };
		if (level <= lowLevel) return lootToolsLow[random.nextInt(lootToolsLow.length)];
		else if (level <= midLevel) return lootToolsMid[random.nextInt(lootToolsMid.length)];
		else if (level <= highLevel) return lootToolsHigh[random.nextInt(lootToolsHigh.length)];
		else return getMaterialLoot(level, random);
	}

	public static ItemStack getArmorLoot(int level, Random random)
	{
		ItemStack[] lootArmorsLow = new ItemStack[] { new ItemStack(Items.leather_boots), new ItemStack(
				Items.leather_chestplate), new ItemStack(Items.leather_helmet), new ItemStack(
				Items.leather_leggings), new ItemStack(RotItems.quiltedArmorBoots), new ItemStack(
				RotItems.quiltedArmorChestplate), new ItemStack(RotItems.quiltedArmorHelm), new ItemStack(
				RotItems.quiltedArmorLeggings), new ItemStack(RotItems.hardLeatherArmorBoots), new ItemStack(
				RotItems.hardLeatherArmorChestplate), new ItemStack(RotItems.hardLeatherArmorHelm), new ItemStack(
				RotItems.hardLeatherArmorLeggings), new ItemStack(RotItems.studdedArmorBoots), new ItemStack(
				RotItems.studdedArmorChestplate), new ItemStack(RotItems.studdedArmorHelm), new ItemStack(
				RotItems.studdedArmorLeggings)};
		ItemStack[] lootArmorsMid = new ItemStack[] { new ItemStack(Items.chainmail_helmet), new ItemStack(
				Items.chainmail_chestplate), new ItemStack(Items.chainmail_leggings), new ItemStack(
				Items.chainmail_boots), new ItemStack(Items.iron_horse_armor), new ItemStack(
				Items.iron_helmet), new ItemStack(Items.iron_chestplate), new ItemStack(
				Items.iron_leggings), new ItemStack(Items.iron_boots), new ItemStack(RotItems.ringMailArmorBoots), new ItemStack(
				RotItems.ringMailArmorChestplate), new ItemStack(RotItems.ringMailArmorHelm), new ItemStack(
				RotItems.ringMailArmorLeggings), new ItemStack(RotItems.scaleMailArmorBoots), new ItemStack(
				RotItems.scaleMailArmorChestplate), new ItemStack(RotItems.scaleMailArmorHelm), new ItemStack(
				RotItems.scaleMailArmorLeggings)};
		ItemStack[] lootArmorsMidHigh = new ItemStack[] {new ItemStack(RotItems.breastPlateArmorBoots), new ItemStack(
				RotItems.breastPlateArmorChestplate), new ItemStack(RotItems.breastPlateArmorHelm), new ItemStack(
				RotItems.breastPlateArmorLeggings), new ItemStack(RotItems.splintMailArmorBoots), new ItemStack(
				RotItems.splintMailArmorChestplate), new ItemStack(RotItems.splintMailArmorHelm), new ItemStack(
				RotItems.splintMailArmorLeggings), new ItemStack(RotItems.lightPlateArmorBoots), new ItemStack(
				RotItems.lightPlateArmorChestplate), new ItemStack(RotItems.lightPlateArmorHelm), new ItemStack(
				RotItems.lightPlateArmorLeggings), new ItemStack(RotItems.fieldPlateArmorBoots), new ItemStack(
				RotItems.fieldPlateArmorChestplate), new ItemStack(RotItems.fieldPlateArmorHelm), new ItemStack(
				RotItems.fieldPlateArmorLeggings),};
		ItemStack[] lootArmorsHigh = new ItemStack[] { new ItemStack(Items.golden_helmet), new ItemStack(
				Items.golden_chestplate), new ItemStack(Items.golden_leggings), new ItemStack(
				Items.golden_boots), new ItemStack(Items.diamond_helmet), new ItemStack(
				Items.diamond_chestplate), new ItemStack(Items.diamond_leggings), new ItemStack(
				Items.diamond_boots), new ItemStack(RotItems.plateMailArmorBoots), new ItemStack(
				RotItems.plateMailArmorChestplate), new ItemStack(RotItems.plateMailArmorHelm), new ItemStack(
				RotItems.plateMailArmorLeggings), new ItemStack(RotItems.gothicPlateArmorBoots), new ItemStack(
				RotItems.gothicPlateArmorChestplate), new ItemStack(RotItems.gothicPlateArmorHelm), new ItemStack(
				RotItems.gothicPlateArmorLeggings), new ItemStack(RotItems.fullPlateArmorBoots), new ItemStack(
				RotItems.fullPlateArmorChestplate), new ItemStack(RotItems.fullPlateArmorHelm), new ItemStack(
				RotItems.fullPlateArmorLeggings) }; // Note: Ancient Armor is not included in this list, it should *only* be obtained via special stuff.
		if (level <= lowLevel) return lootArmorsLow[random.nextInt(lootArmorsLow.length)];
		else if (level <= midLevel) return lootArmorsMid[random.nextInt(lootArmorsMid.length)];
		else if (level <= midHighLevel) return lootArmorsMidHigh[random.nextInt(lootArmorsMid.length)];
		else if (level <= highLevel) return lootArmorsHigh[random.nextInt(lootArmorsHigh.length)];
		else return getMaterialLoot(level, random);
	}

	public static ItemStack getLoot(int rank, Random random)
	{
		int itemType = random.nextInt(4);
		switch (itemType)
		{
		case 0:
			return getMaterialLoot(rank, random);
		case 1:
			return getWeaponLoot(rank, random);
		case 2:
			return getToolLoot(rank, random);
		case 3:
			return getArmorLoot(rank, random);
		}
		return null;
	}

	public static EntityItem[] addLoot(Entity entity, int rank)
	{
		EntityItem[] newList = new EntityItem[0];
		int numOfItems = 0;
		float leftChancePointer = (0.3f - (0.05f * (rank - 1)));
		float rightChancePointer = (0.7f + (0.05f * (rank - 1)));
		float rngRoll = 0;
		for (int i = 0; i < 10; i++)
		{
			rngRoll = entity.worldObj.rand.nextFloat();
			if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
			{
				leftChancePointer += (0.1f - (0.00573f * (rank - 1)));
				rightChancePointer -= (0.1f - (0.00573f * (rank - 1)));
				numOfItems++;
			}
			else break;
		}
		if (numOfItems != 0)
		{
			newList = new EntityItem[numOfItems];
			for (int i = 0; i < numOfItems; i++)
			{
				ItemStack item = getLoot(rank, entity.worldObj.rand);
				if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemTool || item
						.getItem() instanceof ItemArmor || item.getItem() instanceof ItemBow) UtilItemStats
						.applyItemStats(item, entity.worldObj.rand, new int[]{rank,rank+2},new int[]{rank,rank+2});
				newList[i] = new EntityItem(entity.worldObj, entity.getPosition().getX(), entity
						.getPosition().getY(), entity.getPosition().getZ(), item);
			}
		}
		if (numOfItems == 0) return null;
		else return newList;
	}
}
