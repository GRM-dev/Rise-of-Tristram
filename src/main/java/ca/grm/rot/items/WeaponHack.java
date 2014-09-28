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

public class WeaponHack extends WeaponCustom {
	private int	numOfTypes	= 5;
	IIcon[]				heads		= new IIcon[numOfTypes];
	IIcon				handle;
	IIcon				defaultIcon;
	
	public WeaponHack(ToolMaterial mat) {
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
		ItemStack[] hacks = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++) {
			hacks[i] = new ItemStack(p_150895_1_, 1, 0);
			UtilityNBTHelper.setString(hacks[i], UtilityWeaponNBTKeyNames.type, "hack");
			UtilityNBTHelper.setString(hacks[i], UtilityWeaponNBTKeyNames.size, "normal");
			UtilityNBTHelper.setInteger(hacks[i], UtilityWeaponNBTKeyNames.bladeHead, i);
			p_150895_3_.add(hacks[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityWeaponNBTKeyNames.type, "hack");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityWeaponNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.bladeHead, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.heads[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/blades/head_hack_"
					+ i);
		}
		this.handle = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/handle_hack");
		this.defaultIcon = ir.registerIcon(Rot.MODID + ":" + "weapons/fighter_hack_icon");
	}
	
}
