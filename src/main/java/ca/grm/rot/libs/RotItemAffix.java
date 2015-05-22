package ca.grm.rot.libs;

import net.minecraft.item.Item;

public class RotItemAffix
{
	public String affixName = "";
	public int rankRequirement;
	public String[] nbtKeys;
	public float[] nbtValues;

	public RotItemAffix(String affixName, int rankRequirement, String[] nbtKeys, float[] nbtValues)
	{
		this.affixName = affixName;
		this.rankRequirement = rankRequirement;
		this.nbtKeys = nbtKeys;
		this.nbtValues = nbtValues;
	}

	public RotItemAffix(String affixName, int rankRequirement, String nbtKey, float nbtValue)
	{
		this.affixName = affixName;
		this.rankRequirement = rankRequirement;
		this.nbtKeys = new String[] { nbtKey };
		this.nbtValues = new float[] { nbtValue };
	}
}
