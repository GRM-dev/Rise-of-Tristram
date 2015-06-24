package ca.grm.rot.managers;

import java.lang.reflect.Array;
import java.util.Random;

import ca.grm.rot.libs.RotItemAffix;
import ca.grm.rot.libs.RotMobAffix;
import ca.grm.rot.libs.UtilNBTKeys;

public class RotMobAffixManager {
	public RotMobAffixManager()
	{
		
	}
	
	// Returns how long the rank array extends
	/*public static int getLevelCount(int level, RotMobAffix[] affix)
	{
		int count = 0;
		for (RotMobAffix a : affix) // 'for each' affix in array
		{
			if (level == a.levelRequirement) count++;
		}
		return count;
	}
	
	public static int getLevelStart(int level, RotMobAffix[] affix)
	{
		for (int index = 0; index < affix.length; index++)
		{
			if (level == affix[index].levelRequirement) return index;
		}
		return 0;
	}*/
	
	// Return the affix out of the array
	public static RotMobAffix getAffix(int level, RotMobAffix[] affix, Random random)
	{	/*
		//Try to get where, in the array, to start for affix ranking requirement
		if (level > 4) return affix[affix.length - 1];
		int affixIndex = getLevelStart(level, affix);
		if (affixIndex != -1)
		{
			//Get how far up the affix array to search
			int levelRange = getLevelCount(level, affix);
			if (levelRange > 1)
			{
				if (affixIndex > 0)
				{
					//If the index is not at the start get a random spot in the range
					int randomIndex = random.nextInt(levelRange);
					//And if it so happens to be at the start, try to go back one ranking
					if (randomIndex == 0) randomIndex -= random.nextInt(2);
					return affix[randomIndex + affixIndex];
				}
				else return affix[random.nextInt(levelRange)];
			}
			else return affix[affixIndex];
		}
		*/
		for (int i = 0; i < Array.getLength(affix); i++) // 'for each' affix in array
		{
			if (level <= affix[i].levelRequirement)  // If our level is lessthan/equalto affix's level requirement
			{
				if (i > 0) // If we're not at the first one -- to avoid an index out of bounds error
				{
					if (affix[i-1].levelRequirement < level) // If our level is greater than the affix before this one and less than this one.
					{
						return affix[i-1];
					}
				}
				else // If we're at the first one and we're still less than it:
				{
					return affix[i];
				}
			}
			else
			{
				if (i == Array.getLength(affix)-1) // If we're at the very last one and we're *still* higher than it:
				{
					return affix[i];
				}
			}
		}
		return null;
	}
	
	public static RotMobAffix[] getRandomPrefixArray()
	{
		int numberOfPrefixArrays = 7; // Change this each time you add an array of prefixes
		Random random = new Random();
		int roll = random.nextInt(numberOfPrefixArrays);
		switch (roll) {
		case 0: 
			return health;
		case 1: 
			return strength;
		case 2:
			return dexterity;
		case 3:
			return agility;
		case 4:
			return vitality;
		case 5:
			return hpRegenBonusPercent;
		case 6:
			return gold;
		}
		System.out.println("p " + roll);
		return null;
	}
	
	public static RotMobAffix[] getRandomSuffixArray()
	{
		int numberOfSuffixArrays = 3; // Change this each time you add an array of suffixes
		Random random = new Random();
		int roll = random.nextInt(numberOfSuffixArrays);
		switch (roll) {
		case 0:
			return fireImmunity;
		case 1:
			return ice;
		case 2:
			return healing;
		}
		System.out.println("s " + roll);
		return null;
	}
	
	/* Suffixes */
	// The general idea for these is to deal with mobly-stuff. Stuff like size or skills or attributes.
	public static RotMobAffix[] fireImmunity = new RotMobAffix[] { new RotMobAffix("the Heated", 10)};
	public static RotMobAffix[] ice = new RotMobAffix[] { new RotMobAffix("the Cold", 5) };
	public static RotMobAffix[] healing = new RotMobAffix[] { new RotMobAffix("Full of Aura", 15) };
	
	/* Prefixes */
	// The general idea for these is to deal with stats, stuff like health and strength.
	public static RotMobAffix[] health = new RotMobAffix[] { new RotMobAffix("Fed", 1), new RotMobAffix("Hearty", 7), new RotMobAffix("Obese", 13)};
	public static RotMobAffix[] strength = new RotMobAffix[] { new RotMobAffix("Bulky", 2), new RotMobAffix("Muscular", 8), new RotMobAffix("Ripped", 14), new RotMobAffix("Beefcastle", 19)};
	public static RotMobAffix[] dexterity = new RotMobAffix[] { new RotMobAffix("Sharp-Eye", 3), new RotMobAffix("Eagle-Eye", 9), new RotMobAffix("Marksman", 15), new RotMobAffix("Sniper", 20)};
	public static RotMobAffix[] agility = new RotMobAffix[] { new RotMobAffix("Quick", 4), new RotMobAffix("Fast", 10), new RotMobAffix("Wicked Speed", 16), new RotMobAffix("Flash", 21)};
	public static RotMobAffix[] vitality = new RotMobAffix[] { new RotMobAffix("Strong-Will", 5), new RotMobAffix("Stonewall", 11), new RotMobAffix("Tough-Skin", 17), new RotMobAffix("Death Transcending", 22)};
	public static RotMobAffix[] hpRegenBonusPercent = new RotMobAffix[] { new RotMobAffix("Healing", 6), new RotMobAffix("Blessed", 12), new RotMobAffix("Cleric", 18), new RotMobAffix("Altar-Betraying", 23)};
	public static RotMobAffix[] gold = new RotMobAffix[] { new RotMobAffix("Tropical", 7) };
}
