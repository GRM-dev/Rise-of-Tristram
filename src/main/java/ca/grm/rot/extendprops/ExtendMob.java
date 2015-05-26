package ca.grm.rot.extendprops;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ca.grm.rot.Rot;

public class ExtendMob implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "EEMobRot";

	private final EntityLiving mob;

	public int monsterLevel = 0;
	public int strength, agility, dexterity, vitality;
	public int minDmg, maxDmg, defBonus;

	public ExtendMob(EntityLiving mob)
	{
		this.mob = mob;
		this.dexterity = 0;
		this.agility = 0;
		this.strength = 0;
		this.vitality = 0;
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
	}

	@Override
	public void init(Entity entity, World world)
	{

	}

	public void rollStats(int depth)
	{
		//TODO must perfect the math here
		int difficulty = depth + mob.worldObj.rand.nextInt(20);
		monsterLevel = MathHelper.clamp_int(difficulty / 7, 1, 10);
		int baseBonus = monsterLevel * 2;
		strength = (int)(mob.worldObj.rand.nextInt(12) * monsterLevel / 4) + baseBonus;
		dexterity = (int)(mob.worldObj.rand.nextInt(12) * monsterLevel / 4) + baseBonus;
		agility = (int)(mob.worldObj.rand.nextInt(12) * monsterLevel / 4) + baseBonus;
		vitality = (int)(mob.worldObj.rand.nextInt(12) * monsterLevel / 4) + baseBonus;
		minDmg = (int)(mob.worldObj.rand.nextInt(5) * monsterLevel / 4) + baseBonus;
		maxDmg = (int)(mob.worldObj.rand.nextInt(13) * monsterLevel / 4) + baseBonus;
		defBonus = (int)(mob.worldObj.rand.nextInt(5) * monsterLevel / 4) + baseBonus;
	}
}
