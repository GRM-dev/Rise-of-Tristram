package ee.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;

@SideOnly(Side.CLIENT)
public class RotStamGui extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation texturepath = new ResourceLocation(Rot.MODID +":textures/gui/stam_bar_final.png");
	
	private int barW = 55, barH = 9;

	public RotStamGui(Minecraft mc)
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
		if (props == null || props.getMaxStam() == 0)
		{
			return;
		}

		int xPos = 3 + barW;
		int yPos = 3; //+ (barH * 2) + 2;
		mc.getTextureManager().bindTexture(texturepath);
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

		int stambarwidth = (int) (((float) props.getCurrentStam() / props.getMaxStam()) * barW);
		
		drawTexturedModalRect(xPos, yPos, 0, barH, stambarwidth, barH);
		String s = (int)props.getCurrentStam() + "/" + (int)props.getMaxStam();
		
		yPos += barH + 2;
		mc.fontRenderer.drawString(s, xPos + 1, yPos, 0);
		mc.fontRenderer.drawString(s, xPos - 1, yPos, 0);
		mc.fontRenderer.drawString(s, xPos, yPos + 1, 0);
		mc.fontRenderer.drawString(s, xPos, yPos - 1, 0);
		mc.fontRenderer.drawString(s, xPos, yPos, 0xdbdd15);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
	}
}
