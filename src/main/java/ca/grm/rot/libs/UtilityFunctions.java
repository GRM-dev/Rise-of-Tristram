package ca.grm.rot.libs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UtilityFunctions 
{
	
	/*THIS NEEDS TO BE MOVED*/
	//TODO move these somewhere else
	public static Block[]	blockTypeObjects	= {
		Blocks.air, Blocks.planks, Blocks.cobblestone, Blocks.stone,
		Blocks.stonebrick, Blocks.glass, Blocks.glass_pane, Blocks.brick_block, Blocks.sandstone};
	public static int[]		blockTypeColors		= {
		0xFF00CCFF, 0xFFFFBB00, 0xFFAAAAAA, 0xFFBBBBBB, 0xFFBBBBBB, 0xFFFFFFFF, 0xFFFFFFFF,
		0xFFAA0000,0xFFFFBB00							};

	/**Will Scan for an item, and check to see if the correct amount is in a player's inventory**/
	public static boolean checkForItemAndAmount(Item item, int amount,
			InventoryPlayer invPlayer) {
		boolean correctAmount = false;
		int amountCounter = 0;

		// Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory(); slot++) {
			if (invPlayer.getStackInSlot(slot) != null) {
				if (invPlayer.getStackInSlot(slot).getItem().equals(item)
						&& !correctAmount) {
					// If the found stack matches the item and don't have the
					// correct amount
					amountCounter += invPlayer.getStackInSlot(slot).stackSize;
					if (amountCounter >= amount) {
						correctAmount = true;
						break;// no need to go through the rest of the loop,
								// found the item.
					}
				}
			}
		}
		// make sure that all the amounts were found
		if (!correctAmount) { return false; }
		// If one of the amounts didn't add up, return false.
		// This is assuming all amounts were found expect random error
		return true;
	}

	/**Will Scan for a list of items, and check to see if the correct amounts are in a player's inventory**/
	public static boolean checkForItemsAndAmounts(ItemStack[] items, int[] amounts,
			InventoryPlayer invPlayer) {
		if (items.length != amounts.length)// Just to make sure you checked
											// right
		{
			System.out.println("items and amounts do not match up in lengths.");
			return false;
		}
		boolean[] correctAmount = new boolean[items.length];
		int[] amountCounter = new int[amounts.length];
		for (int i = 0; i < correctAmount.length; i++) {
			correctAmount[i] = false;
			amountCounter[i] = 0;
		}

		// Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory(); slot++) {
			if (invPlayer.getStackInSlot(slot) != null) {
				// If something is in the slot check to see if it matches one of
				// the given items
				for (int item = 0; item < items.length; item++) {
					if (invPlayer.getStackInSlot(slot).equals(items[item])
							&& !correctAmount[item]) {
						// If the found stack matches the item and don't have
						// the correct amount
						amountCounter[item] += invPlayer.getStackInSlot(slot).stackSize;
						if (amountCounter[item] >= amounts[item]) {
							correctAmount[item] = true;
							break;// no need to go through the rest of the loop,
									// found the item.
						}
					}
				}
			}
		}

		// make sure that all the amounts were found
		for (int i = 0; i < correctAmount.length; i++) {
			if (!correctAmount[i]) { return false;
			// If one of the amounts didn't add up, return false.
			}
		}
		// This is asssuming all amounts were found expect random error
		return true;
	}	
}
