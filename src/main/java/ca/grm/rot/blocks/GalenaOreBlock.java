package ca.grm.rot.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Only to be used for Galena Ore
public class GalenaOreBlock extends Block
{
	public GalenaOreBlock(Material materialIn) 
	{
		super(materialIn);
	}
	
	public ArrayList <ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) 
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		// Define drops here
		drops.add(new ItemStack(RotBlocks.leadOre, 1));
		if (world.rand.nextFloat() < 0.5F)
	        drops.add(new ItemStack(RotBlocks.silverOre));
		return drops;
    }
}
