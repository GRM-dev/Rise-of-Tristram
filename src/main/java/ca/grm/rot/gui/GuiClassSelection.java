package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.libs.ExtendPlayer;

public class GuiClassSelection extends GuiContainer {
	public static final ResourceLocation	texture			= new ResourceLocation(
																	Rot.MOD_ID
																			.toLowerCase(),
																	"textures/gui/base.png");

	private EntityPlayer					player;
	private int								pad				= 4;
	private int								ch				= 20;
	private int								selectedClass	= 0;
	
	public GuiClassSelection(EntityPlayer player) {
		super(new ContainerNull());

		this.player = player;
		this.xSize = 176;
		this.ySize = 166;
		this.selectedClass = ExtendPlayer.get(player).getCurrentClassIndex() != -1
				? ExtendPlayer.get(player).getCurrentClassIndex() : 0;
	}

	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0 : // Class Forward
				this.selectedClass++;
				this.selectedClass = this.selectedClass == ExtendPlayer.classNames.length
						? 1 : this.selectedClass;
				break;
			case 1 : // Class Back
				this.selectedClass--;
				this.selectedClass = this.selectedClass == 0
						? ExtendPlayer.classNames.length - 1 : this.selectedClass;
				break;
			case 2 : // Change Class
				Rot.net.sendToServer(new ClassRequestPacket(selectedClass));
				break;
			case 3 : Rot.net.sendToServer(new ClassRequestPacket(0)); break;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		
		/*
		 * TextureManager manager = Minecraft.getMinecraft().renderEngine;
		 * manager.bindTexture(manager.getResourceLocation(1));
		 * //RENDER ITEMS
		 * drawTexturedModelRectFromIcon(300, 300,
		 * RotItems.itemGunpowderInfuser.getIconFromDamage(0), 16, 16);
		 */
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.buttonList.clear();
		// Start with main control buttons

		int x1 = this.guiLeft + this.pad;
		int x2 = (this.guiLeft + this.xSize) - (50 + this.pad);
		int x3 = (x2 + x1) / 2;

		this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad, 50, this.ch,
				"Class ->"));
		this.drawString(this.fontRendererObj,
				ExtendPlayer.classNames[this.selectedClass], x3, this.guiTop + this.pad
						+ 5, 0xddeeee);
		this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad, 50, this.ch,
				"<- Class"));
		this.buttonList.add(new GuiButton(2, x1, this.guiTop + this.pad + 20, 50,
				this.ch, "Pick"));
		this.buttonList.add(new GuiButton(3, x2, this.guiTop + this.pad + 20, 50,
				this.ch, "Refresh"));

		this.drawString(this.fontRendererObj,
				"Current Class: " + ExtendPlayer.get(this.player).getCurrentClassName(),
				this.guiLeft + this.pad, this.guiTop + this.pad + 45, 0xdbdd15);
		this.drawString(this.fontRendererObj, "Str: "
				+ ExtendPlayer.get(this.player).getStrength(), this.guiLeft + this.pad,
				this.guiTop + this.pad + 60,
				ExtendPlayer.get(this.player).getStrength() == 0 ? 0xFFFFFF
						: (ExtendPlayer.get(this.player).getStrength() > 0 ? 0x00ff42
								: 0xff0c00));
		this.drawString(this.fontRendererObj, "Vit: "
				+ ExtendPlayer.get(this.player).getVitality(), this.guiLeft + this.pad,
				this.guiTop + this.pad + 75,
				ExtendPlayer.get(this.player).getVitality() == 0 ? 0xFFFFFF
						: (ExtendPlayer.get(this.player).getVitality() > 0 ? 0x00ff42
								: 0xff0c00));
		this.drawString(this.fontRendererObj, "Dex: "
				+ ExtendPlayer.get(this.player).getDexterity(), this.guiLeft + this.pad,
				this.guiTop + this.pad + 90,
				ExtendPlayer.get(this.player).getDexterity() == 0 ? 0xFFFFFF
						: (ExtendPlayer.get(this.player).getDexterity() > 0 ? 0x00ff42
								: 0xff0c00));
		this.drawString(this.fontRendererObj, "Agi: "
				+ ExtendPlayer.get(this.player).getAgility(), this.guiLeft + this.pad,
				this.guiTop + this.pad + 105,
				ExtendPlayer.get(this.player).getAgility() == 0 ? 0xFFFFFF
						: (ExtendPlayer.get(this.player).getAgility() > 0 ? 0x00ff42
								: 0xff0c00));
		this.drawString(this.fontRendererObj, "Int: "
				+ ExtendPlayer.get(this.player).getIntelligence(), this.guiLeft
				+ this.pad, this.guiTop + this.pad + 120, ExtendPlayer.get(this.player)
				.getIntelligence() == 0 ? 0xFFFFFF : (ExtendPlayer.get(this.player)
				.getIntelligence() > 0 ? 0x00ff42 : 0xff0c00));
		
		// Visual information on location
		/*
		 * this.drawString(fontRendererObj,
		 * "X: "+(xOffset+te.xCoord)+" offSet: "+xOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) -
		 * ch, 0xFFFFFF);
		 * this.drawString(fontRendererObj,
		 * "Y: "+(yOffset+te.yCoord)+" offSet: "+yOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4),
		 * 0xFFFFFF);
		 * this.drawString(fontRendererObj,
		 * "Z: "+(zOffset+te.zCoord)+" offSet: "+zOffset, (startLeft +
		 * ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) +
		 * ch, 0xFFFFFF);
		 */
	}

	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2) {
		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
			this.mc.thePlayer.closeScreen();
		}
	}
}
