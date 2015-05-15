package ca.grm.rot.libs;

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
	public int strength, agility, intelligence, dexterity, vitality;
	public int minDmg, maxDmg, defBonus;

	public ExtendMob(EntityLiving mob)
	{
		this.mob = mob;
		this.dexterity = 0;
		this.agility = 0;
		this.intelligence = 0;
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
		mob.registerExtendedProperties(ExtendMob.EXT_PROP_NAME, new ExtendMob(
				mob));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger(Rot.MOD_ID + "Strength", this.strength);
		properties.setInteger(Rot.MOD_ID + "Dexterity", this.dexterity);
		properties.setInteger(Rot.MOD_ID + "Intelligence", this.intelligence);
		properties.setInteger(Rot.MOD_ID + "Agility", this.agility);
		properties.setInteger(Rot.MOD_ID + "Vitality", this.vitality);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound
				.getTag(EXT_PROP_NAME);
		this.strength = properties.getInteger(Rot.MOD_ID + "Strength");
		this.vitality = properties.getInteger(Rot.MOD_ID + "Vitality");
		this.dexterity = properties.getInteger(Rot.MOD_ID + "Dexterity");
		this.intelligence = properties.getInteger(Rot.MOD_ID + "Intelligence");
		this.agility = properties.getInteger(Rot.MOD_ID + "Agility");
	}

	@Override
	public void init(Entity entity, World world)
	{

	}

	public void rollStats(int depth)
	{
		// 63 above ocean level. 62 or 60 for easy math.
		int difficultyNumber = 255 - depth;
		monsterLevel = MathHelper.clamp_int(difficultyNumber / 25, 1, 10);
		strength = (int)((mob.worldObj.rand.nextInt(12) + (monsterLevel * 3)) * monsterLevel);
		dexterity = (int)((mob.worldObj.rand.nextInt(12) + (monsterLevel * 3)) * monsterLevel);
		intelligence = (int)((mob.worldObj.rand.nextInt(12) + (monsterLevel * 3)) * monsterLevel);
		agility = (int)((mob.worldObj.rand.nextInt(12) + (monsterLevel * 3)) * monsterLevel);
		vitality = (int)((mob.worldObj.rand.nextInt(12) + (monsterLevel * 3)) * monsterLevel);
		minDmg = (int)((mob.worldObj.rand.nextInt(5) + monsterLevel) * monsterLevel);
		maxDmg = (int)((mob.worldObj.rand.nextInt(13) + monsterLevel) * monsterLevel);
		defBonus = (int)((mob.worldObj.rand.nextInt(5) + monsterLevel) * monsterLevel);
	}
}
