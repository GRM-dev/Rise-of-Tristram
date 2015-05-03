package ca.grm.rot.items;

import ca.grm.rot.Rot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RotItems {
	
	// Nuggets
	public static Item silverNugget;
	public static Item tinNugget;
	public static Item copperNugget;
	public static Item platinumNugget;
	public static Item leadNugget;
	public static Item bronzeNugget;
	public static Item steelNugget;
	
	// Ingots
	public static Item silverIngot;
	public static Item tinIngot;
	public static Item copperIngot;
	public static Item platinumIngot;
	public static Item leadIngot;
	public static Item bronzeIngot;
	public static Item steelIngot;
	
	// Raw Gems
	public static Item rawAmethyst;
	public static Item rawDiamond;
	public static Item rawEmerald;
	public static Item rawRuby;
	public static Item rawTopaz;
	
	public static void init()
	{
		silverNugget = new Item().setUnlocalizedName("silverNugget").setCreativeTab(Rot.tabRot);
		tinNugget = new Item().setUnlocalizedName("tinNugget").setCreativeTab(Rot.tabRot);
		copperNugget = new Item().setUnlocalizedName("copperNugget").setCreativeTab(Rot.tabRot);
		platinumNugget = new Item().setUnlocalizedName("platinumNugget").setCreativeTab(Rot.tabRot);
		leadNugget = new Item().setUnlocalizedName("leadNugget").setCreativeTab(Rot.tabRot);
		bronzeNugget = new Item().setUnlocalizedName("bronzeNugget").setCreativeTab(Rot.tabRot);
		steelNugget = new Item().setUnlocalizedName("steelNugget").setCreativeTab(Rot.tabRot);
		
		silverIngot = new Item().setUnlocalizedName("silverIngot").setCreativeTab(Rot.tabRot);
		tinIngot = new Item().setUnlocalizedName("tinIngot").setCreativeTab(Rot.tabRot);
		copperIngot = new Item().setUnlocalizedName("copperIngot").setCreativeTab(Rot.tabRot);
		platinumIngot = new Item().setUnlocalizedName("platinumIngot").setCreativeTab(Rot.tabRot);
		leadIngot = new Item().setUnlocalizedName("leadIngot").setCreativeTab(Rot.tabRot);
		bronzeIngot = new Item().setUnlocalizedName("bronzeIngot").setCreativeTab(Rot.tabRot);
		steelIngot = new Item().setUnlocalizedName("steelIngot").setCreativeTab(Rot.tabRot);
		
		rawAmethyst = new Item().setUnlocalizedName("rawAmethyst").setCreativeTab(Rot.tabRot);
		rawDiamond = new Item().setUnlocalizedName("rawDiamond").setCreativeTab(Rot.tabRot);
		rawEmerald = new Item().setUnlocalizedName("rawEmerald").setCreativeTab(Rot.tabRot);
		rawRuby = new Item().setUnlocalizedName("rawRuby").setCreativeTab(Rot.tabRot);
		rawTopaz = new Item().setUnlocalizedName("rawTopaz").setCreativeTab(Rot.tabRot);
	}
	
	public static void register()
	{
		GameRegistry.registerItem(silverNugget, silverNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(tinNugget, tinNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(copperNugget, copperNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(platinumNugget, platinumNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(leadNugget, leadNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(bronzeNugget, bronzeNugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(steelNugget, steelNugget.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(silverIngot, silverIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(tinIngot, tinIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(copperIngot, copperIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(platinumIngot, platinumIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(leadIngot, leadIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(bronzeIngot, bronzeIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(steelIngot, steelIngot.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(rawAmethyst, rawAmethyst.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(rawDiamond, rawDiamond.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(rawEmerald, rawEmerald.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(rawRuby, rawRuby.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(rawTopaz, rawTopaz.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(silverNugget);
		registerRender(tinNugget);
		registerRender(copperNugget);
		registerRender(platinumNugget);
		registerRender(leadNugget);
		registerRender(bronzeNugget);
		registerRender(steelNugget);
		
		registerRender(silverIngot);
		registerRender(tinIngot);
		registerRender(copperIngot);
		registerRender(platinumIngot);
		registerRender(leadIngot);
		registerRender(bronzeIngot);
		registerRender(steelIngot);
		
		registerRender(rawAmethyst);
		registerRender(rawDiamond);
		registerRender(rawEmerald);
		registerRender(rawRuby);
		registerRender(rawTopaz);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
