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
	IIcon nullIcon;
	IIcon				defaultIcon;
	
	public WeaponSlash(ToolMaterial mat) {
		super(mat);
		setNumberOfTypes(numOfTypes);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handles[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.handle)];
			case 1 :
				return this.blades[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.bladeHead)];
			case 2 :
				return this.guards[UtilityNBTHelper.getInt(stack,
						UtilityNBTKeyNames.guard)];
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
		
		icons[0] = this.handles[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.handle)];
		icons[2] = this.blades[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.bladeHead)];
		icons[4] = this.guards[UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.guard)];

		switch(UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.bladeHeadEffect))
		{
		case 1:
			icons[3] = this.bladeEffectsFrost[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.bladeHead)];
			break;
		case 2:
			icons[3] = this.bladeEffectsBleed[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.bladeHead)];
			break;
		case 3:
			icons[3] = this.bladeEffectsVamp[UtilityNBTHelper.getInt(stack,
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
		switch(UtilityNBTHelper.getInt(stack, UtilityNBTKeyNames.guardEffect))
		{
		case 1:
			icons[5] = this.guardEffects0[UtilityNBTHelper.getInt(stack,
					UtilityNBTKeyNames.guard)];
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
			UtilityNBTHelper.setString(swords[i], UtilityNBTKeyNames.type, "slash");
			UtilityNBTHelper
					.setString(swords[i], UtilityNBTKeyNames.size, "large");
			UtilityNBTHelper.setInteger(swords[i], UtilityNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityNBTKeyNames.guard, i);
			p_150895_3_.add(swords[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityNBTKeyNames.type, "slash");
		UtilityNBTHelper
				.setString(par1ItemStack, UtilityNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityNBTKeyNames.bladeHead, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityNBTKeyNames.guard, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityNBTKeyNames.handle, 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.blades[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/blades/blade_"
					+ i);
			this.bladeEffectsFrost[i] = ir.registerIcon(Rot.MOD_ID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_0");
			this.bladeEffectsBleed[i] = ir.registerIcon(Rot.MOD_ID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_1");
			this.bladeEffectsVamp[i] = ir.registerIcon(Rot.MOD_ID+":"+"weapons/blades/effects/blade_"
					+ i +"_e_2"); 
			this.guards[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/guards/guard_"
					+ i);
			this.guardEffects0[i] = ir.registerIcon(Rot.MOD_ID+":"+"weapons/guards/effects/guard_"
					+ i +"_e_0");
			this.handles[i] = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/handles/handle_"
					+ i);
			this.handleEffects0[i] = ir.registerIcon(Rot.MOD_ID+":"+"weapons/handles/effects/handle_"
					+ i +"_e_0");
		}
		this.defaultIcon = ir
				.registerIcon(Rot.MOD_ID + ":" + "weapons/fighter_slash_icon");
		this.nullIcon = ir.registerIcon(Rot.MOD_ID + ":" + "weapons/32x32Null");
	}
	
}
