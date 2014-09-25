package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.ExtendPlayer;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiExtendedPlayerStats extends Gui {
	private Minecraft						mc;
	private static final ResourceLocation	manaTexture		= new ResourceLocation(
																	Rot.MODID
																			+ ":textures/gui/mana_bar_final.png");
	private static final ResourceLocation	staminaTexture	= new ResourceLocation(
																	Rot.MODID
																			+ ":textures/gui/stam_bar_final.png");
	
	private int								barW			= 55, barH = 9;
	
	public GuiExtendedPlayerStats(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent(
			priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if (event.isCancelable() || (event.type != ElementType.EXPERIENCE)) { return; }
		
		ExtendPlayer props = ExtendPlayer.get(this.mc.thePlayer);
		if ((props == null) || (props.getMaxStam() == 0) || (props.getMaxMana() == 0)) { return; }

		// Draw Mana Bar
		int xPos = 3;
		int yPos = 3; // + (barH * 2) + 2;
		this.mc.getTextureManager().bindTexture(manaTexture);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		/**
		 * Draw the background bar which contains a transparent section; note
		 * the new size
		 */
		drawTexturedModalRect(xPos, yPos, 0, 0, this.barW, this.barH);
		
		int currentbarwidth = MathHelper
				.clamp_int(
						(int) (((float) props.getCurrentMana() / props.getMaxMana()) * this.barW),
						0, this.barW);

		drawTexturedModalRect(xPos, yPos, 0, this.barH, currentbarwidth, this.barH);
		String s = (int) props.getCurrentMana() + "/" + (int) props.getMaxMana();
		drawText(s, xPos + 2, yPos + this.barH + 2, 0x0097d9);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);

		// Draw Stamina Bar
		this.mc.getTextureManager().bindTexture(staminaTexture);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		drawTexturedModalRect(xPos + this.barW + 1, yPos, 0, 0, this.barW, this.barH);
		
		currentbarwidth = MathHelper
				.clamp_int(
						(int) (((float) props.getCurrentStam() / props.getMaxStam()) * this.barW),
						0, this.barW);

		drawTexturedModalRect(xPos + this.barW, yPos, 0, this.barH, currentbarwidth,
				this.barH);
		s = (int) props.getCurrentStam() + "/" + (int) props.getMaxStam();
		drawText(s, xPos + this.barW + 2, yPos + this.barH + 2, 0xdbdd15);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
	}

	private void drawText(String text, int x, int y, int hexColor) {
		this.mc.fontRenderer.drawString(text, x + 1, y, 0);
		this.mc.fontRenderer.drawString(text, x - 1, y, 0);
		this.mc.fontRenderer.drawString(text, x, y + 1, 0);
		this.mc.fontRenderer.drawString(text, x, y - 1, 0);
		this.mc.fontRenderer.drawString(text, x, y, hexColor);
	}
}
