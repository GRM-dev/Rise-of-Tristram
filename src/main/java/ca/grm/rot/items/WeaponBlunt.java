package ca.grm.rot.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityNBTKeyNames;

public class WeaponBlunt extends WeaponCustom {
	private int	numOfTypes	= 2;
	IIcon[]				heads		= new IIcon[numOfTypes];
	IIcon				handle;
	IIcon				defaultIcon;
	
	public WeaponBlunt(ToolMaterial mat) {
		super(mat);
		setNumberOfTypes(numOfTypes);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handle;
			case 1 :
				return this.heads[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.bladeHead)];
			case 2 :
				return null;
			default :
				break;
		}
		return this.defaultIcon;
	}

	public IIcon[] getIcons(ItemStack stack) {
		return new IIcon[]{
				this.handle,
				this.heads[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.bladeHead)]};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		ItemStack[] blunts = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++) {
			blunts[i] = new ItemStack(p_150895_1_, 1, 0);
			UtilityNBTHelper.setString(blunts[i], UtilityNBTKeyNames.type, "blunt");
			UtilityNBTHelper
					.setString(blunts[i], UtilityNBTKeyNames.size, "normal");
			UtilityNBTHelper.setInteger(blunts[i], UtilityNBTKeyNames.bladeHead, i);
			p_150895_3_.add(blunts[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityNBTKeyNames.type, "blunt");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityNBTKeyNames.size, "normal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.heads[i] = ir.registerIcon(Rot.MODID + ":"
					+ "weapons/blades/head_blunt_" + i);
		}
		this.handle = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/handle_blunt");
		this.defaultIcon = ir
				.registerIcon(Rot.MODID + ":" + "weapons/fighter_blunt_icon");
	}
	
}
