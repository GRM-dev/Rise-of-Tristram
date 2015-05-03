package ca.grm.rot.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

// Only to be used for Galena Ore
public class GalenaOreBlock extends Block
{
	public GalenaOreBlock(Material materialIn) 
	{
		super(materialIn);
	}
	
	@Override
	public ArrayList <ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		// Define drops here
		drops.add(new ItemStack(RotBlocks.leadOre, 1));
		Random rand = new Random();
		if (rand.nextFloat() < 0.5F)
	        drops.add(new ItemStack(RotBlocks.silverOre));
		return drops;
    }
}
