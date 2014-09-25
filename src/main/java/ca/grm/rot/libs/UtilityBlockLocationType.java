package ca.grm.rot.libs;

import net.minecraft.block.Block;

/** Custom Class used in base building **/
public class UtilityBlockLocationType {
	public int		x, y, z, meta = 0;
	public Block	block;
	
	public UtilityBlockLocationType(int x, int y, int z, Block block) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
	}

	public UtilityBlockLocationType(int x, int y, int z, Block block, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.meta = meta;
	}
}
