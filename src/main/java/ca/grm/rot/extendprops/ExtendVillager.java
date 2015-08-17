package ca.grm.rot.extendprops;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.RotShopType;
import ca.grm.rot.libs.RotNamer;
import ca.grm.rot.managers.RotShopTypeManager;

public class ExtendVillager implements IExtendedEntityProperties{
	
	public final static String EXT_PROP_NAME = "EEVillagerRot";
	
	private final EntityVillager mob;
	
	public RotShopType shopType;
	public boolean needsUpdate;
	public ItemStack[] shopUnlimited; // This would be a restock but because it's unlimited, it's also their inventory
	public ItemStack[] shopLimited; // What the villager restocks to.
	public ItemStack[] shopCurrentLimitedInventory; // This is what the villager actually has in their limited inventory
	public int goldMax; // Used when we refresh the gold.
	public int goldCurrent;
	public String firstName = "";
	public String lastName = "";
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

	public void rollExtendVillager(World world) {
		rollNames(world);
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
		
		this.goldMax = rollGold(world);
		this.goldCurrent = this.goldMax;
		//rollShopGoods();
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
		int i = 0;
		int j = 0;
		if (this.shopType == RotShopTypeManager.FARMER_SEED_SUPPLIER)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.bone, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.cactus, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.brown_mushroom_block, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.cocoa, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.flower_pot, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.melon_seeds, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.pumpkin_seeds, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.red_mushroom_block, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.sapling, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.wheat_seeds, 9999);
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.wooden_hoe, 1);
			this.shopLimited[j++] = new ItemStack(Items.iron_hoe, 1);
			this.shopLimited[j++] = new ItemStack(Items.golden_hoe, 1);
			this.shopLimited[j++] = new ItemStack(Items.diamond_hoe, 1);
		}
		else if (this.shopType == RotShopTypeManager.FARMER_ANIMAL_PRODUCTS)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.apple, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.bucket, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.egg, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.oak_fence, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.oak_fence_gate, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.lead, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.name_tag, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.shears, 9999);
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.saddle, 1);

		}
		else if (this.shopType == RotShopTypeManager.BUTCHER)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.beef, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.chicken, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.mutton, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.porkchop, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.rabbit, 9999);
			
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.COOK)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.apple, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.bread, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.brown_mushroom_block, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.cake, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.carrot, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.cocoa, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.egg, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.melon, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.baked_potato, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.potato, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.rabbit_stew, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.sugar, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.wheat, 9999);
			
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.CLOTHIER)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.leather, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.rabbit_hide, 9999);
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.leather_boots, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_chestplate, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_leggings, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_helmet, 1);
		}
		else if (this.shopType == RotShopTypeManager.SCHOLAR)
		{
			// Unlimited Items
			// TODO Give the Scholar more stuff to sell
			this.shopUnlimited[i++] = new ItemStack(Items.experience_bottle, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.book, 9999);
			this.shopUnlimited[i++] = new ItemStack(Items.map, 9999);
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.DYE_MASTER)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 0);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 1);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 2);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 3);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 4);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 5);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 6);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 7);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 8);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 9);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 10);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 12);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 13);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 14);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 15);
			this.shopUnlimited[i++] = new ItemStack(Items.dye, 9999, 16);
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.TINKERER)
		{
			// Unlimited Items
			this.shopUnlimited[i++] = new ItemStack(Items.redstone, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.redstone_lamp, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.redstone_torch, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.rail, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.activator_rail, 9999);
			this.shopUnlimited[i++] = new ItemStack(Blocks.detector_rail, 9999);
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_WEAPONS)
		{
			// Unlimited Items
			
			
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.wooden_axe, 1);
			this.shopLimited[j++] = new ItemStack(Items.wooden_sword, 1);
			this.shopLimited[j++] = new ItemStack(Items.wooden_pickaxe, 1);
			this.shopLimited[j++] = new ItemStack(Items.iron_axe, 1);
			this.shopLimited[j++] = new ItemStack(Items.iron_sword, 1);
			this.shopLimited[j++] = new ItemStack(Items.iron_pickaxe, 1);
			this.shopLimited[j++] = new ItemStack(Items.golden_axe, 1);
			this.shopLimited[j++] = new ItemStack(Items.golden_sword, 1);
			this.shopLimited[j++] = new ItemStack(Items.golden_pickaxe, 1);
			this.shopLimited[j++] = new ItemStack(Items.diamond_axe, 1);
			this.shopLimited[j++] = new ItemStack(Items.diamond_sword, 1);
			this.shopLimited[j++] = new ItemStack(Items.diamond_pickaxe, 1);
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_ARMORS)
		{
			// Unlimited Items
			
			
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.leather_boots, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_leggings, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_helmet, 1);
			this.shopLimited[j++] = new ItemStack(Items.leather_chestplate, 1);
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_TRINKETS)
		{
			// Unlimited Items
			
			
			// Limited Items
			
		}
		else if (this.shopType == RotShopTypeManager.BLACKSMITH_MOUNT)
		{
			// Unlimited Items
			
			
			// Limited Items
			this.shopLimited[j++] = new ItemStack(Items.iron_horse_armor, 1);
			this.shopLimited[j++] = new ItemStack(Items.golden_horse_armor, 1);
			this.shopLimited[j++] = new ItemStack(Items.diamond_horse_armor, 1);
		}
	}

	private int rollGold(World world)
	{
		int gold = world.rand.nextInt(4000) + 1000; // 1000-5000 gold.
		return gold;
	}

	private void rollNames(World world)
	{
		int firstNameSyllables = world.rand.nextInt(1)+2; // 2-3 syllables
		int lastNameSyllables = world.rand.nextInt(2)+2; // 2-4 syllables
		
		this.firstName += RotNamer.getFirstName(firstNameSyllables);
		this.lastName += RotNamer.getLastName(lastNameSyllables);
	}
}
