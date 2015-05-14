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
	public static String qualityDisplay = Rot.MOD_ID + "qualityDisplay";
	public static String qualityValue = Rot.MOD_ID + "qualityValue";
	public static String identifiedState = Rot.MOD_ID + "isIdentified";

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
				// System.out.println("Success rolled a: " + rngRoll
				// + " between: " + leftChancePointer + "-"
				// + rightChancePointer);
				leftChancePointer += (0.1f - (0.01f * (quality < 0 ? 0
						: quality - 1)) - (0.025f * (rank - 1)));
				rightChancePointer -= (0.1f - (0.01f * (quality < 0 ? 0
						: quality - 1)) - (0.025f * (rank - 1)));
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
						break;
					}
				}
			}
			else
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
		if (is.getItem() instanceof ItemArmor)
		{
			UtilityNBTHelper.setInteger(
					is,
					defStat,
					(11 * rank) + (random.nextInt(10) * rank)
							+ (random.nextInt(6) * (quality - 1)));
		}
	}

	public static void applyItemQuality(ItemStack is, Random random, int rank)
	{
		String name = UtilityNBTHelper.getString(is, qualityDisplay);
		int value = 0;
		float roll = MathHelper.clamp_float(random.nextFloat()
				+ ((10 * (rank - 1)) / 100), 0f, 1f);
		if (name == "")
		{
			if (roll >= 0 && roll <= 0.2f)
			{
				name = "Useless";
				value = -3;
			}
			else if (roll > 0.2f && roll <= 0.4f)
			{
				name = "Broken";
				value = -2;
			}
			else if (roll > 0.4f && roll <= 0.5f)
			{
				name = "Crude";
				value = -1;
			}
			else if (roll > 0.5f && roll <= 0.55f)
			{
				name = "Normal";
				value = 0;
			}
			else if (roll > 0.55f && roll < 0.6f)
			{
				name = "Fine";
				value = 1;
			}
			else if (roll > 0.6f && roll < 0.65f)
			{
				name = "Great";
				value = 2;
			}
			else if (roll > 0.65f && roll <= 0.7f)
			{// Superior
				name = "Superior";
				value = 3;
			}
			else
			{
				name = "Magic";
				// UtilityNBTHelper.setBoolean(is, identifiedState, true);
				value = 4;
			}
		}
		UtilityNBTHelper.setString(is, qualityDisplay, name);
		UtilityNBTHelper.setInteger(is, qualityValue, value);
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

				int rank = UtilityNBTHelper.getInt(i.itemStack, itemRank), str = UtilityNBTHelper
						.getInt(i.itemStack, strStat), agi = UtilityNBTHelper
						.getInt(i.itemStack, agiStat), inte = UtilityNBTHelper
						.getInt(i.itemStack, intStat), vit = UtilityNBTHelper
						.getInt(i.itemStack, vitStat), dex = UtilityNBTHelper
						.getInt(i.itemStack, dexStat);

				int minDmg = UtilityNBTHelper.getInt(is, minDmgStat);
				int maxDmg = UtilityNBTHelper.getInt(is, maxDmgStat);

				int defBonus = UtilityNBTHelper.getInt(is, defStat);

				int quality = UtilityNBTHelper
						.getInt(i.itemStack, qualityValue);
				String qualityName = UtilityNBTHelper.getString(i.itemStack,
						qualityDisplay);

				if (qualityName == "Useless" || qualityName == "Broken"
						|| qualityName == "Crude")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.RED
							+ qualityName);
				}
				else if (qualityName == "Normal")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.YELLOW
							+ qualityName);
				}
				else if (qualityName == "Superior")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.GREEN
							+ qualityName);
				}
				else if (qualityName == "Magic")
				{
					UtilityNBTHelper.setString(is, Rot.MOD_ID
							+ "qualityDisplay", EnumChatFormatting.AQUA
							+ qualityName);
				}

				// Start Adding New Data
				i.toolTip.clear();
				i.toolTip.add(is.getDisplayName());
				i.toolTip.add("");
				if (is.getItem() instanceof ItemSword) i.toolTip
						.add(EnumChatFormatting.BLUE
								+ ""
								+ (int) (minDmg + (((ItemSword) is.getItem())
										.getDamageVsEntity() * 5))
								+ "-"
								+ (int) (maxDmg + (((ItemSword) is.getItem())
										.getDamageVsEntity() * 5)) + " Damage");

				if (is.getItem() instanceof ItemBow) i.toolTip
						.add(EnumChatFormatting.BLUE + "" + minDmg + "-"
								+ maxDmg + " Damage");

				if (is.getItem() instanceof ItemTool) i.toolTip
						.add(EnumChatFormatting.BLUE
								+ ""
								+ (int) (minDmg + (((ItemTool) is.getItem())
										.getToolMaterial().getDamageVsEntity() * 5))
								+ "-"
								+ (int) (maxDmg + (((ItemTool) is.getItem())
										.getToolMaterial().getDamageVsEntity() * 5))
								+ " Damage");
				if (is.getItem() instanceof ItemArmor) i.toolTip.add(defBonus
						+ (((ItemArmor) is.getItem()).damageReduceAmount * 5)
						+ " Armor");

				if (qualityName != "")
				{
					i.toolTip.add(EnumChatFormatting.WHITE + "Quality: "
							+ qualityName);
				}
				if (rank != 0)
				{
					i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);
				}
				if (str != 0)
				{
					i.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
							: EnumChatFormatting.RED)
							+ "Strength Modifier: "
							+ str);
				}
				if (agi != 0)
				{
					i.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
							: EnumChatFormatting.RED)
							+ "Agility Modifier: "
							+ agi);
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
							+ "Vitality Modifier: "
							+ vit);
				}
				if (dex != 0)
				{
					i.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
							: EnumChatFormatting.RED)
							+ "Dexterity Modifier: "
							+ dex);
				}
			}
		}
	}
}
