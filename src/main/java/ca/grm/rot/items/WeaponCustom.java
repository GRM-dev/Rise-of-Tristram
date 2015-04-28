package ca.grm.rot.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityNBTKeyNames;

public class WeaponCustom extends ItemSword {
	
	private int	numberOfLayers = 6;
	private int numberOfTypes;

	public WeaponCustom(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		String size = UtilityNBTHelper.getString(par1ItemStack,
				UtilityNBTKeyNames.size);
		String name = (String) par3List.get(0);
		if ((size != "normal") && (size != "")) {
			par3List.set(0, size.substring(0, 1).toUpperCase() + size.substring(1) + " " + name);
		}
	}
	
	public IIcon[] getIcons(ItemStack stack)
	{
		return null;
	}
	

	public int[] getLayerColors(ItemStack is) {
		int[] colorList = new int[this.numberOfLayers];
		for (int c = 0; c < this.numberOfLayers; c++) {
			int color = UtilityNBTHelper.getInt(is, UtilityNBTKeyNames.layerColor
					+ c);
			colorList[c] = (color == 0 ? 0xFFFFFF : color);
		}
		return colorList;
	}	
	
	public int getNumberOfTypes()
	{
		return numberOfTypes;
	}
	
	public void setNumberOfTypes(int num)
	{
		numberOfTypes = num;
	}
}
