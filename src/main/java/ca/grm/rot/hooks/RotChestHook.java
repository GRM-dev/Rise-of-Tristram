package ca.grm.rot.hooks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class RotChestHook {
	public RotChestHook()
	{
		
	}
	
	public void addChestHooks()
	{
		// This is the big fat method that calls all the little methods to add drops.
		setChestLoot();
	}
	
	public void setChestLoot()
	{
		// Adds to every chest.
		// This is where you call 'setChestsForAll'
	}
	
	private void addLootToAll(Block paraBlock, int min, int max, int rarity)
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
	
	private void addLootToAll(Item paraItem, int min, int max, int rarity)
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
	
	public void setLootToMineshaftCorridor()
	{
		// Call 'ChestGenHooks.MINESHAFT_CORRIDOR' here.
		
	}
	
	private void addLootToMineshaftCorridor(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToMineshaftCorridor(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToPyramidDesertChest()
	{
		// Call 'ChestGenHooks.PYRAMID_DESERT_CHEST' here.
		
	}
	
	private void addLootToPyramidDesertChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToPyramidDesertChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToPyramidJungleChest()
	{
		// Call 'ChestGenHooks.PYRAMID_JUNGLE_CHEST' here.
		
	}
	
	private void addLootToPyramidJungleChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToPyramidJungleChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToPyramidJungleDispenser()
	{
		// Call 'ChestGenHooks.PYRAMID_JUNGLE_DISPENSER' here.
		
	}
	
	private void addLootToPyramidJungleDispenser(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToPyramidJungleDispenser(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToStrongholdCorridor()
	{
		// Call 'ChestGenHooks.STRONGHOLD_CORRIDOR' here.
		
	}
	
	private void addLootToStrongholdCorridor(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToStrongholdCorridor(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToStrongholdLibrary()
	{
		// Call 'ChestGenHooks.STRONGHOLD_LIBRARY' here.
		
	}
	
	private void addLootToStrongholdLibrary(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToStrongholdLibrary(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToStrongholdCrossing()
	{
		// Call 'ChestGenHooks.STRONGHOLD_CROSSING' here.
		
	}
	
	private void addLootToStrongholdCrossing(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToStrongholdCrossing(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToVillageBlacksmith()
	{
		// Call 'ChestGenHooks.VILLAGE_BLACKSMITH' here.
		
	}
	
	private void addLootToVillageBlacksmith(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToVillageBlacksmith(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToBonusChest()
	{
		// Call 'ChestGenHooks.BONUS_CHEST' here.
		
	}
	
	private void addLootToBonusChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToBonusChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToDungeonChest()
	{
		// Call 'ChestGenHooks.DUNGEON_CHEST' here.
		
	}
	
	private void addLootToDungeonChest(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToDungeonChest(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}
	
	public void setLootToNetherFortress()
	{
		// Call 'ChestGenHooks.NETHER_FORTRESS' here.
		
	}
	
	private void addLootToNetherFortress(Block paraBlock, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraBlock), min, max, rarity));
	}
	
	private void addLootToNetherFortress(Item paraItem, int min, int max, int rarity)
	{
		ChestGenHooks.getInfo(ChestGenHooks.NETHER_FORTRESS).addItem(new WeightedRandomChestContent(new ItemStack(paraItem), min, max, rarity));
	}

}
