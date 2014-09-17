package ee.rot.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiExtendedPlayerStats extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation manaTexture = new ResourceLocation(Rot.MODID +":textures/gui/mana_bar_final.png");
	private static final ResourceLocation staminaTexture = new ResourceLocation(Rot.MODID +":textures/gui/stam_bar_final.png");	
	
	private int barW = 55, barH = 9;

	public GuiExtendedPlayerStats(Minecraft mc)
	{
		super();
		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
		{
			return;
		}
	
		ExtendPlayer props = ExtendPlayer.get(this.mc.thePlayer);
		if (props == null || props.getMaxStam() == 0 || props.getMaxMana() == 0)
		{
			return;
		}
		
		//Draw Mana Bar
		int xPos = 3;
		int yPos = 3; //+ (barH * 2) + 2;
		mc.getTextureManager().bindTexture(manaTexture);
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
		drawTexturedModalRect(xPos, yPos, 0, 0, barW, barH);

		int currentbarwidth = MathHelper.clamp_int((int) (((float) props.getCurrentMana() / props.getMaxMana()) * barW), 0, barW);
		
		drawTexturedModalRect(xPos, yPos, 0, barH, currentbarwidth, barH);
		String s = (int)props.getCurrentMana() + "/" + (int)props.getMaxMana();
		drawText(s,xPos + 2,yPos + barH + 2,0x0097d9);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		
		//Draw Stamina Bar
		mc.getTextureManager().bindTexture(staminaTexture);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		drawTexturedModalRect(xPos + barW + 1, yPos, 0, 0, barW, barH);

		currentbarwidth = MathHelper.clamp_int((int) (((float) props.getCurrentStam() / props.getMaxStam()) * barW), 0, barW);
		
		drawTexturedModalRect(xPos + barW, yPos, 0, barH, currentbarwidth, barH);
		s = (int)props.getCurrentStam() + "/" + (int)props.getMaxStam();
		drawText(s,xPos + barW + 2,yPos + barH + 2,0xdbdd15);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
	}
	
	private void drawText(String text, int x, int y, int hexColor)
	{
		mc.fontRenderer.drawString(text, x + 1, y, 0);
		mc.fontRenderer.drawString(text, x - 1, y, 0);
		mc.fontRenderer.drawString(text, x, y + 1, 0);
		mc.fontRenderer.drawString(text, x, y - 1, 0);
		mc.fontRenderer.drawString(text, x, y, hexColor);
	}
}
