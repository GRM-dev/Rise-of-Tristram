package ee.rot.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRot;
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
	//A weapon that will use the player's stamina reserves to fire weak arrows
	//It has different functions with different effects
	//Will be reworked hard when a proper leveling system is created
	//See notes on this.
	private IIcon[] icons = new IIcon[2];
	
	public ItemCrossbowRepater()
	{
		setFull3D();
		setMaxDamage(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) 
	{
		/*for (int i = 0;i < textures.length; i++)
		{
			textures[i] = ir.registerIcon(Rot.MODID+":"+"relicLife_"+i);
		}*/
		icons[0] = ir.registerIcon(Rot.MODID+":"+"cbr");
		icons[1] = ir.registerIcon(Rot.MODID+":"+"cbr_overLay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 == 0)return 0xFFFFFF;
		else
		{
			switch (par1ItemStack.getItemDamage())
			{
				case 0:
					return 0xFFFFFF;
				case 1:
					return 0xFF622E;
				case 2:
					return 0x4DC9FF;
				case 3 :
					return 0xFFE263;
				default:
					return 0xFFFFFF;
			}
		}
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
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		// TODO Auto-generated method stub
		return icons[pass];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) 
	{
		if (!par3EntityPlayer.isSneaking())
		{			
			ExtendPlayerRot props = ExtendPlayerRot.get(par3EntityPlayer);
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
						entityarrow.setDamage(1.15);
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
