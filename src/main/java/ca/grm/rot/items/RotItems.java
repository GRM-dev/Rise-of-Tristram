package ca.grm.rot.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.Rot;

public class RotItems {
	
	// Armor Materials
	public static ArmorMaterial QUILTED = EnumHelper.addArmorMaterial("QUILTED", Rot.MOD_ID + ":quiltedArmor", 5, new int[] {1, 2, 3, 1}, 13);
	public static ArmorMaterial HARDLEATHER = EnumHelper.addArmorMaterial("HARDLEATHER", Rot.MOD_ID + ":hardLeatherArmor", 7, new int[] {1, 2, 3, 1}, 14);
	public static ArmorMaterial STUDDED = EnumHelper.addArmorMaterial("STUDDED", Rot.MOD_ID + ":studdedArmor", 9, new int[] {1, 2, 3, 1}, 13);
	public static ArmorMaterial RINGMAIL = EnumHelper.addArmorMaterial("RINGMAIL", Rot.MOD_ID + ":ringMailArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial SCALEMAIL = EnumHelper.addArmorMaterial("SCALEMAIL", Rot.MOD_ID + ":scaleMailArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial BREASTPLATE = EnumHelper.addArmorMaterial("BREASTPLATE", Rot.MOD_ID + ":breastPlateArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial SPLINTMAIL = EnumHelper.addArmorMaterial("SPLINTMAIL", Rot.MOD_ID + ":splintMailArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial LIGHTPLATE = EnumHelper.addArmorMaterial("LIGHTPLATE", Rot.MOD_ID + ":lightPlateArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial FIELDPLATE = EnumHelper.addArmorMaterial("FIELDPLATE", Rot.MOD_ID + ":fieldPlateArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial PLATEMAIL = EnumHelper.addArmorMaterial("PLATEMAIL", Rot.MOD_ID + ":plateMailArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial GOTHICPLATE = EnumHelper.addArmorMaterial("GOTHICPLATE", Rot.MOD_ID + ":gothicPlateArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial FULLPLATE = EnumHelper.addArmorMaterial("FULLPLATE", Rot.MOD_ID + ":fullPlateArmor", 13, new int[] {1, 2, 3, 1}, 10);
	public static ArmorMaterial ANCIENT = EnumHelper.addArmorMaterial("ANCIENT", Rot.MOD_ID + ":ancientArmor", 13, new int[] {1, 2, 3, 1}, 10);

	
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
	
	// Armors
	//// Quilted Armor
	public static Item quiltedArmorHelm;
	public static Item quiltedArmorChestplate;
	public static Item quiltedArmorLeggings;
	public static Item quiltedArmorBoots;
	
	// Hard Leather Armor
	public static Item hardLeatherArmorHelm;
	public static Item hardLeatherArmorChestplate;
	public static Item hardLeatherArmorLeggings;
	public static Item hardLeatherArmorBoots;
	
	// Studded Armor
	public static Item studdedArmorHelm;
	public static Item studdedArmorChestplate;
	public static Item studdedArmorLeggings;
	public static Item studdedArmorBoots;
	
	// Ring Mail Armor
	public static Item ringMailArmorHelm;
	public static Item ringMailArmorChestplate;
	public static Item ringMailArmorLeggings;
	public static Item ringMailArmorBoots;
	
	// Scale Mail Armor
	public static Item scaleMailArmorHelm;
	public static Item scaleMailArmorChestplate;
	public static Item scaleMailArmorLeggings;
	public static Item scaleMailArmorBoots;
	
	// Breast Plate Armor
	public static Item breastPlateArmorHelm;
	public static Item breastPlateArmorChestplate;
	public static Item breastPlateArmorLeggings;
	public static Item breastPlateArmorBoots;
	
	// Splint Mail Armor
	public static Item splintMailArmorHelm;
	public static Item splintMailArmorChestplate;
	public static Item splintMailArmorLeggings;
	public static Item splintMailArmorBoots;
	
	// Light Plate Armor
	public static Item lightPlateArmorHelm;
	public static Item lightPlateArmorChestplate;
	public static Item lightPlateArmorLeggings;
	public static Item lightPlateArmorBoots;
	
	// Field Plate Armor
	public static Item fieldPlateArmorHelm;
	public static Item fieldPlateArmorChestplate;
	public static Item fieldPlateArmorLeggings;
	public static Item fieldPlateArmorBoots;
	
	// Plate Mail Armor
	public static Item plateMailArmorHelm;
	public static Item plateMailArmorChestplate;
	public static Item plateMailArmorLeggings;
	public static Item plateMailArmorBoots;
	
	// Gothic Plate Armor
	public static Item gothicPlateArmorHelm;
	public static Item gothicPlateArmorChestplate;
	public static Item gothicPlateArmorLeggings;
	public static Item gothicPlateArmorBoots;
	
	// Full Plate Armor
	public static Item fullPlateArmorHelm;
	public static Item fullPlateArmorChestplate;
	public static Item fullPlateArmorLeggings;
	public static Item fullPlateArmorBoots;
	
	// Ancient Armor
	public static Item ancientArmorHelm;
	public static Item ancientArmorChestplate;
	public static Item ancientArmorLeggings;
	public static Item ancientArmorBoots;
	
	// Gold
	public static Item gold;
	
	public static void init()
	{
		// Nuggets
		silverNugget = new Item().setUnlocalizedName("silverNugget").setCreativeTab(Rot.tabRot);
		tinNugget = new Item().setUnlocalizedName("tinNugget").setCreativeTab(Rot.tabRot);
		copperNugget = new Item().setUnlocalizedName("copperNugget").setCreativeTab(Rot.tabRot);
		platinumNugget = new Item().setUnlocalizedName("platinumNugget").setCreativeTab(Rot.tabRot);
		leadNugget = new Item().setUnlocalizedName("leadNugget").setCreativeTab(Rot.tabRot);
		bronzeNugget = new Item().setUnlocalizedName("bronzeNugget").setCreativeTab(Rot.tabRot);
		steelNugget = new Item().setUnlocalizedName("steelNugget").setCreativeTab(Rot.tabRot);
		
		// Ingots
		silverIngot = new Item().setUnlocalizedName("silverIngot").setCreativeTab(Rot.tabRot);
		tinIngot = new Item().setUnlocalizedName("tinIngot").setCreativeTab(Rot.tabRot);
		copperIngot = new Item().setUnlocalizedName("copperIngot").setCreativeTab(Rot.tabRot);
		platinumIngot = new Item().setUnlocalizedName("platinumIngot").setCreativeTab(Rot.tabRot);
		leadIngot = new Item().setUnlocalizedName("leadIngot").setCreativeTab(Rot.tabRot);
		bronzeIngot = new Item().setUnlocalizedName("bronzeIngot").setCreativeTab(Rot.tabRot);
		steelIngot = new Item().setUnlocalizedName("steelIngot").setCreativeTab(Rot.tabRot);
		
		// Raw Gems
		rawAmethyst = new Item().setUnlocalizedName("rawAmethyst").setCreativeTab(Rot.tabRot);
		rawDiamond = new Item().setUnlocalizedName("rawDiamond").setCreativeTab(Rot.tabRot);
		rawEmerald = new Item().setUnlocalizedName("rawEmerald").setCreativeTab(Rot.tabRot);
		rawRuby = new Item().setUnlocalizedName("rawRuby").setCreativeTab(Rot.tabRot);
		rawTopaz = new Item().setUnlocalizedName("rawTopaz").setCreativeTab(Rot.tabRot);
		
		// Armors
		//// Quilted Armor
		quiltedArmorHelm = new RotArmors("quiltedArmorHelm", QUILTED, 1, 0).setUnlocalizedName("quiltedArmorHelm").setCreativeTab(Rot.tabRot);
		quiltedArmorChestplate = new RotArmors("quiltedArmorChestplate", QUILTED, 2, 1).setUnlocalizedName("quiltedArmorChestplate").setCreativeTab(Rot.tabRot);
		quiltedArmorLeggings = new RotArmors("quiltedArmorLeggings", QUILTED, 1, 2).setUnlocalizedName("quiltedArmorLeggings").setCreativeTab(Rot.tabRot);
		quiltedArmorBoots = new RotArmors("quiltedArmorBoots", QUILTED, 1, 3).setUnlocalizedName("quiltedArmorBoots").setCreativeTab(Rot.tabRot);
		
		//// Hard Leather Armor
		hardLeatherArmorHelm = new RotArmors("hardLeatherArmorHelm", HARDLEATHER, 1, 0).setCreativeTab(Rot.tabRot);
		hardLeatherArmorChestplate = new RotArmors("hardLeatherArmorChestplate", HARDLEATHER, 2, 1).setCreativeTab(Rot.tabRot);
		hardLeatherArmorLeggings = new RotArmors("hardLeatherArmorLeggings", HARDLEATHER, 1, 2).setCreativeTab(Rot.tabRot);
		hardLeatherArmorBoots = new RotArmors("hardLeatherArmorBoots", HARDLEATHER, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Studded Armor
		studdedArmorHelm = new RotArmors("studdedArmorHelm", STUDDED, 1, 0).setCreativeTab(Rot.tabRot);
		studdedArmorChestplate = new RotArmors("studdedArmorChestplate", STUDDED, 2, 1).setCreativeTab(Rot.tabRot);
		studdedArmorLeggings = new RotArmors("studdedArmorLeggings", STUDDED, 1, 2).setCreativeTab(Rot.tabRot);
		studdedArmorBoots = new RotArmors("studdedArmorBoots", STUDDED, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Ring Mail Armor
		ringMailArmorHelm = new RotArmors("ringMailArmorHelm", RINGMAIL, 1, 0).setCreativeTab(Rot.tabRot);
		ringMailArmorChestplate = new RotArmors("ringMailArmorChestplate", RINGMAIL, 2, 1).setCreativeTab(Rot.tabRot);
		ringMailArmorLeggings = new RotArmors("ringMailArmorLeggings", RINGMAIL, 1, 2).setCreativeTab(Rot.tabRot);
		ringMailArmorBoots = new RotArmors("ringMailArmorBoots", RINGMAIL, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Scale Mail Armor
		scaleMailArmorHelm = new RotArmors("scaleMailArmorHelm", SCALEMAIL, 1, 0).setCreativeTab(Rot.tabRot);
		scaleMailArmorChestplate = new RotArmors("scaleMailArmorChestplate", SCALEMAIL, 2, 1).setCreativeTab(Rot.tabRot);
		scaleMailArmorLeggings = new RotArmors("scaleMailArmorLeggings", SCALEMAIL, 1, 2).setCreativeTab(Rot.tabRot);
		scaleMailArmorBoots = new RotArmors("scaleMailArmorBoots", SCALEMAIL, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Breast Plate Armor
		breastPlateArmorHelm = new RotArmors("breastPlateArmorHelm", BREASTPLATE, 1, 0).setCreativeTab(Rot.tabRot);
		breastPlateArmorChestplate = new RotArmors("breastPlateArmorChestplate", BREASTPLATE, 2, 1).setCreativeTab(Rot.tabRot);
		breastPlateArmorLeggings = new RotArmors("breastPlateArmorLeggings", BREASTPLATE, 1, 2).setCreativeTab(Rot.tabRot);
		breastPlateArmorBoots = new RotArmors("breastPlateArmorBoots", BREASTPLATE, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Splint Mail Armor
		splintMailArmorHelm = new RotArmors("splintMailArmorHelm", SPLINTMAIL, 1, 0).setCreativeTab(Rot.tabRot);
		splintMailArmorChestplate = new RotArmors("splintMailArmorChestplate", SPLINTMAIL, 2, 1).setCreativeTab(Rot.tabRot);
		splintMailArmorLeggings = new RotArmors("splintMailArmorLeggings", SPLINTMAIL, 1, 2).setCreativeTab(Rot.tabRot);
		splintMailArmorBoots = new RotArmors("splintMailArmorBoots", SPLINTMAIL, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Light Plate Armor
		lightPlateArmorHelm = new RotArmors("lightPlateArmorHelm", LIGHTPLATE, 1, 0).setCreativeTab(Rot.tabRot);
		lightPlateArmorChestplate = new RotArmors("lightPlateArmorChestplate", LIGHTPLATE, 2, 1).setCreativeTab(Rot.tabRot);
		lightPlateArmorLeggings = new RotArmors("lightPlateArmorLeggings", LIGHTPLATE, 1, 2).setCreativeTab(Rot.tabRot);
		lightPlateArmorBoots = new RotArmors("lightPlateArmorBoots", LIGHTPLATE, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Field Plate Armor
		fieldPlateArmorHelm = new RotArmors("fieldPlateArmorHelm", FIELDPLATE, 1, 0).setCreativeTab(Rot.tabRot);
		fieldPlateArmorChestplate = new RotArmors("fieldPlateArmorChestplate", FIELDPLATE, 2, 1).setCreativeTab(Rot.tabRot);
		fieldPlateArmorLeggings = new RotArmors("fieldPlateArmorLeggings", FIELDPLATE, 1, 2).setCreativeTab(Rot.tabRot);
		fieldPlateArmorBoots = new RotArmors("fieldPlateArmorBoots", FIELDPLATE, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Plate Mail Armor
		plateMailArmorHelm = new RotArmors("plateMailArmorHelm", PLATEMAIL, 1, 0).setCreativeTab(Rot.tabRot);
		plateMailArmorChestplate = new RotArmors("plateMailArmorChestplate", PLATEMAIL, 2, 1).setCreativeTab(Rot.tabRot);
		plateMailArmorLeggings = new RotArmors("plateMailArmorLeggings", PLATEMAIL, 1, 2).setCreativeTab(Rot.tabRot);
		plateMailArmorBoots = new RotArmors("plateMailArmorBoots", PLATEMAIL, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Gothic Plate Armor
		gothicPlateArmorHelm = new RotArmors("gothicPlateArmorHelm", GOTHICPLATE, 1, 0).setCreativeTab(Rot.tabRot);
		gothicPlateArmorChestplate = new RotArmors("gothicPlateArmorChestplate", GOTHICPLATE, 2, 1).setCreativeTab(Rot.tabRot);
		gothicPlateArmorLeggings = new RotArmors("gothicPlateArmorLeggings", GOTHICPLATE, 1, 2).setCreativeTab(Rot.tabRot);
		gothicPlateArmorBoots = new RotArmors("gothicPlateArmorBoots", GOTHICPLATE, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Full Plate Armor
		fullPlateArmorHelm = new RotArmors("fullPlateArmorHelm", FULLPLATE, 1, 0).setCreativeTab(Rot.tabRot);
		fullPlateArmorChestplate = new RotArmors("fullPlateArmorChestplate", FULLPLATE, 2, 1).setCreativeTab(Rot.tabRot);
		fullPlateArmorLeggings = new RotArmors("fullPlateArmorLeggings", FULLPLATE, 1, 2).setCreativeTab(Rot.tabRot);
		fullPlateArmorBoots = new RotArmors("fullPlateArmorBoots", FULLPLATE, 1, 3).setCreativeTab(Rot.tabRot);
		
		//// Ancient Armor
		ancientArmorHelm = new RotArmors("ancientArmorHelm", ANCIENT, 1, 0).setCreativeTab(Rot.tabRot);
		ancientArmorChestplate = new RotArmors("ancientArmorChestplate", ANCIENT, 2, 1).setCreativeTab(Rot.tabRot);
		ancientArmorLeggings = new RotArmors("ancientArmorLeggings", ANCIENT, 1, 2).setCreativeTab(Rot.tabRot);
		ancientArmorBoots = new RotArmors("ancientArmorBoots", ANCIENT, 1, 3).setCreativeTab(Rot.tabRot);
		
		// Gold
		gold = new Item().setUnlocalizedName("gold");
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
		
		GameRegistry.registerItem(quiltedArmorHelm, quiltedArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(quiltedArmorChestplate, quiltedArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(quiltedArmorLeggings, quiltedArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(quiltedArmorBoots, quiltedArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(hardLeatherArmorHelm, hardLeatherArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(hardLeatherArmorChestplate, hardLeatherArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(hardLeatherArmorLeggings, hardLeatherArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(hardLeatherArmorBoots, hardLeatherArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(studdedArmorHelm, studdedArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(studdedArmorChestplate, studdedArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(studdedArmorLeggings, studdedArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(studdedArmorBoots, studdedArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(ringMailArmorHelm, ringMailArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ringMailArmorChestplate, ringMailArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ringMailArmorLeggings, ringMailArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ringMailArmorBoots, ringMailArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(scaleMailArmorHelm, scaleMailArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(scaleMailArmorChestplate, scaleMailArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(scaleMailArmorLeggings, scaleMailArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(scaleMailArmorBoots, scaleMailArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(breastPlateArmorHelm, breastPlateArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(breastPlateArmorChestplate, breastPlateArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(breastPlateArmorLeggings, breastPlateArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(breastPlateArmorBoots, breastPlateArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(splintMailArmorHelm, splintMailArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(splintMailArmorChestplate, splintMailArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(splintMailArmorLeggings, splintMailArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(splintMailArmorBoots, splintMailArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(lightPlateArmorHelm, lightPlateArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(lightPlateArmorChestplate, lightPlateArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(lightPlateArmorLeggings, lightPlateArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(lightPlateArmorBoots, lightPlateArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(fieldPlateArmorHelm, fieldPlateArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fieldPlateArmorChestplate, fieldPlateArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fieldPlateArmorLeggings, fieldPlateArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fieldPlateArmorBoots, fieldPlateArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(plateMailArmorHelm, plateMailArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(plateMailArmorChestplate, plateMailArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(plateMailArmorLeggings, plateMailArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(plateMailArmorBoots, plateMailArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(gothicPlateArmorHelm, gothicPlateArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(gothicPlateArmorChestplate, gothicPlateArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(gothicPlateArmorLeggings, gothicPlateArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(gothicPlateArmorBoots, gothicPlateArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(fullPlateArmorHelm, fullPlateArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fullPlateArmorChestplate, fullPlateArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fullPlateArmorLeggings, fullPlateArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fullPlateArmorBoots, fullPlateArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(ancientArmorHelm, ancientArmorHelm.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ancientArmorChestplate, ancientArmorChestplate.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ancientArmorLeggings, ancientArmorLeggings.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ancientArmorBoots, ancientArmorBoots.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(gold, gold.getUnlocalizedName().substring(5));
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
		
		registerRender(quiltedArmorHelm);
		registerRender(quiltedArmorChestplate);
		registerRender(quiltedArmorLeggings);
		registerRender(quiltedArmorBoots);
		
		registerRender(hardLeatherArmorHelm);
		registerRender(hardLeatherArmorChestplate);
		registerRender(hardLeatherArmorLeggings);
		registerRender(hardLeatherArmorBoots);
		
		registerRender(studdedArmorHelm);
		registerRender(studdedArmorChestplate);
		registerRender(studdedArmorLeggings);
		registerRender(studdedArmorBoots);
		
		registerRender(ringMailArmorHelm);
		registerRender(ringMailArmorChestplate);
		registerRender(ringMailArmorLeggings);
		registerRender(ringMailArmorBoots);
		
		registerRender(scaleMailArmorHelm);
		registerRender(scaleMailArmorChestplate);
		registerRender(scaleMailArmorLeggings);
		registerRender(scaleMailArmorBoots);
		
		registerRender(breastPlateArmorHelm);
		registerRender(breastPlateArmorChestplate);
		registerRender(breastPlateArmorLeggings);
		registerRender(breastPlateArmorBoots);
		
		registerRender(splintMailArmorHelm);
		registerRender(splintMailArmorChestplate);
		registerRender(splintMailArmorLeggings);
		registerRender(splintMailArmorBoots);
		
		registerRender(lightPlateArmorHelm);
		registerRender(lightPlateArmorChestplate);
		registerRender(lightPlateArmorLeggings);
		registerRender(lightPlateArmorBoots);
		
		registerRender(fieldPlateArmorHelm);
		registerRender(fieldPlateArmorChestplate);
		registerRender(fieldPlateArmorLeggings);
		registerRender(fieldPlateArmorBoots);
		
		registerRender(plateMailArmorHelm);
		registerRender(plateMailArmorChestplate);
		registerRender(plateMailArmorLeggings);
		registerRender(plateMailArmorBoots);
		
		registerRender(gothicPlateArmorHelm);
		registerRender(gothicPlateArmorChestplate);
		registerRender(gothicPlateArmorLeggings);
		registerRender(gothicPlateArmorBoots);
		
		registerRender(fullPlateArmorHelm);
		registerRender(fullPlateArmorChestplate);
		registerRender(fullPlateArmorLeggings);
		registerRender(fullPlateArmorBoots);
		
		registerRender(ancientArmorHelm);
		registerRender(ancientArmorChestplate);
		registerRender(ancientArmorLeggings);
		registerRender(ancientArmorBoots);
		
		registerRender(gold);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
