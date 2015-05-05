package ca.grm.rot.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SandBlocks extends Block
{
	private Item drop;
	private int meta;
	private int least_quantity;
	private int most_quantity;
	
	public SandBlocks(Material materialIn, Item drop, int meta, int least_quantity, int most_quantity) 
	{
		super(materialIn);
		this.drop = drop;
	    this.meta = meta;
	    this.least_quantity = least_quantity;
	    this.most_quantity = most_quantity;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
	    return this.drop;
	}

	@Override
	public int damageDropped(IBlockState state) {
	    return this.meta;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
	    if (this.least_quantity >= this.most_quantity)
	        return this.least_quantity;
	    return this.least_quantity + random.nextInt(this.most_quantity - this.least_quantity + fortune + 1);
	}
	
}