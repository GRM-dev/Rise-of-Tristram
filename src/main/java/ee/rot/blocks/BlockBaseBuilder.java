package ee.rot.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;

public class BlockBaseBuilder extends BlockContainer
{

	public BlockBaseBuilder() {
		super( Material.iron);
		setHardness(5f);
		setResistance(10f);
	}
	
	@SideOnly(Side.CLIENT)
	public static IIcon topIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon frontIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) 
	{
		topIcon = icon.registerIcon(Rot.MODID.toLowerCase() + ":" + "repairBox_Top");
		sideIcon = icon.registerIcon(Rot.MODID.toLowerCase() + ":" + "repairBox_Side");
		frontIcon = icon.registerIcon(Rot.MODID.toLowerCase() + ":" + "repairBox_Side");
	}

	@Override
	public IIcon getIcon(int side, int meta) 
	{
		if(side == 0 || side == 1) {
		return topIcon;
		} else if(side != meta) {
		return sideIcon;
		} else {
		return frontIcon;
		}
	}
	
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) 
	{
		// TODO Auto-generated method stub
		super.onBlockAdded(par1World, par2, par3, par4);
		//setDefaultDirection(par1World, par2, par3, par4);
	}
	
	/*private void setDefaultDirection(World world, int x, int y, int z) 
	{
		if(!world.isRemote) {
		int zNeg = world.getBlock(x, y, z - 1);
		int zPos = world.getBlock(x, y, z + 1);
		int xNeg = world.getBlock(x - 1, y, z);
		int xPos = world.getBlock(x + 1, y, z);
		byte meta = 3;

		if(Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos]) {
		meta = 5;
		}

		if(Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg]) {
		meta = 4;
		}

		if(Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos]) {
		meta = 3;
		}

		if(Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg]) {
		meta = 2;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}*/
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) 
	{
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		if(rotation == 0) {
		world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if(rotation == 1) {
		world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if(rotation == 2) {
		world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if(rotation == 3) {
		world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityBaseBuilder();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote) {
			//FMLNetworkHandler.openGui(player, Main.instance, 0, world, x, y, z);
		}
		return true;
	}

}
