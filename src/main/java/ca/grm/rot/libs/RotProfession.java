package ca.grm.rot.libs;

public class RotProfession
{
	// Unlike Classes, you just need to know what professions you are
	// As the title is what determines the passive effects and skills
	public String professionName;
	public String professionDesc;

	public RotProfession(String professionName, String professionDesc)
	{
		this.professionName = professionName;
		this.professionDesc = professionDesc;
	}

}
