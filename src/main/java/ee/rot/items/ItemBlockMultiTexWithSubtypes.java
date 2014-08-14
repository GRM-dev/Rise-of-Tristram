package ee.rot.items;

import ee.rot.blocks.BlockMultiTexWithSubSets;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiTexWithSubtypes extends ItemBlock
{

	private String[] names = new String[]{"base block"};
	private String itemName = "block";
	private Block block;
	
	public ItemBlockMultiTexWithSubtypes(Block block/*, String[] names, String itemName*/) 
	{
		super(block);
		setHasSubtypes(true);
		this.block = block;
		//this.names = names;
		//this.itemName = itemName;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		//return names[is.getItemDamage()] +" "+ itemName;
		return ((BlockMultiTexWithSubSets)block).getName();
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
	
}
