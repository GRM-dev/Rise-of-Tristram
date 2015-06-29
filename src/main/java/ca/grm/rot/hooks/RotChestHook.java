package ca.grm.rot.hooks;

import ca.grm.rot.items.RotItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class RotChestHook {
	
	public static void addChestHooks()
	{
		// This is the big fat method that calls all the little methods to add drops.
		setChestLoot();
		setLootToMineshaftCorridor();
		setLootToPyramidDesertChest();
		setLootToPyramidJungleChest();
		setLootToPyramidJungleDispenser();
		setLootToStrongholdCorridor();
		setLootToStrongholdLibrary();
		setLootToStrongholdCrossing();
		setLootToVillageBlacksmith();
		setLootToBonusChest();
		setLootToDungeonChest();
		setLootToNetherFortress();
		
	}
	
	public static void setChestLoot()
	{
		// Adds to every chest.
		// This is where you call 'setChestsForAll'
		addLootToAll(RotItems.ancientArmorBoots, 1, 1, 1); // Stupidly rare
		addLootToAll(RotItems.ancientArmorChestplate, 1, 1, 1);
		addLootToAll(RotItems.ancientArmorHelm, 1, 1, 1);
		addLootToAll(RotItems.ancientArmorLeggings, 1, 1, 1);
		
		addLootToAll(RotItems.fullPlateArmorBoots, 1, 1, 10);
		addLootToAll(RotItems.fullPlateArmorChestplate, 1, 1, 10);
		addLootToAll(RotItems.fullPlateArmorHelm, 1, 1, 10);
		addLootToAll(RotItems.fullPlateArmorLeggings, 1, 1, 10);
		
		addLootToAll(RotItems.gothicPlateArmorBoots, 1, 1, 15);
		addLootToAll(RotItems.gothicPlateArmorChestplate, 1, 1, 15);
		addLootToAll(RotItems.gothicPlateArmorHelm, 1, 1, 15);
		addLootToAll(RotItems.gothicPlateArmorLeggings, 1, 1, 15);
	}
	
	private static void addLootToAll(Block paraBlock, int min, int max, int rarity)
	{
		// This exists so I don't have to type out ".MINESHAFT_CORRIDOR, .STRUCTURE," a million times.
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToAll(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToMineshaftCorridor()
	{
		// Call 'ChestGenHooks.MINESHAFT_CORRIDOR' here.
		//addLootToMineshaftCorridor(null, 0, 0, 0);
	}
	
	private static void addLootToMineshaftCorridor(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToMineshaftCorridor(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToPyramidDesertChest()
	{
		// Call 'ChestGenHooks.PYRAMID_DESERT_CHEST' here.
		
	}
	
	private static void addLootToPyramidDesertChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToPyramidDesertChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToPyramidJungleChest()
	{
		// Call 'ChestGenHooks.PYRAMID_JUNGLE_CHEST' here.
		
	}
	
	private static void addLootToPyramidJungleChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToPyramidJungleChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToPyramidJungleDispenser()
	{
		// Call 'ChestGenHooks.PYRAMID_JUNGLE_DISPENSER' here.
		
	}
	
	private static void addLootToPyramidJungleDispenser(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToPyramidJungleDispenser(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToStrongholdCorridor()
	{
		// Call 'ChestGenHooks.STRONGHOLD_CORRIDOR' here.
		
	}
	
	private static void addLootToStrongholdCorridor(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToStrongholdCorridor(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToStrongholdLibrary()
	{
		// Call 'ChestGenHooks.STRONGHOLD_LIBRARY' here.
		
	}
	
	private static void addLootToStrongholdLibrary(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToStrongholdLibrary(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToStrongholdCrossing()
	{
		// Call 'ChestGenHooks.STRONGHOLD_CROSSING' here.
		
	}
	
	private static void addLootToStrongholdCrossing(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToStrongholdCrossing(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToVillageBlacksmith()
	{
		// Call 'ChestGenHooks.VILLAGE_BLACKSMITH' here.
		
	}
	
	private static void addLootToVillageBlacksmith(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToVillageBlacksmith(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToBonusChest()
	{
		// Call 'ChestGenHooks.BONUS_CHEST' here.
		
	}
	
	private static void addLootToBonusChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToBonusChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToDungeonChest()
	{
		// Call 'ChestGenHooks.DUNGEON_CHEST' here.
		
	}
	
	private static void addLootToDungeonChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToDungeonChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public static void setLootToNetherFortress()
	{
		// Call 'ChestGenHooks.NETHER_FORTRESS' here.
		
	}
	
	private static void addLootToNetherFortress(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private static void addLootToNetherFortress(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}

}
