package ca.grm.rot.items;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;

public class ItemRendererSizeable implements IItemRenderer {
	private float	scale	= 0;

	public ItemRendererSizeable() {}

	public ItemRendererSizeable(float scale) {
		this.scale = scale;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return (type == ItemRenderType.EQUIPPED)
				|| (type == ItemRenderType.EQUIPPED_FIRST_PERSON);
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case EQUIPPED_FIRST_PERSON :
				renderEquippedItem(item, (EntityLivingBase) data[1], true);
				break;
			case EQUIPPED :
				renderEquippedItem(item, (EntityLivingBase) data[1], false);
				break;
			default :
		}
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}
	
	private void renderEquippedItem(ItemStack stack, EntityLivingBase entity,
			boolean firstPerson) {
		GL11.glPushMatrix(); // Push the render matrix out
		// Proceed to make alterations
		float f = this.scale == 0 ? 1f : 1 + this.scale;
		if (firstPerson) {
			f = this.scale < 0 ? f : 1 + (this.scale / 2);
		} else {
			if (this.scale < 0) {
				if (UtilityNBTHelper.getBoolean(stack, Rot.MODID + "rFlip")) {
					GL11.glRotatef(-170, 0, 0, 0);
					GL11.glTranslatef(-0.55F - f, 1.625F * this.scale, 0.05F * this.scale);
				} else {
					GL11.glTranslatef(1.0F - f, -0.125F * this.scale, 0.05F * this.scale);
				}
			} else {
				GL11.glTranslatef(1.0F - f, -0.125F * this.scale, 0.05F * this.scale);
			}
		}
		GL11.glScalef(f, f, f);

		IIcon icon = stack.getItem().getIcon(stack, 0);
		Tessellator tessellator = Tessellator.instance;
		ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(),
				icon.getMinU(), icon.getMaxV(), icon.getIconWidth(),
				icon.getIconHeight(), 0.0625F);
		GL11.glPopMatrix();// Pop the render matrix back in with changes to be
							// used.
	}
}
