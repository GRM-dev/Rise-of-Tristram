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
import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;

@SideOnly(Side.CLIENT)
public class RotManaGui extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation texturepath = new ResourceLocation(Rot.MODID +":textures/gui/mana_bar.png"/*"tutorial", "textures/gui/mana_bar.png"*/);
	
	private int barWidth = 50;

	//I left all the comments in "RotStamGui"
	public RotManaGui(Minecraft mc)
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
		ExtendPlayerRot props = ExtendPlayerRot.get(this.mc.thePlayer);

		if (props == null || props.getMaxMana() == 0)
		{
			return;
		}
		int xPos = 2;
		int yPos = 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.getTextureManager().bindTexture(texturepath);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, barWidth, 4);
		int manabarwidth = (int)(((float) props.getCurrentMana() / props.getMaxMana()) * barWidth);
		this.drawTexturedModalRect(xPos, yPos + 1, 0, 4, manabarwidth, 2);
	}
}
