package ca.grm.rot.items;

import ca.grm.rot.Rot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RotItems {
	
	public static Item silver_nugget;
	public static Item tin_nugget;
	public static Item copper_nugget;
	public static Item platinum_nugget;
	public static Item lead_nugget;
	public static Item bronze_nugget;
	public static Item steel_nugget;
	
	public static void init()
	{
		silver_nugget = new Item().setUnlocalizedName("silver_nugget").setCreativeTab(Rot.tabRot);
		tin_nugget = new Item().setUnlocalizedName("tin_nugget").setCreativeTab(Rot.tabRot);
		copper_nugget = new Item().setUnlocalizedName("copper_nugget").setCreativeTab(Rot.tabRot);
		platinum_nugget = new Item().setUnlocalizedName("platinum_nugget").setCreativeTab(Rot.tabRot);
		lead_nugget = new Item().setUnlocalizedName("lead_nugget").setCreativeTab(Rot.tabRot);
		bronze_nugget = new Item().setUnlocalizedName("bronze_nugget").setCreativeTab(Rot.tabRot);
		steel_nugget = new Item().setUnlocalizedName("steel_nugget").setCreativeTab(Rot.tabRot);
	}
	
	public static void register()
	{
		GameRegistry.registerItem(silver_nugget, silver_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(tin_nugget, tin_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(copper_nugget, copper_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(platinum_nugget, platinum_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(lead_nugget, lead_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(bronze_nugget, bronze_nugget.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(steel_nugget, steel_nugget.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(silver_nugget);
		registerRender(tin_nugget);
		registerRender(copper_nugget);
		registerRender(platinum_nugget);
		registerRender(lead_nugget);
		registerRender(bronze_nugget);
		registerRender(steel_nugget);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
