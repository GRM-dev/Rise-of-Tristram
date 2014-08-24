package ee.rot.blocks;

import ee.rot.RotOld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class NormalBlock extends Block
{

	protected NormalBlock(Material p_i45394_1_, String textureName) {
		super(p_i45394_1_);
		this.setBlockTextureName(RotOld.MODID + ":" + textureName);
	}
	
}
