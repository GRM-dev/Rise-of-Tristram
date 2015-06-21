package ca.grm.rot.managers;

import java.util.Random;

import ca.grm.rot.libs.RotItemAffix;
import ca.grm.rot.libs.RotMobAffix;
import ca.grm.rot.libs.UtilNBTKeys;

public class RotMobAffixManager {
	public RotMobAffixManager()
	{
		
	}
	
	// Returns how long the rank array extends
	public static int getLevelCount(int level, RotMobAffix[] affix)
	{
		int count = 0;
		for (RotMobAffix a : affix)
		{
			if (level == a.levelRequirement) count++;
		}
		return count;
	}
	
	public static int getLevelStart(int level, RotMobAffix[] affix)
	{
		if (level > 4) return affix.length;
		for (int index = 0; index < affix.length; index++)
		{
			if (level == affix[index].levelRequirement) return index;
		}
		return 0;
	}
	
	// Return the affix out of the array
	public static RotMobAffix getAffix(int level, RotMobAffix[] affix, Random random)
	{	
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
					if (randomIndex == 0) randomIndex -= random.nextInt(1);
					return affix[randomIndex + affixIndex];
				}
				else return affix[random.nextInt(levelRange)];
			}
			else return affix[affixIndex];
		}
		return null;
	}
	
	public static RotMobAffix[] getRandomPrefixArray()
	{
		int numberOfPrefixArrays = 3; // Change this each time you add an array of prefixes
		Random random = new Random();
		int roll = random.nextInt(numberOfPrefixArrays);
		switch (roll) {
		case 0: 
			return size;
		case 1: 
			return ice;
		case 2:
			return healing;
		}
		System.out.println("p " + roll);
		return strength;
	}
	
	public static RotMobAffix[] getRandomSuffixArray()
	{
		int numberOfSuffixArrays = 2; // Change this each time you add an array of suffixes
		Random random = new Random();
		int roll = random.nextInt(numberOfSuffixArrays);
		switch (roll) {
		case 0:
			return health;
		case 1:
			return strength;
		}
		System.out.println("s " + roll);
		return ice;
	}
	
	/* Prefixes */
	// The general idea for these is to deal with mobly-stuff. Stuff like size or skills or attributes.
	public static RotMobAffix[] size = new RotMobAffix[] { new RotMobAffix("Big", 5), new RotMobAffix("Huge", 8), new RotMobAffix("Giant", 12), new RotMobAffix("Massive", 16) };
	public static RotMobAffix[] ice = new RotMobAffix[] { new RotMobAffix("Cold", 3) };
	public static RotMobAffix[] healing = new RotMobAffix[] { new RotMobAffix("Aura", 6) };
	
	/* Suffixes */
	// The general idea for these is to deal with stats, stuff like health and strength.
	public static RotMobAffix[] health = new RotMobAffix[] { new RotMobAffix("the Fed", 4), new RotMobAffix("the Hearty", 7), new RotMobAffix("the Obese", 15)};
	public static RotMobAffix[] strength = new RotMobAffix[] { new RotMobAffix("the Bulky", 6), new RotMobAffix("the Muscular", 14), new RotMobAffix("the Ripped", 18)};

}
