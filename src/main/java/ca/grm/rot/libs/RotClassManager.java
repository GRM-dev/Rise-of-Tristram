package ca.grm.rot.libs;

public class RotClassManager 
{
	//String className,int strStat,int dexStat,
	//int intStat,int vitStat,int agiStat,
	//float baseHp,float baseMana,float baseStam,
	//float hpPerVit,float manaPerIntStat,float stamPerAgiStat
	public static RotClass[] classes = new RotClass[]
			{
		new RotClass("Traveller",16, 16, 16, 16, 16, 100, 100, 100, 5, 5, 5),
		new RotClass("Ranger",10, 30, 10, 10, 20, 100, 100, 200, 10, 5, 40),
		new RotClass("Rogue",0, 0, 0, 0, 0, 0, 0, 0, 10, 5, 40),
		new RotClass("Arcane Archer",0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 35),
		new RotClass("Barbarian",0, 0, 0, 0, 0, 0, 0, 0, 10, 5, 60),
		new RotClass("Spellsword",0, 0, 0, 0, 0, 0, 0, 0, 15, 10, 50),
		new RotClass("Battle Cleric",0, 0, 0, 0, 0, 0, 0, 0, 15, 10, 30),
		new RotClass("Elementalist",0, 0, 0, 0, 0, 0, 0, 0, 5, 25, 10),
		new RotClass("Druid",0, 0, 0, 0, 0, 0, 0, 0, 10, 15, 25),
		new RotClass("Healer",0, 0, 0, 0, 0, 0, 0, 0, 5, 20, 15),
		new RotClass("Knight",0, 0, 0, 0, 0, 0, 0, 0, 20, 5, 35),
		new RotClass("Paladin",0, 0, 0, 0, 0, 0, 0, 0, 25, 5, 30)
			};
	
	public static String professionMiner = "Miner";
	public static String professionFarmer = "Farmer";
	public static String professionBlacksmith = "Blacksmith";
	public static String professionScholar = "Arcane Scholar";
	public static RotProfession[] professions = new RotProfession[]
			{
		new RotProfession("Nomand", "Someone that chooses to not settle down, and instead travel the world."),
		new RotProfession(professionMiner, "Digging stone blocks is faster, ore yields more, and a rare chance to get lesser materials from mining."),
		new RotProfession(professionFarmer, "Harvesting plants has a chance for a greater yield, can cut wood faster, does more damage to livestock."),
		new RotProfession(professionBlacksmith, "Can repair items, crafted gear has a better rank and quality."),
		new RotProfession(professionScholar, "")
			};
}
