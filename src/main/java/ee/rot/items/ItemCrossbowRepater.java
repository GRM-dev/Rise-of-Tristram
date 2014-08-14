package ee.rot.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.Rot;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.BiomeEvent.GetWaterColor;

public class ItemCrossbowRepater extends Item
{
	private IIcon[] icons = new IIcon[4];
	
	public ItemCrossbowRepater()
	{
		setFull3D();
		setMaxDamage(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) 
	{
		for (int i = 0;i < icons.length; i++)
		{
			icons[i] = ir.registerIcon(Rot.MODID+":"+"crossbow_repeater_"+i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) 
	{
		return icons[par1];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) 
	{
		if (!par3EntityPlayer.isSneaking())
		{			
			ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(par3EntityPlayer);
			EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, 2.5f);
			switch (par1ItemStack.getItemDamage())
			{
				case 0://Normal	
					if (props.consumeStam(1.5f))
					{				
						entityarrow.canBePickedUp = 2;
						entityarrow.setDamage(.75);
						if (!par2World.isRemote)
			            {
			                par2World.spawnEntityInWorld(entityarrow);
			            }
					}
					else par1ItemStack.setItemDamage(0);
					break;
				case 1://Fire	
					if (props.consumeStam(2.5f))
					{				
						entityarrow.canBePickedUp = 2;
						entityarrow.setDamage(.75);
						entityarrow.setFire(15);
						if (!par2World.isRemote)
			            {
			                par2World.spawnEntityInWorld(entityarrow);
			            }
					}
					else par1ItemStack.setItemDamage(0);
					break;
				case 2://Knockback	
					if (props.consumeStam(3.5f))
					{				
						entityarrow.canBePickedUp = 2;
						entityarrow.setDamage(.75);
						entityarrow.setKnockbackStrength(2);
						if (!par2World.isRemote)
			            {
			                par2World.spawnEntityInWorld(entityarrow);
			            }
					}
					else par1ItemStack.setItemDamage(0);
					break;
				case 3://Damage
					if (props.consumeStam(5.5f))
					{				
						entityarrow.canBePickedUp = 2;
						entityarrow.setDamage(2.75);
						entityarrow.setIsCritical(true);
						if (!par2World.isRemote)
			            {
			                par2World.spawnEntityInWorld(entityarrow);
			            }
					}
					else par1ItemStack.setItemDamage(0);
					break;
			}
			
		}		
		else
		{			
			if (par1ItemStack.getItemDamage() > 0)par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()-1);
			else par1ItemStack.setItemDamage(3);
		}
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
}
