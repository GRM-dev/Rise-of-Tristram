package ee.rot.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;
import ee.rot.UtilityNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMana extends Item
{
	private float manaRestore = 15;
	private float maxMana = 75;
	private IIcon[] icons = new IIcon[3];
	private String[] names = new String[]{"manaCrystal", "manaShard","manaFoci"};
	
	public ItemMana() 
	{
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(4);
	}

	/*@Override
	public void onUpdate(ItemStack item, World world,
			Entity entity, int par4, boolean par5)
	{
		int type = item.getItemDamage();
		if (type == 2)
		{
			float currentMana =	UtilityNBTHelper.getFloat(item, Rot.MODID+"itemMana");
			if (entity instanceof EntityPlayer)
			{
				ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get((EntityPlayer)entity);
				if (!props.needsMana())
				{
					if (currentMana < maxMana)
					{
						currentMana += 10f / (3* 60 * 10);
						System.out.println("currentMana added: "+currentMana);
						UtilityNBTHelper.setFloat(item, Rot.MODID+"itemMana", currentMana);
					}					
				}
				else
				{
					if (currentMana >= 1)
					{
						props.regenMana(1);
						currentMana -= 1;
						UtilityNBTHelper.setFloat(item, Rot.MODID+"itemMana", currentMana);
					}
				}
			}			
		}
	}*/
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) 
	{
		int type = item.getItemDamage();
		ExtendPlayerRot props = ExtendPlayerRot.get(player);
		switch (type)
		{
			case 0 :
				props.regenMana(manaRestore * 4);					
				--item.stackSize;
				break;
			case 1 :
				props.regenMana(manaRestore);
				--item.stackSize;
				break;
		}		
		return item;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) 
	{
		icons[0] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"manaCrystal");
		icons[1] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"manaShard");
		icons[2] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"manaFoci");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) 
	{
		switch (par1)
		{
			case 0:return icons[0];
			case 1:return icons[1];
			case 2:return icons[2];
			default:return icons[0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) 
	{
		for (int i = 0; i < icons.length;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}	
	
	@Override
	public String getUnlocalizedName(ItemStack item) 
	{		
		//System.out.println(getUnlocalizedName() + "." + names[getDamage(item)]);
		return getUnlocalizedName() + "." + names[getDamage(item)];		
	}
}
