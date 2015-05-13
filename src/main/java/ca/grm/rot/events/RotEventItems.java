package ca.grm.rot.events;

import java.util.Random;
import java.util.stream.BaseStream;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;

public class RotEventItems
{
	public static String itemRank = Rot.MOD_ID + "rankLevel";
	public static String strStat = Rot.MOD_ID + "strModifier";
	public static String dexStat = Rot.MOD_ID + "dexModifier";
	public static String intStat = Rot.MOD_ID + "intModifier";
	public static String vitStat = Rot.MOD_ID + "vitModifier";
	public static String agiStat = Rot.MOD_ID + "agiModifier";
	public static String minDmgStat = Rot.MOD_ID + "minDmgModifier";
	public static String maxDmgStat = Rot.MOD_ID + "maxDmgModifier";
	public static String defStat = Rot.MOD_ID + "defModifier";
	public static String lifeStat = Rot.MOD_ID + "lifeModifier";
	public static String manaStat = Rot.MOD_ID + "manaModifier";
	public static String stamStat = Rot.MOD_ID + "stamModifier";
	public static String lifeRegenStat = Rot.MOD_ID + "lifeRegenModifier";
	public static String manaRegenStat = Rot.MOD_ID + "manaRegenModifier";
	public static String stamRegenStat = Rot.MOD_ID + "stamRegenModifier";
	public static String numSockets = Rot.MOD_ID + "numSocketsModifier";
	public static String lifeSteal = Rot.MOD_ID + "lifeStealModifier";
	public static String manaSteal = Rot.MOD_ID + "manaStealModifier";

	private static String[] baseStats = new String[] { strStat, dexStat,
			intStat, vitStat, agiStat };

	/**
	 * Methods for setting a stat, and getting them via from the item or from an
	 * item socket
	 **/
	public static int getStatValue(ItemStack is, String sid)
	{
		return UtilityNBTHelper.getInt(is, sid);
	}

	public static int getStatValue(ItemStack is, String sid, int slotId)
	{
		return UtilityNBTHelper.getInt(is, (sid + slotId));
	}

	public static void setStatValue(ItemStack is, String sid, int sv)
	{
		UtilityNBTHelper.setInteger(is, sid, sv);
	}

	public static void setStatValue(ItemStack is, String sid, int sv, int slotId)
	{
		UtilityNBTHelper.setInteger(is, (sid + slotId), sv);
	}

	/** Methods to trigger then applying stats **/
	public static void applyItemStats(ItemStack is, Random random)
	{
		int rank = UtilityNBTHelper.getInt(is, itemRank);
		if (rank == 0)
		{
			rank = random.nextInt(3) + 1;
			UtilityNBTHelper.setInteger(is, itemRank, rank);
			applyItemQuality(is, random, rank);
			int quality = UtilityNBTHelper.getInt(is, Rot.MOD_ID
					+ "qualityValue");
			statRoll(is, random, rank, quality);
		}
	}

	public static void applyItemStats(ItemStack is, Random random, int rank)
	{
		UtilityNBTHelper.setInteger(is, itemRank, rank);
		applyItemQuality(is, random, rank);
		int quality = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityValue");
		statRoll(is, random, rank, quality);
	}

	private static void statRoll(ItemStack is, Random random, int rank,
			int quality)
	{
		boolean rollStats = true;
		float leftChancePointer = (0.3f - (0.05f * quality));
		float rightChancePointer = (0.7f + (0.05f * quality));
		float rngRoll = 0;
		int statsAdded = 0;
		// System.out.println("Starting the statRoll");
		while (rollStats)
		{
			rngRoll = random.nextFloat();
			if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
			{
//				 System.out.println("Success rolled a: " + rngRoll
//				 + " between: " + leftChancePointer + "-"
//				 + rightChancePointer);
				leftChancePointer += (0.1f - (0.01f * (quality - 1)) - (0.025f * (rank - 1)));
				rightChancePointer -= (0.1f - (0.01f * (quality - 1)) - (0.025f * (rank - 1)));
				for (int tries = 0; tries < 50; tries++)
				{
					int r = random.nextInt(baseStats.length - 1);
					// System.out.println("trying to add: " + baseStats[r]);
					int statValue = UtilityNBTHelper.getInt(is, baseStats[r]);
					// System.out.println("stat found: " + statValue);
					if (statValue == 0)
					{
						// System.out.println("Adding: " + baseStats[r]);
						statsAdded++;
						UtilityNBTHelper.setInteger(is, baseStats[r],
								random.nextInt(11) * rank);
					}
				}
//				boolean keepRolling = true;
//				
//				while (keepRolling)
//				{
//					// System.out.println("Trying to apply a stat");
//					int r = random.nextInt(baseStats.length - 1);
//					// System.out.println("trying to add: " + baseStats[r]);
//					int statValue = UtilityNBTHelper.getInt(is, baseStats[r]);
//					// System.out.println("stat found: " + statValue);
//					if (statValue == 0)
//					{
//						// System.out.println("Adding: " + baseStats[r]);
//						statsAdded++;
//						UtilityNBTHelper.setInteger(is, baseStats[r],
//								random.nextInt(11) * rank);
//						keepRolling = false;
//					}
//					if (statsAdded == baseStats.length)
//						keepRolling = false;
//				}
				// System.out.println("Done trying to apply a stat");
			} else
			{
				rollStats = false;
			}
		}
		if (is.getItem() instanceof ItemSword
				|| is.getItem() instanceof ItemTool
				|| is.getItem() instanceof ItemBow)
		{
			UtilityNBTHelper.setInteger(
					is,
					minDmgStat,
					(11 * rank) + (random.nextInt(10) * rank)
							+ (random.nextInt(6) * (quality - 1)));
			UtilityNBTHelper.setInteger(
					is,
					maxDmgStat,
					(23 * rank) + (random.nextInt(30) * rank)
							+ (random.nextInt(9) * (quality - 1)));
		}
	}

	public static void applyItemQuality(ItemStack is, Random random, int rank)
	{
		String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
		int qualityModifier = 0;
		int qualityValue = 0;
		int q = random.nextInt(100);
		q = MathHelper.clamp_int(q + (10 * rank), 0, 100);
		int qd = random.nextInt(3);
		if (quality == "")
		{
			// Low, Normal, Superior, Magic.
			// 30% = Low, all stats -1
			// 30% = Normal, +/- 0
			// 30% = Superior, all stats +1
			// 10% = Magic, all stats +2 w/ random magic effect
			if (q >= 00 && q <= 29)
			{
				qualityValue = 1;
				// Low
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
				qualityModifier = -1;
				switch (qd)
				{
				case (0):
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", "Cracked");
					break;
				case (1):
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", "Damaged");
					break;
				case (2):
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", "Crude");
					break;
				}
			} else if (q >= 30 && q <= 59)
			{
				qualityValue = 2;
				// Normal
				UtilityNBTHelper
						.setString(is, Rot.MOD_ID + "quality", "Normal");

				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay",
						"Normal");
			} else if (q >= 60 && q <= 89)
			{
				qualityValue = 3;
				qualityModifier = 1;
				// Superior
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality",
						"Superior");
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay",
						"Superior");
			} else if (q >= 90)
			{
				qualityValue = 4;
				qualityModifier = 2;
				// Magic
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");

				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay",
						"Magic");
			}
		}
		UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier",
				qualityModifier);
		UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityValue",
				qualityValue);
	}

	@SubscribeEvent
	public void onItemToolTipUpdate(ItemTooltipEvent i)
	{

		// Adding stats to Equipment
		ItemStack is = i.itemStack;
		if (is != null)
		{
			Item item = is.getItem();
			if (item instanceof ItemArmor || item instanceof ItemTool
					|| item instanceof ItemSword || item instanceof ItemBow)
			{
				Random random = new Random();
				applyItemStats(is, random);

				int rank = UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID
						+ "rankLevel"), str = UtilityNBTHelper.getInt(
						i.itemStack, Rot.MOD_ID + "strModifier"), agi = UtilityNBTHelper
						.getInt(i.itemStack, Rot.MOD_ID + "agiModifier"), inte = UtilityNBTHelper
						.getInt(i.itemStack, Rot.MOD_ID + "intModifier"), vit = UtilityNBTHelper
						.getInt(i.itemStack, Rot.MOD_ID + "vitModifier"), dex = UtilityNBTHelper
						.getInt(i.itemStack, Rot.MOD_ID + "dexModifier");

				String quality = UtilityNBTHelper.getString(i.itemStack,
						Rot.MOD_ID + "quality");
				String qualityDisplay = UtilityNBTHelper.getString(i.itemStack,
						Rot.MOD_ID + "qualityDisplay");
				String magicModifierPrefix = UtilityNBTHelper.getString(
						i.itemStack, Rot.MOD_ID + "magicModifierPrefix");
				String magicModifierSuffix = UtilityNBTHelper.getString(
						i.itemStack, Rot.MOD_ID + "magicModifierSuffix");
				String magicModifierDescriptionPrefix = UtilityNBTHelper
						.getString(i.itemStack, Rot.MOD_ID
								+ "magicModifierDescriptionPrefix");
				String magicModifierDescriptionSuffix = UtilityNBTHelper
						.getString(i.itemStack, Rot.MOD_ID
								+ "magicModifierDescriptionSuffix");
				if (qualityDisplay == "Crude" || qualityDisplay == "Cracked"
						|| qualityDisplay == "Damaged")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.RED
							+ qualityDisplay);
				} else if (qualityDisplay == "Normal")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.YELLOW
							+ qualityDisplay);
				} else if (qualityDisplay == "Superior")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.GREEN
							+ qualityDisplay);
				} else if (qualityDisplay == "Magic")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.AQUA
							+ qualityDisplay);
				}

				if ((i.itemStack.getItem() instanceof ItemTool)
						|| (i.itemStack.getItem() instanceof ItemSword)
						|| (i.itemStack.getItem() instanceof ItemArmor)
						|| (i.itemStack.getItem() instanceof ItemBow))
				{
					if (quality != "")
					{
						i.toolTip.add(EnumChatFormatting.WHITE
								+ "Quality: "
								+ EnumChatFormatting.LIGHT_PURPLE
								+ UtilityNBTHelper.getString(is,
										magicModifierPrefix)
								+ " "
								+ qualityDisplay
								+ " "
								+ EnumChatFormatting.LIGHT_PURPLE
								+ UtilityNBTHelper.getString(is,
										magicModifierSuffix));
					}
					if (rank != 0)
					{
						i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: "
								+ rank);
					}
					if (str != 0)
					{
						i.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
								: EnumChatFormatting.RED)
								+ "Strength Modifier: " + str);
					}
					if (agi != 0)
					{
						i.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
								: EnumChatFormatting.RED)
								+ "Agility Modifier: " + agi);
					}
					if (inte != 0)
					{
						i.toolTip.add((inte > 0 ? EnumChatFormatting.GREEN
								: EnumChatFormatting.RED)
								+ "Intelligence Modifier: " + inte);
					}
					if (vit != 0)
					{
						i.toolTip.add((vit > 0 ? EnumChatFormatting.GREEN
								: EnumChatFormatting.RED)
								+ "Vitality Modifier: " + vit);
					}
					if (dex != 0)
					{
						i.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
								: EnumChatFormatting.RED)
								+ "Dexterity Modifier: " + dex);
					}
					if (magicModifierPrefix != "")
					{
						i.toolTip.add(""); // Blank line for spacing.
						i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE
								+ "Prefix Effect: " + EnumChatFormatting.GOLD
								+ magicModifierDescriptionPrefix);
					}
					if (magicModifierSuffix != "")
					{
						i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE
								+ "Suffix Effect: " + EnumChatFormatting.GOLD
								+ magicModifierDescriptionSuffix);
					}
				}
			}
		}
	}
}
