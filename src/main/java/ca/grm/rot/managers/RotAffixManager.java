package ca.grm.rot.managers;

import java.util.ArrayList;
import java.util.Random;

import ca.grm.rot.libs.ItemAffix;
import ca.grm.rot.libs.UtilNBTKeys;

public class RotAffixManager
{
	public RotAffixManager()
	{

	}

	public static ItemAffix getPrefix(int rank, int itemType)
	{
		Random rand = new Random();
		ArrayList<ItemAffix> returnableAffix = new ArrayList<ItemAffix>();
		for (ItemAffix a : allPrefixes)
		{
			//If the Item Type is Held or Worn
			if (itemType == 1 || itemType == 2)
			{
				//All Prefixes that are General, Held/Worn, and For Held and Worn only
				if (a.type == 0 || a.type == itemType || a.type == 3)
				{
					//And the item rank matches
					if (rank >= a.rankLowRequirement && rank <= a.rankHighRequirement)
					{
						//Add the prefix to list of acceptable affixes
						returnableAffix.add(a);
					}
				}
			}
			//If for some reason something that is not held or worn, just return prefixes that are general
			else if (a.type == 0)
			{
				if (rank >= a.rankLowRequirement && rank <= a.rankHighRequirement)
				{
					returnableAffix.add(a);
				}
			}
		}
		if (!returnableAffix.isEmpty())
		{
			return returnableAffix.get(rand.nextInt(returnableAffix.size()));
		}
		else return null;
	}
	
	public static ItemAffix getSuffix(int rank, int itemType)
	{
		Random rand = new Random();
		ArrayList<ItemAffix> returnableAffix = new ArrayList<ItemAffix>();
		for (ItemAffix a : allSuffixes)
		{
			//If the Item Type is Held or Worn
			if (itemType == 1 || itemType == 2)
			{
				//All Suffixes that are General, Held/Worn, and For Held and Worn only
				if (a.type == 0 || a.type == itemType || a.type == 3)
				{
					//And the item rank matches
					if (rank >= a.rankLowRequirement && rank <= a.rankHighRequirement)
					{
						//Add the suffix to list of acceptable affixes
						returnableAffix.add(a);
					}
				}
			}
			//If for some reason something that is not held or worn, just return suffixes that are general
			else if (a.type == 0)
			{
				if (rank >= a.rankLowRequirement && rank <= a.rankHighRequirement)
				{
					returnableAffix.add(a);
				}
			}
		}
		if (!returnableAffix.isEmpty())
		{
			return returnableAffix.get(rand.nextInt(returnableAffix.size()));
		}
		else return null;
	}

	/** Prefixes **/
	public static ItemAffix[] allPrefixes = new ItemAffix[] {
			new ItemAffix("Vile", 4, 5, UtilNBTKeys.vile, 1f, 1),
			new ItemAffix("Poisonous", 2, 4, UtilNBTKeys.poison, 1f, 1),
			new ItemAffix("Venomous", 3, 5, UtilNBTKeys.poison, 2f, 1),
			new ItemAffix("Toxic", 4, 6, UtilNBTKeys.poison, 3f, 1),
			new ItemAffix("Sickly", 2, 4, UtilNBTKeys.sickness, 1f, 1),
			new ItemAffix("Feverish", 3, 5, UtilNBTKeys.sickness, 2f, 1),
			new ItemAffix("Crippling", 4, 6, UtilNBTKeys.sickness, 3f, 1),
			new ItemAffix("Mechanic's", 1, 2, UtilNBTKeys.sockets, 1f,3),
			new ItemAffix("Artisan's", 1, 4, UtilNBTKeys.sockets, 2f,3),
			new ItemAffix("Jeweler's", 3, 6, UtilNBTKeys.sockets, 3f,3),
			new ItemAffix("Jagged", 1, 2, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 10f, 20f }, 1),
			new ItemAffix("Deadly", 1, 3, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 21f, 31f }, 1),
			new ItemAffix("Vicious", 2, 4, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 31f, 41f }, 1),
			new ItemAffix("Brutal", 2, 5, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 41f, 51f }, 1),
			new ItemAffix("Massive", 3, 5, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 61f, 75f }, 1),
			new ItemAffix("Savage", 3, 6, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 66f, 80f }, 1),
			new ItemAffix("Merciless", 4, 6, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 81f, 100f }, 1),
			new ItemAffix("Ferocious", 4, 6, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 101f, 200f }, 1),
			new ItemAffix("Cruel", 5, 6, new String[] {
					UtilNBTKeys.minDmgStat,
					UtilNBTKeys.maxDmgStat }, new float[] { 201f, 300f }, 1),
			new ItemAffix("Careless", 1, 3, new String[] {
					UtilNBTKeys.dmgEnhance,
					UtilNBTKeys.dmgPrice }, new float[] { 1.05f, 0.01f }, 1),
			new ItemAffix("Reckless", 2, 5, new String[] {
					UtilNBTKeys.dmgEnhance,
					UtilNBTKeys.dmgPrice }, new float[] { 1.10f, 0.02f }, 1),
			new ItemAffix("Vengeful", 3, 6, new String[] {
					UtilNBTKeys.dmgEnhance,
					UtilNBTKeys.dmgPrice }, new float[] { 1.15f, 0.03f }, 1),
			new ItemAffix("Suicidal", 4, 6, new String[] {
					UtilNBTKeys.dmgEnhance,
					UtilNBTKeys.dmgPrice }, new float[] { 1.20f, 0.04f }, 1),
			new ItemAffix("Sacrificial", 5, 6, new String[] {
					UtilNBTKeys.dmgEnhance,
					UtilNBTKeys.dmgPrice }, new float[] { 1.25f, 0.05f }, 1),
			new ItemAffix("Stout", 1, 3, UtilNBTKeys.defStat, 10f, 2),
			new ItemAffix("Sturdy", 1, 4, UtilNBTKeys.defStat, 20f, 2),
			new ItemAffix("Strong", 2, 4, UtilNBTKeys.defStat, 30f, 2),
			new ItemAffix("Glorious", 2, 5, UtilNBTKeys.defStat, 40f, 2),
			new ItemAffix("Stalwart", 3, 5, UtilNBTKeys.defStat, 50f, 2),
			new ItemAffix("Blessed", 3, 6, UtilNBTKeys.defStat, 60f, 2),
			new ItemAffix("Saintly", 4, 6, UtilNBTKeys.defStat, 70f, 2),
			new ItemAffix("Holy", 4, 6, UtilNBTKeys.defStat, 80f, 2),
			new ItemAffix("Godly", 5, 6, UtilNBTKeys.defStat, 90f, 2),
			new ItemAffix("Toad's", 1, 3, UtilNBTKeys.manaStat, 50f, 2),
			new ItemAffix("Lizard's", 1, 4, UtilNBTKeys.manaStat, 70f, 2),
			new ItemAffix("Snake's", 2, 4, UtilNBTKeys.manaStat, 90f, 2),
			new ItemAffix("Serpent's", 2, 5, UtilNBTKeys.manaStat, 110f, 2),
			new ItemAffix("Drake's", 3, 5, UtilNBTKeys.manaStat, 130f, 2),
			new ItemAffix("Dragon's", 3, 6, UtilNBTKeys.manaStat, 150f, 2),
			new ItemAffix("Wyrm's", 4, 6, UtilNBTKeys.manaStat, 170f, 2),
			new ItemAffix("Great Wyrm's", 4, 6, UtilNBTKeys.manaStat, 190f, 2),
			new ItemAffix("Bahamut's", 5, 6, UtilNBTKeys.manaStat, 210f, 2),
			new ItemAffix("Invigorating", 1, 3, UtilNBTKeys.manaRegenStat, 5f, 2),
			new ItemAffix("Rejuvenating", 2, 4, UtilNBTKeys.manaRegenStat, 10f, 2),
			new ItemAffix("Exhilarating", 3, 5, UtilNBTKeys.manaRegenStat, 15f, 2),
			new ItemAffix("Revitalizing", 4, 6, UtilNBTKeys.manaRegenStat, 20f, 2),
			new ItemAffix("Refreshing", 5, 6, UtilNBTKeys.manaRegenStat, 25f, 2),
			new ItemAffix("Rugged", 1, 3, UtilNBTKeys.stamStat, 150f, 2),
			new ItemAffix("Vigourous", 2, 4, UtilNBTKeys.stamStat, 300f, 2),
			new ItemAffix("Rigourous", 3, 5, UtilNBTKeys.stamStat, 450f, 2),
			new ItemAffix("Provoking", 4, 6, UtilNBTKeys.stamStat, 600f, 2),
			new ItemAffix("Grievous", 5, 6, UtilNBTKeys.stamStat, 750f, 2),
			new ItemAffix("Eager", 1, 3, UtilNBTKeys.stamRegenStat, 10f, 2),
			new ItemAffix("Restless", 2, 4, UtilNBTKeys.stamRegenStat, 20f, 2),
			new ItemAffix("Tireless", 3, 5, UtilNBTKeys.stamRegenStat, 30f, 2),
			new ItemAffix("Unwearying", 4, 6, UtilNBTKeys.stamRegenStat, 40f, 2),
			new ItemAffix("Energetic", 5, 6, UtilNBTKeys.stamRegenStat, 50f, 2)

	};

	/** Suffixes **/
	public static ItemAffix[] allSuffixes = new ItemAffix[] {
			new ItemAffix("the Flea", 1, 2, UtilNBTKeys.lifeSteal, 0.01f, 1),
			new ItemAffix("the Louse", 1, 3, UtilNBTKeys.lifeSteal, 0.015f, 1),
			new ItemAffix("the Mosquito", 2, 3, UtilNBTKeys.lifeSteal, 0.02f, 1),
			new ItemAffix("the Tick", 2, 4, UtilNBTKeys.lifeSteal, 0.025f, 1),
			new ItemAffix("the Leech", 3, 4, UtilNBTKeys.lifeSteal, 0.03f, 1),
			new ItemAffix("the Locust", 3, 5, UtilNBTKeys.lifeSteal, 0.035f, 1),
			new ItemAffix("the Spider", 4, 5, UtilNBTKeys.lifeSteal, 0.04f, 1),
			new ItemAffix("the Lamprey", 4, 6, UtilNBTKeys.lifeSteal, 0.045f, 1),
			new ItemAffix("the Parasite", 5, 6, UtilNBTKeys.lifeSteal, 0.05f, 1),
			new ItemAffix("the Bat", 1, 2, UtilNBTKeys.manaSteal, 0.01f, 1),
			new ItemAffix("the Shadow", 1, 3, UtilNBTKeys.manaSteal, 0.015f, 1),
			new ItemAffix("the Phantom", 2, 3, UtilNBTKeys.manaSteal, 0.02f, 1),
			new ItemAffix("the Ghost", 2, 4, UtilNBTKeys.manaSteal, 0.025f, 1),
			new ItemAffix("the Wraith", 3, 4, UtilNBTKeys.manaSteal, 0.03f, 1),
			new ItemAffix("the Spectre", 3, 5, UtilNBTKeys.manaSteal, 0.035f, 1),
			new ItemAffix("the Vampire", 4, 5, UtilNBTKeys.manaSteal, 0.04f, 1),
			new ItemAffix("the Lich", 4, 6, UtilNBTKeys.manaSteal, 0.045f, 1),
			new ItemAffix("the Demon", 5, 6, UtilNBTKeys.manaSteal, 0.05f, 1),
			new ItemAffix("the Jackel", 1, 2, UtilNBTKeys.vitStat, 6f),
			new ItemAffix("the Fox", 1, 3, UtilNBTKeys.vitStat, 9f),
			new ItemAffix("Hope", 2, 3, UtilNBTKeys.vitStat, 12f),
			new ItemAffix("the Wolf", 2, 4, UtilNBTKeys.vitStat, 15f),
			new ItemAffix("the Tiger", 3, 4, UtilNBTKeys.vitStat, 18f),
			new ItemAffix("the Horse", 3, 5, UtilNBTKeys.vitStat, 21f),
			new ItemAffix("the Mammoth", 4, 5, UtilNBTKeys.vitStat, 24f),
			new ItemAffix("the Whale", 4, 6, UtilNBTKeys.vitStat, 27f),
			new ItemAffix("the Colossus", 5, 6, UtilNBTKeys.vitStat, 30f),
			new ItemAffix("Dexterity", 1, 2, UtilNBTKeys.dexStat, 6f),
			new ItemAffix("Skill", 1, 3, UtilNBTKeys.dexStat, 9f),
			new ItemAffix("Talent", 2, 3, UtilNBTKeys.dexStat, 12f),
			new ItemAffix("Hunting", 2, 4, UtilNBTKeys.dexStat, 15f),
			new ItemAffix("Accuracy", 3, 4, UtilNBTKeys.dexStat, 18f),
			new ItemAffix("Sniping", 3, 5, UtilNBTKeys.dexStat, 21f),
			new ItemAffix("Precision", 4, 5, UtilNBTKeys.dexStat, 24f),
			new ItemAffix("Perfection", 4, 6, UtilNBTKeys.dexStat, 27f),
			new ItemAffix("Nirvana", 5, 6, UtilNBTKeys.dexStat, 30f),
			new ItemAffix("Strength", 1, 2, UtilNBTKeys.strStat, 6f),
			new ItemAffix("Might", 1, 3, UtilNBTKeys.strStat, 9f),
			new ItemAffix("the Bull", 2, 3, UtilNBTKeys.strStat, 12f),
			new ItemAffix("the Ox", 2, 4, UtilNBTKeys.strStat, 15f),
			new ItemAffix("the Gorilla", 3, 4, UtilNBTKeys.strStat, 18f),
			new ItemAffix("the Elephant", 3, 5, UtilNBTKeys.strStat, 21f),
			new ItemAffix("Giants", 4, 5, UtilNBTKeys.strStat, 24f),
			new ItemAffix("Titans", 4, 6, UtilNBTKeys.strStat, 27f),
			new ItemAffix("Atlas", 5, 6, UtilNBTKeys.strStat, 30f),
			new ItemAffix("Energy", 1, 2, UtilNBTKeys.intStat, 6f),
			new ItemAffix("Knowledge", 1, 3, UtilNBTKeys.intStat, 9f),
			new ItemAffix("Mind", 2, 3, UtilNBTKeys.intStat, 12f),
			new ItemAffix("Thoughts", 2, 4, UtilNBTKeys.intStat, 15f),
			new ItemAffix("Inspiration", 3, 4, UtilNBTKeys.intStat, 18f),
			new ItemAffix("Brilliancet", 3, 5, UtilNBTKeys.intStat, 21f),
			new ItemAffix("Sorcery", 4, 5, UtilNBTKeys.intStat, 24f),
			new ItemAffix("Wizardry", 4, 6, UtilNBTKeys.intStat, 27f),
			new ItemAffix("Enlightenment", 5, 6, UtilNBTKeys.intStat, 30f),
			new ItemAffix("Zodiac", 3, 6, new String[] {
					UtilNBTKeys.strStat,
					UtilNBTKeys.dexStat,
					UtilNBTKeys.vitStat,
					UtilNBTKeys.agiStat,
					UtilNBTKeys.intStat }, new float[] { 18f, 18f, 18f, 18f, 18f }),
			new ItemAffix("Honor", 1, 3, UtilNBTKeys.lifeRegenStat, 5f, 2),
			new ItemAffix("Regeneration", 2, 4, UtilNBTKeys.lifeRegenStat, 10f, 2),
			new ItemAffix("Regrowth", 3, 5, UtilNBTKeys.lifeRegenStat, 15f, 2),
			new ItemAffix("Revivification", 4, 6, UtilNBTKeys.lifeRegenStat, 20f, 2),
			new ItemAffix("Immortality", 5, 6, UtilNBTKeys.lifeRegenStat, 25f, 2),
			new ItemAffix("Self-Repair", 1, 3, new String[] {
					UtilNBTKeys.selfRepairing,
					UtilNBTKeys.selfRepairTime }, new float[] { 33f, 33f * 20 },3),
			new ItemAffix("Restoration", 3, 6, new String[] {
					UtilNBTKeys.selfRepairing,
					UtilNBTKeys.selfRepairTime }, new float[] { 20f, 20f * 20 },3),
			new ItemAffix("Ages", 5, 6, UtilNBTKeys.indestructible, 1f,3) };
}
