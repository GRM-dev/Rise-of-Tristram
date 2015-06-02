package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;

public class GuiExtendedPlayerStats extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation manaTexture = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/mana_bar_final.png");
	private static final ResourceLocation staminaTexture = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/stam_bar_final.png");
	private static final ResourceLocation healthTexture = new ResourceLocation(
			Rot.MOD_ID + ":textures/gui/hp_bar_final.png");

	private int barW = 55, barH = 9;

	public GuiExtendedPlayerStats(Minecraft mc)
	{
		super();
		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if (event.type == ElementType.FOOD)
		{
			event.setCanceled(true);
			return;
		}
		if (event.type == ElementType.HEALTH)
		{
			event.setCanceled(true);
			return;
		}
		if (event.isCancelable() || (event.type != ElementType.EXPERIENCE)) { return; }

		ExtendPlayer props = ExtendPlayer.get(this.mc.thePlayer);
		if ((props == null) || (props.getMaxStam() == 0) || (props.getMaxMana() == 0)) { return; }

		// Draw Mana Bar
		int xPos = 3;
		int yPos = 3; // + (barH * 2) + 2;
		this.mc.getTextureManager().bindTexture(manaTexture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(xPos, yPos, 0, 0, this.barW, this.barH);

		int currentbarwidth = MathHelper.clamp_int(
				(int) (((float) props.getCurrentMana() / props.realManaStamMaxes) * this.barW), 0,
				this.barW);

		drawTexturedModalRect(xPos, yPos, 0, this.barH, currentbarwidth, this.barH);
		String s = (int) (props.getMaxMana() * (props.getCurrentMana() / props.realManaStamMaxes)) + "/" + (int) props
				.getMaxMana();
		drawText(s, xPos + 2, yPos + this.barH + 2, 0x0097d9);
		
		// Draw gold under mana bar
		drawText("Gold: " + props.getGold(), xPos + 2, yPos + this.barH + 16, 0xdbdd15);
		// Draw Stamina Bar
		this.mc.getTextureManager().bindTexture(staminaTexture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(xPos + this.barW + 1, yPos, 0, 0, this.barW, this.barH);

		currentbarwidth = MathHelper.clamp_int(
				(int) (((float) props.getCurrentStam() / props.realManaStamMaxes) * this.barW), 0,
				this.barW);

		drawTexturedModalRect(xPos + this.barW + 1, yPos, 0, this.barH, currentbarwidth, this.barH);
		s = (int) (props.getMaxStam() * (props.getCurrentStam() / props.realManaStamMaxes)) + "/" + (int) props
				.getMaxStam();
		drawText(s, xPos + this.barW + 2, yPos + this.barH + 2, 0xdbdd15);

		// Draw Health Bar
		this.mc.getTextureManager().bindTexture(healthTexture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(xPos + (this.barW * 2) + 2, yPos, 0, 0, this.barW, this.barH);

		float adjustedHP = (this.mc.thePlayer.getMaxHealth() * 5) + props.pickedClass.baseHp + (props.pickedClass.hpPerVit * props
				.getVitality());
		float currentHPPercent = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();

		currentbarwidth = MathHelper.clamp_int((int) (currentHPPercent) * this.barW, 0, this.barW);

		drawTexturedModalRect(xPos + (this.barW * 2) + 2, yPos, 0, this.barH, currentbarwidth,
				this.barH);
		s = (int) (adjustedHP * currentHPPercent) + "/" + (int) (adjustedHP);
		drawText(s, xPos + (this.barW * 2) + 2, yPos + this.barH + 2, 0xdbdd15);

	}

	private void drawText(String text, int x, int y, int hexColor)
	{
		this.mc.fontRendererObj.drawString(text, x + 1, y, 0);
		this.mc.fontRendererObj.drawString(text, x - 1, y, 0);
		this.mc.fontRendererObj.drawString(text, x, y + 1, 0);
		this.mc.fontRendererObj.drawString(text, x, y - 1, 0);
		this.mc.fontRendererObj.drawString(text, x, y, hexColor);
	}
}
