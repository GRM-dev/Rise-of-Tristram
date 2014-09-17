package ee.rot.items;


import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.Rot;

public class RotItems 
{	
	public static Item itemManaConverter;
	public static Item itemRelicRepair;
    public static Item itemGunpowderInfuser;
    public static Item itemRelicHeal;
    public static Item itemRelicLife;
    
    public static Item weaponSwordSoul;
    public static Item weaponFighterSlash;
    public static Item weaponFighterHack;
    public static Item weaponFighterBlunt;
    public static Item weaponFighterPierce;
    public static Item weaponCBR;
    
    public static Item itemMana;
    public static Item weaponStaffBlue;
	
    public static Item tutHelmet;
    public static Item tutPlate;
    public static Item tutPants;
    public static Item tutBoots;
    
	public static void init()
	{
		itemManaConverter = new ItemManaConverter()
			.setUnlocalizedName("manaConverter")
			.setCreativeTab(Rot.tabRoT)
			.setTextureName(Rot.MODID +":" + "manaConverter");
		
		itemRelicRepair = new ItemRelicRepair()
			.setUnlocalizedName("relicRepair")
			.setCreativeTab(Rot.tabRoT)
			.setTextureName(Rot.MODID +":" + "relicRepair");
		
		itemGunpowderInfuser = new ItemGunpowderInfuser()
			.setUnlocalizedName("gunpowderInfuser")
			.setCreativeTab(Rot.tabRoT)
			.setTextureName(Rot.MODID +":" + "gunpowderInfuser");
		
		itemRelicHeal = new ItemRelicHeal()
			.setUnlocalizedName("relicHeal")
			.setCreativeTab(Rot.tabRoT)
			.setTextureName(Rot.MODID+":"+"relicHeal");
		
		itemRelicLife = new ItemRelicLife()
			.setUnlocalizedName("relicLife")
			.setCreativeTab(Rot.tabRoT);
		
		itemMana = new ItemMana()
			.setUnlocalizedName("manaCrystal")
			.setCreativeTab(Rot.tabRoT);
		
		weaponCBR = new ItemCrossbowRepater()
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("weaponCBR");
		
		weaponSwordSoul = new ItemSwordSoul(ToolMaterial.EMERALD)
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("weaponSwordSoul")
			.setTextureName(Rot.MODID+":"+"weapons/soul_sword");
		
		weaponFighterSlash = new WeaponFighterSlash(ToolMaterial.IRON)
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("weaponFighterSlash");
		
		weaponFighterHack = new WeaponFighterHack(ToolMaterial.IRON)
		.setCreativeTab(Rot.tabRoT)
		.setUnlocalizedName("weaponFighterHack");
		
		weaponFighterBlunt = new WeaponFighterBlunt(ToolMaterial.IRON)
		.setCreativeTab(Rot.tabRoT)
		.setUnlocalizedName("weaponFighterBlunt");
		
		weaponFighterPierce = new WeaponFighterPierce(ToolMaterial.IRON)
		.setCreativeTab(Rot.tabRoT)
		.setUnlocalizedName("weaponFighterPierce");
		
		weaponStaffBlue = new WeaponStaff()
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("weaponStaffBlue")
			.setTextureName(Rot.MODID+":"+"weapons/staffBlue");
		
		tutHelmet = new ItemArmorCustom(ArmorMaterial.CLOTH, 0, "mage2").setUnlocalizedName("ta").setCreativeTab(Rot.tabRoT);
		tutPlate = new ItemArmorCustom(ArmorMaterial.CLOTH, 1, "mage2").setUnlocalizedName("taa").setCreativeTab(Rot.tabRoT);
		tutPants = new ItemArmorCustom(ArmorMaterial.CLOTH, 2, "mage2").setUnlocalizedName("taaq").setCreativeTab(Rot.tabRoT);
		tutBoots = new ItemArmorCustom(ArmorMaterial.CLOTH, 3, "mage2").setUnlocalizedName("taaaa").setCreativeTab(Rot.tabRoT);
	}
	
	public static void registerItems()
	{
		GameRegistry.registerItem(itemManaConverter, itemManaConverter.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicRepair, itemRelicRepair.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemGunpowderInfuser, itemGunpowderInfuser.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicHeal, itemRelicHeal.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicLife, itemRelicLife.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(weaponCBR, weaponCBR.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponSwordSoul, weaponSwordSoul.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponFighterSlash, weaponFighterSlash.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponFighterHack, weaponFighterHack.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponFighterBlunt, weaponFighterBlunt.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponFighterPierce, weaponFighterPierce.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(weaponStaffBlue, weaponStaffBlue.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(itemMana, itemMana.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(tutHelmet, tutHelmet.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(tutPlate, tutPlate.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(tutPants, tutPants.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(tutBoots, tutBoots.getUnlocalizedName().substring(5));
	}
	
	
	//Special Item related methods
	public static boolean checkForItemsAndAmounts(ItemStack[] items, int[] amounts, InventoryPlayer invPlayer)
	{
		if (items.length != amounts.length)//Just to make sure you checked right
		{
			System.out.println("items and amounts do not match up in lengths.");
			return false;
		}
		boolean[] correctAmount = new boolean[items.length];
		int[] amountCounter = new int[amounts.length];
		for (int i = 0; i < correctAmount.length; i++)
		{
			correctAmount[i] = false;
			amountCounter[i] = 0;
		}
		
		//Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory();slot++)
		{
			if (invPlayer.getStackInSlot(slot) != null)
			{
				//If something is in the slot check to see if it matches one of the given items
				for (int item = 0; item < items.length; item++)
				{
					if (invPlayer.getStackInSlot(slot).equals(items[item]) && !correctAmount[item])
					{
						//If the found stack matches the item and don't have the correct amount
						amountCounter[item] += invPlayer.getStackInSlot(slot).stackSize;
						if (amountCounter[item] >= amounts[item])
						{
							correctAmount[item] = true;
							break;//no need to go through the rest of the loop, found the item.
						}						
					}
				}
			}
		}
		
		//make sure that all the amounts were found
		for (int i = 0; i < correctAmount.length; i++)
		{
			if (!correctAmount[i])return false;
			//If one of the amounts didn't add up, return false.
		}
		//This is asssuming all amounts were found expect random error
		return true;
	}
	
	public static boolean checkForItemAndAmount(Item item, int amount, InventoryPlayer invPlayer)
	{
		boolean correctAmount = false;
		int amountCounter = 0;
		
		//Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory();slot++)
		{
			if (invPlayer.getStackInSlot(slot) != null)
			{				
				if (invPlayer.getStackInSlot(slot).getItem().equals(item) && !correctAmount)
				{
					//If the found stack matches the item and don't have the correct amount
					amountCounter += invPlayer.getStackInSlot(slot).stackSize;
					if (amountCounter >= amount)
					{
						correctAmount = true;
						break;//no need to go through the rest of the loop, found the item.
					}						
				}				
			}
		}		
		//make sure that all the amounts were found
		if (!correctAmount)return false;
		//If one of the amounts didn't add up, return false.	
		//This is asssuming all amounts were found expect random error
		return true;
	}
	
}
