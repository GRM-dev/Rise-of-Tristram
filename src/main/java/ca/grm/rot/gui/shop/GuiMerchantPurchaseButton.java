package ca.grm.rot.gui.shop;

import ca.grm.rot.Rot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiMerchantPurchaseButton extends GuiButton{
	private static final ResourceLocation guiButtons = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/guiButtons.png");

	public GuiMerchantPurchaseButton(int buttonId, int x, int y, String buttonText)
	{
		super(buttonId, x, y, buttonText);
		this.width = 90;
		this.height = 20;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.visible)
		{
			FontRenderer fontrenderer = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(guiButtons);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int k = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			int locationUVx = 0;
			int locationUVy = 36; 
			if (this.mousePressed(mc, mouseX, mouseY)) // TODO Doesn't seem to be working properly?
			{
				locationUVx = 0;
				locationUVy = 56;
			}
			else if (this.hovered)
			{
				locationUVx = 0;
				locationUVy = 16;
			}
			this.drawTexturedModalRect(this.xPosition, this.yPosition, locationUVx, locationUVy,
					90, 20);

			this.mouseDragged(mc, mouseX, mouseY);
			int l = 14737632;

			if (packedFGColour != 0)
			{
				l = packedFGColour;
			}
			else if (!this.enabled)
			{
				l = 10526880;
			}
			else if (this.hovered)
			{
				l = 16777120;
			}

			this.drawCenteredString(fontrenderer, this.displayString,
					this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
		}
	}
}
