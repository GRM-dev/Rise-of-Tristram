package ca.grm.rot.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import ca.grm.rot.Rot;

public class BlockBaseBuilder extends BlockContainer {

	public BlockBaseBuilder() {
		super(Material.iron);
		setHardness(5f);
		setResistance(10f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBaseBuilder();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			FMLNetworkHandler.openGui(playerIn, Rot.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
