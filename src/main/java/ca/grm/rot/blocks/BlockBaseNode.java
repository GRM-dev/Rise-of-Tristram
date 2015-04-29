package ca.grm.rot.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.Rot;

public class BlockBaseNode extends BlockContainer {

	private IIcon[]	icons	= new IIcon[8];

	public BlockBaseNode() {
		super(Material.iron);
		setHardness(5f);
		setResistance(10f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBaseNode();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int meta) {
		if ((meta >= 0) && (meta <= 7)) {
			return this.icons[meta];
		} else {
			return this.icons[0];
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			FMLNetworkHandler.openGui(player, Rot.instance, 0, world, x, y, z);
		}
		return true;
	}

	@Override
	public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_,
			int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_,
			float p_149660_8_, int p_149660_9_) {

		return super.onBlockPlaced(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_,
				p_149660_5_, p_149660_6_, p_149660_7_, p_149660_8_, 7);// meta 7
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		for (int i = 0; i < this.icons.length; i++) {
			this.icons[i] = ir
					.registerIcon(Rot.MOD_ID + ":" + "itemGen_" + (i + 1) + "_8");
		}
	}

}
