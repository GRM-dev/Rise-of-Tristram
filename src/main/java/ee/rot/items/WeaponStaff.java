package ee.rot.items;

import ee.rot.Rot;
import ee.rot.libs.UtilityNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class WeaponStaff extends ItemSword
{

	public WeaponStaff()
	{
		super(ToolMaterial.WOOD);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5)
	{
		UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"weaponType", "staff");
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
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
	
}
