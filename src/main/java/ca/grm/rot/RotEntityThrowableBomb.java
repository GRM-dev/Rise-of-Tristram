package ca.grm.rot;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class RotEntityThrowableBomb extends EntityThrowable {
	public RotEntityThrowableBomb(World par1World) {
		super(par1World);
	}

	public RotEntityThrowableBomb(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}
	
	public RotEntityThrowableBomb(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if (!this.worldObj.isRemote) {
			// this.worldObj.createExplosion(this, this.posX, this.posY,
			// this.posZ, 1.5F, true);
			// this.worldObj.spawnEntityInWorld(new EntityVillager(worldObj));
		}
	}
	
}
