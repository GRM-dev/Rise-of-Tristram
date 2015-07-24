package ca.grm.rot.extendprops;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.RotShopType;
import ca.grm.rot.managers.RotShopTypeManager;

public class ExtendVillager implements IExtendedEntityProperties{
	
	public final static String EXT_PROP_NAME = "EEVillagerRot";
	
	private final EntityVillager mob;
	
	public RotShopType shopType;
	public boolean needsUpdate;
	public ItemStack[] shop;
	
	public ExtendVillager(EntityVillager mob)
	{
		this.mob = mob;
		this.needsUpdate = true;
	}
	
	public static final ExtendVillager get(EntityLiving mob)
	{
		try
		{
			return (ExtendVillager) mob.getExtendedProperties(EXT_PROP_NAME);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger(Rot.MOD_ID + "ShopTypeIndex", this.shopType.index);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.shopType = RotShopTypeManager.getShopTypeFromIndex(properties.getInteger(Rot.MOD_ID + "ShopTypeIndex"));
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

	public static final void register(EntityVillager mob)
	{
		mob.registerExtendedProperties(ExtendVillager.EXT_PROP_NAME, new ExtendVillager(mob));
	}

	public void rollExtendVillager() {
		switch (mob.getProfession())
		{
		case 0:// farmer
			this.shopType = rollFarmerShopType();
			break;
		case 1: // lab
			this.shopType = rollMiscShopType(); // TODO Make this different than the purple pimp villager.
			break;
		case 2:// purple
			this.shopType = rollMiscShopType();
			break;
		case 3:// blacksmith
			this.shopType = rollBlacksmithShopType();
			break;
		case 4: // butcher
			this.shopType = rollCookingShopType();
			break;
		default:
			//System.out.println("Villager didn't have a known profession ID; ID: " + mob.getProfession());
			this.shopType = rollMiscShopType();
			break;
		}
	}
	
	private RotShopType rollFarmerShopType()
	{
		int roll = mob.worldObj.rand.nextInt(2);
		if (roll == 0)
		{
			return RotShopTypeManager.FARMER_SEED_SUPPLIER;
		}
		else if (roll == 1)
		{
			return RotShopTypeManager.FARMER_ANIMAL_PRODUCTS;
		}
		//System.out.println("Farmer Roll failed.");
		return null;
	}
	
	private RotShopType rollCookingShopType()
	{
		int roll = mob.worldObj.rand.nextInt(2);
		if (roll == 0)
		{
			return RotShopTypeManager.BUTCHER;
		}
		else if (roll == 1)
		{
			return RotShopTypeManager.COOK;
		}
		//System.out.println("Cooking Roll failed.");
		return null;
	}

	private RotShopType rollMiscShopType()
	{
		int roll = mob.worldObj.rand.nextInt(4);
		if (roll == 0)
		{
			return RotShopTypeManager.CLOTHIER;
		}
		else if (roll == 1)
		{
			return RotShopTypeManager.SCHOLAR;
		}
		else if (roll == 2)
		{
			return RotShopTypeManager.DYE_MASTER;
		}
		else if (roll == 3)
		{
			return RotShopTypeManager.TINKERER;
		}
		//System.out.println("Misc Roll failed.");
		return null;
	}

	private RotShopType rollBlacksmithShopType()
	{
		int roll = mob.worldObj.rand.nextInt(4);
		if (roll == 0)
		{
			return RotShopTypeManager.BLACKSMITH_WEAPONS;
		}
		else if (roll == 1)
		{
			return RotShopTypeManager.BLACKSMITH_ARMORS;
		}
		else if (roll == 2)
		{
			return RotShopTypeManager.BLACKSMITH_TRINKETS;
		}
		else if (roll == 3)
		{
			return RotShopTypeManager.BLACKSMITH_MOUNT;
		}
		//System.out.println("Blacksmith Roll failed.");
		return null;
	}

	private void rollShopGoods()
	{
		if (this.shopType == RotShopTypeManager.FARMER_SEED_SUPPLIER)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.FARMER_ANIMAL_PRODUCTS)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.BUTCHER)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.COOK)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.CLOTHIER)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.SCHOLAR)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.DYE_MASTER)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.TINKERER)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_WEAPONS)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_ARMORS)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_TRINKETS)
		{
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_MOUNT)
		{
			
		}
	}
}
