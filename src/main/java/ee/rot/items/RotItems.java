package ee.rot.items;


import cpw.mods.fml.common.registry.GameRegistry;
import ee.rot.RotOld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class RotItems {

	public static Item itemManaConverter;
	public static Item itemRelicRepair;
    public static Item itemGunpowderInfuser;
    public static Item itemRelicHeal;
    public static Item itemRelicLife;
    
    public static Item itemSwordSoul;
    public static Item itemWandMS;
    public static Item itemCBR;
    
    public static Item itemMana;
    
    public static Item spellSpeed;
    public static Item spellJump;
	
	public static void init()
	{
		itemManaConverter = new ItemManaConverter()
			.setUnlocalizedName("manaConverter")
			.setCreativeTab(RotOld.tabRoT)
			.setTextureName(RotOld.MODID +":" + "manaConverter");
		
		itemRelicRepair = new ItemRelicRepair()
			.setUnlocalizedName("relicRepair")
			.setCreativeTab(RotOld.tabRoT)
			.setTextureName(RotOld.MODID +":" + "relicRepair");
		
		itemGunpowderInfuser = new ItemGunpowderInfuser()
			.setUnlocalizedName("gunpowderInfuser")
			.setCreativeTab(RotOld.tabRoT)
			.setTextureName(RotOld.MODID +":" + "gunpowderInfuser");
		
		itemRelicHeal = new ItemRelicHeal()
			.setUnlocalizedName("relicHeal")
			.setCreativeTab(RotOld.tabRoT)
			.setTextureName(RotOld.MODID+":"+"relicHeal");
		
		itemRelicLife = new ItemRelicLife()
			.setUnlocalizedName("relicLife")
			.setCreativeTab(RotOld.tabRoT);
		
		itemMana = new ItemMana()
			.setUnlocalizedName("manaCrystal")
			.setCreativeTab(RotOld.tabRoT);
		
		itemWandMS = new ItemWandManaSteal(ToolMaterial.WOOD)
			.setUnlocalizedName("itemWandMS")
			.setCreativeTab(RotOld.tabRoT)
			.setTextureName(RotOld.MODID+":"+"wandMana");
		
		itemCBR = new ItemCrossbowRepater()
			.setCreativeTab(RotOld.tabRoT)
			.setUnlocalizedName("itemCBR");
		
		itemSwordSoul = new ItemSwordSoul(ToolMaterial.EMERALD)
			.setCreativeTab(RotOld.tabRoT)
			.setUnlocalizedName("itemSwordSoul")
			.setTextureName(RotOld.MODID+":"+"soul_sword");
		
		spellSpeed = new ItemSpellWind()
			.setTextureName(RotOld.MODID+":"+"spell_1")
			.setCreativeTab(RotOld.tabRoT)
			.setUnlocalizedName("spellSpeed");
		
		spellJump = new ItemSpellJump()
			.setTextureName(RotOld.MODID+":"+"spell_2")
			.setCreativeTab(RotOld.tabRoT)
			.setUnlocalizedName("spellJump");
	}
	
	public static void registerItems()
	{
		GameRegistry.registerItem(itemManaConverter, itemManaConverter.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicRepair, itemRelicRepair.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemGunpowderInfuser, itemGunpowderInfuser.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicHeal, itemRelicHeal.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemRelicLife, itemRelicLife.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(itemWandMS, itemWandMS.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemCBR, itemCBR.getUnlocalizedName().substring(5));
    	GameRegistry.registerItem(itemSwordSoul, itemSwordSoul.getUnlocalizedName().substring(5));
    	
    	GameRegistry.registerItem(itemMana, itemMana.getUnlocalizedName().substring(5));
    	
    	//GameRegistry.registerItem(spellSpeed, spellSpeed.getUnlocalizedName().substring(5));
    	//GameRegistry.registerItem(spellJump, spellJump.getUnlocalizedName().substring(5));
	}
	
}
