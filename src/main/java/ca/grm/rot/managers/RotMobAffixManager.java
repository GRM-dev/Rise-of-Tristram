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
		return -1;
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
		int numberOfPrefixArrays = 1; // Change this each time you add an array of prefixes
		Random random = new Random();
		int roll = random.nextInt(numberOfPrefixArrays);
		switch (roll) {
		case 0: 
			return testerinoPrefixio;
		}
		return null;
	}
	
	public static RotMobAffix[] getRandomSuffixArray()
	{
		int numberOfSuffixArrays = 1; // Change this each time you add an array of suffixes
		Random random = new Random();
		int roll = random.nextInt(numberOfSuffixArrays);
		switch (roll) {
		case 0:
			return testerinoSuffixio;
		}
		return null;
		
	}
	
	/* Prefixes */
	public static RotMobAffix[] testerinoPrefixio = new RotMobAffix[] { new RotMobAffix("Fuckboii", 1) };
	
	/* Suffixes */
	public static RotMobAffix[] testerinoSuffixio = new RotMobAffix[] { new RotMobAffix("Rich Homie Quan", 1) };
}
