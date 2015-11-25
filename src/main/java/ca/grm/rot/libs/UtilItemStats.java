package ca.grm.rot.libs;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MathHelper;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.managers.RotAffixManager;
import ca.grm.rot.managers.RotMobAffixManager;

public class UtilItemStats
{
	private static String[] baseStats = new String[] {
			UtilNBTKeys.strStat,
			UtilNBTKeys.dexStat,
			UtilNBTKeys.intStat,
			UtilNBTKeys.vitStat,
			UtilNBTKeys.agiStat };

	public static int[] qualityValues = new int[] { -3, -2, -1, 0, 1, 2, 3, 4 };
	public static String[] qualityNames = new String[] {
			"Useless",
			"Broken",
			"Crude",
			"Normal",
			"Fine",
			"Great",
			"Superior",
			"Magic" };

	public static void setItemDisplayName(ItemStack is)
	{
		boolean hasPrefix = false;
		String newName = "";
		if (UtilNBTHelper.getString(is, UtilNBTKeys.prefixName).trim() != "")
		{
			newName += UtilNBTHelper.getString(is, UtilNBTKeys.prefixName) + " " + is
					.getDisplayName();
			hasPrefix = true;
		}
		if (UtilNBTHelper.getString(is, UtilNBTKeys.suffixName).trim() != "")
		{
			if (hasPrefix) newName += " of " + UtilNBTHelper.getString(is, UtilNBTKeys.suffixName);
			else newName += is.getDisplayName() + " of " + UtilNBTHelper.getString(is,
					UtilNBTKeys.suffixName);
		}
		if (newName != "") is.setStackDisplayName(newName);
	}

	/**
	 * Methods for setting a stat, and getting them via from the item or from an
	 * item socket
	 **/
	public static int getStatValue(ItemStack is, String sid)
	{
		return UtilNBTHelper.getInt(is, sid);
	}

	public static int getStatValue(ItemStack is, String sid, int slotId)
	{
		return UtilNBTHelper.getInt(is, (sid + slotId));
	}

	public static void setStatValue(ItemStack is, String sid, int sv)
	{
		UtilNBTHelper.setInteger(is, sid, sv);
	}

	public static void setStatValue(ItemStack is, String sid, int sv, int slotId)
	{
		UtilNBTHelper.setInteger(is, (sid + slotId), sv);
	}

	/** Methods to trigger then applying stats **/
	public static void applyItemStats(ItemStack is, Random random)
	{
		int rank = UtilNBTHelper.getInt(is, UtilNBTKeys.itemRank);
		if (rank == 0)
		{
			rank = random.nextInt(3) + 1;
			UtilNBTHelper.setInteger(is, UtilNBTKeys.itemRank, rank);
			applyItemQuality(is, random, rank);
			int quality = UtilNBTHelper.getInt(is, Rot.MOD_ID + "qualityValue");
			statRoll(is, random);
		}
	}

	public static void applyItemStats(ItemStack is, Random random, int rank)
	{

		UtilNBTHelper.setInteger(is, UtilNBTKeys.itemRank, rank);
		applyItemQuality(is, random, rank);
		int quality = UtilNBTHelper.getInt(is, Rot.MOD_ID + "qualityValue");
		statRoll(is, random);
	}

	public static void applyItemStats(ItemStack is, Random random, int rank, int quality)
	{
		UtilNBTHelper.setInteger(is, UtilNBTKeys.itemRank, rank);
		UtilNBTHelper.setInteger(is, UtilNBTKeys.qualityIndex, quality);
		statRoll(is, random);
	}

	public static void applyItemStats(ItemStack is, Random random, int[] rankStartEnd,
			int[] qualityLimitStartEnd)
	{
		int newRank = 1;
		int quality = MathHelper
				.clamp_int(
						random.nextInt(qualityLimitStartEnd[1] - qualityLimitStartEnd[0]) + qualityLimitStartEnd[0],
						0, qualityValues.length - 1);
		if (rankStartEnd[0] - rankStartEnd[1] != 0) newRank = random
				.nextInt(rankStartEnd[1] - rankStartEnd[0]) + rankStartEnd[0];
		else newRank = rankStartEnd[0];
		UtilNBTHelper.setInteger(is, UtilNBTKeys.itemRank, newRank);
		UtilNBTHelper.setInteger(is, UtilNBTKeys.qualityIndex, quality);
		statRoll(is, random);
	}

	private static void statRoll(ItemStack is, Random random)
	{

		int quality = qualityValues[UtilNBTHelper.getInt(is, UtilNBTKeys.qualityIndex)];
		int rank = UtilNBTHelper.getInt(is, UtilNBTKeys.itemRank);
		float leftChancePointer = (0.3f - (0.05f * quality));
		float rightChancePointer = (0.7f + (0.05f * quality));
		float rngRoll = 0;
		int statsAdded = 0;
		// System.out.println("Starting the statRoll");
		for (int i = 0; i < 50; i++)
		{
			if (UtilityFunctions.recursiveRandom(leftChancePointer, rightChancePointer))
			{

				leftChancePointer += MathHelper.clamp_float(
						0.1f - (0.01f * (quality < 0 ? 0 : quality - 1)) - (0.025f * (rank - 1)),
						0.01f, 0.08f);
				rightChancePointer -= MathHelper.clamp_float(
						0.1f - (0.01f * (quality < 0 ? 0 : quality - 1)) - (0.025f * (rank - 1)),
						0.01f, 0.08f);
				for (int tries = 0; tries < 50; tries++)
				{
					int r = random.nextInt(baseStats.length);
					// System.out.println("trying to add: " + baseStats[r]);
					int statValue = UtilNBTHelper.getInt(is, baseStats[r]);
					// System.out.println("stat found: " + statValue);
					if (statValue == 0)
					{
						// System.out.println("Adding: " + baseStats[r]);
						statsAdded++;
						UtilNBTHelper.setInteger(is, baseStats[r], random.nextInt(11) * rank);
						break;
					}
				}
			}
			else
			{
				break;
			}
		}
		if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool || is.getItem() instanceof ItemBow)
		{
			UtilNBTHelper
					.setInteger(
							is,
							UtilNBTKeys.minDmgStat,
							(11 * rank) + (random.nextInt(10) * rank) + (random.nextInt(6) * (quality - 1)));
			UtilNBTHelper
					.setInteger(
							is,
							UtilNBTKeys.maxDmgStat,
							(23 * rank) + (random.nextInt(30) * rank) + (random.nextInt(9) * (quality - 1)));
		}
		if (is.getItem() instanceof ItemArmor)
		{
			UtilNBTHelper
					.setInteger(
							is,
							UtilNBTKeys.defStat,
							(11 * rank) + (random.nextInt(10) * rank) + (random.nextInt(6) * (quality - 1)));
		}
		applyPrefix(is, random, rank);
		applySuffix(is, random, rank);
		setItemDisplayName(is);
	}

	public static void applyItemQuality(ItemStack is, Random random, int rank)
	{

		int value = 0;
		float roll = MathHelper.clamp_float(random.nextFloat() + ((10 * (rank - 1)) / 100), 0f, 1f);

		if (roll >= 0 && roll <= 0.2f)
		{
			value = 0;
		}
		else if (roll > 0.2f && roll <= 0.4f)
		{
			value = 1;
		}
		else if (roll > 0.4f && roll <= 0.5f)
		{
			value = 2;
		}
		else if (roll > 0.5f && roll <= 0.55f)
		{
			value = 3;
		}
		else if (roll > 0.55f && roll < 0.6f)
		{
			value = 4;
		}
		else if (roll > 0.6f && roll < 0.65f)
		{
			value = 5;
		}
		else if (roll > 0.65f && roll <= 0.8f)
		{
			value = 6;
		}
		else
		{
			// UtilityNBTHelper.setBoolean(is, identifiedState, true);
			value = 7;
		}

		UtilNBTHelper.setInteger(is, UtilNBTKeys.qualityIndex, value);
	}

	public static void applyPrefix(ItemStack is, Random random, int rank)
	{
		// Get a random Roll Chance
		if (UtilityFunctions.recursiveRandom(0.4f, 1.0f)) // If Roll is a
															// success
		{
			// If it is a Held item
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemBow || is
					.getItem() instanceof ItemTool)
			{
				// Grab a random Prefix
				ItemAffix affix = RotAffixManager.getPrefix(rank, 1);
				// Some Affixes can have more than one value that they alter
				for (int a = 0; a < affix.nbtKeys.length; a++)
				{
					// Attach their values as a float
					UtilNBTHelper.setFloat(is, affix.nbtKeys[a], affix.nbtValues[a]);
					// For things that are saved as an int that will be handled
					// by the methods that retrieve that info
				}
				// Attach the Prefix name
				UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
			}
			// If it is a Worn Item (currently just
			if (is.getItem() instanceof ItemArmor)// TODO create magic trinkets,
													// rings and amulets
			{
				ItemAffix affix = RotAffixManager.getPrefix(rank, 2);
				for (int a = 0; a < affix.nbtKeys.length; a++)
				{
					UtilNBTHelper.setFloat(is, affix.nbtKeys[a], affix.nbtValues[a]);
				}
				UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
			}
		}
	}

	public static void applySuffix(ItemStack is, Random random, int rank)
	{
		// Same comments as applyPrefix
		if (UtilityFunctions.recursiveRandom(0.4f, 1.0f))
		{
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool || is
					.getItem() instanceof ItemBow)
			{
				ItemAffix affix = RotAffixManager.getSuffix(rank, 1);
				if (affix.nbtKeys[0] == UtilNBTKeys.indestructible)
				{
					UtilNBTHelper.setBoolean(is, affix.nbtKeys[0], true);
					UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
				}
				else
				{
					for (int a = 0; a < affix.nbtKeys.length; a++)
					{
						UtilNBTHelper.setFloat(is, affix.nbtKeys[a], affix.nbtValues[a]);
					}
					UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
				}
			}
			if (is.getItem() instanceof ItemArmor)// TODO create magic
													// trinkets, rings and
													// amulets
			{
				ItemAffix affix = RotAffixManager.getSuffix(rank, 2);
				if (affix.nbtKeys[0] == UtilNBTKeys.indestructible)
				{
					UtilNBTHelper.setBoolean(is, affix.nbtKeys[0], true);
					UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
				}
				else
				{
					for (int a = 0; a < affix.nbtKeys.length; a++)
					{
						UtilNBTHelper.setFloat(is, affix.nbtKeys[a], affix.nbtValues[a]);
					}
					UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
				}
			}
		}
	}

	/** Collect NBT tags that are common from held and worn items **/
	private static void getBasicStats(ItemStack is, int[] listCollect, String[] ListSearch)
	{
		for (int i = 0; i < listCollect.length; i++)
		{
			listCollect[i] += (int) UtilNBTHelper.getFloat(is, ListSearch[i]);
		}
	}

	private static void getBonusStats(ItemStack is, float[] listCollect, String[] ListSearch)
	{
		for (int i = 0; i < listCollect.length; i++)
		{
			listCollect[i] += UtilNBTHelper.getFloat(is, ListSearch[i]);
		}
	}

	private static void selfRepair(ItemStack item)
	{
		float selfRepairLevel = UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairing);
		
		if (selfRepairLevel > 1f)
		{
			float selfRepairBaseTime = UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairing);
			float selfRepairTime = UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairTime);
			if (UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairTime) <= 0)
			{
				if (item.getItemDamage() > 0)
				{
					item.setItemDamage(item.getItemDamage() - 1);
					UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, selfRepairBaseTime * 20);
				}
			}
			else
			{
				UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, selfRepairTime - 10);
			}
		}

	}

	public static void handlePlayerStats(ExtendPlayer props, EntityPlayer player)
	{
		// Stat handling
		// int strMod = 0, dexMod = 0, vitMod = 0, agiMod = 0, intMod = 0,
		// minDmg = 0, maxDmg = 0, defBonus = 0;
		//equipment is all wearable items that the player can wear that will give stats, including the correct held type
		ItemStack[] equipment = new ItemStack[]{player.getEquipmentInSlot(0), player.getEquipmentInSlot(1), player
				.getEquipmentInSlot(2), player.getEquipmentInSlot(3), player
				.getEquipmentInSlot(4)};
		int[] stats = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] statsS = new String[] {
				UtilNBTKeys.strStat,
				UtilNBTKeys.dexStat,
				UtilNBTKeys.vitStat,
				UtilNBTKeys.agiStat,
				UtilNBTKeys.intStat,
				UtilNBTKeys.minDmgStat,
				UtilNBTKeys.maxDmgStat,
				UtilNBTKeys.defStat };
		float[] bonusStats = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] bonusStatsS = new String[] {
				UtilNBTKeys.lifeSteal,
				UtilNBTKeys.manaSteal,
				UtilNBTKeys.manaStat,
				UtilNBTKeys.stamStat,
				UtilNBTKeys.lifeStat,
				UtilNBTKeys.manaRegenStat,
				UtilNBTKeys.stamRegenStat,
				UtilNBTKeys.lifeRegenStat };
		// float lifeSteal = 0, manaSteal = 0;
		// float bonusMana = 0, bonusStam = 0, bonusHealth = 0, bonusManaRegen =
		// 0, bonusStamRegen = 0, bonusHealthRegen = 0;
		ItemStack held = player.getEquipmentInSlot(0), armor1 = player.getEquipmentInSlot(1), armor2 = player
				.getEquipmentInSlot(2), armor3 = player.getEquipmentInSlot(3), armor4 = player
				.getEquipmentInSlot(4);
		
		for (int i = 0; i < equipment.length; i++)
		{
			if (equipment[i] != null)
			{
				if (i == 0)
				{
					if ((equipment[i].getItem() instanceof ItemSword) || (equipment[i].getItem() instanceof ItemTool) || (equipment[i]
							.getItem() instanceof ItemBow))
					{
						getBasicStats(equipment[i], stats, statsS);
						getBonusStats(equipment[i], bonusStats, bonusStatsS);
						//TODO move this function somewhere more controlled to prevent infinite tool 
						//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(held);
					}
				}
				else
				{
					getBasicStats(equipment[i], stats, statsS);
					getBonusStats(equipment[i], bonusStats, bonusStatsS);
					//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(held);
				}
			}
		}

		if (held != null)
		{
			if ((held.getItem() instanceof ItemSword) || (held.getItem() instanceof ItemTool) || (held
					.getItem() instanceof ItemBow))
			{
				getBasicStats(held, stats, statsS);
				getBonusStats(held, bonusStats, bonusStatsS);
				if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(held);
			}
		}
		if (armor1 != null)
		{
			getBasicStats(armor1, stats, statsS);
			getBonusStats(armor1, bonusStats, bonusStatsS);
			//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(armor1);
		}
		if (armor2 != null)
		{
			getBasicStats(armor2, stats, statsS);
			getBonusStats(armor2, bonusStats, bonusStatsS);
			//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(armor2);
		}
		if (armor3 != null)
		{
			getBasicStats(armor3, stats, statsS);
			getBonusStats(armor3, bonusStats, bonusStatsS);
			//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(armor3);
		}
		if (armor4 != null)
		{
			getBasicStats(armor4, stats, statsS);
			getBonusStats(armor4, bonusStats, bonusStatsS);
			//if (player.worldObj.getWorldTime() % 20 == 0) selfRepair(armor4);
		}

		if (props.getStrength() != (stats[0] - props.getClassModifers()[0]))
		{
			props.setStrength(stats[0]);
		}
		if (props.getDexterity() != (stats[1] - props.getClassModifers()[4]))
		{
			props.setDexterity(stats[1]);
		}
		if (props.getVitality() != (stats[2] - props.getClassModifers()[3]))
		{
			props.setVitality(stats[2]);
		}
		if (props.getAgility() != (stats[3] - props.getClassModifers()[1]))
		{
			props.setAgility(stats[3]);
			ExtendPlayer.get(player).updateAthleticScore();
		}
		if (props.getIntelligence() != (stats[4] - props.getClassModifers()[2]))
		{
			props.setIntelligence(stats[4]);
		}

		if (props.getMinDmg() != stats[5])
		{
			props.setMinDmg(stats[5]);
		}
		if (props.getMaxDmg() != stats[6])
		{
			props.setMaxDmg(stats[6]);
		}
		if (props.getDefBonus() != stats[7])
		{
			props.setDefBonus(stats[7]);
		}
		if (props.getLifeSteal() != bonusStats[0])
		{
			props.setLifeSteal(bonusStats[0]);
		}
		if (props.getManaSteal() != bonusStats[1])
		{
			props.setManaSteal(bonusStats[1]);
		}

		if (props.getBonusMana() != bonusStats[2])
		{
			props.setBonusMana(bonusStats[2]);
		}
		if (props.getBonusStam() != bonusStats[3])
		{
			props.setBonusStam(bonusStats[3]);
		}
		if (props.getBonusHealth() != bonusStats[4])
		{
			props.setBonusHealth(bonusStats[4]);
		}

		if (props.getManaRegen() != bonusStats[5])
		{
			props.setManaRegen(bonusStats[5]);
		}
		if (props.getStamRegen() != bonusStats[6])
		{
			props.setStamRegen(bonusStats[6]);
		}
		if (props.getHealthRegen() != bonusStats[7])
		{
			props.setHealthRegen(bonusStats[7]);
		}
	}
}
