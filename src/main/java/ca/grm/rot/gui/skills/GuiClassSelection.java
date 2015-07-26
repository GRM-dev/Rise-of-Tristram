package ca.grm.rot.gui.skills;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ProfessionRequestPacket;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.managers.RotClassManager;

public class GuiClassSelection extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Rot.MOD_ID.toLowerCase(),
			"textures/gui/rotGui.png");

	private EntityPlayer player;
	private int pad = 4;
	private int ch = 20;
	private int selectedClass = 0;
	private int selectedProfession = 0;

	public GuiClassSelection(EntityPlayer player)
	{
		super(new ContainerPseudoPlayer(player.inventory, player, ExtendPlayer.get(player).inventory));

		this.player = player;
		this.xSize = 176;
		this.ySize = 166;
		this.selectedClass = ExtendPlayer.get(player).getCurrentClassIndex();
		this.selectedProfession = ExtendPlayer.get(player).getCurrentProfessionIndex();
	}

	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 0: // Class Forward
			this.selectedClass++;
			this.selectedClass = this.selectedClass == RotClassManager.classes.length ? 1 : this.selectedClass;
			break;
		case 1: // Class Back
			this.selectedClass--;
			this.selectedClass = this.selectedClass <= 0 ? RotClassManager.classes.length - 1 : this.selectedClass;
			break;
		case 2: // Change Class
			Rot.net.sendToServer(new ClassRequestPacket(selectedClass));
			break;
		case 3: // Refresh Class
			Rot.net.sendToServer(new ClassRequestPacket(0));
			break;
		case 4: // Profession Forward
			this.selectedProfession++;
			this.selectedProfession = this.selectedProfession == RotClassManager.professions.length ? 1 : this.selectedProfession;
			break;
		case 5: // Profession Back
			this.selectedProfession--;
			this.selectedProfession = this.selectedProfession <= 0 ? RotClassManager.professions.length - 1 : this.selectedProfession;
			break;
		case 6: // Change Profession
			Rot.net.sendToServer(new ProfessionRequestPacket(selectedProfession));
			break;
		case 7:// Refresh Profession
			Rot.net.sendToServer(new ProfessionRequestPacket(0));
			break;
		}
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

		this.buttonList.add(new GuiClassSelectionButton(0, x2, this.guiTop + this.pad, "", 1));
		this.buttonList.add(new GuiClassSelectionButton(4, x2, this.guiTop + this.pad + (8 * 2),
				"", 1));

		this.drawString(this.fontRendererObj,
				RotClassManager.classes[this.selectedClass].className, x3,
				this.guiTop + this.pad + 5, 0xddeeee);

		this.drawString(this.fontRendererObj,
				RotClassManager.professions[this.selectedProfession].professionName, x3,
				this.guiTop + this.pad + 20, 0xddeeee);

		this.buttonList.add(new GuiClassSelectionButton(1, x2 - 8, this.guiTop + this.pad, "", 0));
		this.buttonList.add(new GuiClassSelectionButton(2, x2 - 8, this.guiTop + this.pad + 8, "",
				2));
		this.buttonList.add(new GuiClassSelectionButton(3, x2, this.guiTop + this.pad + 8, "", 3));

		this.buttonList.add(new GuiClassSelectionButton(5, x2 - 8,
				this.guiTop + this.pad + (8 * 2), "", 0));
		this.buttonList.add(new GuiClassSelectionButton(6, x2 - 8,
				this.guiTop + this.pad + (8 * 3), "", 2));
		this.buttonList.add(new GuiClassSelectionButton(7, x2, this.guiTop + this.pad + (8 * 3),
				"", 3));

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

	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			this.mc.thePlayer.closeScreen();
		}
	}
}
