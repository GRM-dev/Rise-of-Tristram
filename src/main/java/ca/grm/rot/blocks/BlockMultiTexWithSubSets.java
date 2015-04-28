package ca.grm.rot.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiTexWithSubSets extends Block {
	
	private IIcon		sideTextureI;
	private IIcon[]		topBottomTexturesI;
	private String		sideTextureS;
	private String[]	topBottomeTexturesS;
	private String		name;

	protected BlockMultiTexWithSubSets(Material mat, String[] topBottomTextures,
			String sideTexture, String name) {
		super(mat);
		this.sideTextureS = sideTexture;
		this.topBottomeTexturesS = topBottomTextures;
		this.name = name;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if ((side == 0) || (side == 1)) {
			return this.topBottomTexturesI[meta];
		} else {
			return this.sideTextureI;
		}
	}

	public String getName() {
		return this.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < this.topBottomTexturesI.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		this.topBottomTexturesI = new IIcon[this.topBottomeTexturesS.length];
		for (int i = 0; i < this.topBottomeTexturesS.length; i++) {
			this.topBottomTexturesI[i] = ir.registerIcon(this.topBottomeTexturesS[i]);
		}
		this.sideTextureI = ir.registerIcon(this.sideTextureS);
	}
}
