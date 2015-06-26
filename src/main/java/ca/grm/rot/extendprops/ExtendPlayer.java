package ca.grm.rot.extendprops;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.RotClass;
import ca.grm.rot.libs.RotProfession;
import ca.grm.rot.managers.RotClassManager;

public class ExtendPlayer implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "EEPlayerRot";
	private final EntityPlayer player;

	private int statMax = 255;

	// TODO add in skills
	// TODO Create SkillsClass
	
	private int currentClass;
	private int currentProfession;
	public RotClass pickedClass = RotClassManager.classes[currentClass];
	public RotProfession pickedProfession = RotClassManager.professions[currentProfession];
	public boolean needsUpdate = false;
	public boolean isExhausted = false;

	public final float realManaStamMaxes = 100f;

	private float bonusMana;
	private float bonusStam;
	private float athleticScore = 0;

	private float currentMana, maxMana, currentStam, maxStam;
	private int strength, agility, intelligence, dexterity, vitality;
	private int minDmg, maxDmg, defBonus;
	private int gold;
	private float lifeSteal, manaSteal;

	public static final int MANA_WATCHER = 20;
	public static final int STAM_WATCHER = 21;

	// Player Settings
	public int skill1; // Used in keeping track of hotkeys
	public int skill2; // So users can switch what skill is used.
	
	public ExtendPlayer(EntityPlayer player)
	{
		this.player = player;

		this.currentClass = 0;
		this.currentProfession = 0;
		this.dexterity = pickedClass.dexStat;
		this.agility = pickedClass.agiStat;
		this.intelligence = pickedClass.intStat;
		this.strength = pickedClass.strStat;
		this.vitality = pickedClass.vitStat;

		this.minDmg = 0;
		this.maxDmg = 0;
		this.defBonus = 0;
		this.lifeSteal = 0;
		this.manaSteal = 0;
		
		this.gold = 0;

		// this.currentMana = this.maxMana = pickedClass.baseMana;
		// this.currentStam = this.maxStam = pickedClass.baseStam;
		this.currentMana = this.maxMana = realManaStamMaxes;
		this.currentStam = this.maxStam = realManaStamMaxes;
		this.player.getDataWatcher().addObject(MANA_WATCHER, this.maxMana);
		this.player.getDataWatcher().addObject(STAM_WATCHER, this.maxStam);
		
		// Default Settings
		this.skill1 = 0;
		this.skill2 = 1;
	}

	/**
	 * Returns ExtendedPlayer properties for player This method is for
	 * convenience only; it will make your code look nicer
	 */
	public static final ExtendPlayer get(EntityPlayer player)
	{
		try
		{
			return (ExtendPlayer) player.getExtendedProperties(EXT_PROP_NAME);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event This method is for convenience only; it will
	 * make your code look nicer
	 */
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(ExtendPlayer.EXT_PROP_NAME, new ExtendPlayer(player));
	}

	/**
	 * Returns true if the amount of mana was consumed or false if the player's
	 * current mana was insufficient
	 */
	public final boolean consumeMana(float amount)
	{
		float mana = this.player.getDataWatcher().getWatchableObjectFloat(MANA_WATCHER);
		boolean sufficient = (realManaStamMaxes * (amount / getMaxMana())) <= mana;
		if (sufficient)
		{
			mana -= (realManaStamMaxes * (amount / getMaxMana()));
			this.player.getDataWatcher().updateObject(MANA_WATCHER, mana);
		}
		return sufficient;

	}

	public final boolean consumeStam(float amount)
	{
		float stam = this.player.getDataWatcher().getWatchableObjectFloat(STAM_WATCHER);
		boolean sufficient = (realManaStamMaxes * (amount / getMaxStam())) <= stam;
		if (sufficient)
		{
			stam -= (realManaStamMaxes * (amount / getMaxStam()));
			this.player.getDataWatcher().updateObject(STAM_WATCHER, stam);
		}
		return sufficient;
	}

	public int getAgility()
	{
		return this.agility;
	}

	public int getMinDmg()
	{
		return this.minDmg;
	}

	public int getMaxDmg()
	{
		return this.maxDmg;
	}

	public int getDefBonus()
	{
		return this.defBonus;
	}

	/*
	 * That's it for the IExtendedEntityProperties methods, but we need to add a
	 * few of our own in order to interact with our new variables. For now,
	 * let's make one method to consume mana and one to replenish it.
	 */

	/** Returns Str, Agi, Int, Vit, Dex **/
	public int[] getClassModifers()
	{
		return new int[] { pickedClass.strStat, pickedClass.agiStat, pickedClass.intStat, pickedClass.vitStat, pickedClass.dexStat
		/*
		 * classAttributeStr[currentClass],classAttributeAgi[currentClass],
		 * classAttributeInt[currentClass],classAttributeVit[currentClass],
		 * classAttributeDex[currentClass]
		 */
		/*
		 * this.currentClass.getStr(), this.currentClass.getAgi(),
		 * this.currentClass.getInte(), this.currentClass.getVit(),
		 * this.currentClass.getDex()
		 */};
	}

	public int getCurrentClassIndex()
	{
		return this.currentClass;
	}

	public int getCurrentProfessionIndex()
	{
		return this.currentProfession;
	}

	public String getCurrentClassName()
	{
		return pickedClass.className;
	}

	public String getCurrentProfessionName()
	{
		return pickedProfession.professionName;
	}

	public float getCurrentMana()
	{
		return this.player.getDataWatcher().getWatchableObjectFloat(MANA_WATCHER);
	}

	public float getCurrentStam()
	{
		return this.player.getDataWatcher().getWatchableObjectFloat(STAM_WATCHER);
	}

	public int getDexterity()
	{
		return this.dexterity;
	}

	public int getIntelligence()
	{
		return this.intelligence;
	}

	/** Returns the added up MaxMana **/
	public float getMaxMana()
	{
		return this.realManaStamMaxes + pickedClass.baseMana + bonusMana + (this.intelligence * pickedClass.manaPerIntStat);
	}

	/** Returns the added up MaxStamina **/
	public float getMaxStam()
	{
		return this.realManaStamMaxes + pickedClass.baseStam + bonusStam + (this.vitality * pickedClass.stamPerVitStat);
	}

	public int getStrength()
	{
		return this.strength;
	}

	public float getLifeSteal()
	{
		return this.lifeSteal;
	}

	public float getManaSteal()
	{
		return this.manaSteal;
	}

	/**
	 * outputs an Object array of String className, Float currentMana, Float
	 * currentStamina
	 **/
	public Object[] getValues()
	{
		Object[] data = new Object[] { getCurrentClassIndex(), getCurrentMana(), getCurrentStam() };
		return data;
	}

	public void setValues(Object[] data)
	{
		setCurrentClass(Integer.parseInt(data[0].toString()));
		setCurrentMana(Float.parseFloat(data[1].toString()));
		setCurrentStam(Float.parseFloat(data[2].toString()));
	}

	public int getVitality()
	{
		return this.vitality;
	}

	/*
	 * I personally have yet to find a use for this method. If you know of any,
	 * please let me know and I'll add it in!
	 */
	@Override
	public void init(Entity entity, World world)
	{
	}

	// Load whatever data you saved
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		// Here we fetch the unique tag compound we set for this class of
		// Extended Properties
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		// Get our data from the custom tag compound
		this.strength = properties.getInteger(Rot.MOD_ID + "Strength");
		this.vitality = properties.getInteger(Rot.MOD_ID + "Vitality");
		this.dexterity = properties.getInteger(Rot.MOD_ID + "Dexterity");
		this.intelligence = properties.getInteger(Rot.MOD_ID + "Intelligence");
		this.agility = properties.getInteger(Rot.MOD_ID + "Agility");
		this.gold = properties.getInteger(Rot.MOD_ID + "Gold");

		this.player.getDataWatcher().updateObject(MANA_WATCHER,
				properties.getFloat(Rot.MOD_ID + "CurrentMana"));
		this.maxMana = properties.getFloat(Rot.MOD_ID + "MaxMana");
		this.player.getDataWatcher().updateObject(STAM_WATCHER,
				properties.getFloat(Rot.MOD_ID + "CurrentStam"));
		this.maxStam = properties.getFloat(Rot.MOD_ID + "MaxStam");

		this.currentClass = properties.getInteger(Rot.MOD_ID + "Class");
		setCurrentClass(currentClass);
		this.currentProfession = properties.getInteger(Rot.MOD_ID + "Profession");
		setCurrentProfession(currentProfession);
		// Just so you know it's working, add this line:
		// System.out.println("[TUT PROPS] Mana from NBT: " + this.currentMana +
		// "/" + this.maxMana);
	}

	public boolean needsMana()
	{
		if (getCurrentMana() < this.maxMana)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean needsStam()
	{
		if (getCurrentStam() < this.maxStam)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void regenMana(float amount)
	{
		float mana = this.player.getDataWatcher().getWatchableObjectFloat(MANA_WATCHER);

		if (mana != this.maxMana)
		{
			mana += (realManaStamMaxes * (amount / getMaxMana()));
			if (mana > this.maxMana)
			{
				this.player.getDataWatcher().updateObject(MANA_WATCHER, realManaStamMaxes);
			}
			else
			{
				this.player.getDataWatcher().updateObject(MANA_WATCHER, mana);
			}
		}
	}

	public void regenStam(float amount)
	{
		float stam = this.player.getDataWatcher().getWatchableObjectFloat(STAM_WATCHER);

		if (stam != this.maxStam)
		{
			stam += (realManaStamMaxes * (amount / getMaxStam()));
			if (stam > this.maxStam)
			{
				this.player.getDataWatcher().updateObject(STAM_WATCHER, realManaStamMaxes);
			}
			else
			{
				this.player.getDataWatcher().updateObject(STAM_WATCHER, stam);
			}
		}
	}

	/**
	 * Simple methods sets current to max
	 */
	public void replenishMana()
	{
		this.player.getDataWatcher().updateObject(MANA_WATCHER, this.maxMana);
	}

	public void replenishStam()
	{
		this.player.getDataWatcher().updateObject(STAM_WATCHER, this.maxStam);
	}

	// Save any custom data that needs saving here
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		// We need to create a new tag compound that will save everything for
		// our Extended Properties
		NBTTagCompound properties = new NBTTagCompound();

		properties.setFloat(Rot.MOD_ID + "CurrentMana", this.player.getDataWatcher()
				.getWatchableObjectFloat(MANA_WATCHER));
		properties.setFloat(Rot.MOD_ID + "MaxMana", this.maxMana);
		properties.setFloat(Rot.MOD_ID + "CurrentStam", this.player.getDataWatcher()
				.getWatchableObjectFloat(STAM_WATCHER));
		properties.setFloat(Rot.MOD_ID + "MaxStam", this.maxStam);

		properties.setInteger(Rot.MOD_ID + "Strength", this.strength);
		properties.setInteger(Rot.MOD_ID + "Dexterity", this.dexterity);
		properties.setInteger(Rot.MOD_ID + "Intelligence", this.intelligence);
		properties.setInteger(Rot.MOD_ID + "Agility", this.agility);
		properties.setInteger(Rot.MOD_ID + "Vitality", this.vitality);
		properties.setInteger(Rot.MOD_ID + "Gold", this.gold);

		properties.setInteger(Rot.MOD_ID + "Class", this.currentClass);
		properties.setInteger(Rot.MOD_ID + "Profession", this.currentProfession);

		compound.setTag(EXT_PROP_NAME, properties);

	}

	public void setAgility(int value)
	{
		this.agility = MathHelper.clamp_int(value + pickedClass.agiStat, -statMax, statMax);
	}

	public void setCurrentClass(int classID)
	{
		this.currentClass = classID;
		this.pickedClass = RotClassManager.classes[this.currentClass];
	}

	public void setCurrentProfession(int professionID)
	{
		this.currentProfession = professionID;
		this.pickedProfession = RotClassManager.professions[this.currentProfession];
	}

	public void setCurrentMana(float readFloat)
	{
		this.player.getDataWatcher().updateObject(MANA_WATCHER,
				(readFloat < this.maxMana ? readFloat : this.maxMana));
	}

	public void setCurrentStam(float readFloat)
	{
		this.player.getDataWatcher().updateObject(STAM_WATCHER,
				(readFloat < this.maxStam ? readFloat : this.maxStam));
	}

	public void setDexterity(int value)
	{
		this.dexterity = MathHelper.clamp_int(value + pickedClass.dexStat, -statMax, statMax);
	}

	public void setMinDmg(int value)
	{
		this.minDmg = value;
	}

	public void setMaxDmg(int value)
	{
		this.maxDmg = value;
	}

	public void setDefBonus(int value)
	{
		this.defBonus = value;
	}

	public void setIntelligence(int value)
	{
		this.intelligence = MathHelper.clamp_int(value + pickedClass.intStat, -statMax, statMax);
		//setMaxMana(pickedClass.baseMana);
	}

	public void setMaxMana(float readFloat)
	{
		float omm = this.maxMana;
		float ocm = this.currentMana;
		this.maxMana = MathHelper.clamp_float(
				readFloat + (pickedClass.manaPerIntStat * this.intelligence), pickedClass.baseMana,
				5000f);
	}

	public void setMaxStam(float readFloat)
	{
		float oms = this.maxStam;
		float ocs = this.currentStam;
		this.maxStam = MathHelper.clamp_float(
				readFloat + (pickedClass.stamPerVitStat * this.vitality), pickedClass.baseStam,
				5000f);
	}

	public void setStrength(int value)
	{
		this.strength = MathHelper.clamp_int(value + pickedClass.strStat, -statMax, statMax);
	}

	public void setLifeSteal(float value)
	{
		this.lifeSteal = value;
	}

	public void setManaSteal(float value)
	{
		this.manaSteal = value;
	}

	public void setVitality(int value)
	{
		this.vitality = MathHelper.clamp_int(value + pickedClass.vitStat, -statMax, statMax);
		//setMaxStam(pickedClass.baseStam);
	}

	public float getAthleticScore()
	{
		return athleticScore;
	}

	public void updateAthleticScore()
	{
		athleticScore = this.agility / 27;
		PlayerCapabilities pc = player.capabilities;
		try
		{
			Field walkSpeed = PlayerCapabilities.class.getDeclaredField("walkSpeed");
			walkSpeed.setAccessible(true);
			walkSpeed.setFloat(pc, MathHelper.clamp_float(0.1F * (1 + (athleticScore / 10)), 0.04f,
					0.3f));// 0.1f is default player movespeed
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
	}

	public int getGold()
	{
		return this.gold;
	}
	
	public void setGold(int value)
	{
		this.gold = value;
	}
	
	public void addGold(int value)
	{
		this.gold += value;
	}
}