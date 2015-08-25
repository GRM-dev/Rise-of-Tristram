package ca.grm.rot.gui.skills;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.managers.RotClassManager;

public class GuiClassProfessionInfo extends GuiContainer{
	public static final ResourceLocation texture = new ResourceLocation(Rot.MOD_ID.toLowerCase(),
			"textures/gui/base.png");

	private EntityPlayer player;
	private int pad = 4;
	private int ch = 20;
	
	public GuiClassProfessionInfo(EntityPlayer player)
	{
		super(null);

		this.player = player;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{

		/*
		 * TextureManager manager = Minecraft.getMinecraft().renderEngine;
		 * manager.bindTexture(manager.getResourceLocation(1)); //RENDER ITEMS
		 * drawTexturedModelRectFromIcon(300, 300,
		 * RotItems.itemGunpowderInfuser.getIconFromDamage(0), 16, 16);
		 */

		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		this.buttonList.clear();
		// Start with main control buttons

		int x1 = this.guiLeft + this.pad;
		int x2 = (this.guiLeft + this.xSize) - (8 + this.pad);
		int x3 = (x2 + x1) / 2;

		int textY = this.guiTop + this.pad;
		int textX = (this.guiLeft + this.xSize) + pad;

		this.drawString(this.fontRendererObj, "Current Class:", textX, textY, 0xdbdd15);
		textY += 15;
		this.drawString(this.fontRendererObj, ExtendPlayer.get(this.player).getCurrentClassName(),
				textX, textY, 0xdbdd15);
		textY += 15;
		this.drawString(this.fontRendererObj,
				"Str: " + ExtendPlayer.get(this.player).getStrength(), textX, textY, ExtendPlayer
						.get(this.player).getStrength() == 0 ? 0xFFFFFF : (ExtendPlayer.get(
						this.player).getStrength() > 0 ? 0x00ff42 : 0xff0c00));
		textY += 15;
		this.drawString(this.fontRendererObj,
				"Vit: " + ExtendPlayer.get(this.player).getVitality(), textX, textY, ExtendPlayer
						.get(this.player).getVitality() == 0 ? 0xFFFFFF : (ExtendPlayer.get(
						this.player).getVitality() > 0 ? 0x00ff42 : 0xff0c00));
		textY += 15;
		this.drawString(this.fontRendererObj, "Dex: " + ExtendPlayer.get(this.player)
				.getDexterity(), textX, textY,
				ExtendPlayer.get(this.player).getDexterity() == 0 ? 0xFFFFFF : (ExtendPlayer.get(
						this.player).getDexterity() > 0 ? 0x00ff42 : 0xff0c00));
		textY += 15;
		this.drawString(this.fontRendererObj, "Agi: " + ExtendPlayer.get(this.player).getAgility(),
				textX, textY,
				ExtendPlayer.get(this.player).getAgility() == 0 ? 0xFFFFFF : (ExtendPlayer.get(
						this.player).getAgility() > 0 ? 0x00ff42 : 0xff0c00));
		textY += 15;
		this.drawString(this.fontRendererObj, "Int: " + ExtendPlayer.get(this.player)
				.getIntelligence(), textX, textY,
				ExtendPlayer.get(this.player).getIntelligence() == 0 ? 0xFFFFFF : (ExtendPlayer
						.get(this.player).getIntelligence() > 0 ? 0x00ff42 : 0xff0c00));
		textY += 15;
		this.drawString(this.fontRendererObj, "Current Profession:", textX, textY, 0xdbdd15);
		textY += 15;
		this.drawString(this.fontRendererObj, ExtendPlayer.get(this.player)
				.getCurrentProfessionName(), textX, textY, 0xdbdd15);

		// Visual information on location
		/*
		 * this.drawString(fontRendererObj,
		 * "X: "+(xOffset+te.xCoord)+" offSet: "+xOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) -
		 * ch, 0xFFFFFF); this.drawString(fontRendererObj,
		 * "Y: "+(yOffset+te.yCoord)+" offSet: "+yOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4),
		 * 0xFFFFFF); this.drawString(fontRendererObj,
		 * "Z: "+(zOffset+te.zCoord)+" offSet: "+zOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) +
		 * ch, 0xFFFFFF);
		 */
	}
}
