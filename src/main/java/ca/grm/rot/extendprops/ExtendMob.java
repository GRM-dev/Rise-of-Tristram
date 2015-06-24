package ca.grm.rot.extendprops;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	private float hpRegenBonusPercent = 0.0f; // Set this with method, later it gets divided by 20 - the number of ticks in a second - to become the next number.
	public int minDmg, maxDmg, defBonus;
	public int gold;
	public boolean isBoss;
	public String prefix;
	public String bossPrefix2;
	public String bossPrefix3;
	public String bossPrefix4; // An array isn't used here because it's simpler to just use these. The max # of prefixes isn't dynamic and its not massive.
	public String suffix;

	public ExtendMob(EntityLiving mob)
	{
		this.mob = mob;
		this.dexterity = 0;
		this.agility = 0;
		this.strength = 0;
		this.vitality = 0;
		this.hpRegenBonusPercent = 0; 
		this.gold = 0;
		this.suffix = "";
		this.prefix = "";
		this.bossPrefix2 = "";
		this.bossPrefix3 = "";
		this.bossPrefix4 = "";
		this.isBoss = false;
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
		properties.setString(Rot.MOD_ID + "BossPrefix2", this.bossPrefix2);
		properties.setString(Rot.MOD_ID + "BossPrefix3", this.bossPrefix3);
		properties.setString(Rot.MOD_ID + "BossPrefix4", this.bossPrefix4);
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
		this.bossPrefix2 = properties.getString(Rot.MOD_ID + "BossPrefix2");
		this.bossPrefix3 = properties.getString(Rot.MOD_ID + "BossPrefix3");
		this.bossPrefix4 = properties.getString(Rot.MOD_ID + "BossPrefix4");
	}

	@Override
	public void init(Entity entity, World world)
	{

	}
	
	public boolean isBoss()
	{
		if(this.isBoss)
		{
			return true;
		}
		return false;
	}
	
	public void setHpRegenBonusPercent(float amountPerSecond)
	{
		this.hpRegenBonusPercent = amountPerSecond / 20;
	}
	
	public float getHpRegenBonusPercent()
	{
		return hpRegenBonusPercent;
	}
	
	public void rollBossStatus(int depth)
	{
		if ((mob.worldObj.rand.nextInt(100) + depth > 75))
		{
			isBoss = true;
			
			// TODO Make the boss bonuses more balanced.
			strength += 10;
			dexterity += 10;
			agility += 10;
			vitality += 10;
			minDmg += 10;
			maxDmg += 10;
			defBonus+= 10;
			gold += 100;
			setHpRegenBonusPercent(1); // half heart per second.
		}
	}
	
	private RotMobAffix rollPrefix()
	{
		Random random = new Random();
		return RotMobAffixManager.getAffix(this.monsterLevel, RotMobAffixManager.getRandomPrefixArray(), random);
	}
	
	private RotMobAffix rollSuffix()
	{
		Random random = new Random();
		return RotMobAffixManager.getAffix(this.monsterLevel, RotMobAffixManager.getRandomSuffixArray(), random);
	}

	
	public void rollAffixes()
	{
		if (this.isBoss()) // If its a boss, give it a prefix, up to 3 more prefixes, and maybe suffix.
		{
			this.prefix = rollPrefix().titleName;
			switch (mob.worldObj.rand.nextInt(4)) {
			case 0:
				break;
			case 1:
				this.bossPrefix2 = rollPrefix().titleName;
				break;
			case 2:
				this.bossPrefix2 = rollPrefix().titleName;
				this.bossPrefix3 = rollPrefix().titleName;
				break;
			case 3:
				this.bossPrefix2 = rollPrefix().titleName;
				this.bossPrefix3 = rollPrefix().titleName;
				this.bossPrefix4 = rollPrefix().titleName;
				break;
			}
			if (mob.worldObj.rand.nextInt(10) < 4) // 40% chance (0-3 = winrar)
			{
				this.suffix = rollSuffix().titleName;
			}
		}
		else // If its not a boss, never give a prefix and only maybe a suffix.
		{
			if (mob.worldObj.rand.nextInt(10) < 2) // 20% chance (0-1 = winrar)
			{
				this.suffix = rollSuffix().titleName;
			}
		}
		
		applyAffixStats(); // This just makes the affixes do stuff. Mostly prefixes.
	}
	
	public void applyAffixStats()
	{
		/* Prefixes */
		if (this.prefix == "Fed")
		{
			//this.health += 3
		}
		
		if (this.bossPrefix2 == "Fed" && this.isBoss())
		{
			//this.health += 3
		}
		
		if (this.bossPrefix3 == "Fed" && this.isBoss())
		{
			//this.health += 3
		}
		
		if (this.bossPrefix4 == "Fed" && this.isBoss())
		{
			//this.health += 3
		}
		
		if (this.prefix == "Hearty")
		{
			//this.health += 6
		}
		
		if (this.bossPrefix2 == "Hearty" && this.isBoss())
		{
			//this.health += 6
		}
		
		if (this.bossPrefix3 == "Hearty" && this.isBoss())
		{
			//this.health += 6
		}
		
		if (this.bossPrefix4 == "Hearty" && this.isBoss())
		{
			//this.health += 6
		}
		
		if (this.prefix == "Obese")
		{
			//this.health += 12
		}
		
		if (this.bossPrefix2 == "Obese" && this.isBoss())
		{
			//this.health += 12
		}
		
		if (this.bossPrefix3 == "Obese" && this.isBoss())
		{
			//this.health += 12
		}
		
		if (this.bossPrefix4 == "Obese" && this.isBoss())
		{
			//this.health += 12
		}		
		if (this.prefix == "Bulky")
		{
			this.strength += 5;
		}
		
		if (this.bossPrefix2 == "Bulky" && this.isBoss())
		{
			this.strength += 5;
		}
		
		if (this.bossPrefix3 == "Bulky" && this.isBoss())
		{
			this.strength += 5;
		}
		
		if (this.bossPrefix4 == "Bulky" && this.isBoss())
		{
			this.strength += 5;
		}		
		if (this.prefix == "Muscular")
		{
			this.strength += 10;
		}
		
		if (this.bossPrefix2 == "Muscular" && this.isBoss())
		{
			this.strength += 10;
		}
		
		if (this.bossPrefix3 == "Muscular" && this.isBoss())
		{
			this.strength += 10;
		}
		
		if (this.bossPrefix4 == "Muscular" && this.isBoss())
		{
			this.strength += 10;
		}		
		if (this.prefix == "Ripped")
		{
			this.strength += 15;
		}
		
		if (this.bossPrefix2 == "Ripped" && this.isBoss())
		{
			this.strength += 15;
		}
		
		if (this.bossPrefix3 == "Ripped" && this.isBoss())
		{
			this.strength += 15;
		}
		
		if (this.bossPrefix4 == "Ripped" && this.isBoss())
		{
			this.strength += 15;
		}		
		if (this.prefix == "Beefcastle")
		{
			this.strength += 20;
		}
		
		if (this.bossPrefix2 == "Beefcastle" && this.isBoss())
		{
			this.strength += 20;
		}
		
		if (this.bossPrefix3 == "Beefcastle" && this.isBoss())
		{
			this.strength += 20;
		}
		
		if (this.bossPrefix4 == "Beefcastle" && this.isBoss())
		{
			this.strength += 20;
		}		
		if (this.prefix == "Sharp-Eye")
		{
			this.dexterity += 5;
		}
		
		if (this.bossPrefix2 == "Sharp-Eye" && this.isBoss())
		{
			this.dexterity += 5;
		}
		
		if (this.bossPrefix3 == "Sharp-Eye" && this.isBoss())
		{
			this.dexterity += 5;
		}
		
		if (this.bossPrefix4 == "Sharp-Eye" && this.isBoss())
		{
			this.dexterity += 5;
		}		
		if (this.prefix == "Eagle-Eye")
		{
			this.dexterity += 10;
		}
		
		if (this.bossPrefix2 == "Eagle-Eye" && this.isBoss())
		{
			this.dexterity += 10;
		}
		
		if (this.bossPrefix3 == "Eagle-Eye" && this.isBoss())
		{
			this.dexterity += 10;
		}
		
		if (this.bossPrefix4 == "Eagle-Eye" && this.isBoss())
		{
			this.dexterity += 10;
		}		
		if (this.prefix == "Marksman")
		{
			this.dexterity += 15;
		}
		
		if (this.bossPrefix2 == "Marksman" && this.isBoss())
		{
			this.dexterity += 15;
		}
		
		if (this.bossPrefix3 == "Marksman" && this.isBoss())
		{
			this.dexterity += 15;
		}
		
		if (this.bossPrefix4 == "Marksman" && this.isBoss())
		{
			this.dexterity += 15;
		}		
		if (this.prefix == "Sniper")
		{
			this.dexterity += 20;
		}
		
		if (this.bossPrefix2 == "Sniper" && this.isBoss())
		{
			this.dexterity += 20;
		}
		
		if (this.bossPrefix3 == "Sniper" && this.isBoss())
		{
			this.dexterity += 20;
		}
		
		if (this.bossPrefix4 == "Sniper" && this.isBoss())
		{
			this.dexterity += 20;
		}		
		if (this.prefix == "Quick")
		{
			this.agility += 5;
		}
		
		if (this.bossPrefix2 == "Quick" && this.isBoss())
		{
			this.agility += 5;
		}
		
		if (this.bossPrefix3 == "Quick" && this.isBoss())
		{
			this.agility += 5;
		}
		
		if (this.bossPrefix4 == "Quick" && this.isBoss())
		{
			this.agility += 5;
		}		
		if (this.prefix == "Fast")
		{
			this.agility += 10;
		}
		
		if (this.bossPrefix2 == "Fast" && this.isBoss())
		{
			this.agility += 10;
		}
		
		if (this.bossPrefix3 == "Fast" && this.isBoss())
		{
			this.agility += 10;
		}
		
		if (this.bossPrefix4 == "Fast" && this.isBoss())
		{
			this.agility += 10;
		}		
		if (this.prefix == "Wicked Speed")
		{
			this.agility += 15;
		}
		
		if (this.bossPrefix2 == "Wicked Speed" && this.isBoss())
		{
			this.agility += 15;
		}
		
		if (this.bossPrefix3 == "Wicked Speed" && this.isBoss())
		{
			this.agility += 15;
		}
		
		if (this.bossPrefix4 == "Wicked Speed" && this.isBoss())
		{
			this.agility += 15;
		}		
		if (this.prefix == "Flash")
		{
			this.agility += 20;
		}
		
		if (this.bossPrefix2 == "Flash" && this.isBoss())
		{
			this.agility += 20;
		}
		
		if (this.bossPrefix3 == "Flash" && this.isBoss())
		{
			this.agility += 20;
		}
		
		if (this.bossPrefix4 == "Flash" && this.isBoss())
		{
			this.agility += 20;
		}		
		if (this.prefix == "Strong-Will")
		{
			this.vitality += 5;
		}
		
		if (this.bossPrefix2 == "Strong-Will" && this.isBoss())
		{
			this.vitality += 5;
		}
		
		if (this.bossPrefix3 == "Strong-Will" && this.isBoss())
		{
			this.vitality += 5;
		}
		
		if (this.bossPrefix4 == "Strong-Will" && this.isBoss())
		{
			this.vitality += 5;
		}		
		if (this.prefix == "Stonewall")
		{
			this.vitality += 10;
		}
		
		if (this.bossPrefix2 == "Stonewall" && this.isBoss())
		{
			this.vitality += 10;
		}
		
		if (this.bossPrefix3 == "Stonewall" && this.isBoss())
		{
			this.vitality += 10;
		}
		
		if (this.bossPrefix4 == "Stonewall" && this.isBoss())
		{
			this.vitality += 10;
		}		
		if (this.prefix == "Tough-Skin")
		{
			this.vitality += 15;
		}
		
		if (this.bossPrefix2 == "Tough-Skin" && this.isBoss())
		{
			this.vitality += 15;
		}
		
		if (this.bossPrefix3 == "Tough-Skin" && this.isBoss())
		{
			this.vitality += 15;
		}
		
		if (this.bossPrefix4 == "Tough-Skin" && this.isBoss())
		{
			this.vitality += 15;
		}		
		if (this.prefix == "Death Transcending")
		{
			this.vitality += 20;
		}
		
		if (this.bossPrefix2 == "Death Transcending" && this.isBoss())
		{
			this.vitality += 20;
		}
		
		if (this.bossPrefix3 == "Death Transcending" && this.isBoss())
		{
			this.vitality += 20;
		}
		
		if (this.bossPrefix4 == "Death Transcending" && this.isBoss())
		{
			this.vitality += 20;
		}		
		if (this.prefix == "Healing")
		{
			setHpRegenBonusPercent(1);
		}
		
		if (this.bossPrefix2 == "Healing" && this.isBoss())
		{
			setHpRegenBonusPercent(1);
		}
		
		if (this.bossPrefix3 == "Healing" && this.isBoss())
		{
			setHpRegenBonusPercent(1);
		}
		
		if (this.bossPrefix4 == "Healing" && this.isBoss())
		{
			setHpRegenBonusPercent(1);
		}		
		if (this.prefix == "Blessed")
		{
			setHpRegenBonusPercent(1.5f);
		}
		
		if (this.bossPrefix2 == "Blessed" && this.isBoss())
		{
			setHpRegenBonusPercent(1.5f);
		}
		
		if (this.bossPrefix3 == "Blessed" && this.isBoss())
		{
			setHpRegenBonusPercent(1.5f);
		}
		
		if (this.bossPrefix4 == "Blessed" && this.isBoss())
		{
			setHpRegenBonusPercent(1.5f);
		}		
		if (this.prefix == "Cleric")
		{
			setHpRegenBonusPercent(2f);
		}
		
		if (this.bossPrefix2 == "Cleric" && this.isBoss())
		{
			setHpRegenBonusPercent(2f);
		}
		
		if (this.bossPrefix3 == "Cleric" && this.isBoss())
		{
			setHpRegenBonusPercent(2f);
		}
		
		if (this.bossPrefix4 == "Cleric" && this.isBoss())
		{
			setHpRegenBonusPercent(2f);
		}		
		if (this.prefix == "Altar-Betraying")
		{
			setHpRegenBonusPercent(2.5f);
		}
		
		if (this.bossPrefix2 == "Altar-Betraying" && this.isBoss())
		{
			setHpRegenBonusPercent(2.5f);
		}
		
		if (this.bossPrefix3 == "Altar-Betraying" && this.isBoss())
		{
			setHpRegenBonusPercent(2.5f);
		}
		
		if (this.bossPrefix4 == "Altar-Betraying" && this.isBoss())
		{
			setHpRegenBonusPercent(2.5f);
		}		
		if (this.prefix == "Tropical")
		{
			this.gold += 150;
		}
		
		if (this.bossPrefix2 == "Tropical" && this.isBoss())
		{
			this.gold += 150;
		}
		
		if (this.bossPrefix3 == "Tropical" && this.isBoss())
		{
			this.gold += 150;
		}
		
		if (this.bossPrefix4 == "Tropical" && this.isBoss())
		{
			this.gold += 150;
		}
		
		
		/* Suffixes */
	}
	
	public void rollStats(int depth)
	{
		//TODO must perfect the math here
		//TODO Change the /2 in monsterLevel's calculation to be something better.
		int difficulty = depth + mob.worldObj.rand.nextInt(20);
		int monsterDifficultyRoll = MathHelper.clamp_int(difficulty / 7, 1, 10); // This exists to replace the old calculation that used monsterLevel so as that monsterLevel is calculated based on the stats.
		int baseBonus = monsterDifficultyRoll * 2;
		strength += (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		dexterity += (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		agility += (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		vitality += (int)(mob.worldObj.rand.nextInt(12) * monsterDifficultyRoll / 4) + baseBonus;
		minDmg += (int)(mob.worldObj.rand.nextInt(5) * monsterDifficultyRoll / 4) + baseBonus;
		maxDmg += (int)(mob.worldObj.rand.nextInt(13) * monsterDifficultyRoll / 4) + baseBonus;
		defBonus += (int)(mob.worldObj.rand.nextInt(5) * monsterDifficultyRoll / 4) + baseBonus;
		monsterLevel += (int)(((strength+dexterity+agility+vitality) / 4)/2); // /4 gets the average, the /2 is just to get a lower number for the level.
		//TODO write actual gold code here.
		gold += (int)(mob.worldObj.rand.nextInt(10) * monsterLevel);
	}
	
	public void rollExtendMob(int depth)
	{
		rollBossStatus(depth);
		rollStats(depth);
		rollAffixes();
	}
}
