package ca.grm.rot.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SandBlocks extends Block
{
	private Item drop;
	
	public SandBlocks(Material materialIn, Item drop) 
	{
		super(materialIn);
		this.drop = drop;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.drop;
	}
}