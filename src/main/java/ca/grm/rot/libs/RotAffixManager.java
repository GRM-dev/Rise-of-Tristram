package ca.grm.rot.libs;

public class RotAffixManager
{
	public RotAffixManager()
	{

	}

	// Returns how long the rank array extends
	public static int getRankCount(int rank, RotItemAffix[] affix)
	{
		int count = 0;
		for (RotItemAffix a : affix)
		{
			if (rank == a.rankRequirement) count++;
		}
		return count;
	}

	// Returns where that rank level starts
	public static int getRankStart(int rank, RotItemAffix[] affix)
	{
		int lastRank = 0;
		for (int index = 0; index < affix.length; index++)
		{
			if (rank == affix[index].rankRequirement) return index;
		}
		return -1;
	}

	/** Prefixes **/
	public static RotItemAffix vilePrefix = new RotItemAffix("Vile", 4, UtilNBTKeys.vile, 1f);

	public static RotItemAffix[] poisonPrefixes = new RotItemAffix[] { new RotItemAffix(
			"Poisonous", 2, UtilNBTKeys.poison, 1f), new RotItemAffix("Venomous", 3,
			UtilNBTKeys.poison, 2f), new RotItemAffix("Toxic", 4, UtilNBTKeys.poison, 3f) };

	public static RotItemAffix[] sickPrefixes = new RotItemAffix[] { new RotItemAffix("Sickly", 2,
			UtilNBTKeys.sickness, 1f), new RotItemAffix("Feverish", 3, UtilNBTKeys.sickness, 2f), new RotItemAffix(
			"Crippling", 4, UtilNBTKeys.sickness, 3f) };

	public static RotItemAffix[] socketPrefixes = new RotItemAffix[] { new RotItemAffix(
			"Mechanic's", 2, UtilNBTKeys.sockets, 1f), new RotItemAffix("Artisan's", 3,
			UtilNBTKeys.sockets, 2f), new RotItemAffix("Jeweler's", 4, UtilNBTKeys.sockets, 3f) };

	public static RotItemAffix[] damagePrefixes = new RotItemAffix[] { new RotItemAffix("Jagged",
			1, new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 10f, 20f }), new RotItemAffix("Deadly", 1,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 21f, 31f }), new RotItemAffix("Vicious", 2,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 31f, 41f }), new RotItemAffix("Brutal", 2,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 41f, 51f }), new RotItemAffix("Massive", 3,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 61f, 75f }), new RotItemAffix("Savage", 3,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 66f, 80f }), new RotItemAffix("Merciless", 4,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 81f, 100f }), new RotItemAffix("Ferocious", 4,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 101f, 200f }), new RotItemAffix("Cruel", 5,
			new String[] { UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat },
			new float[] { 201f, 300f }) };

	public static RotItemAffix[] damageExtraPrefixes = new RotItemAffix[] { new RotItemAffix(
			"Careless", 1, new String[] { UtilNBTKeys.dmgEnhance, UtilNBTKeys.dmgPrice },
			new float[] { 1.05f, 0.01f }), new RotItemAffix("Reckless", 2,
			new String[] { UtilNBTKeys.dmgEnhance, UtilNBTKeys.dmgPrice },
			new float[] { 1.10f, 0.02f }), new RotItemAffix("Vengeful", 3,
			new String[] { UtilNBTKeys.dmgEnhance, UtilNBTKeys.dmgPrice },
			new float[] { 1.15f, 0.03f }), new RotItemAffix("Suicidal", 4,
			new String[] { UtilNBTKeys.dmgEnhance, UtilNBTKeys.dmgPrice },
			new float[] { 1.20f, 0.04f }), new RotItemAffix("Sacrificial", 5,
			new String[] { UtilNBTKeys.dmgEnhance, UtilNBTKeys.dmgPrice },
			new float[] { 1.25f, 0.05f }) };

	public static RotItemAffix[] defencePrefixes = new RotItemAffix[] { new RotItemAffix("Stout",
			1, UtilNBTKeys.defStat, 10f), new RotItemAffix("Sturdy", 1, UtilNBTKeys.defStat, 20), new RotItemAffix(
			"Strong", 2, UtilNBTKeys.defStat, 30), new RotItemAffix("Glorious", 2,
			UtilNBTKeys.defStat, 40), new RotItemAffix("Stalwart", 3, UtilNBTKeys.defStat, 50f), new RotItemAffix(
			"Blessed", 3, UtilNBTKeys.defStat, 60), new RotItemAffix("Saintly", 4,
			UtilNBTKeys.defStat, 70f), new RotItemAffix("Holy", 4, UtilNBTKeys.defStat, 80f), new RotItemAffix(
			"Godly", 5, UtilNBTKeys.defStat, 90f) };

	public static RotItemAffix[] manaPrefixes = new RotItemAffix[] { new RotItemAffix("Toad's", 1,
			new String[] { UtilNBTKeys.manaStat }, new float[] { 50f }), new RotItemAffix(
			"Lizard's", 1, new String[] { UtilNBTKeys.manaStat }, new float[] { 70 }), new RotItemAffix(
			"Snake's", 2, new String[] { UtilNBTKeys.manaStat }, new float[] { 90 }), new RotItemAffix(
			"Serpent's", 2, new String[] { UtilNBTKeys.manaStat }, new float[] { 110 }), new RotItemAffix(
			"Drake's", 3, new String[] { UtilNBTKeys.manaStat }, new float[] { 130f }), new RotItemAffix(
			"Dragon's", 3, new String[] { UtilNBTKeys.manaStat }, new float[] { 150 }), new RotItemAffix(
			"Wyrm's", 4, new String[] { UtilNBTKeys.manaStat }, new float[] { 170f }), new RotItemAffix(
			"Great Wyrm's", 4, new String[] { UtilNBTKeys.manaStat }, new float[] { 190f }), new RotItemAffix(
			"Bahamut's", 5, new String[] { UtilNBTKeys.manaStat }, new float[] { 210f }) };

	public static RotItemAffix[] manaRegenPrefixes = new RotItemAffix[] { new RotItemAffix(
			"Invigorating", 1, new String[] { UtilNBTKeys.manaRegenStat }, new float[] { 5f }), new RotItemAffix(
			"Rejuvenating", 2, new String[] { UtilNBTKeys.manaRegenStat }, new float[] { 10f }), new RotItemAffix(
			"Exhilarating", 3, new String[] { UtilNBTKeys.manaRegenStat }, new float[] { 15f }), new RotItemAffix(
			"Revitalizing", 4, new String[] { UtilNBTKeys.manaRegenStat }, new float[] { 20f }), new RotItemAffix(
			"Refreshing", 5, new String[] { UtilNBTKeys.manaRegenStat }, new float[] { 25f }) };

	public static RotItemAffix[] stamPrefixes = new RotItemAffix[] { new RotItemAffix("Rugged", 1,
			new String[] { UtilNBTKeys.stamStat }, new float[] { 150f }), new RotItemAffix(
			"Vigourous", 2, new String[] { UtilNBTKeys.stamStat }, new float[] { 300f }), new RotItemAffix(
			"Rigourous", 3, new String[] { UtilNBTKeys.stamStat }, new float[] { 450f }), new RotItemAffix(
			"Provoking", 4, new String[] { UtilNBTKeys.stamStat }, new float[] { 600f }), new RotItemAffix(
			"Grievous", 5, new String[] { UtilNBTKeys.stamStat }, new float[] { 750f }) };

	public static RotItemAffix[] stamRegenPrefixes = new RotItemAffix[] { new RotItemAffix("Eager",
			1, new String[] { UtilNBTKeys.stamRegenStat }, new float[] { 10f }), new RotItemAffix(
			"Restless", 2, new String[] { UtilNBTKeys.stamRegenStat }, new float[] { 20f }), new RotItemAffix(
			"Tireless", 3, new String[] { UtilNBTKeys.stamRegenStat }, new float[] { 30f }), new RotItemAffix(
			"Unwearying", 4, new String[] { UtilNBTKeys.stamRegenStat }, new float[] { 40f }), new RotItemAffix(
			"Energetic", 5, new String[] { UtilNBTKeys.stamRegenStat }, new float[] { 50f }) };

	/** Suffixes **/
	public static RotItemAffix[] lifeStealSuffixes = new RotItemAffix[] { new RotItemAffix(
			"the Flea", 1, UtilNBTKeys.lifeSteal, 0.01f), new RotItemAffix("the Louse", 1,
			UtilNBTKeys.lifeSteal, 0.02f), new RotItemAffix("the Mosquito", 2,
			UtilNBTKeys.lifeSteal, 0.03f), new RotItemAffix("the Tick", 2, UtilNBTKeys.lifeSteal,
			0.04f), new RotItemAffix("the Leech", 3, UtilNBTKeys.lifeSteal, 0.05f), new RotItemAffix(
			"the Locust", 3, UtilNBTKeys.lifeSteal, 0.06f), new RotItemAffix("the Spider", 4,
			UtilNBTKeys.lifeSteal, 0.07f), new RotItemAffix("the Lamprey", 4,
			UtilNBTKeys.lifeSteal, 0.08f), new RotItemAffix("the Parasite", 5,
			UtilNBTKeys.lifeSteal, 0.09f) };

	public static RotItemAffix[] manaStealSuffixes = new RotItemAffix[] { new RotItemAffix(
			"the Bat", 1, UtilNBTKeys.manaSteal, 0.01f), new RotItemAffix("the Shadow", 1,
			UtilNBTKeys.manaSteal, 0.02f), new RotItemAffix("the Phantom", 2,
			UtilNBTKeys.manaSteal, 0.03f), new RotItemAffix("the Ghost", 2, UtilNBTKeys.manaSteal,
			0.04f), new RotItemAffix("the Wraith", 3, UtilNBTKeys.manaSteal, 0.05f), new RotItemAffix(
			"the Spectre", 3, UtilNBTKeys.manaSteal, 0.06f), new RotItemAffix("the Vampire", 4,
			UtilNBTKeys.manaSteal, 0.07f), new RotItemAffix("the Lich", 4, UtilNBTKeys.manaSteal,
			0.08f), new RotItemAffix("the Demon", 5, UtilNBTKeys.manaSteal, 0.09f) };

	public static RotItemAffix[] vitSuffixes = new RotItemAffix[] { new RotItemAffix("the Jackel",
			1, UtilNBTKeys.vitStat, 6f), new RotItemAffix("the Fox", 1, UtilNBTKeys.vitStat, 9f), new RotItemAffix(
			"Hope", 2, UtilNBTKeys.vitStat, 12f), new RotItemAffix("the Wolf", 2,
			UtilNBTKeys.vitStat, 15f), new RotItemAffix("the Tiger", 3, UtilNBTKeys.vitStat, 18f), new RotItemAffix(
			"the Horse", 3, UtilNBTKeys.vitStat, 21f), new RotItemAffix("the Mammoth", 4,
			UtilNBTKeys.vitStat, 24f), new RotItemAffix("the Whale", 4, UtilNBTKeys.vitStat, 27f), new RotItemAffix(
			"the Colossus", 5, UtilNBTKeys.vitStat, 30f) };

	public static RotItemAffix[] dexSuffixes = new RotItemAffix[] { new RotItemAffix("Dexterity",
			1, UtilNBTKeys.dexStat, 6f), new RotItemAffix("Skill", 1, UtilNBTKeys.dexStat, 9f), new RotItemAffix(
			"Talent", 2, UtilNBTKeys.dexStat, 12f), new RotItemAffix("Hunting", 2,
			UtilNBTKeys.dexStat, 15f), new RotItemAffix("Accuracy", 3, UtilNBTKeys.dexStat, 18f), new RotItemAffix(
			"Sniping", 3, UtilNBTKeys.dexStat, 21f), new RotItemAffix("Precision", 4,
			UtilNBTKeys.dexStat, 24f), new RotItemAffix("Perfection", 4, UtilNBTKeys.dexStat, 27f), new RotItemAffix(
			"Nirvana", 5, UtilNBTKeys.dexStat, 30f) };

	public static RotItemAffix[] strSuffixes = new RotItemAffix[] { new RotItemAffix("Strength", 1,
			UtilNBTKeys.strStat, 6f), new RotItemAffix("Might", 1, UtilNBTKeys.strStat, 9f), new RotItemAffix(
			"the Bull", 2, UtilNBTKeys.strStat, 12f), new RotItemAffix("the Ox", 2,
			UtilNBTKeys.strStat, 15f), new RotItemAffix("the Gorilla", 3, UtilNBTKeys.strStat, 18f), new RotItemAffix(
			"the Elephant", 3, UtilNBTKeys.strStat, 21f), new RotItemAffix("Giants", 4,
			UtilNBTKeys.strStat, 24f), new RotItemAffix("Titans", 4, UtilNBTKeys.strStat, 27f), new RotItemAffix(
			"Atlas", 5, UtilNBTKeys.strStat, 30f) };

	public static RotItemAffix[] intSuffixes = new RotItemAffix[] { new RotItemAffix("Energy", 1,
			UtilNBTKeys.intStat, 6f), new RotItemAffix("Knowledge", 1, UtilNBTKeys.intStat, 9f), new RotItemAffix(
			"Mind", 2, UtilNBTKeys.intStat, 12f), new RotItemAffix("Thoughts", 2,
			UtilNBTKeys.intStat, 15f), new RotItemAffix("Inspiration", 3, UtilNBTKeys.intStat, 18f), new RotItemAffix(
			"Brilliancet", 3, UtilNBTKeys.intStat, 21f), new RotItemAffix("Sorcery", 4,
			UtilNBTKeys.intStat, 24f), new RotItemAffix("Wizardry", 4, UtilNBTKeys.intStat, 27f), new RotItemAffix(
			"Enlightenment", 5, UtilNBTKeys.intStat, 30f) };

	public static RotItemAffix[] allBaseSuffixes = new RotItemAffix[] { new RotItemAffix(
			"Zodiac",
			1,
			new String[] { UtilNBTKeys.strStat, UtilNBTKeys.dexStat, UtilNBTKeys.vitStat, UtilNBTKeys.agiStat, UtilNBTKeys.intStat },
			new float[] { 12f, 12f, 12f, 12f, 12f }) };

	public static RotItemAffix[] hpRegenSuffixes = new RotItemAffix[] { new RotItemAffix("Honor",
			1, new String[] { UtilNBTKeys.lifeRegenStat }, new float[] { 5f }), new RotItemAffix(
			"Regeneration", 2, new String[] { UtilNBTKeys.lifeRegenStat }, new float[] { 10f }), new RotItemAffix(
			"Regrowth", 3, new String[] { UtilNBTKeys.lifeRegenStat }, new float[] { 15f }), new RotItemAffix(
			"Revivification", 4, new String[] { UtilNBTKeys.lifeRegenStat }, new float[] { 20f }), new RotItemAffix(
			"Immortality", 5, new String[] { UtilNBTKeys.lifeRegenStat }, new float[] { 25f }) };

	public static RotItemAffix[] selfRepairSuffixes = new RotItemAffix[] { new RotItemAffix(
			"Self-Repair", 1, UtilNBTKeys.selfRepairing, 30f), new RotItemAffix("Restoration", 1,
			UtilNBTKeys.selfRepairing, 22f), new RotItemAffix("Ages", 1,
			UtilNBTKeys.indestructible, 1f) };
}
