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
			rngRoll = random.nextFloat();
			if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
			{
				// System.out.println("Success rolled a: " + rngRoll
				// + " between: " + leftChancePointer + "-"
				// + rightChancePointer);
				leftChancePointer += (0.1f - (0.01f * (quality < 0 ? 0 : quality - 1)) - (0.025f * (rank - 1)));
				rightChancePointer -= (0.1f - (0.01f * (quality < 0 ? 0 : quality - 1)) - (0.025f * (rank - 1)));
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
		float roll = MathHelper.clamp_float(random.nextFloat() + ((10 * (rank - 1)) / 100), 0f, 1f);
		if (roll > 0.6f)
		{
			boolean gotAffix = false;
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemBow || is
					.getItem() instanceof ItemTool)
			{

				for (int i = 0; i < 50; i++)
				{
					if (gotAffix) break;
					int affixTypeRoll = random.nextInt(6);
					ItemAffix affix;
					switch (affixTypeRoll)
					{
					case 0:// Damage
						affix = RotAffixManager.getAffix(rank, RotAffixManager.damagePrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setInteger(is, affix.nbtKeys[1], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[1]) + (int) affix.nbtValues[1]));
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;not go
					case 1:// Enhanced Damage
						affix = RotAffixManager.getAffix(rank, RotAffixManager.damageExtraPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setFloat(is, affix.nbtKeys[1], affix.nbtValues[1]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 2: // Poison
						affix = RotAffixManager.getAffix(rank, RotAffixManager.poisonPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 3: // Sickness
						affix = RotAffixManager
								.getAffix(rank, RotAffixManager.sickPrefixes, random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 4: // Vile
						if (rank < RotAffixManager.vilePrefix.rankRequirement) break;
						UtilNBTHelper.setBoolean(is, RotAffixManager.vilePrefix.nbtKeys[0], true);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName,
								RotAffixManager.vilePrefix.affixName);
						gotAffix = true;
						break;
					case 5: // Socket
						affix = RotAffixManager.getAffix(rank, RotAffixManager.socketPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (int) affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					}
				}// For loop End
			}
			if (is.getItem() instanceof ItemArmor)// TODO create magic trinkets,
													// rings and amulets
			{
				for (int i = 0; i < 50; i++)
				{
					if (gotAffix) break;
					int affixTypeRoll = random.nextInt(6);
					ItemAffix affix;
					switch (affixTypeRoll)
					{
					case 0: // Defence
						affix = RotAffixManager.getAffix(rank, RotAffixManager.defencePrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 1:// Mana
						affix = RotAffixManager
								.getAffix(rank, RotAffixManager.manaPrefixes, random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 2:// Stam
						affix = RotAffixManager
								.getAffix(rank, RotAffixManager.stamPrefixes, random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 3:// Stam Regen
						affix = RotAffixManager.getAffix(rank, RotAffixManager.stamRegenPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 4:// Mana Regen
						affix = RotAffixManager.getAffix(rank, RotAffixManager.manaRegenPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					case 5:// Socket
						affix = RotAffixManager.getAffix(rank, RotAffixManager.socketPrefixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (int) affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.prefixName, affix.affixName);
						gotAffix = true;
						break;
					}
				}
			}

		}
	}

	public static void applySuffix(ItemStack is, Random random, int rank)
	{
		float roll = MathHelper.clamp_float(random.nextFloat() + ((10 * (rank - 1)) / 100), 0f, 1f);
		if (roll > 0.6f)
		{
			boolean gotAffix = false;
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool)
			{

				for (int i = 0; i < 50; i++)
				{
					if (gotAffix) break;
					int affixTypeRoll = random.nextInt(4);
					ItemAffix affix;
					switch (affixTypeRoll)
					{
					case 0:// lifeSteal
						affix = RotAffixManager.getAffix(rank, RotAffixManager.lifeStealSuffixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 1:// manaSteal
						affix = RotAffixManager.getAffix(rank, RotAffixManager.manaStealSuffixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 2: // Str
						affix = RotAffixManager.getAffix(rank, RotAffixManager.strSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 3: // durability
						affix = RotAffixManager.getAffix(rank, RotAffixManager.selfRepairSuffixes,
								random);
						if (affix == null) break;
						if (affix.affixName != "Ages")
						{
							UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
							UtilNBTHelper.setFloat(is, affix.nbtKeys[1], affix.nbtValues[1]);
						}
						else UtilNBTHelper.setBoolean(is, affix.nbtKeys[0], true);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					}// For loop End
				}

			}
			if (is.getItem() instanceof ItemBow)
			{
				for (int i = 0; i < 50; i++)
				{
					if (gotAffix) break;
					int affixTypeRoll = random.nextInt(2);
					ItemAffix affix;
					switch (affixTypeRoll)
					{
					case 0:// Dex
						affix = RotAffixManager.getAffix(rank, RotAffixManager.dexSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 1:// durability
						affix = RotAffixManager.getAffix(rank, RotAffixManager.selfRepairSuffixes,
								random);
						if (affix == null) break;
						if (affix.affixName != "Ages")
						{
							UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
							UtilNBTHelper.setFloat(is, affix.nbtKeys[1], affix.nbtValues[1]);
						}
						else UtilNBTHelper.setBoolean(is, affix.nbtKeys[0], true);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					}
				}
			}
			if (is.getItem() instanceof ItemArmor)// TODO create magic
													// trinkets, rings and
													// amulets
			{
				for (int i = 0; i < 50; i++)
				{
					if (gotAffix) break;
					int affixTypeRoll = random.nextInt(7);
					ItemAffix affix;
					switch (affixTypeRoll)
					{
					case 0: // Vit
						affix = RotAffixManager.getAffix(rank, RotAffixManager.vitSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 1:// Dex
						affix = RotAffixManager.getAffix(rank, RotAffixManager.dexSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 2:// Str
						affix = RotAffixManager.getAffix(rank, RotAffixManager.strSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 3:// All Stats
						affix = RotAffixManager.getAffix(rank, RotAffixManager.allBaseSuffixes,
								random);
						if (affix == null) break;
						for (int j = 0; j < affix.nbtKeys.length; j++)
						{
							UtilNBTHelper.setInteger(is, affix.nbtKeys[j], UtilNBTHelper.getInt(is,
									affix.nbtKeys[j]) + (int) affix.nbtValues[j]);
						}
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 4:// int
						affix = RotAffixManager.getAffix(rank, RotAffixManager.intSuffixes, random);
						if (affix == null) break;
						UtilNBTHelper.setInteger(is, affix.nbtKeys[0], (UtilNBTHelper.getInt(is,
								affix.nbtKeys[0]) + (int) affix.nbtValues[0]));
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 5:// hp regen
						affix = RotAffixManager.getAffix(rank, RotAffixManager.hpRegenSuffixes,
								random);
						if (affix == null) break;
						UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					case 6:// durability
						affix = RotAffixManager.getAffix(rank, RotAffixManager.selfRepairSuffixes,
								random);
						if (affix == null) break;
						if (affix.affixName != "Ages")
						{
							UtilNBTHelper.setFloat(is, affix.nbtKeys[0], affix.nbtValues[0]);
							UtilNBTHelper.setFloat(is, affix.nbtKeys[1], affix.nbtValues[1]);
						}
						else UtilNBTHelper.setBoolean(is, affix.nbtKeys[0], true);
						UtilNBTHelper.setString(is, UtilNBTKeys.suffixName, affix.affixName);
						gotAffix = true;
						break;
					}
				}
			}
		}
	}
	
	/** Collect NBT tags that are common from held and worn items **/
	private static void getBasicStats(ItemStack is, int[] listCollect, String[] ListSearch)
	{
		for (int i = 0; i < listCollect.length; i++)
		{
			listCollect[i] += UtilNBTHelper.getInt(is, ListSearch[i]);
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
		if (UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairing) > 1f)
		{
			if (UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairTime) == 0)
			{
				item.setItemDamage(item.getItemDamage() - 1);
				UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, UtilNBTHelper.getFloat(
						item, UtilNBTKeys.selfRepairing) * 20);
			}
			else
			{
				UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, UtilNBTHelper.getFloat(
						item, UtilNBTKeys.selfRepairTime) - 1);
			}
		}
	}

	public static void handlePlayerStats(ExtendPlayer props, EntityPlayer player)
	{
		// Stat handling
		// int strMod = 0, dexMod = 0, vitMod = 0, agiMod = 0, intMod = 0,
		// minDmg = 0, maxDmg = 0, defBonus = 0;
		int[] stats = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] statsS = new String[] { UtilNBTKeys.strStat, UtilNBTKeys.dexStat, UtilNBTKeys.vitStat, UtilNBTKeys.agiStat, UtilNBTKeys.intStat, UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat, UtilNBTKeys.defStat };
		float[] bonusStats = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] bonusStatsS = new String[] { UtilNBTKeys.lifeSteal, UtilNBTKeys.manaSteal, UtilNBTKeys.manaStat, UtilNBTKeys.stamStat, UtilNBTKeys.lifeStat, UtilNBTKeys.manaRegenStat, UtilNBTKeys.stamRegenStat, UtilNBTKeys.lifeRegenStat };
		// float lifeSteal = 0, manaSteal = 0;
		// float bonusMana = 0, bonusStam = 0, bonusHealth = 0, bonusManaRegen =
		// 0, bonusStamRegen = 0, bonusHealthRegen = 0;
		ItemStack held = player.getEquipmentInSlot(0), armor1 = player.getEquipmentInSlot(1), armor2 = player
				.getEquipmentInSlot(2), armor3 = player.getEquipmentInSlot(3), armor4 = player
				.getEquipmentInSlot(4);

		if (held != null)
		{
			if ((held.getItem() instanceof ItemSword) || (held.getItem() instanceof ItemTool) || (held
					.getItem() instanceof ItemBow))
			{
				getBasicStats(held, stats, statsS);
				getBonusStats(held, bonusStats, bonusStatsS);
				selfRepair(held);
			}
		}
		if (armor1 != null)
		{
			getBasicStats(armor1, stats, statsS);
			getBonusStats(armor1, bonusStats, bonusStatsS);
			selfRepair(armor1);
		}
		if (armor2 != null)
		{
			getBasicStats(armor2, stats, statsS);
			getBonusStats(armor2, bonusStats, bonusStatsS);
			selfRepair(armor2);
		}
		if (armor3 != null)
		{
			getBasicStats(armor3, stats, statsS);
			getBonusStats(armor3, bonusStats, bonusStatsS);
			selfRepair(armor3);
		}
		if (armor4 != null)
		{
			getBasicStats(armor4, stats, statsS);
			getBonusStats(armor4, bonusStats, bonusStatsS);
			selfRepair(armor4);
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
