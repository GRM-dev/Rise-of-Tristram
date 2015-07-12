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
import ca.grm.rot.libs.UtilItemStats;
import ca.grm.rot.libs.UtilNBTHelper;
import ca.grm.rot.libs.UtilNBTKeys;
import ca.grm.rot.managers.RotAffixManager;
import ca.grm.rot.managers.RotClassManager;

public class EventItemToolTip
{
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
				float bonusHealth = UtilNBTHelper.getFloat(is, UtilNBTKeys.lifeStat), bonusMana = UtilNBTHelper
						.getFloat(is, UtilNBTKeys.manaStat), bonusStam = UtilNBTHelper.getFloat(is,
						UtilNBTKeys.stamStat), bonusHealthRegen = UtilNBTHelper.getFloat(is,
						UtilNBTKeys.lifeRegenStat), bonusManaRegen = UtilNBTHelper.getFloat(is,
						UtilNBTKeys.manaRegenStat), bonusStamRegen = UtilNBTHelper.getFloat(is,
						UtilNBTKeys.stamRegenStat);
				float poisonLevel = UtilNBTHelper.getFloat(is, UtilNBTKeys.poison);
				float sickLevel = UtilNBTHelper.getFloat(is, UtilNBTKeys.sickness);
				boolean isVile = UtilNBTHelper.getBoolean(is, UtilNBTKeys.vile);
				float selfRepair = UtilNBTHelper.getFloat(is, UtilNBTKeys.selfRepairing);
				float sockets = UtilNBTHelper.getFloat(is, UtilNBTKeys.sockets);
				float socketsUsed = UtilNBTHelper.getFloat(is, UtilNBTKeys.socketsUsed);
				float dmgBoost = UtilNBTHelper.getFloat(is, UtilNBTKeys.dmgEnhance), dmgPrice = UtilNBTHelper
						.getFloat(is, UtilNBTKeys.dmgPrice);

				int defBonus = UtilNBTHelper.getInt(is, UtilNBTKeys.defStat);

				int quality = UtilItemStats.qualityValues[UtilNBTHelper.getInt(i.itemStack,
						UtilNBTKeys.qualityIndex)];
				String qualityName = UtilItemStats.qualityNames[UtilNBTHelper.getInt(i.itemStack,
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

				if (dmgBoost > 0f)
				{
					i.toolTip
							.add(EnumChatFormatting.DARK_BLUE + "Damage Percent Boost: " + (int) ((dmgBoost - 1f) * 100) + "%");
					i.toolTip
							.add(EnumChatFormatting.DARK_RED + "Damage Health Cost: " + (int) (dmgPrice * 100) + "%");
				}

				if (is.getItem() instanceof ItemArmor) i.toolTip.add(defBonus + (((ItemArmor) is
						.getItem()).damageReduceAmount * 5) + " Armor");

				if (bonusHealth > 0f)
				{
					i.toolTip.add(EnumChatFormatting.RED + "Bonus Health: " + (int) bonusHealth);
				}
				if (bonusHealthRegen > 0f)
				{
					i.toolTip
							.add(EnumChatFormatting.RED + "Bonus Health Regen: " + (int) bonusHealthRegen);
				}

				if (bonusMana > 0f)
				{
					i.toolTip.add(EnumChatFormatting.BLUE + "Bonus Mana: " + (int) bonusMana);
				}
				if (bonusManaRegen > 0f)
				{
					i.toolTip
							.add(EnumChatFormatting.BLUE + "Bonus Mana Regen: " + (int) bonusManaRegen);
				}

				if (bonusStam > 0f)
				{
					i.toolTip.add(EnumChatFormatting.YELLOW + "Bonus Stamina: " + (int) bonusStam);
				}
				if (bonusStamRegen > 0f)
				{
					i.toolTip
							.add(EnumChatFormatting.YELLOW + "Bonus Stamina Regen: " + (int) bonusStamRegen);
				}

				if (lifeSteal != 0) i.toolTip
						.add(EnumChatFormatting.RED + "Life Steal: " + (int) (lifeSteal * 100) + "%");
				if (manaSteal != 0) i.toolTip
						.add(EnumChatFormatting.AQUA + "Mana Steal: " + (int) (manaSteal * 100) + "%");
				if (poisonLevel > 0f)
				{
					if (poisonLevel == 1f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Poison for 3 seconds");
					if (poisonLevel == 2f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Poison for 5 seconds");
					if (poisonLevel == 3f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Wither for 5 seconds");
				}

				if (sickLevel > 0f)
				{
					if (sickLevel >= 1f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Nausea for 6 seconds");
					if (sickLevel >= 2f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Slow for 6 seconds");
					if (sickLevel == 3f) i.toolTip
							.add(EnumChatFormatting.GREEN + "Weakness for 6 seconds");
				}

				if (isVile)
				{
					i.toolTip.add(EnumChatFormatting.GREEN + "Prevents Health and");
					i.toolTip.add(EnumChatFormatting.GREEN + "Stamina Regen for 6 seconds");
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

				if (selfRepair != 1f && is.isItemStackDamageable())
				{
					i.toolTip
							.add(EnumChatFormatting.WHITE + ("Durability: " + (is.getMaxDamage() - is
									.getItemDamage()) + " / " + is.getMaxDamage()));
					if (selfRepair > 1f)
					{
						i.toolTip
								.add(EnumChatFormatting.WHITE + ("Repairs in: " + (int) (UtilNBTHelper
										.getFloat(is, UtilNBTKeys.selfRepairTime) / 20) + "s"));
					}
				}

				if (sockets > 0)
				{
					i.toolTip
							.add(EnumChatFormatting.WHITE + ("Sockets: " + (int) (sockets) + " free: " + (int) (sockets - socketsUsed)));
				}

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
