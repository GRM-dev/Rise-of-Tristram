package ca.grm.rot.gui.shop;

import ca.grm.rot.Rot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class GuiMerchantQuantityControlButton extends GuiButton
{
	private static final ResourceLocation guiButtons = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/guiButtons.png");
	boolean isSmall = false;
	public int buttonBoolIndex = 0;
	
	public GuiMerchantQuantityControlButton(int buttonId, int x, int y, int buttonIndex, boolean isSmaller, String displayText)
	{
		super(buttonId, x, y, displayText);
		this.isSmall = isSmaller;
		
		if (this.isSmall)
			this.width = 4;
		else
			this.width = 5;
		
		this.height = 8;
		
		this.buttonBoolIndex = buttonIndex;
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
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 32 + (isSmall ? 5:0), 8 * (hovered ? 1:0),
					isSmall ? 4:5, 8);
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

            if (this.hovered)
            	this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height), l);
        }
    }
}
