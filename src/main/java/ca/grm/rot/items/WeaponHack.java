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
	private int	numOfTypes	= 6;
	IIcon[]				heads		= new IIcon[numOfTypes];
	IIcon[]				headsE		= new IIcon[numOfTypes];
	IIcon[]				headEffectsFrost		= new IIcon[numOfTypes];
	IIcon[]				headEffectsBleed		= new IIcon[numOfTypes];
	IIcon[]				headEffectsVamp		= new IIcon[numOfTypes];
	IIcon[]				headEffectsFrostE		= new IIcon[numOfTypes];
	IIcon[]				headEffectsBleedE		= new IIcon[numOfTypes];
	IIcon[]				headEffectsVampE		= new IIcon[numOfTypes];
	IIcon[]				handles		= new IIcon[numOfTypes];
	IIcon[]				handleEffects0		= new IIcon[numOfTypes];
	IIcon				nullIcon;
	IIcon				defaultIcon;
	
	public String showExtraBlade = Rot.MODID + "bladeHeadExtra";
	
	public WeaponHack(ToolMaterial mat) {
		super(mat);
		setNumberOfTypes(numOfTypes);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handles[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handle)];
			case 1 :
				return this.heads[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.bladeHead)];
			case 2 :
				if (UtilityNBTHelper.getBoolean(stack, showExtraBlade))
					return this.headsE[UtilityNBTHelper.getInt(stack,
							UtilityWeaponNBTKeyNames.bladeHead)];
				else 
					return null;
			default :
				break;
		}
		return this.defaultIcon;
	}

	@Override
	public IIcon[] getIcons(ItemStack stack) {
		IIcon[] icons = new IIcon[6];
		
		icons[0] = this.handles[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handle)];
		icons[2] = this.heads[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHead)];
		if (UtilityNBTHelper.getBoolean(stack, showExtraBlade))
			icons[4] = this.headsE[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHead)];
		else
			icons[4] = nullIcon;
		
		switch(UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHeadEffect))
		{
		case 1:
			icons[3] = this.headEffectsFrost[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.bladeHead)];
			break;
		case 2:
			icons[3] = this.headEffectsBleed[UtilityNBTHelper.getInt(stack,
					UtilityWeaponNBTKeyNames.bladeHead)];
			break;
		case 3:
			icons[3] = this.headEffectsVamp[UtilityNBTHelper.getInt(stack,
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
		if (UtilityNBTHelper.getBoolean(stack, showExtraBlade))
		{
			switch(UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.guardEffect))
			{
				case 1:
					icons[5] = this.headEffectsFrostE[UtilityNBTHelper.getInt(stack,
							UtilityWeaponNBTKeyNames.bladeHead)];
					break;
				case 2:
					icons[5] = this.headEffectsBleedE[UtilityNBTHelper.getInt(stack,
							UtilityWeaponNBTKeyNames.bladeHead)];
					break;
				case 3:
					icons[5] = this.headEffectsVampE[UtilityNBTHelper.getInt(stack,
							UtilityWeaponNBTKeyNames.bladeHead)];
					break;
				default:	
					icons[5] = nullIcon;
					break;
			}
		}
		else
			icons[5] = nullIcon;
		
		return icons;
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
			UtilityNBTHelper.setInteger(hacks[i], UtilityWeaponNBTKeyNames.handle, i);
			UtilityNBTHelper.setBoolean(hacks[i], showExtraBlade, false);
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
		UtilityNBTHelper.setBoolean(par1ItemStack, showExtraBlade, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.heads[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/blades/head_hack_"
					+ i);
			this.headsE[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/guards/head_hack_"
					+ i);
			this.handles[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/handle_hack_"+i);
		}
		
		this.defaultIcon = ir.registerIcon(Rot.MODID + ":" + "weapons/fighter_hack_icon");
		this.nullIcon = ir.registerIcon(Rot.MODID + ":" + "weapons/32x32Null");
	}
	
}
