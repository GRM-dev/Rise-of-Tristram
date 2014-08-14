package ee.rot.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.Rot;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMana extends Item
{
	
	private IIcon[] icons = new IIcon[3];
	private String[] names = new String[]{"manaCrystal", "manaShard","manaFoci"};
	
	public ItemMana() 
	{
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(4);
	}

	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) 
	{
		int type = item.getItemDamage();
		ExtendPlayerRotManaStam props = ExtendPlayerRotManaStam.get(player);
		if (type == 0)
		{
			props.regenMana(40);					
			--item.stackSize;
		}
		else if (type == 1)
		{
			props.regenMana(10);
			--item.stackSize;
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
