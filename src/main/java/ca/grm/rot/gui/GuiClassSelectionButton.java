package ca.grm.rot.gui;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityFunctions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiClassSelectionButton extends GuiButton
{
	private int type = 0;
	private int dim = 8;
	private static final ResourceLocation guiButtons = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/guiButtons.png");

	public GuiClassSelectionButton(int buttonId, int x, int y, String buttonText, int type)
	{
		super(buttonId, x, y, buttonText);
		this.type = type;
		this.width = dim;
		this.height = dim;
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
			int locationUVx = dim * type;
			int locationUVy = dim * (hovered ? 1:0);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, locationUVx, locationUVy,
					this.dim, this.dim);

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
