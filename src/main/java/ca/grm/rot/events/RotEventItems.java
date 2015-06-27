package ca.grm.rot.events;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.libs.RotItemAffix;
import ca.grm.rot.libs.UtilNBTHelper;
import ca.grm.rot.libs.UtilNBTKeys;
import ca.grm.rot.managers.RotAffixManager;
import ca.grm.rot.managers.RotClassManager;

public class RotEventItems
{

	private static String[] baseStats = new String[] { UtilNBTKeys.strStat, UtilNBTKeys.dexStat, UtilNBTKeys.intStat, UtilNBTKeys.vitStat, UtilNBTKeys.agiStat };

	private static int[] qualityValues = new int[] { -3, -2, -1, 0, 1, 2, 3, 4 };
	private static String[] qualityNames = new String[] { "Useless", "Broken", "Crude", "Normal", "Fine", "Great", "Superior", "Magic" };

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
					RotItemAffix affix;
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
						break;
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
					RotItemAffix affix;
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
					RotItemAffix affix;
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
					RotItemAffix affix;
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
					RotItemAffix affix;
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

	@SubscribeEvent
	public void onItemToolTipUpdate(ItemTooltipEvent i)
	{

		// Adding stats to Equipment
		ItemStack is = i.itemStack;
		if (is != null)
		{
			Item item = is.getItem();
			if (item instanceof ItemArmor || item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemBow)
			{
				// Random random = new Random();
				// applyItemStats(is, random);

				int rank = UtilNBTHelper.getInt(i.itemStack, UtilNBTKeys.itemRank), str = UtilNBTHelper
						.getInt(i.itemStack, UtilNBTKeys.strStat), agi = UtilNBTHelper.getInt(
						i.itemStack, UtilNBTKeys.agiStat), inte = UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.intStat), vit = UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.vitStat), dex = UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.dexStat);

				int minDmg = UtilNBTHelper.getInt(is, UtilNBTKeys.minDmgStat);
				int maxDmg = UtilNBTHelper.getInt(is, UtilNBTKeys.maxDmgStat);
				float lifeSteal = UtilNBTHelper.getFloat(is, UtilNBTKeys.lifeSteal);
				float manaSteal = UtilNBTHelper.getFloat(is, UtilNBTKeys.manaSteal);

				int defBonus = UtilNBTHelper.getInt(is, UtilNBTKeys.defStat);

				int quality = qualityValues[UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.qualityIndex)];
				String qualityName = qualityNames[UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.qualityIndex)];

				if (quality >= -3 && quality < 0)
				{
					qualityName = EnumChatFormatting.RED + qualityName;
				}
				else if (quality == 0 || quality == 1)
				{
					qualityName = EnumChatFormatting.YELLOW + qualityName;
				}
				else if (quality > 1 && quality < 4)
				{
					qualityName = EnumChatFormatting.GREEN + qualityName;
				}
				else if (quality == 4)
				{
					qualityName = EnumChatFormatting.AQUA + qualityName;
				}

				// Start Adding New Data
				// i.toolTip.clear();
				// i.toolTip.add(is.getDisplayName());
				// i.toolTip.add("");
				if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool)
				{
					String totalDamage = EnumChatFormatting.BLUE + "";
					if (minDmg != 0)
					{
						totalDamage += (int) minDmg + "-" + (int) maxDmg + " Damage";
						i.toolTip.add(totalDamage);
					}
				}

				if (is.getItem() instanceof ItemBow)
				{
					String totalDamage = EnumChatFormatting.BLUE + "";
					if (minDmg != 0)
					{
						totalDamage += (int) minDmg + "-" + (int) maxDmg + " Damage";
						i.toolTip.add("");
						i.toolTip.add(totalDamage);
					}
				}

				if (is.getItem() instanceof ItemArmor) i.toolTip.add(defBonus + (((ItemArmor) is
						.getItem()).damageReduceAmount * 5) + " Armor");

				if (lifeSteal != 0) i.toolTip
						.add(EnumChatFormatting.RED + "Life Steal: " + (int) (lifeSteal * 100) + "%");
				if (manaSteal != 0) i.toolTip
						.add(EnumChatFormatting.AQUA + "Mana Steal: " + (int) (manaSteal * 100) + "%");

				if (is.getItemDamage() != is.getMaxDamage())
				{
					i.toolTip
							.add(EnumChatFormatting.WHITE + ("Durability: " + (is.getMaxDamage() - is
									.getItemDamage()) + " / " + is.getMaxDamage()));
				}

				if (qualityName != "") i.toolTip
						.add(EnumChatFormatting.WHITE + "Quality: " + qualityName);

				if (rank != 0) i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);

				if (str != 0) i.toolTip
						.add((str > 0 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + "Strength Modifier: " + str);

				if (agi != 0) i.toolTip
						.add((agi > 0 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + "Agility Modifier: " + agi);

				if (inte != 0) i.toolTip
						.add((inte > 0 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + "Intelligence Modifier: " + inte);

				if (vit != 0) i.toolTip
						.add((vit > 0 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + "Vitality Modifier: " + vit);

				if (dex != 0) i.toolTip
						.add((dex > 0 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + "Dexterity Modifier: " + dex);

				// String prefixName = UtilNBTHelper.getString(is,
				// UtilNBTKeys.prefixName);
				// if (prefixName.trim() != "")
				// {
				// i.toolTip.add(prefixName);
				// }
				//
				// String suffixName = UtilNBTHelper.getString(is,
				// UtilNBTKeys.suffixName);
				// if (suffixName.trim() != "")
				// {
				// i.toolTip.add(suffixName);
				// }
			}
		}
	}
}
