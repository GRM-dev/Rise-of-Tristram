package ee.rot.items;

import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemWandManaSteal extends ItemSword
{

	private IIcon[] textures;
	private int color;
	private int c;
	
	public ItemWandManaSteal(ToolMaterial mat) 
	{
		super(mat);
		this.color = 0xffffff;
		this.c = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 == 0)
		{
			color = 0xFF0000;
		}
		else if (par2 == 1)
		{
			color = 0x00FFFF;
		}
		else if (par2 == 2)
		{
			color = 0xFFFFFF;
		}
		else color = 0xFFFFFF;
		return color;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata)
	{
		return 3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		textures = new IIcon[3];
		textures[0] = ir.registerIcon(Rot.MODID+":"+"soul_sword");
		textures[1] = ir.registerIcon(Rot.MODID+":"+"wandMana");
		textures[2] = ir.registerIcon(Rot.MODID+":"+"relicRepair");
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		/*if (pass == 0)
		{
			color = 0xFF0000;
		}
		else if (pass == 1)
		{
			color = 0x00FF00;
		}
		else if (pass == 2)
		{
			color = 0x0000FF;
		}
		else color = 0xFFFFFF;*/
		return textures[pass];
	}
}
