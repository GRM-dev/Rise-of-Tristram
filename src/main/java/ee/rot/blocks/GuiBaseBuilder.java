package ee.rot.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ee.rot.RotOld;

public class GuiBaseBuilder extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(RotOld.MODID.toLowerCase(), "textures/gui/blankGui.png");
	
	private TileEntityBaseBuilder bb;
	private int cw = 15; //control Width
	private int ch = 15; //control Height
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	public GuiBaseBuilder(Container par1Container) 
	{
		super(par1Container);
	}
	
	public GuiBaseBuilder(InventoryPlayer invPlayer, TileEntityBaseBuilder entity) 
	{
		super(new ContainerBaseBuilder(invPlayer, entity));
		xSize = 176;
		ySize = 165;
		bb = entity;
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		int posX = (this.width) / 2;//middle of the GUI image
		int posY = (this.height) / 2;//middle of the GUI image
		int xBuffer = 29;//just my own margin for drawing buttons
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		this.buttonList.clear();
		//Start with main control buttons		
		this.buttonList.add(new GuiButton(0, posX + xBuffer - 60, posY - 80, 60, 20, "Start/Stop")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, posX + xBuffer, posY - 80, 25, 20, "Add")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(8, posX + xBuffer + 25, posY - 80, 25, 20, "Clear")); //Clears all the locations
		
		//Location controls
		this.buttonList.add(new GuiButton(2, posX + xBuffer, posY - 50, cw, ch, "<"));//X left
		this.buttonList.add(new GuiButton(3, posX + xBuffer + cw, posY - 50, cw, ch, ">"));//X right
		
		this.buttonList.add(new GuiButton(6, posX + xBuffer, posY - 35, cw, ch, "Y+"));//Y up
		this.buttonList.add(new GuiButton(7, posX + xBuffer + cw, posY - 35, cw, ch, "Y-"));//Y down
		
		this.buttonList.add(new GuiButton(4, posX + xBuffer, posY - 20, cw, ch, "^"));//Z up confusing should be 'forward'
		this.buttonList.add(new GuiButton(5, posX + xBuffer + cw, posY - 20, cw, ch, "v"));//Z down confusing should be 'back'
		
		//Visual information on location
		//this.drawString(fontRenderer, "X: "+(x+bb.xCoord)+" offSet: "+x, posX + xBuffer - 90, posY - 38, 0xFFFFFF);
		//this.drawString(fontRenderer, "Y: "+(y+bb.yCoord)+" offSet: "+y, posX + xBuffer - 90, posY - 28, 0xFFFFFF);
		//this.drawString(fontRenderer, "Z: "+(z+bb.zCoord)+" offSet: "+z, posX + xBuffer - 90, posY - 18, 0xFFFFFF);
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) 
	{
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) 
	{		
		switch (button.id)
		{
		case 0:
			bb.switchOnOff();
			break;
		case 1:
			bb.addLocation(x, y, z);
			break;
		case 2:
			x--;
			break;
		case 3:
			x++;
			break;
		case 4:
			z--;
			break;
		case 5:
			z++;
			break;
		case 6:
			y++;
			break;
		case 7:
			y--;
			break;
		case 8:
			bb.clearList();
			break;
		}
	}

}
