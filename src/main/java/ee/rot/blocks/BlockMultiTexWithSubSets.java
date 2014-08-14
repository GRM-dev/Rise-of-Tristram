package ee.rot.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMultiTexWithSubSets extends Block
{

	private IIcon sideTextureI;
	private IIcon[] topBottomTexturesI;
	private String sideTextureS;
	private String[] topBottomeTexturesS;
	private String name;
	
	protected BlockMultiTexWithSubSets(Material mat,String[] topBottomTextures, String sideTexture, String name) 
	{
		super(mat);
		sideTextureS = sideTexture;
		topBottomeTexturesS = topBottomTextures;
		this.name = name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) 
	{
		topBottomTexturesI = new IIcon[topBottomeTexturesS.length];
		for (int i = 0;i < topBottomeTexturesS.length; i++)
		{
			topBottomTexturesI[i] = ir.registerIcon(topBottomeTexturesS[i]);
		}
		sideTextureI = ir.registerIcon(sideTextureS);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) 
	{
		if(side == 0 || side == 1) 
		{
			return topBottomTexturesI[meta];
		} 
		else 
		{
			return sideTextureI;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) 
	{
		for (int i = 0; i < topBottomTexturesI.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta) 
	{
		return meta;
	}
	
	public String getName()
	{
		return name;
	}
}
