package ee.rot.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.UtilityNBTHelper;
import ee.rot.libs.WeaponsNBTKeyNames;

public class WeaponStaff extends WeaponCustom
{
	public static int numOfTypes = 2;
	IIcon[] blades = new IIcon[numOfTypes];
	IIcon[] guards = new IIcon[numOfTypes];
	IIcon[] handles = new IIcon[numOfTypes];
	IIcon defaultIcon;
	
	public WeaponStaff()
	{
		super(ToolMaterial.WOOD);
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		UtilityNBTHelper.setString(par1ItemStack, WeaponsNBTKeyNames.type, "staff");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		
		EntityArrow a = new EntityArrow(par2World, par3EntityPlayer, 1.0f);
		EntityFireball fb = new EntityFireball(par2World, a.posX, a.posY, a.posZ, a.motionX, a.motionY, a.motionZ) {
			
			@Override
			protected void onImpact(MovingObjectPosition var1)
			{
				if (var1.entityHit != shootingEntity)
				{
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
							2.5f, false);
					this.setDead();
				}
			}
		};
		fb.shootingEntity = par3EntityPlayer;
		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(fb);
		}
		
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		for (int i = 0; i < numOfTypes; i++)
		{
			blades[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/staff_"+i);
			guards[i] = ir.registerIcon(Rot.MODID+":"+"weapons/guards/cradle_"+i);
			handles[i] = ir.registerIcon(Rot.MODID+":"+"weapons/handles/rod_"+i);
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
		ItemStack[] staves = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++)
		{
			staves[i] = new ItemStack(p_150895_1_,1,0);
			UtilityNBTHelper.setString(staves[i], WeaponsNBTKeyNames.type, "staff");
			UtilityNBTHelper.setInteger(staves[i], WeaponsNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(staves[i], WeaponsNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(staves[i], WeaponsNBTKeyNames.guard, i);
			p_150895_3_.add(staves[i]);
		}
	}
}
