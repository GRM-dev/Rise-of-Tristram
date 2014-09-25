package ca.grm.rot.items;

import ca.grm.rot.Rot;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorCustom extends ItemArmor {
	
	private String	texturePath	= Rot.MODID + ":textures/model/armor/";

	public ItemArmorCustom(ItemArmor.ArmorMaterial mat, int wornSlot, String type) {
		super(mat, 0, wornSlot);
		this.setMaxStackSize(1);
		this.setTextureName(type, wornSlot);
		Rot.proxy.addArmor(type);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return this.texturePath;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.itemIcon = ir.registerIcon(Rot.MODID
				+ ":"
				+ this.getUnlocalizedName().substring(
						this.getUnlocalizedName().indexOf('.') + 1));
		super.registerIcons(ir);
	}

	private void setTextureName(String type, int armorPart) {
		if (armorPart != 2) {
			this.texturePath += type + "_layer_1.png";
		} else {
			this.texturePath += type + "_layer_2.png";
		}
	}
}
