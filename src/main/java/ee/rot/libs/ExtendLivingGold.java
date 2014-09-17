package ee.rot.libs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendLivingGold implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "ExtendLivingBaseRot";
	
	private final EntityLivingBase entity;
	
	private int gold;
	
	public ExtendLivingGold (EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	public static final void register(EntityLivingBase entity)
	{
		entity.registerExtendedProperties(ExtendLivingGold.EXT_PROP_NAME, new ExtendLivingGold(entity));
	}
	
	public static final ExtendLivingGold get(EntityLivingBase entity)
	{
		return (ExtendLivingGold) entity.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void init(Entity arg0, World arg1) 
	{
		/*// Gives a random amount of gold between 0 and 15
		this.gold = arg1.rand.nextInt(16);
		System.out.println("[LIVING BASE] Gold: " + this.gold);		*/
	}

	@Override
	public void loadNBTData(NBTTagCompound arg0) 
	{
		this.gold = arg0.getInteger("Gold");
		System.out.println("[LIVING BASE] Gold from NBT: " + this.gold);
	}

	@Override
	public void saveNBTData(NBTTagCompound arg0) 
	{
		arg0.setInteger("Gold", this.gold);		
	}
	
	public void addGold(int gold)
	{
		this.gold += gold;
	}
	
	public int getGold()
	{
		return gold;
	}

}
