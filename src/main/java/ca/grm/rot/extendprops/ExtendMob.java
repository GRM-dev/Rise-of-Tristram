package ca.grm.rot.extendprops;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.RotMobAffix;
import ca.grm.rot.managers.RotMobAffixManager;

public class ExtendMob implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "EEMobRot";

	private final EntityLiving mob;

	public int monsterLevel = 0;
	public int strength, agility, dexterity, vitality;
	public int minDmg, maxDmg, defBonus;
	public int gold;
	public String prefix;
	public String suffix;

	public ExtendMob(EntityLiving mob)
	{
		this.mob = mob;
		this.dexterity = 0;
		this.agility = 0;
		this.strength = 0;
		this.vitality = 0;
		this.gold = 0;
		this.suffix = "";
		this.prefix = "";
	}

	public static final ExtendMob get(EntityLiving mob)
	{
		try
		{
			return (ExtendMob) mob.getExtendedProperties(EXT_PROP_NAME);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static final void register(EntityLiving mob)
	{
		mob.registerExtendedProperties(ExtendMob.EXT_PROP_NAME, new ExtendMob(mob));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger(Rot.MOD_ID + "Level", this.monsterLevel);
		properties.setInteger(Rot.MOD_ID + "Strength", this.strength);
		properties.setInteger(Rot.MOD_ID + "Dexterity", this.dexterity);
		properties.setInteger(Rot.MOD_ID + "Agility", this.agility);
		properties.setInteger(Rot.MOD_ID + "Vitality", this.vitality);
		properties.setInteger(Rot.MOD_ID + "MinDamage", this.minDmg);
		properties.setInteger(Rot.MOD_ID + "MaxDamage", this.maxDmg);
		properties.setInteger(Rot.MOD_ID + "DefenceBonus", this.defBonus);
		properties.setInteger(Rot.MOD_ID + "Gold", this.gold);
		properties.setString(Rot.MOD_ID + "Prefix", this.prefix);
		properties.setString(Rot.MOD_ID + "Suffix", this.suffix);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.monsterLevel = properties.getInteger(Rot.MOD_ID + "Level");
		this.strength = properties.getInteger(Rot.MOD_ID + "Strength");
		this.vitality = properties.getInteger(Rot.MOD_ID + "Vitality");
		this.dexterity = properties.getInteger(Rot.MOD_ID + "Dexterity");
		this.agility = properties.getInteger(Rot.MOD_ID + "Agility");
		this.minDmg = properties.getInteger(Rot.MOD_ID + "MinDamage");
		this.maxDmg = properties.getInteger(Rot.MOD_ID + "MaxDamage");
		this.defBonus = properties.getInteger(Rot.MOD_ID + "DefenceBonus");
		this.gold = properties.getInteger(Rot.MOD_ID + "Gold");
		this.prefix = properties.getString(Rot.MOD_ID + "Prefix");
		this.suffix = properties.getString(Rot.MOD_ID + "Suffix");
	}

	@Override
	public void init(Entity entity, World world)
	{

	}
	
	private RotMobAffix rollPrefixes()
	{
		Random random = new Random();
		return RotMobAffixManager.getAffix(this.monsterLevel, RotMobAffixManager.getRandomPrefixArray(), random);
	}
	
	private RotMobAffix rollSuffixes()
	{
		Random random = new Random();
		return RotMobAffixManager.getAffix(this.monsterLevel, RotMobAffixManager.getRandomSuffixArray(), random);
	}
	
	public void rollAffixes()
	{
		// First decide if it's gonna be one or both.
		int roll = mob.worldObj.rand.nextInt(10);
		if (roll < 4)
		{
			// Prefix
			prefix = rollPrefixes().titleName;
		}
		else if (roll > 3 && roll < 7)
		{
			// Suffix
			suffix = rollSuffixes().titleName;
		}
		else if (roll == 8)
		{
			// Both
			prefix = rollPrefixes().titleName;
			suffix = rollSuffixes().titleName;
		}
		else if (roll > 8)
		{
			// Nothing.
		}
	}

	public void rollStats(int depth)
	{
		//TODO must perfect the math here
		int difficulty = depth + mob.worldObj.rand.nextInt(20);
		int monsterDifficultyRoll = MathHelper.clamp_int(difficulty / 7, 1, 10); // This exists to replace the old calculation that used monsterLevel so as that monsterLevel is calculated based on the stats.
		int baseBonus = monsterDifficultyRoll * 2;
		strength = (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		dexterity = (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		agility = (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		vitality = (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		minDmg = (int)(mob.worldObj.rand.nextInt(5) * monsterDifficultyRoll / 4) + baseBonus;
		maxDmg = (int)(mob.worldObj.rand.nextInt(13) * monsterDifficultyRoll / 4) + baseBonus;
		defBonus = (int)(mob.worldObj.rand.nextInt(5) * monsterDifficultyRoll / 4) + baseBonus;
		monsterLevel = (int)(((strength+dexterity+agility+vitality) / 4)/2); // /4 gets the average, the /2 is just to get a lower number for the level.
		//TODO Change the /2 in monsterLevel's calculation to be something better.
		
		rollAffixes();
		
		//TODO write actual gold code here.
		gold = (int)(mob.worldObj.rand.nextInt(10) * monsterLevel);
	}
}
