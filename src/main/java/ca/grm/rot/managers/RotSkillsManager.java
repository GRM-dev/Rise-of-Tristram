package ca.grm.rot.managers;

import ca.grm.rot.libs.RotSkill;
import ca.grm.rot.libs.RotSkillToggle;

public class RotSkillsManager
{

	public static String repairSkill = "Repair";
	public static String digSpeedSkill = "Hastened Mining";
	public static String healSkill = "Heal";
	
	public RotSkillsManager()
	{
		
	}
	
	public static RotSkill[] attackSkills = new RotSkill[]{};
	public static RotSkill[] buffSkills = new RotSkill[]{};
	
	public static RotSkillToggle[] oneShotSkills = new RotSkillToggle[]{};
	public static RotSkillToggle[] toggleSkills = new RotSkillToggle[]{};
	
}
