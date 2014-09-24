package ee.rot.items;

import ee.rot.libs.UtilityNBTHelper;
import ee.rot.libs.WeaponsNBTKeyNames;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

public class WeaponCustom extends ItemSword
{

	public WeaponCustom(ToolMaterial p_i45356_1_)
	{
		super(p_i45356_1_);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		String size = UtilityNBTHelper.getString(par1ItemStack, WeaponsNBTKeyNames.size);
		if (size == "normal" || size == "")
		{
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
		}
		else
		{
			return (size.substring(0, 1).toUpperCase() + size.substring(1) + " " + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
		}
		
	}

}
