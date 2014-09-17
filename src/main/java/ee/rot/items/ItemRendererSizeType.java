package ee.rot.items;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ee.rot.Rot;
import ee.rot.libs.UtilityNBTHelper;

public class ItemRendererSizeType implements IItemRenderer
{		
	public ItemRendererSizeType()
	{
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type){return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){return false;}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			case EQUIPPED_FIRST_PERSON:
				renderEquippedItem(item, (EntityLivingBase) data[1], true);
				break;
			case EQUIPPED:
				renderEquippedItem(item, (EntityLivingBase) data[1], false);
				break;
			default:
		}
	}

	private void renderEquippedItem(ItemStack stack, EntityLivingBase entity, boolean firstPerson) 
	{
		GL11.glPushMatrix();
		
		String size = UtilityNBTHelper.getString(stack, Rot.MODID+"weaponSize");
		if (firstPerson) 
		{
			GL11.glScalef(1f, 1f, 1f);
		} 
		else 
		{
			String type = UtilityNBTHelper.getString(stack, Rot.MODID+"weaponType");
			if (type.equals("slash") || type.equals("blunt"))
			{
				if (size.equals("small"))
				{					
					GL11.glTranslatef(0.11f, 0.23f, 0.05f);
					GL11.glScalef(0.75f, 0.75f, 0.90f);
				}
				else if (size.equals("big"))
				{
					GL11.glTranslatef(-0.38f, 0.095f, 0.05f);
					GL11.glScalef(1.35f, 1.35f, 1.1f);
				}
				else if (size.equals("large"))
				{
					GL11.glTranslatef(-0.68f, 0.045f, 0.05f);
					GL11.glScalef(1.65f, 1.65f, 1.3f);
				}
				else if (size.equals("great"))
				{
					GL11.glTranslatef(-1f, -0.05f, 0.05f);
					GL11.glScalef(2f, 2f, 1.45f);
				}
				else
				{
					GL11.glTranslatef(-0.1f, 0.095f, 0.05f);
					GL11.glScalef(1f, 1f, 1.2f);
				}
			}
			else if(type.equals("hack"))
			{
				if (size.equals("small"))
				{					
					GL11.glTranslatef(-0.34f, 0.23f, 0.05f);
					GL11.glScalef(1.1f, 1.1f, 1.3f);
				}
				else if (size.equals("big"))
				{
					GL11.glTranslatef(-0.58f, 0.095f, 0.05f);
					GL11.glScalef(1.55f, 1.55f, 1.6f);
				}
				else if (size.equals("large"))
				{
					GL11.glTranslatef(-0.98f, 0.045f, 0.05f);
					GL11.glScalef(1.95f, 1.95f, 1.7f);
				}
				else if (size.equals("great"))
				{
					GL11.glTranslatef(-1.3f, -0.05f, 0.05f);
					GL11.glScalef(2.4f, 2.4f, 1.8f);
				}
				else
				{
					GL11.glTranslatef(-0.42f, 0.13f, 0.05f);
					GL11.glScalef(1.3f, 1.3f, 1.5f);
				}
			}			
			else if (type.equals("pierce"))
			{
				if (size.equals("big"))
				{
					GL11.glTranslatef(-0.18f, -0.29f, 0);
					GL11.glScalef(1.55f, 1.55f, 1.6f);
				}
				else if (size.equals("large"))
				{
					GL11.glTranslatef(-0.68f, -0.20f, 0);
					GL11.glScalef(1.95f, 1.95f, 1.7f);
				}
				else if (size.equals("great"))
				{
					GL11.glTranslatef(-0.95f, -0.45f, 0f);
					GL11.glScalef(2.4f, 2.4f, 1.8f);
				}
				else
				{
					GL11.glTranslatef(-0.09f, -0.15f, 0f);
					GL11.glScalef(1.3f, 1.3f, 1.5f);
				}
			}
			else if (type.equals("staff"))
			{
				if (firstPerson) 
				{
					GL11.glTranslatef(-0.25F, -0.125F, 0);
					GL11.glScalef(1.25f, 1.25f, 1.25f);
				} 
				else 
				{
					GL11.glTranslatef(-0.2f, -0.45f, 0);
					GL11.glScalef(1.55f, 1.55f, 1.25f);
				}
			}
		}
		
		
		IIcon icon = stack.getItem().getIcon(stack, 0);
		Tessellator tessellator = Tessellator.instance;
		ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		GL11.glPopMatrix();//Pop the render matrix back in with changes to be used.
	}
}
