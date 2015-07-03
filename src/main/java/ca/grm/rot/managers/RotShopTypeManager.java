package ca.grm.rot.managers;

import ca.grm.rot.libs.RotShopType;

public class RotShopTypeManager {
	public static RotShopType BLACKSMITH_WEAPONS = new RotShopType("the Weapon Smith", 0);
	public static RotShopType BLACKSMITH_ARMORS = new RotShopType("the Armor Smith", 1);
	public static RotShopType BLACKSMITH_TRINKETS = new RotShopType("the Trinket Smith", 2);
	public static RotShopType BLACKSMITH_MOUNT = new RotShopType("the Mount-Gear Smith", 3);
	public static RotShopType CLOTHIER = new RotShopType("the Clothier", 4);
	public static RotShopType BUTCHER = new RotShopType("the Butcher", 5);
	public static RotShopType COOK = new RotShopType("the Chef", 6);
	public static RotShopType FARMER_SEED_SUPPLIER = new RotShopType("the Farming Seed Supplier", 7);
	public static RotShopType FARMER_ANIMAL_PRODUCTS = new RotShopType("the Farming-Animal Product Supplier", 8);
	public static RotShopType SCHOLAR = new RotShopType("the Scholar", 9);
	public static RotShopType DYE_MASTER = new RotShopType("the Dye Master", 10);
	public static RotShopType TINKERER = new RotShopType("the Tinkerer", 11);
	
	public static RotShopType getShopTypeFromIndex(int index)
	{
		if (index == BLACKSMITH_WEAPONS.index)
		{
			return BLACKSMITH_WEAPONS;
		}
		else if (index == BLACKSMITH_ARMORS.index)
		{
			return BLACKSMITH_ARMORS;
		}
		else if (index == BLACKSMITH_TRINKETS.index)
		{
			return BLACKSMITH_TRINKETS;
		}
		else if (index == BLACKSMITH_MOUNT.index)
		{
			return BLACKSMITH_MOUNT;
		}
		else if (index == CLOTHIER.index)
		{
			return CLOTHIER;
		}
		else if (index == BUTCHER.index)
		{
			return BUTCHER;
		}
		else if (index == COOK.index)
		{
			return COOK;
		}
		else if (index == FARMER_SEED_SUPPLIER.index)
		{
			return FARMER_SEED_SUPPLIER;
		}
		else if (index == FARMER_ANIMAL_PRODUCTS.index)
		{
			return FARMER_ANIMAL_PRODUCTS;
		}
		else if (index == SCHOLAR.index)
		{
			return SCHOLAR;
		}
		else if (index == DYE_MASTER.index)
		{
			return DYE_MASTER;
		}
		else if (index == TINKERER.index)
		{
			return TINKERER;
		}
		System.out.println("Get shop type from index failed.");
		return null;
	}
}
