package ca.grm.rot.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import ca.grm.rot.Rot;

public class SpawnerBlock extends Block {

	private Block	bts;
	
	protected SpawnerBlock(Material p_i45394_1_, String textureName, Block bts) {
		super(p_i45394_1_);
		this.setBlockTextureName(Rot.MOD_ID + ":" + textureName);
		this.setTickRandomly(true);
		this.bts = bts;
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World world) {
		return 1;
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, this.bts);
		} else if (world.getBlock(x, y + 1, z) == this.bts) {
			world.setBlock(x, y + 1, z, this.bts);
		}
	}

}
