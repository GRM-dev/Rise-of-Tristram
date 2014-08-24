package ee.rot.blocks;

import java.util.Random;

import ee.rot.RotOld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SpawnerBlock extends Block
{
	
	private Block bts;

	protected SpawnerBlock(Material p_i45394_1_, String textureName, Block bts) {
		super(p_i45394_1_);
		this.setBlockTextureName(RotOld.MODID + ":" + textureName);
		this.setTickRandomly(true);
		this.bts = bts;
		
		// TODO Auto-generated constructor stub
	}
	
	/**
     * How many world ticks before ticking
     */
    public int tickRate(World world)
    {
        return 1;
    }
    
	/**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.isAirBlock(x, y + 1, z))
        {
           world.setBlock(x, y + 1, z, bts);   
        }
        else if (world.getBlock(x, y+1, z) == bts)
        {
        	world.setBlock(x, y + 1, z, bts);
        }
    }
	
}
