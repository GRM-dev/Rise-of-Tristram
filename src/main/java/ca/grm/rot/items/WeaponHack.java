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
	
	public WeaponHack(ToolMaterial mat) {
		super(mat);
		setNumberOfTypes(numOfTypes);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handles[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.handle)];
			case 1 :
				return this.heads[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.bladeHead)];
			case 2 :
				if (UtilityNBTHelper.getBoolean(stack, UtilityNBTKeyNames.showExtra))
					return this.headsE[UtilityNBTHelper.getInt(stack,
							UtilityNBTKeyNames.bladeHead)];
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
		
		icons[0] = this.handles[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.handle)];
		icons[2] = this.heads[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.bladeHead)];
		if (UtilityNBTHelper.getBoolean(stack, UtilityNBTKeyNames.showExtra))
			icons[4] = this.headsE[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.bladeHead)];
		else
			icons[4] = nullIcon;
		
		switch(UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.bladeHeadEffect))
		{
		case 1:
			icons[3] = this.headEffectsFrost[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.bladeHead)];
			break;
		case 2:
			icons[3] = this.headEffectsBleed[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.bladeHead)];
			break;
		case 3:
			icons[3] = this.headEffectsVamp[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.bladeHead)];
			break;
		default:	
			icons[3] = nullIcon;
			break;
		}
		switch(UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.handleEffect))
		{
			case 1:
				icons[1] = this.handleEffects0[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.handle)];
				break;
			default:	
				icons[1] = nullIcon;
				break;
			}
		if (UtilityNBTHelper.getBoolean(stack, UtilityNBTKeyNames.showExtra))
		{
			switch(UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.guardEffect))
			{
				case 1:
					icons[5] = this.headEffectsFrostE[UtilityNBTHelper.getInt(stack,
							UtilityNBTKeyNames.bladeHead)];
					break;
				case 2:
					icons[5] = this.headEffectsBleedE[UtilityNBTHelper.getInt(stack,
							UtilityNBTKeyNames.bladeHead)];
					break;
				case 3:
					icons[5] = this.headEffectsVampE[UtilityNBTHelper.getInt(stack,
							UtilityNBTKeyNames.bladeHead)];
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
			UtilityNBTHelper.setString(hacks[i], UtilityNBTKeyNames.type, "hack");
			UtilityNBTHelper.setString(hacks[i], UtilityNBTKeyNames.size, "normal");
			UtilityNBTHelper.setInteger(hacks[i], UtilityNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(hacks[i], UtilityNBTKeyNames.handle, i);
			UtilityNBTHelper.setBoolean(hacks[i], UtilityNBTKeyNames.showExtra, false);
			p_150895_3_.add(hacks[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityNBTKeyNames.type, "hack");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityNBTKeyNames.bladeHead, 1);
		UtilityNBTHelper.setBoolean(par1ItemStack, UtilityNBTKeyNames.showExtra, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.heads[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/blades/head_hack_"
					+ i);
			this.headsE[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/guards/head_hack_"
					+ i);
			this.handles[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/handles/handle_hack_"+i);
		}
		
		this.defaultIcon = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/fighter_hack_icon");
		this.nullIcon = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/32x32Null");
	}
	
}
