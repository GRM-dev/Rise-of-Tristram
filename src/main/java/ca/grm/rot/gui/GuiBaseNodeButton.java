package ca.grm.rot.gui;

import ca.grm.rot.libs.UtilityFunctions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiBaseNodeButton extends GuiButton {

	public int x,y,z;
	public Block block;
	private int color = 0xFFFFFFFF;
	
	public GuiBaseNodeButton(int buttonId, int x, int y, int widthIn,
			int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) 
	{
		if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            //mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            /*this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);*/
            for (int i = 0; i < UtilityFunctions.blockTypeColors.length;i++)
            {
            	if (block.equals(UtilityFunctions.blockTypeObjects[i]))
            	{
            		color = UtilityFunctions.blockTypeColors[i];
            		break;
            	}
            }
            drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, 0xff000000);
            drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + (width - 2), this.yPosition + (height - 2), color);
           // System.out.println(block.getBlockColor() + ":"+block.getLocalizedName());
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

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
	}

}
