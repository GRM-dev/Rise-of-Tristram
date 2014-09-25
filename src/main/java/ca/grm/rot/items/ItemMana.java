package ca.grm.rot.items;

import java.util.List;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMana extends Item {
	private float		manaRestore	= 15;
	private float		maxMana		= 75;
	private IIcon[]		icons		= new IIcon[6];
	private String[]	names		= new String[]{
			"manaCrystal", "manaShard", "manaFoci", "baseNodeList"};

	public ItemMana() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if (par2 == 0) {
			return 0xFFFFFF;
		} else {
			return 0x4DC9FF;
		}
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (stack.getItemDamage() == 3) {
			if (pass == 0) { return this.icons[3]; }
			if ((pass == 1)
					&& (UtilityNBTHelper.getString(stack,
							Rot.MODID + "baseNodeListCoords").trim() != "")) { return this.icons[4]; }
		} else {
			if (pass == 0) { return this.icons[stack.getItemDamage()]; }
		}
		if (pass == 1) { return this.icons[5]; }
		return this.icons[1];
	}

	@Override
	public int getRenderPasses(int metadata) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < this.names.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		// System.out.println(getUnlocalizedName() + "." +
		// names[getDamage(item)]);
		return getUnlocalizedName() + "." + this.names[getDamage(item)];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		int type = item.getItemDamage();
		ExtendPlayer props = ExtendPlayer.get(player);
		switch (type) {
			case 0 :
				props.regenMana(this.manaRestore * 4);
				--item.stackSize;
				break;
			case 1 :
				props.regenMana(this.manaRestore);
				--item.stackSize;
				break;
			case 3 :
				UtilityNBTHelper.setString(item, Rot.MODID + "baseNodeListCoords", "");
				break;
		}
		return item;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity,
			int par4, boolean par5) {
		if (par1ItemStack.getItemDamage() == 3) {
			if (UtilityNBTHelper
					.getString(par1ItemStack, Rot.MODID + "baseNodeListCoords").trim()
					.equals("")) {
				UtilityNBTHelper.setString(par1ItemStack, Rot.MODID
						+ "baseNodeListCoords", "");
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.icons[0] = ir.registerIcon(Rot.MODID.toLowerCase() + ":" + "manaCrystal");
		this.icons[1] = ir.registerIcon(Rot.MODID.toLowerCase() + ":" + "manaShard");
		this.icons[2] = ir.registerIcon(Rot.MODID.toLowerCase() + ":" + "manaFoci");
		this.icons[3] = ir.registerIcon(Rot.MODID.toLowerCase() + ":" + "baseNodeList");
		this.icons[4] = ir.registerIcon(Rot.MODID.toLowerCase() + ":"
				+ "baseNodeList_overLay");
		this.icons[5] = ir.registerIcon(Rot.MODID.toLowerCase() + ":" + "null");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
