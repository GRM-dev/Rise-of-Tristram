package ca.grm.rot.items;

import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityWeaponNBTKeyNames;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

public class WeaponCustom extends ItemSword {
	
	private int	numberOfColorLayers;

	public WeaponCustom(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String size = UtilityNBTHelper.getString(par1ItemStack,
				UtilityWeaponNBTKeyNames.size);
		if ((size == "normal") || (size == "")) {
			return ("" + StatCollector.translateToLocal(this
					.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
		} else {
			return (size.substring(0, 1).toUpperCase() + size.substring(1) + " " + StatCollector
					.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack)
							+ ".name")).trim();
		}

	}

	public int[] getLayerColors(ItemStack is) {
		int[] colorList = new int[this.numberOfColorLayers];
		for (int c = 0; c < this.numberOfColorLayers; c++) {
			int color = UtilityNBTHelper.getInt(is, UtilityWeaponNBTKeyNames.layerColor
					+ c);
			colorList[c] = (color == 0 ? 0xFFFFFF : color);
		}
		return colorList;
	}

	public int[] getLayerColors(ItemStack is, int numLayers) {
		int[] colorList = new int[numLayers];
		for (int c = 0; c < numLayers; c++) {
			int color = UtilityNBTHelper.getInt(is, UtilityWeaponNBTKeyNames.layerColor
					+ c);
			colorList[c] = (color == 0 ? 0xFFFFFF : color);
		}
		return colorList;
	}

	public void setNumberOfColorLayers(int num) {
		this.numberOfColorLayers = num;
	}
	
}
