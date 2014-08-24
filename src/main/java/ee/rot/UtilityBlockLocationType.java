package ee.rot;

import net.minecraft.block.Block;

/** Custom Class used in base building **/
public class UtilityBlockLocationType 
{
	public int x,y,z;
	public Block block;
	public UtilityBlockLocationType(int x, int y, int z, Block block)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
	}
}
