package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

/**
 * Just a GuiButton with an extra variable, really wanted this in the normal
 * button
 **/
public class GuiBaseNodeButton extends GuiButton {
	public int		x, y, z;
	public IIcon	tex;
	public float	brightness	= 1.0f;
	
	public GuiBaseNodeButton(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		if (this.visible) {
			TextureManager manager = Minecraft.getMinecraft().renderEngine;
			manager.bindTexture(manager.getResourceLocation(0));
			// RENDER ITEMS
			FontRenderer fontrenderer = p_146112_1_.fontRenderer;
			GL11.glColor4f(this.brightness, this.brightness, this.brightness, 1.0F);
			this.field_146123_n = (p_146112_2_ >= this.xPosition)
					&& (p_146112_3_ >= this.yPosition)
					&& (p_146112_2_ < (this.xPosition + this.width))
					&& (p_146112_3_ < (this.yPosition + this.height));
			int k = this.getHoverState(this.field_146123_n);
			
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (this.tex != null) {
				drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, this.tex,
						16, 16);
			}
			this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
			int l = 14737632;
			
			if (this.packedFGColour != 0) {
				l = this.packedFGColour;
			} else if (!this.enabled) {
				l = 10526880;
			} else if (this.field_146123_n) {
				l = 16777120;
			}
			
			this.drawCenteredString(fontrenderer, this.displayString, this.xPosition
					+ (this.width / 2), this.yPosition + ((this.height - 8) / 2), l);
		}
	}
	
}
