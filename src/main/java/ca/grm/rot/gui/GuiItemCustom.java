package ca.grm.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.CustomItemPacket;
import ca.grm.rot.items.WeaponBlunt;
import ca.grm.rot.items.WeaponCustom;
import ca.grm.rot.items.WeaponHack;
import ca.grm.rot.items.WeaponPierce;
import ca.grm.rot.items.WeaponSlash;
import ca.grm.rot.items.WeaponStaff;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityNBTKeyNames;

public class GuiItemCustom extends GuiContainer {
	public static final ResourceLocation	texture				= new ResourceLocation(
																		Rot.MODID
																				.toLowerCase(),
																		"textures/gui/base.png");

	private int[] colors = new int[]{0xFFFFFF,0x999999,0x666666,0x333333, 
			0xD9D100,0x918b00,
			0xb36000,0x7C4300,
			0x619b00,0x4D6D17,
			0x47C0FF,0x21489C,
			0xe40004,0xaa0003};
	private int[] selectedColors = new int[]{0,0,0};
	private EntityPlayer					player;
	private ItemStack						item;
	private int								pad					= 4;
	private int								ch					= 20;
	private int								selectedBladeHead	= 0;
	private int								selectedGuard		= 0;
	private int								selectedHandle		= 0;
	private int								weaponStyles		= 1;
	private boolean swordStaff = false;
	private boolean wasChanged = false;
	
	public GuiItemCustom(EntityPlayer player) {
		super(new ContainerNull());

		this.player = player;
		this.item = player.getHeldItem();
		this.xSize = 176;
		this.ySize = 166;
		this.selectedBladeHead = UtilityNBTHelper.getInt(this.item,
				UtilityNBTKeyNames.bladeHead);
		this.selectedGuard = UtilityNBTHelper.getInt(this.item,
				UtilityNBTKeyNames.guard);
		this.selectedHandle = UtilityNBTHelper.getInt(this.item,
				UtilityNBTKeyNames.handle);
		
		if (this.item.getItem() instanceof WeaponCustom)
		{
			weaponStyles = ((WeaponCustom) this.item.getItem()).getNumberOfTypes();
		}
		if ((this.item.getItem() instanceof WeaponSlash)
				|| (this.item.getItem() instanceof WeaponStaff)) {
			swordStaff = true;
		}
		
		for (int i = 0; i < colors.length;i++)
		{
			if (UtilityNBTHelper.getInt(item, UtilityNBTKeyNames.layerColor+0) == colors[i])
				selectedColors[2] = i;
			if (UtilityNBTHelper.getInt(item, UtilityNBTKeyNames.layerColor+2) == colors[i])
				selectedColors[0] = i;
			if (UtilityNBTHelper.getInt(item, UtilityNBTKeyNames.layerColor+4) == colors[i])
				selectedColors[1] = i;
		}
		this.wasChanged = UtilityNBTHelper.getBoolean(item, UtilityNBTKeyNames.wasChanged);
	}

	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0 : // Class Forward
				if (button.displayString.contains("Blade")
						|| button.displayString.contains("Head")) {
					if (this.selectedBladeHead < (this.weaponStyles - 1)) {
						this.selectedBladeHead++;
					} else {
						this.selectedBladeHead = 0;
					}
				} else if (button.displayString.contains("Guard")) {
					if (this.selectedGuard < (this.weaponStyles - 1)) {
						this.selectedGuard++;
					} else {
						this.selectedGuard = 0;
					}
				} else if (button.displayString.contains("Extra")) {
					UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.showExtra, !UtilityNBTHelper.getBoolean(item, UtilityNBTKeyNames.showExtra));
				}else if (button.displayString.contains("Handle")) {
					if (this.selectedHandle < (this.weaponStyles - 1)) {
						this.selectedHandle++;
					} else {
						this.selectedHandle = 0;
					}
				}
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.bladeHead, this.selectedBladeHead);
				UtilityNBTHelper.setInteger(this.item, UtilityNBTKeyNames.handle,
						this.selectedHandle);
				UtilityNBTHelper.setInteger(this.item, UtilityNBTKeyNames.guard,
						this.selectedGuard);
				wasChanged = true;
				UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.wasChanged, wasChanged);
				break;
			case 1 : // Class Back
				if (button.displayString.contains("Blade")
						|| button.displayString.contains("Head")) {
					if (this.selectedBladeHead > 0) {
						this.selectedBladeHead--;
					} else {
						this.selectedBladeHead = this.weaponStyles - 1;
					}
				} else if (button.displayString.contains("Guard")) {
					if (this.selectedGuard > 0) {
						this.selectedGuard--;
					} else {
						this.selectedGuard = this.weaponStyles - 1;
					}
				}
				else if (button.displayString.contains("Extra")) {
					UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.showExtra, !UtilityNBTHelper.getBoolean(item, UtilityNBTKeyNames.showExtra));
				} else if (button.displayString.contains("Handle")) {
					if (this.selectedHandle > 0) {
						this.selectedHandle--;
					} else {
						this.selectedHandle = this.weaponStyles - 1;
					}
				}
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.bladeHead, this.selectedBladeHead);
				UtilityNBTHelper.setInteger(this.item, UtilityNBTKeyNames.handle,
						this.selectedHandle);
				UtilityNBTHelper.setInteger(this.item, UtilityNBTKeyNames.guard,
						this.selectedGuard);
				wasChanged = true;
				UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.wasChanged, wasChanged);
				break;
			case 2 :
				if (button.displayString.contains("CB")) {
					if (selectedColors[0] < colors.length - 1)
						selectedColors[0]++;
					else
						selectedColors[0] = 0;
				}
				else if (button.displayString.contains("CG")) {
					if (selectedColors[1] < colors.length - 1)
						selectedColors[1]++;
					else
						selectedColors[1] = 0;
				}
				else if (button.displayString.contains("CH")) {
					if (selectedColors[2] < colors.length - 1)
						selectedColors[2]++;
					else
						selectedColors[2] = 0;
				}
				if (swordStaff)
				{
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+0, colors[selectedColors[2]]);//handle
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+2, colors[selectedColors[0]]);//blade
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+4, colors[selectedColors[1]]);//guard
				}
				else
				{
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+0, colors[selectedColors[2]]);//handle
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+2, colors[selectedColors[0]]);//blade
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+4, colors[selectedColors[0]]);//guard
				}
				wasChanged = true;
				UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.wasChanged, wasChanged);
				break;
			case 3 :
				if (button.displayString.contains("CB")) {
					if (selectedColors[0] > 0)
						selectedColors[0]--;
					else
						selectedColors[0] = colors.length - 1;
				}
				else if (button.displayString.contains("CG")) {
					if (selectedColors[1] > 0)
						selectedColors[1]--;
					else
						selectedColors[1] = colors.length - 1;
				}
				else if (button.displayString.contains("CH")) {
					if (selectedColors[2] > 0)
						selectedColors[2]--;
					else
						selectedColors[2] = colors.length - 1;
				}
				if (swordStaff)
				{
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+0, colors[selectedColors[2]]);//handle
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+2, colors[selectedColors[0]]);//blade
				UtilityNBTHelper.setInteger(this.item,
						UtilityNBTKeyNames.layerColor+4, colors[selectedColors[1]]);//guard
				}
				else
				{
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+0, colors[selectedColors[2]]);//handle
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+2, colors[selectedColors[0]]);//blade
					UtilityNBTHelper.setInteger(this.item,
							UtilityNBTKeyNames.layerColor+4, colors[selectedColors[0]]);//guard
				}
				wasChanged = true;
				UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.wasChanged, wasChanged);
				break;
			case 4 :
				wasChanged = false;
				UtilityNBTHelper.setBoolean(item, UtilityNBTKeyNames.wasChanged, wasChanged);
				Rot.net.sendToServer(new CustomItemPacket(item));
				break;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		TextureManager manager = Minecraft.getMinecraft().renderEngine;
		
		this.buttonList.clear();
		// Start with main control buttons

		int x1 = this.guiLeft + this.pad;
		int x2 = (this.guiLeft + this.xSize) - (50 + this.pad);
		
		this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad, 50, this.ch,
				(swordStaff ? "Blade" : "Head") + " ->"));
		this.buttonList.add(new GuiButton(2, x2 - 20, this.guiTop + this.pad, 20, this.ch,
				"CB>")/*.packedFGColour = colors[selectedColors[0]]*/);
		this.drawCenteredString(this.fontRendererObj, "" + this.selectedBladeHead,
				(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5, colors[selectedColors[0]]);
		this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad, 50, this.ch,
				"<- " + (swordStaff ? "Blade" : "Head")));
		this.buttonList.add(new GuiButton(3, x1 + 50, this.guiTop + this.pad, 20, this.ch,
				"<CB")/*.packedFGColour = colors[selectedColors[0]]*/);

		if (swordStaff)
		{
			this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad + 20, 50,
					this.ch, "Guard ->"));
			this.buttonList.add(new GuiButton(2, x2 - 20, this.guiTop + this.pad + 20, 20,
					this.ch, "CG>")/*.packedFGColour = colors[selectedColors[1]]*/);
			this.drawCenteredString(this.fontRendererObj, "" + this.selectedGuard,
					(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5 + 20, colors[selectedColors[1]]);
			this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad + 20, 50,
					this.ch, "<- Guard"));
			this.buttonList.add(new GuiButton(3, x1 + 50, this.guiTop + this.pad + 20, 20,
					this.ch, "<CG")/*.packedFGColour = colors[selectedColors[1]]*/);
		}
		else
		{
			this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad + 20, 50,
					this.ch, "Extra ->"));
			this.drawCenteredString(this.fontRendererObj, "" + UtilityNBTHelper.getBoolean(item, UtilityNBTKeyNames.showExtra),
					(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5 + 20, colors[selectedColors[0]]);
			this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad + 20, 50,
					this.ch, "<- Extra"));
		}
		
		this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad + (20 * 2),
				50, this.ch, "Handle ->"));
		this.buttonList.add(new GuiButton(2, x2 - 20, this.guiTop + this.pad + (20 * 2),
				20, this.ch, "CH>")/*.packedFGColour = colors[selectedColors[2]]*/);
		this.drawCenteredString(this.fontRendererObj, "" + this.selectedHandle,
				(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5 + (20 * 2), colors[selectedColors[2]]);
		this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad + (20 * 2),
				50, this.ch, "<- Handle"));
		this.buttonList.add(new GuiButton(3, x1 + 50, this.guiTop + this.pad + (20 * 2),
				20, this.ch, "<CH")/*.packedFGColour = */);
		
		this.buttonList.add(new GuiButton(4, x1, this.guiTop + this.pad
				+ (20 * 3), 50, this.ch, "Save"));
		this.drawString(this.fontRendererObj, wasChanged ? "Please Save Changes":"", x1, this.guiTop + this.pad
				+ (20 * 4), 0xe40004);

		manager.bindTexture(manager.getResourceLocation(1));
		// RENDER ITEMS
		IIcon displayItem;
		for (int l = 0; l < 3; l++) {
			displayItem = this.item.getItem().getIcon(this.item, l);
			if (displayItem != null) {
				drawTexturedModelRectFromIcon(x2, this.guiTop + this.pad
						+ (20 * 3), displayItem, 16, 16);
			}
		}
	}

	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2) {
		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
			this.mc.thePlayer.closeScreen();
		}
	}
}
