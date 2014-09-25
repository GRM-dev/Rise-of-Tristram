package ca.grm.rot.items;

import java.util.List;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityWeaponNBTKeyNames;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WeaponPierce extends WeaponCustom {
	public static int	numOfTypes	= 2;
	IIcon[]				heads		= new IIcon[numOfTypes];
	IIcon				handle;
	IIcon				defaultIcon;
	
	public WeaponPierce(ToolMaterial mat) {
		super(mat);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handle;
			case 1 :
				return this.heads[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.bladeHead)];
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
						UtilityWeaponNBTKeyNames.bladeHead)]};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		ItemStack[] pierces = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++) {
			pierces[i] = new ItemStack(p_150895_1_, 1, 0);
			UtilityNBTHelper.setString(pierces[i], UtilityWeaponNBTKeyNames.type,
					"pierce");
			UtilityNBTHelper.setString(pierces[i], UtilityWeaponNBTKeyNames.size,
					"normal");
			UtilityNBTHelper
					.setInteger(pierces[i], UtilityWeaponNBTKeyNames.bladeHead, i);
			p_150895_3_.add(pierces[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityWeaponNBTKeyNames.type, "pierce");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityWeaponNBTKeyNames.size, "normal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.heads[i] = ir.registerIcon(Rot.MODID + ":"
					+ "weapons/blades/head_pierce_" + i);
		}
		this.handle = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/handle_pierce");
		this.defaultIcon = ir.registerIcon(Rot.MODID + ":"
				+ "weapons/fighter_pierce_icon");
	}
	
}
