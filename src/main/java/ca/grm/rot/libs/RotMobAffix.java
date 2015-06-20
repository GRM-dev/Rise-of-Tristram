package ca.grm.rot.libs;

public class RotMobAffix {
	public String titleName = "";
	public int levelRequirement;
	public String[] nbtKeys;
	public float[] nbtValues;
	
	public RotMobAffix(String titleName, int levelRequirement)
	{
		this.titleName = titleName;
		this.levelRequirement = levelRequirement;
	}
}
