package ee.rot.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;
import ee.rot.libs.UtilityNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class WeaponFighterSlash extends ItemSword
{

	IIcon[] textures = new IIcon[7];
	public WeaponFighterSlash(ToolMaterial mat) 
	{
		super(mat);
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"weaponType", "slash");
		UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"weaponSize", "large");
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5)
	{
		UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"weaponType", "slash");
		UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"weaponSize", "large");
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		for (int i = 0; i < textures.length; i++)
		{
			textures[i] = ir.registerIcon(Rot.MODID+":"+"weapons/fighter_slash_"+(i+1));
		}
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return textures[/*UtilityNBTHelper.getInt(stack, Rot.MODID+"textureIndex")*/5];
	}

}
