package ca.grm.rot.items;

import net.minecraft.item.ItemArmor;

public class RotArmors extends ItemArmor{

	public RotArmors(String unlocalizedName, ArmorMaterial material, int renderIndex, int armorType) {
        super(material, renderIndex, armorType);

        this.setUnlocalizedName(unlocalizedName);
    }
	
}
