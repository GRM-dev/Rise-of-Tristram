package ca.grm.rot.events;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;

public class RotEventItems 
{
	/**Methods for setting a stat, and getting them via from the item or from an item socket**/
	public static int getStatValue(ItemStack is,String sid)
	{
		return UtilityNBTHelper.getInt(is, sid);
	}
	
	public static int getStatValue(ItemStack is,String sid,int slotId)
	{
		return UtilityNBTHelper.getInt(is, (sid + slotId));
	}
	
	public static void setStatValue(ItemStack is,String sid, int sv)
	{
		UtilityNBTHelper.setInteger(is,sid,sv);
	}
	
	public static void setStatValue(ItemStack is,String sid,int sv,int slotId)
	{
		UtilityNBTHelper.setInteger(is,(sid + slotId),sv);
	}
	
	/**Methods to trigger then applying stats**/
	public static void applyItemStats(ItemStack is, Random random)
	{		
		int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
		if (rank == 0) {
			rank = 1;
			UtilityNBTHelper
					.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemArmor || is.getItem() instanceof ItemTool)
				UtilityNBTHelper.setInteger(is,Rot.MOD_ID + "strModifier",random.nextInt(3) + (rank * (random.nextInt(2) == 0 ? 1 : -1)));
			if (is.getItem() instanceof ItemArmor)
				UtilityNBTHelper.setInteger(is,Rot.MOD_ID + "agiModifier", random.nextInt(3) + (rank * (random.nextInt(2) == 0 ? 1 : -1)));
			if (is.getItem() instanceof ItemArmor)
				UtilityNBTHelper.setInteger(is,Rot.MOD_ID + "intModifier",random.nextInt(3) + (rank * (random.nextInt(2) == 0 ? 1 : -1)));
			if (is.getItem() instanceof ItemArmor)
				UtilityNBTHelper.setInteger(is,Rot.MOD_ID + "vitModifier",random.nextInt(3) + (rank * (random.nextInt(2) == 0 ? 1 : -1)));
			if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemBow || is.getItem() instanceof ItemArmor)
				UtilityNBTHelper.setInteger(is,Rot.MOD_ID + "dexModifier",random.nextInt(3) + (rank * (random.nextInt(2) == 0 ? 1 : -1)));
		}
	}
	
	public static void applyItemQuality(ItemStack is, Random random)
	{
		String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
		int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
		int q = random.nextInt(100);
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
				// Low
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
				switch (qd)
				{
				case (0):
					UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
					break;
				case(1):
					UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
					break;
				case(2):
					UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
					break;
				}
			}
			else if (q >= 30 && q <= 59)
			{
				// Normal
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
			}
			else if (q >= 60 && q <= 89)
			{
				// Superior
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
			}
			else if (q >= 90)
			{
				// Magic
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
				
				applyAffix(is,random);
			}
		}
	}
	
	public static void applyAffix(ItemStack is, Random random)
	{
		// Armor Magics; 40% Prefix, 40% Suffix, 20% Both.
		// This means there's a 4% chance of getting a magic item with a prefix, same for suffix, and 2% for both.
		
		// Prefixes
		// Sturdy = + 1 Vit
		// Crimson = +1 Fire Resist
		// Glimmering = +1 Light Radius
		// Lizard's = +1 Int/Mana regen
		// Rugged = +1 Stam regen
		
		// Suffixes
		// Balance = +1 Vit
		// Health = +1 health regen
		// Warding = +1 Magic Resist
		// Self Repair = 1 +dura every 33 seconds
		// Skill = +1 Dex
		// Energy = +1 stam
		// Jackal = +1 vit
		int psb = random.nextInt(10);
		if (psb >= 0 && psb <= 3)
		{
			// Apply prefix only
			int p = random.nextInt(5);
			switch (p)
			{
			case (0):
				// Sturdy
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Sturdy");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (1):
				// Crimson
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Crimson");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (2):
				// Glimmering
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Glimmering");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (3):
				// Lizard's
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Lizard's");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (4):
				// Rugged
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Rugged");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			}
		}
		else if (psb >= 4 && psb <= 7)
		{
			// Apply suffix only
			int s = random.nextInt(7);
			switch(s)
			{
			case (0):
				// Balance
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Balance");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (1):
				// Health
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Health");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (2):
				// Warding
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Warding");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (3):
				// Self Repair
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Self Repair");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (4):
				// Skill
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Skill");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (5):
				// Energy
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Energy");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (6):
				// Jackal
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Jackal");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			}
		}
		else if (psb >= 8 && psb <= 9)
		{
			// Apply both
			
			// Apply prefix only
			int p = random.nextInt(5);
			switch (p)
			{
			case (0):
				// Sturdy
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Sturdy");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (1):
				// Crimson
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Crimson");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (2):
				// Glimmering
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Glimmering");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (3):
				// Lizard's
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Lizard's");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			case (4):
				// Rugged
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Rugged");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
				break;
			}
			
			// Apply suffix
			int s = random.nextInt(7);
			switch(s)
			{
			case (0):
				// Balance
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Balance");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (1):
				// Health
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Health");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (2):
				// Warding
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Warding");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (3):
				// Self Repair
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Self Repair");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (4):
				// Skill
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Skill");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (5):
				// Energy
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Energy");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			case (6):
				// Jackal
				UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Jackal");
				UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
				break;
			}
		}
	}
	
	@SubscribeEvent
	public void onItemToolTipUpdate(ItemTooltipEvent i) {
		
		Random random = new Random();
		// Adding stats to Equipment
						ItemStack is = i.itemStack;
						if (is != null) {
							Item item = is.getItem();
							if (item instanceof ItemArmor) {
								String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
								int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
								String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
								String magicModifierPrefix = UtilityNBTHelper.getString(is, Rot.MOD_ID + "magicModifierPrefix");
								int magicModifierPrefixLevel = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "magicModifierPrefixLevel");
								String magicModifierSuffix = UtilityNBTHelper.getString(is, Rot.MOD_ID + "magicModifierSuffix");
								int magicModifierSuffixLevel = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "magicModifierSuffixLevel");
								applyItemQuality(is, random);
								applyItemStats(is,random);
							}
							if ((item instanceof ItemSword) || (item instanceof ItemTool)) {
								String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
								int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
								String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
								if (quality == "")
								{
									// Low, Normal, Superior, Magic.
									// 30% = Low, all stats -1
									// 30% = Normal, +/- 0
									// 30% = Superior, all stats +1
									// 10% = Magic, all stats +2 w/ random magic effect
									int q = random.nextInt(100);
									int qd = random.nextInt(3);
									if (q >= 00 && q <= 29)
									{
										// Low
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
										switch (qd)
										{
										case (0):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
											break;
										case(1):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
											break;
										case(2):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
											break;
										}
									}
									else if (q >= 30 && q <= 59)
									{
										// Normal
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
									}
									else if (q >= 60 && q <= 89)
									{
										// Superior
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
									}
									else if (q >= 90 && q <= 99)
									{
										// Magic
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
									}			
								}
								applyItemStats(is, random);
									if (item instanceof ItemSword) {

									}
									// UtilityNBTHelper.setFloat(is, Rot.MODID+"size",
									// 0.45f * (player.worldObj.rand.nextInt(2) == 0 ? 1
									// : -1));
									// UtilityNBTHelper.setFloat(is, Rot.MODID+"size",
									// 0.65f * (player.worldObj.rand.nextInt(2) == 0 ? 1
									// : -1));
								}
							}
							if (is.getItem() instanceof ItemBow) {
								String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
								int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
								String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
								if (quality == "")
								{
									// Low, Normal, Superior, Magic.
									// 30% = Low, all stats -1
									// 30% = Normal, +/- 0
									// 30% = Superior, all stats +1
									// 10% = Magic, all stats +2 w/ random magic effect
									int q = random.nextInt(100);
									int qd = random.nextInt(3);
									if (q >= 00 && q <= 29)
									{
										// Low
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
										switch (qd)
										{
										case (0):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
											break;
										case(1):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
											break;
										case(2):
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
											break;
										}
									}
									else if (q >= 30 && q <= 59)
									{
										// Normal
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
									}
									else if (q >= 60 && q <= 89)
									{
										// Superior
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
									}
									else if (q >= 90 && q <= 99)
									{
										// Magic
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
										UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
										UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
									}			
								}
								applyItemStats(is, random);
								}
		
		
		
		// This is here incase we wanna do the old stuff. Basically a backup.
		//updateItemToolTipOld(e);
		int rank = UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "rankLevel"), str = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "strModifier"), agi = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "agiModifier"), inte = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "intModifier"), vit = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "vitModifier"), dex = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "dexModifier");
		
				str += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
				if (i.itemStack.getItem() instanceof ItemArmor) {
					agi += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					inte += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					vit += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					dex += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
				}
				 String quality = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "quality");
				 String qualityDisplay = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "qualityDisplay");
				 String magicModifierPrefix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierPrefix");
				 String magicModifierSuffix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierSuffix");
				 String magicModifierDescriptionPrefix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierDescriptionPrefix");
				 String magicModifierDescriptionSuffix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierDescriptionSuffix");
				 if (qualityDisplay == "Crude" || qualityDisplay == "Cracked" || qualityDisplay == "Damaged")
				 {
					 qualityDisplay = EnumChatFormatting.RED + qualityDisplay;
				 }
				 else if (qualityDisplay == "Normal")
				 {
					 qualityDisplay = EnumChatFormatting.YELLOW + qualityDisplay;
				 }
				 else if (qualityDisplay == "Superior")
				 {
					 qualityDisplay = EnumChatFormatting.GREEN + qualityDisplay;
				 }
				 else if (qualityDisplay == "Magic")
				 {
					 qualityDisplay = EnumChatFormatting.AQUA + qualityDisplay;
				 }
		
		if ((i.itemStack.getItem() instanceof ItemTool)
				|| (i.itemStack.getItem() instanceof ItemSword)
				|| (i.itemStack.getItem() instanceof ItemArmor)
				|| (i.itemStack.getItem() instanceof ItemBow)) {
			if (quality != "")
			{
				i.toolTip.add(EnumChatFormatting.WHITE + "Quality: " + EnumChatFormatting.LIGHT_PURPLE + magicModifierPrefix +" "+ qualityDisplay +" "+ EnumChatFormatting.LIGHT_PURPLE + magicModifierSuffix);
			}	
			if (rank != 0) {
				i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);
			}
			if (str != 0) {
				i.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Strength Modifier: " + str);
			}
			if (agi != 0) {
				i.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Agility Modifier: " + agi);
			}
			if (inte != 0) {
				i.toolTip.add((inte > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Intelligence Modifier: " + inte);
			}
			if (vit != 0) {
				i.toolTip.add((vit > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Vitality Modifier: " + vit);
			}
			if (dex != 0) {
				i.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Dexterity Modifier: " + dex);
			}
			if (magicModifierPrefix != "")
			{
				i.toolTip.add(""); // Blank line for spacing.
				i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + "Prefix Effect: " + EnumChatFormatting.GOLD + magicModifierDescriptionPrefix);
			}
			if (magicModifierSuffix != "")
			{
				i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + "Suffix Effect: " + EnumChatFormatting.GOLD + magicModifierDescriptionSuffix);
			}
		}
	}	
}
