package ee.rot.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;
import ee.rot.libs.UtilityNBTHelper;
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
	private IIcon[] icons = new IIcon[6];
	private String[] names = new String[]{"manaCrystal", "manaShard","manaFoci","baseNodeList"};
	
	public ItemMana() 
	{
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(4);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5)
	{
		if (par1ItemStack.getItemDamage() == 3)
		{
			if (UtilityNBTHelper.getString(par1ItemStack, Rot.MODID+"baseNodeListCoords").trim().equals(""))
			{
				UtilityNBTHelper.setString(par1ItemStack, Rot.MODID+"baseNodeListCoords", "");
			}
		}
	}	
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) 
	{
		int type = item.getItemDamage();
		ExtendPlayer props = ExtendPlayer.get(player);
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
			case 3:
				UtilityNBTHelper.setString(item, Rot.MODID+"baseNodeListCoords", "");
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
		icons[3] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"baseNodeList");
		icons[4] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"baseNodeList_overLay");
		icons[5] = ir.registerIcon(Rot.MODID.toLowerCase()+":"+"null");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 == 0)return 0xFFFFFF;
		else
		{			
			return 0x4DC9FF;
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
		if (stack.getItemDamage() == 3)
		{
			if(pass == 0)return icons[3];
			if(pass == 1 && UtilityNBTHelper.getString(stack, Rot.MODID+"baseNodeListCoords").trim() != "")return icons[4];
		}
		else
		{
			if (pass == 0)
			{
				return icons[stack.getItemDamage()];
			}
		}
		if (pass == 1)return icons[5];
		return icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) 
	{
		for (int i = 0; i < names.length;i++)
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
