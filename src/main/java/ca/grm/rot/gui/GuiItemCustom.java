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
import ca.grm.rot.items.WeaponBlunt;
import ca.grm.rot.items.WeaponCustom;
import ca.grm.rot.items.WeaponHack;
import ca.grm.rot.items.WeaponPierce;
import ca.grm.rot.items.WeaponSlash;
import ca.grm.rot.items.WeaponStaff;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityWeaponNBTKeyNames;

public class GuiItemCustom extends GuiContainer {
	public static final ResourceLocation	texture				= new ResourceLocation(
																		Rot.MODID
																				.toLowerCase(),
																		"textures/gui/base.png");

	private EntityPlayer					player;
	private ItemStack						item;
	private int								pad					= 4;
	private int								ch					= 20;
	private int								selectedBladeHead	= 0;
	private int								selectedGuard		= 0;
	private int								selectedHandle		= 0;
	private int								weaponStyles		= 1;
	
	public GuiItemCustom(EntityPlayer player) {
		super(new ContainerNull());

		this.player = player;
		this.item = player.getHeldItem();
		this.xSize = 176;
		this.ySize = 166;
		this.selectedBladeHead = UtilityNBTHelper.getInt(this.item,
				UtilityWeaponNBTKeyNames.bladeHead);
		this.selectedGuard = UtilityNBTHelper.getInt(this.item,
				UtilityWeaponNBTKeyNames.guard);
		this.selectedHandle = UtilityNBTHelper.getInt(this.item,
				UtilityWeaponNBTKeyNames.handle);
		
		if (this.item.getItem() instanceof WeaponCustom)
		{
			weaponStyles = ((WeaponCustom) this.item.getItem()).getNumberOfTypes();
		}
		
		/*if (this.item.getItem() instanceof WeaponSlash) {
			this.weaponStyles = ((WeaponSlash) this.item.getItem()).numOfTypes;
		} else if (this.item.getItem() instanceof WeaponPierce) {
			this.weaponStyles = ((WeaponPierce) this.item.getItem()).numOfTypes;
		} else if (this.item.getItem() instanceof WeaponBlunt) {
			this.weaponStyles = ((WeaponBlunt) this.item.getItem()).numOfTypes;
		} else if (this.item.getItem() instanceof WeaponHack) {
			this.weaponStyles = ((WeaponHack) this.item.getItem()).numOfTypes;
		} else if (this.item.getItem() instanceof WeaponStaff) {
			this.weaponStyles = ((WeaponStaff) this.item.getItem()).numOfTypes;
		}*/
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
				} else if (button.displayString.contains("Handle")) {
					if (this.selectedHandle < (this.weaponStyles - 1)) {
						this.selectedHandle++;
					} else {
						this.selectedHandle = 0;
					}
				}
				UtilityNBTHelper.setInteger(this.item,
						UtilityWeaponNBTKeyNames.bladeHead, this.selectedBladeHead);
				UtilityNBTHelper.setInteger(this.item, UtilityWeaponNBTKeyNames.handle,
						this.selectedHandle);
				UtilityNBTHelper.setInteger(this.item, UtilityWeaponNBTKeyNames.guard,
						this.selectedGuard);
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
				} else if (button.displayString.contains("Handle")) {
					if (this.selectedHandle > 0) {
						this.selectedHandle--;
					} else {
						this.selectedHandle = this.weaponStyles - 1;
					}
				}
				UtilityNBTHelper.setInteger(this.item,
						UtilityWeaponNBTKeyNames.bladeHead, this.selectedBladeHead);
				UtilityNBTHelper.setInteger(this.item, UtilityWeaponNBTKeyNames.handle,
						this.selectedHandle);
				UtilityNBTHelper.setInteger(this.item, UtilityWeaponNBTKeyNames.guard,
						this.selectedGuard);
				break;
			case 2 :
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
		
		boolean swordStaff = false;
		if ((this.item.getItem() instanceof WeaponSlash)
				|| (this.item.getItem() instanceof WeaponStaff)) {
			swordStaff = true;
		}
		this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad, 50, this.ch,
				(swordStaff ? "Blade" : "Head") + " ->"));
		this.drawCenteredString(this.fontRendererObj, "" + this.selectedBladeHead,
				(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5, 0xffffff);
		this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad, 50, this.ch,
				"<- " + (swordStaff ? "Blade" : "Head")));

		if (swordStaff) {
			this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad + 20, 50,
					this.ch, "Guard ->"));
			this.drawCenteredString(this.fontRendererObj, "" + this.selectedGuard,
					(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5 + 20, 0xffffff);
			this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad + 20, 50,
					this.ch, "<- Guard"));
			
			this.buttonList.add(new GuiButton(0, x2, this.guiTop + this.pad + (20 * 2),
					50, this.ch, "Handle ->"));
			this.drawCenteredString(this.fontRendererObj, "" + this.selectedHandle,
					(x1 + 50 + x2) / 2, this.guiTop + this.pad + 5 + (20 * 2), 0xffffff);
			this.buttonList.add(new GuiButton(1, x1, this.guiTop + this.pad + (20 * 2),
					50, this.ch, "<- Handle"));
		}
		this.buttonList.add(new GuiButton(2, x1, this.guiTop + this.pad
				+ (20 * (swordStaff ? 3 : 1)), 50, this.ch, "Save"));

		manager.bindTexture(manager.getResourceLocation(1));
		// RENDER ITEMS
		IIcon displayItem;
		for (int l = 0; l < 3; l++) {
			displayItem = this.item.getItem().getIcon(this.item, l);
			if (displayItem != null) {
				drawTexturedModelRectFromIcon(x2, this.guiTop + this.pad
						+ (20 * (swordStaff ? 3 : 1)), displayItem, 16, 16);
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
