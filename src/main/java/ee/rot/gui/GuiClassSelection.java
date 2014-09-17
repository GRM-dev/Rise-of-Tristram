package ee.rot.gui;


import javax.vecmath.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ee.rot.Rot;
import ee.rot.blocks.RotBlocks;
import ee.rot.blocks.TileEntityBaseNode;
import ee.rot.comms.BaseNodeRequestPacket;
import ee.rot.comms.ClassRequestPacket;
import ee.rot.items.RotItems;
import ee.rot.libs.ExtendPlayer;
import ee.rot.libs.UtilityNBTHelper;

public class GuiClassSelection extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/base.png");
	
	private EntityPlayer player;
	private int pad = 4;
	private int ch = 20;
	private int selectedClass = 0;

	public GuiClassSelection(EntityPlayer player)
	{
		super(new ContainerNull());
		
		this.player = player;
		xSize = 176;
		ySize = 166;
		selectedClass = 
				ExtendPlayer.get(player).getCurrentClassIndex() != -1 
				? ExtendPlayer.get(player).getCurrentClassIndex() : 0;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{	
		
		
		/*TextureManager manager = Minecraft.getMinecraft().renderEngine;
	    manager.bindTexture(manager.getResourceLocation(1));
	    //RENDER ITEMS
	    
	    drawTexturedModelRectFromIcon(300, 300, RotItems.itemGunpowderInfuser.getIconFromDamage(0), 16, 16);*/	
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		this.buttonList.clear();
		//Start with main control buttons
		
		int x1 = guiLeft + pad;
		int x2 = (guiLeft + xSize) - (50 + pad);
		int x3 = (x2 + x1) / 2;
		
		this.buttonList.add(new GuiButton(0, x2, guiTop + pad, 50, ch, "Class ->"));
		this.drawString(fontRendererObj, ExtendPlayer.classNames[selectedClass], x3, guiTop + pad + 5, 0xddeeee);
		this.buttonList.add(new GuiButton(1, x1, guiTop + pad, 50, ch, "<- Class"));
		this.buttonList.add(new GuiButton(2, x1, guiTop + pad + 20, 50, ch, "Pick"));
		
		this.drawString(fontRendererObj, "Current Class: " + ExtendPlayer.get(player).getCurrentClassName(), guiLeft + pad, guiTop + pad + 45, 0xdbdd15);
		this.drawString(fontRendererObj, "Str: " + ExtendPlayer.get(player).getStrength(), guiLeft + pad, guiTop + pad + 60, 
				ExtendPlayer.get(player).getStrength() == 0 ? 0xFFFFFF : (ExtendPlayer.get(player).getStrength() > 0 ? 0x00ff42 : 0xff0c00));
		this.drawString(fontRendererObj, "Vit: " + ExtendPlayer.get(player).getVitality(), guiLeft + pad, guiTop + pad + 75, 
				ExtendPlayer.get(player).getVitality() == 0 ? 0xFFFFFF : (ExtendPlayer.get(player).getVitality() > 0 ? 0x00ff42 : 0xff0c00));
		this.drawString(fontRendererObj, "Dex: " + ExtendPlayer.get(player).getDexterity(), guiLeft + pad, guiTop + pad + 90, 
				ExtendPlayer.get(player).getDexterity() == 0 ? 0xFFFFFF : (ExtendPlayer.get(player).getDexterity() > 0 ? 0x00ff42 : 0xff0c00));
		this.drawString(fontRendererObj, "Agi: " + ExtendPlayer.get(player).getAgility(), guiLeft + pad, guiTop + pad + 105, 
				ExtendPlayer.get(player).getAgility() == 0 ? 0xFFFFFF : (ExtendPlayer.get(player).getAgility() > 0 ? 0x00ff42 : 0xff0c00));
		this.drawString(fontRendererObj, "Int: " + ExtendPlayer.get(player).getIntelligence(), guiLeft + pad, guiTop + pad + 120, 
				ExtendPlayer.get(player).getIntelligence() == 0 ? 0xFFFFFF : (ExtendPlayer.get(player).getIntelligence() > 0 ? 0x00ff42 : 0xff0c00));

		//Visual information on location
		/*this.drawString(fontRendererObj, "X: "+(xOffset+te.xCoord)+" offSet: "+xOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) - ch, 0xFFFFFF);
		this.drawString(fontRendererObj, "Y: "+(yOffset+te.yCoord)+" offSet: "+yOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4), 0xFFFFFF);
		this.drawString(fontRendererObj, "Z: "+(zOffset+te.zCoord)+" offSet: "+zOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) + ch, 0xFFFFFF);*/
	}
	
	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) 
	{	
		switch (button.id)
		{
			case 0: // Class Forward
					selectedClass++;
					selectedClass =  selectedClass == ExtendPlayer.classNames.length ? 1 : selectedClass;
				break;
			case 1: // Class Back
					selectedClass--;
					selectedClass =  selectedClass == 0 ? ExtendPlayer.classNames.length - 1 : selectedClass;
				break;
			case 2: // Change Class
				Rot.net.sendToServer(new ClassRequestPacket(ExtendPlayer.classNames[selectedClass]));
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
