package ee.rot.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.UtilityNBTHelper;
import ee.rot.libs.WeaponsNBTKeyNames;

public class WeaponSlash extends WeaponCustom
{
	public static int numOfTypes = 7;
	IIcon[] blades = new IIcon[numOfTypes];
	IIcon[] guards = new IIcon[numOfTypes];
	IIcon[] handles = new IIcon[numOfTypes];
	IIcon defaultIcon;
	public WeaponSlash(ToolMaterial mat) 
	{
		super(mat);
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		UtilityNBTHelper.setString(par1ItemStack, WeaponsNBTKeyNames.type, "slash");
		UtilityNBTHelper.setString(par1ItemStack, WeaponsNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, WeaponsNBTKeyNames.bladeHead, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, WeaponsNBTKeyNames.guard, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, WeaponsNBTKeyNames.handle, 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		for (int i = 0; i < numOfTypes; i++)
		{
			blades[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/blade_"+i);
			guards[i] = ir.registerIcon(Rot.MODID+":"+"weapons/guards/guard_"+i);
			handles[i] = ir.registerIcon(Rot.MODID+":"+"weapons/handles/handle_"+i);
		}
		defaultIcon = ir.registerIcon(Rot.MODID+":"+"weapons/fighter_slash_icon");
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		switch(pass)
		{
			case 0: 
				return handles[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.handle)];
			case 1:
				return blades[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.bladeHead)];
			case 2:
				return guards[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.guard)];
			default:
				break;
		}
		return defaultIcon;
	}
	
	public IIcon[] getIcons(ItemStack stack)
	{
		return new IIcon[]{
				handles[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.handle)],
				blades[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.bladeHead)],
				guards[UtilityNBTHelper.getInt(stack, WeaponsNBTKeyNames.guard)]};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_,
			List p_150895_3_)
	{
		ItemStack[] swords = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++)
		{
			swords[i] = new ItemStack(p_150895_1_,1,0);
			UtilityNBTHelper.setString(swords[i], WeaponsNBTKeyNames.type, "slash");
			UtilityNBTHelper.setString(swords[i], WeaponsNBTKeyNames.size, "normal");
			UtilityNBTHelper.setInteger(swords[i], WeaponsNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(swords[i], WeaponsNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(swords[i], WeaponsNBTKeyNames.guard, i);
			p_150895_3_.add(swords[i]);
		}
	}

}
