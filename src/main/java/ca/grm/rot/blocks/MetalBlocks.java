package ca.grm.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MetalBlocks extends Block
{
	public MetalBlocks(Material materialIn) 
	{
		super(materialIn);
		setHarvestLevel("pickaxe", 2);
	}
}
