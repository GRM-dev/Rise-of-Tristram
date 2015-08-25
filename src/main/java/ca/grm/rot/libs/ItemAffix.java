package ca.grm.rot.libs;

import net.minecraft.item.Item;

public class ItemAffix
{
	public String affixName = "";
	public int rankLowRequirement;
	public int rankHighRequirement;
	public String[] nbtKeys;
	public float[] nbtValues;
	public int type = 0;

	/** Name that is used to display, 
	 * lowest rank of item this can be on, 
	 * highest rank of item needed this can be on, 
	 * list of nbts, 
	 * list of values for nbts, 
	 * type of affix:0=General, 1=Weapon, 2=Worn, 3=Weapon and Worn **/
	public ItemAffix(String affixName, int rankLow, int rankHigh, String[] nbtKeys, float[] nbtValues,int type)
	{
		this.affixName = affixName;
		this.rankLowRequirement = rankLow;
		this.rankHighRequirement = rankHigh;
		this.nbtKeys = nbtKeys;
		this.nbtValues = nbtValues;
		this.type = type;
	}
	
	/** Name that is used to display, 
	 * lowest rank of item this can be on, 
	 * highest rank of item needed this can be on, 
	 * list of nbts, 
	 * list of value for nbts, 
	 * type of affix:0=General **/
	public ItemAffix(String affixName, int rankLow, int rankHigh, String[] nbtKeys, float[] nbtValues)
	{
		this.affixName = affixName;
		this.rankLowRequirement = rankLow;
		this.rankHighRequirement = rankHigh;
		this.nbtKeys = nbtKeys;
		this.nbtValues = nbtValues;
		this.type = 0;
	}

	/** Name that is used to display, 
	 * lowest rank of item this can be on, 
	 * highest rank of item needed this can be on, 
	 * nbt, 
	 * value for nbt, 
	 * type of affix;0=General, 1=Weapon, 2=Worn, 3=Weapon and Worn **/
	public ItemAffix(String affixName, int rankLow, int rankHigh, String nbtKey, float nbtValue ,int type)
	{
		this.affixName = affixName;
		this.rankLowRequirement = rankLow;
		this.rankHighRequirement = rankHigh;
		this.nbtKeys = new String[] { nbtKey };
		this.nbtValues = new float[] { nbtValue };
		this.type = type;
	}
	
	/** Name that is used to display, 
	 * lowest rank of item this can be on, 
	 * highest rank of item needed this can be on, 
	 * nbt, 
	 * value for nbt,  
	 * type of affix:0=General **/
	public ItemAffix(String affixName, int rankLow, int rankHigh, String nbtKey, float nbtValue)
	{
		this.affixName = affixName;
		this.rankLowRequirement = rankLow;
		this.rankHighRequirement = rankHigh;
		this.nbtKeys = new String[] { nbtKey };
		this.nbtValues = new float[] { nbtValue };
		this.type = 0;
	}
}
