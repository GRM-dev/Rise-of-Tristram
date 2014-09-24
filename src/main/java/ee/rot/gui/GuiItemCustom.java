package ee.rot.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ee.rot.Rot;
import ee.rot.items.WeaponBlunt;
import ee.rot.items.WeaponHack;
import ee.rot.items.WeaponPierce;
import ee.rot.items.WeaponSlash;
import ee.rot.items.WeaponStaff;
import ee.rot.libs.UtilityNBTHelper;
import ee.rot.libs.WeaponsNBTKeyNames;

public class GuiItemCustom extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/base.png");
	
	private EntityPlayer player;
	private ItemStack item;
	private int pad = 4;
	private int ch = 20;
	private int selectedBladeHead = 0;
	private int selectedGuard = 0;
	private int selectedHandle = 0;
	private int weaponStyles = 1;

	public GuiItemCustom(EntityPlayer player)
	{
		super(new ContainerNull());
		
		this.player = player;
		item = player.getHeldItem();
		xSize = 176;
		ySize = 166;
		selectedBladeHead = UtilityNBTHelper.getInt(item, WeaponsNBTKeyNames.bladeHead);
		selectedGuard = UtilityNBTHelper.getInt(item, WeaponsNBTKeyNames.guard);
		selectedHandle = UtilityNBTHelper.getInt(item, WeaponsNBTKeyNames.handle);

		if (item.getItem() instanceof WeaponSlash)
			weaponStyles = ((WeaponSlash)item.getItem()).numOfTypes;
		else if (item.getItem() instanceof WeaponPierce)
			weaponStyles = ((WeaponPierce)item.getItem()).numOfTypes;
		else if (item.getItem() instanceof WeaponBlunt)
			weaponStyles = ((WeaponBlunt)item.getItem()).numOfTypes;
		else if (item.getItem() instanceof WeaponHack)
			weaponStyles = ((WeaponHack)item.getItem()).numOfTypes;
		else if (item.getItem() instanceof WeaponStaff)
			weaponStyles = ((WeaponStaff)item.getItem()).numOfTypes;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		TextureManager manager = Minecraft.getMinecraft().renderEngine;	    

		this.buttonList.clear();
		//Start with main control buttons
		
		int x1 = guiLeft + pad;
		int x2 = (guiLeft + xSize) - (50 + pad);

		boolean swordStaff = false;
		if (item.getItem() instanceof WeaponSlash || item.getItem() instanceof WeaponStaff)
		{
			swordStaff = true;
		}
		this.buttonList.add(new GuiButton(0, x2, guiTop + pad, 50, ch, (swordStaff ? "Blade" : "Head") + " ->"));
		this.drawCenteredString(fontRendererObj, ""+selectedBladeHead, (x1 + 50 + x2) / 2, guiTop + pad + 5, 0xffffff);
		this.buttonList.add(new GuiButton(1, x1, guiTop + pad, 50, ch, "<- " + (swordStaff ? "Blade" : "Head")));
		
		if (swordStaff)
		{
			this.buttonList.add(new GuiButton(0, x2, guiTop + pad + 20, 50, ch, "Guard ->"));
			this.drawCenteredString(fontRendererObj, ""+selectedGuard, (x1 + 50 + x2) / 2, guiTop + pad + 5 + 20, 0xffffff);
			this.buttonList.add(new GuiButton(1, x1, guiTop + pad + 20, 50, ch, "<- Guard"));
		
			this.buttonList.add(new GuiButton(0, x2, guiTop + pad + 20 * 2, 50, ch, "Handle ->"));
			this.drawCenteredString(fontRendererObj, ""+selectedHandle, (x1 + 50 + x2) / 2, guiTop + pad + 5 + 20 * 2, 0xffffff);
			this.buttonList.add(new GuiButton(1, x1, guiTop + pad + 20 * 2, 50, ch, "<- Handle"));
		}
		this.buttonList.add(new GuiButton(2, x1, guiTop + pad + 20 * (swordStaff ? 3 : 1), 50, ch, "Save"));
		
		manager.bindTexture(manager.getResourceLocation(1));
		//RENDER ITEMS
	    IIcon displayItem;
	    for (int l = 0; l < 3; l++)
	    {
	    	displayItem = item.getItem().getIcon(item, l);
	    	if (displayItem != null)
	    		drawTexturedModelRectFromIcon(x2, guiTop + pad + 20 * (swordStaff ? 3 : 1), displayItem, 16, 16);
	    }
	}
	
	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) 
	{	
		switch (button.id)
		{
			case 0: // Class Forward
				if (button.displayString.contains("Blade") || button.displayString.contains("Head"))
				{
					if (selectedBladeHead < weaponStyles - 1)
						selectedBladeHead++;
					else
						selectedBladeHead = 0;
				}
				else if (button.displayString.contains("Guard"))
				{
					if (selectedGuard < weaponStyles - 1)
						selectedGuard++;
					else
						selectedGuard = 0;
				}
				else if (button.displayString.contains("Handle"))
				{
					if (selectedHandle < weaponStyles - 1)
						selectedHandle++;
					else
						selectedHandle = 0;
				}
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.bladeHead, selectedBladeHead);
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.handle, selectedHandle);
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.guard, selectedGuard);
				break;
			case 1: // Class Back
				if (button.displayString.contains("Blade") || button.displayString.contains("Head"))
				{
					if (selectedBladeHead > 0)
						selectedBladeHead--;
					else
						selectedBladeHead = weaponStyles - 1;
				}
				else if (button.displayString.contains("Guard"))
				{
					if (selectedGuard > 0)
						selectedGuard--;
					else
						selectedGuard = weaponStyles - 1;
				}
				else if (button.displayString.contains("Handle"))
				{
					if (selectedHandle > 0)
						selectedHandle--;
					else
						selectedHandle = weaponStyles - 1;
				}
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.bladeHead, selectedBladeHead);
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.handle, selectedHandle);
				UtilityNBTHelper.setInteger(item, WeaponsNBTKeyNames.guard, selectedGuard);
				break;
			case 2: 
				break;			
		}
	}
	
	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2)
	{		
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }		
	}
}
