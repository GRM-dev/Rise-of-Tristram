package ee.rot.items;


import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.Rot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class RotItems {

	public static Item itemManaConverter;
	public static Item itemRelicRepair;
    public static Item itemGunpowderInfuser;
    public static Item itemRelicHeal;
    public static Item itemRelicLife;
    
    public static Item itemSwordSoul;
    public static Item itemCBR;
    
    public static Item itemMana;
	
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
		
		itemCBR = new ItemCrossbowRepater()
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("itemCBR");
		
		itemSwordSoul = new ItemSwordSoul(ToolMaterial.EMERALD)
			.setCreativeTab(Rot.tabRoT)
			.setUnlocalizedName("itemSwordSoul")
			.setTextureName(Rot.MODID+":"+"soul_sword");
	}
	
	public static void registerItems()
	{
		GameRegistry.registerItem(itemManaConverter, itemManaConverter.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicRepair, itemRelicRepair.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemGunpowderInfuser, itemGunpowderInfuser.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicHeal, itemRelicHeal.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicLife, itemRelicLife.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(itemCBR, itemCBR.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemSwordSoul, itemSwordSoul.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(itemMana, itemMana.getUnlocalizedName().substring(5));
	}
	
}
