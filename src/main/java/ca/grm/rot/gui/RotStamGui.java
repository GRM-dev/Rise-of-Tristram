package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.ExtendPlayer;

@SideOnly(Side.CLIENT)
public class RotStamGui extends Gui {
	private Minecraft						mc;
	private static final ResourceLocation	texturepath	= new ResourceLocation(
																Rot.MOD_ID
																		+ ":textures/gui/stam_bar_final.png");

	private int								barW		= 55, barH = 9;
	
	public RotStamGui(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent(
			priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if (event.isCancelable() || (event.type != ElementType.EXPERIENCE)) { return; }
		
		ExtendPlayer props = ExtendPlayer.get(this.mc.thePlayer);
		if ((props == null) || (props.getMaxStam() == 0)) { return; }
		
		int xPos = 3 + this.barW;
		int yPos = 3; // + (barH * 2) + 2;
		this.mc.getTextureManager().bindTexture(texturepath);
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
		
		int stambarwidth = (int) (((float) props.getCurrentStam() / props.getMaxStam()) * this.barW);

		drawTexturedModalRect(xPos, yPos, 0, this.barH, stambarwidth, this.barH);
		String s = (int) props.getCurrentStam() + "/" + (int) props.getMaxStam();

		yPos += this.barH + 2;
		this.mc.fontRendererObj.drawString(s, xPos + 1, yPos, 0);
		this.mc.fontRendererObj.drawString(s, xPos - 1, yPos, 0);
		this.mc.fontRendererObj.drawString(s, xPos, yPos + 1, 0);
		this.mc.fontRendererObj.drawString(s, xPos, yPos - 1, 0);
		this.mc.fontRendererObj.drawString(s, xPos, yPos, 0xdbdd15);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
	}
}
