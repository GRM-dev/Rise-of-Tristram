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

public class WeaponSlash extends WeaponCustom {
	private int	numOfTypes	= 8;
	IIcon[]				blades		= new IIcon[numOfTypes];
	IIcon[]				bladeEffectsFrost		= new IIcon[numOfTypes];
	IIcon[]				bladeEffectsBleed		= new IIcon[numOfTypes];
	IIcon[]				bladeEffectsVamp		= new IIcon[numOfTypes];
	IIcon[]				guards		= new IIcon[numOfTypes];
	IIcon[]				guardEffects0		= new IIcon[numOfTypes];
	IIcon[]				handles		= new IIcon[numOfTypes];
	IIcon[]				handleEffects0		= new IIcon[numOfTypes];
	IIcon				nullIcon;
	IIcon				defaultIcon;
	
	public WeaponSlash(ToolMaterial mat) {
		super(mat);
		setNumberOfTypes(numOfTypes);
	}
	//TODO add in weapon effects, and effect Icons

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handles[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.handle)];
			case 1 :
				return this.blades[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.bladeHead)];
			case 2 :
				return this.guards[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.guard)];
			default :
				break;
		}
		return this.defaultIcon;
	}

	@Override
	public IIcon[] getIcons(ItemStack stack) {
		//0 is handle, 2 is blade, 4 is guard
		//1 he, 3 be, and 5 ge; are effects
		IIcon[] icons = new IIcon[6];
		
		icons[0] = this.handles[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handle)];
		icons[2] = this.blades[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHead)];
		icons[4] = this.guards[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.guard)];

		switch(UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHeadEffect))
		{
		case 1:
			icons[3] = this.bladeEffectsFrost[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.bladeHead)];
			break;
		case 2:
			icons[3] = this.bladeEffectsBleed[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.bladeHead)];
			break;
		case 3:
			icons[3] = this.bladeEffectsVamp[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.bladeHead)];
			break;
		default:	
			icons[3] = nullIcon;
			break;
		}
		switch(UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handleEffect))
		{
		case 1:
			icons[1] = this.handleEffects0[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.handle)];
			break;
		default:	
			icons[1] = nullIcon;
			break;
		}
		switch(UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.guardEffect))
		{
		case 1:
			icons[5] = this.guardEffects0[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.guard)];
			break;
		default:	
			icons[5] = nullIcon;
			break;
		}
		
		return icons;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		ItemStack[] swords = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++) {
			swords[i] = new ItemStack(p_150895_1_, 1, 0);
			UtilityNBTHelper.setInteger(swords[i],
					UtilityWeaponNBTKeyNames.layerColor+2, 0xd9d100);
			UtilityNBTHelper.setInteger(swords[i],
					UtilityWeaponNBTKeyNames.layerColor+4, 0xffBBbb);
			UtilityNBTHelper.setString(swords[i], UtilityWeaponNBTKeyNames.type, "slash");
			UtilityNBTHelper
					.setString(swords[i], UtilityWeaponNBTKeyNames.size, "large");
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.guard, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.handleEffect, 1);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.bladeHeadEffect, 0);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.guardEffect, 1);
			p_150895_3_.add(swords[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityWeaponNBTKeyNames.type, "slash");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityWeaponNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.bladeHead, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.guard, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.handle, 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.blades[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/blades/blade_"
					+ i);
			this.bladeEffectsFrost[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_0");
			this.bladeEffectsBleed[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_1");
			this.bladeEffectsVamp[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_2"); 
			this.guards[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/guards/guard_"
					+ i);
			this.guardEffects0[i] = ir.registerIcon(Rot.MODID+":"+"weapons/guards/effects/guard_"
					+ i +"_e_0");
			this.handles[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/handle_"
					+ i);
			this.handleEffects0[i] = ir.registerIcon(Rot.MODID+":"+"weapons/handles/effects/handle_"
					+ i +"_e_0");
		}
		this.defaultIcon = ir
				.registerIcon(Rot.MODID + ":" + "weapons/fighter_slash_icon");
		this.nullIcon = ir.registerIcon(Rot.MODID + ":" + "weapons/32x32Null");
	}
	
}
