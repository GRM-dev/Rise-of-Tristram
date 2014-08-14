package ee.rot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ee.rot.Rot;
import ee.rot.blocks.ContainerItemGen;
import ee.rot.blocks.TileEntityItemGenerator;

public class GuiItemGen extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/test.png");
	
	private TileEntityItemGenerator te;
	
	public GuiItemGen(TileEntityItemGenerator tileEntity)
	{
		super(new ContainerItemGen(tileEntity));
		te = tileEntity;
		xSize = 176;
		ySize = 165;
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		int posX = (this.width) / 2;
		int posY = (this.height) / 2;
		int xBuffer = 29;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		/*this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, posX + xBuffer, posY - 60, 55, 20, "Infuse XP"));
		this.drawString(fontRenderer, "XP Stored", posX + xBuffer, posY - 38, 0x000000);
		this.drawString(fontRenderer, "0", posX + xBuffer, posY - 28, 0x000000);*/
	}
	
}
