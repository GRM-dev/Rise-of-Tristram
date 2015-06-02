package ca.grm.rot.libs;

import ca.grm.rot.Rot;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RotSkill
{
	public String skillName;
	public IMessage packet;
	private int skillIconIndex;
	private int maxCoolDown;
	public int coolDownReduction;
	public int coolDown;
	public int requiredLevel = 0;
	
	
	public RotSkill(String name,int iconIndex, IMessage packet, int coolDown, int reqLevel)
	{
		this.skillName = name;
		this.skillIconIndex = iconIndex;
		this.packet = packet;
		this.maxCoolDown = coolDown;
		this.requiredLevel = reqLevel;
	}
	
	public void update()
	{
		if (coolDown > 0)
		{
			coolDown--;
		}
	}
	
	public void use()
	{
		if (coolDown == 0)
		{
			Rot.net.sendToServer(packet);
		}
	}
}
