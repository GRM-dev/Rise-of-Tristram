package ca.grm.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ca.grm.rot.Rot;

public class NormalBlock extends Block {
	
	protected NormalBlock(Material p_i45394_1_, String textureName) {
		super(p_i45394_1_);
		this.setBlockTextureName(Rot.MODID + ":" + textureName);
	}

}
