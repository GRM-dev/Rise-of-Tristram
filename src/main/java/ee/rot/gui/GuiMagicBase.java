package ee.rot.gui;


import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import scala.collection.immutable.List;

import ee.rot.Rot;
import ee.rot.blocks.ContainerNull;
import ee.rot.blocks.TileEntityMagicBase;
import ee.rot.comms.BaseBuilderPacket;

public class GuiMagicBase extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/blank.png");
	
	private TileEntityMagicBase te;
	private int cw = 15; //control Width
	private int ch = 15; //control Height
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private String block = "planks";
	private String[] list;
	
	public GuiMagicBase(TileEntityMagicBase tileEntity)
	{
		super(new ContainerNull());
		
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

		this.buttonList.clear();
		//Start with main control buttons		
		this.buttonList.add(new GuiButton(0, posX + xBuffer - 60, posY - 80, 30, 20, "Start Building")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, posX + xBuffer, posY - 80, 25, 20, "Send List")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(8, posX + xBuffer + 25, posY - 80, 25, 20, "Clear")); //Clears all the locations
		
		//Location controls
		this.buttonList.add(new GuiButton(2, posX + xBuffer, posY - 50, cw, ch, "<"));//X left
		this.buttonList.add(new GuiButton(3, posX + xBuffer + cw, posY - 50, cw, ch, ">"));//X right
		
		this.buttonList.add(new GuiButton(6, posX + xBuffer, posY - 35, cw, ch, "Y+"));//Y up
		this.buttonList.add(new GuiButton(7, posX + xBuffer + cw, posY - 35, cw, ch, "Y-"));//Y down
		
		this.buttonList.add(new GuiButton(4, posX + xBuffer, posY - 20, cw, ch, "^"));//Z up confusing should be 'forward'
		this.buttonList.add(new GuiButton(5, posX + xBuffer + cw, posY - 20, cw, ch, "v"));//Z down confusing should be 'back'
		int counter = 9;
		for (int x = 3; x >= -3; x--)
		{
			for (int z = 3; z >= -3;z--)
			{
				GuiButton b = new GuiButton(counter++, posX - 37 + ((cw + 1) * x), posY - 32 + ((ch + 1) * z), cw, ch, (x + this.x)+","+(z + this.z));
				if (x + this.x == 0 && z + this.z == 0)b.packedFGColour = 255;
				this.buttonList.add(b);//Y up
			}
		}
		
		//Visual information on location
		this.drawString(fontRendererObj, "X: "+(x+te.xCoord)+" offSet: "+x, posX + xBuffer - 110, posY + 60, 0xFFFFFF);
		this.drawString(fontRendererObj, "Y: "+(y+te.yCoord)+" offSet: "+y, posX + xBuffer - 110, posY + 50, 0xFFFFFF);
		this.drawString(fontRendererObj, "Z: "+(z+te.zCoord)+" offSet: "+z, posX + xBuffer - 110, posY + 40, 0xFFFFFF);
				
		//Start Rendering Off Screen Map
		String s1 = "", s2 = "", s3 = "";//These are the Letters to show what block is where on the map
		int c1 = 0xFFFFFF, c2 = 0xFFFFFF, c3= 0xFFFFFF;//Colors for map letters
		//Start drawing the map 7x7
		for (int x = 3; x >= -3; x--)
		{
			for (int z = 3; z >= -3;z--)
			{
				//If the list of locations has something
				if (list != null)
				{
					//Each locations is a string of x,y,z,block
					String[] locCoords;
					//Look through every Item of the list
					for (int l = 0; l < list.length; l++)
					{
						//If the location is the x and z of the tile entity
						if (x + this.x == 0 && z + this.z == 0)
						{
							//If the y location is the tile entity draw a blue T
							if (this.y - 1 == 0)
							{
								s1 = "T";
								c1 = 0x0000FF;
							}
							if (this.y == 0)
							{
								s2 = "T";
								c2 = 0x0000FF;
							}
							if (this.y + 1 == 0)
							{
								s3 = "T";
								c3 = 0x0000FF;
							}
						}
						//If the x and z are not the same as the tile entity
						else
						{
							//take the concatenated location string and split it, normally 4 elements 0-3
							locCoords = list[l].split(",");
							//If the locations x and z match the displayed x and z
							if (x + this.x == Integer.parseInt(locCoords[0]) && z + this.z == Integer.parseInt(locCoords[2]))
							{
								//Check to see if locations y is in the displayed view
								if (this.y - 1 == Integer.parseInt(locCoords[1]))
								{
									s1 = "P";
									c1 = 0xFF0000;
								}
								if (this.y == Integer.parseInt(locCoords[1]))
								{
									s2 = "P";
									c2 = 0xFF0000;
								}
								if (this.y + 1 == Integer.parseInt(locCoords[1]))
								{
									s3 = "P";
									c3 = 0xFF0000;
								}
							}
							/*else
							{
								s1 = "N";
								s2 = "N";
								s3 = "N";
								c1 = 0x888888;
								c2 = 0x888888;
								c3 = 0x888888;
							}*/
						}						
					}
				}
				else // if there is no list, default render
				{
					if (x + this.x == 0 && z + this.z == 0)
					{
						if (this.y - 1 == 0)
						{
							s1 = "T";
							c1 = 0x0000FF;
						}
						else
						{
							s1 = "N";
							c1 = 0x888888;
						}
						
						if (this.y == 0)
						{
							s2 = "T";
							c2 = 0x0000FF;
						}
						else
						{
							s2 = "N";
							c2 = 0x888888;
						}
						if (this.y + 1 == 0)
						{
							s3 = "T";
							c3 = 0x0000FF;
						}
						
						else
						{
							s3 = "N";
							c3 = 0x888888;
						}
					}
					else
					{
						s1 = "N";
						s2 = "N";
						s3 = "N";
						c1 = 0x888888;
						c2 = 0x888888;
						c3 = 0x888888;
					}
				}
				this.drawString(fontRendererObj, s1, posX * 2 + 16 * x - 100, posY + 16 * z + 150, c1);// -1 y up
				this.drawString(fontRendererObj, s2, posX * 2 + 16 * x - 100, posY + 16 * z, c2);
				this.drawString(fontRendererObj, s3, posX * 2 + 16 * x - 100, posY + 16 * z - 150, c3);// +1 y down
			}
		}
	}
	
	
	@Override
	protected void actionPerformed(GuiButton button) 
	{	
		if (button.id < 9)
		{
			switch (button.id)
			{
			case 0:
				Rot.net.sendToServer(new BaseBuilderPacket("START;"+te.xCoord+","+te.yCoord+","+te.zCoord));
				break;
			case 1:
				//bb.addLocation(x, y, z);
				//Rot.net.sendToServer(new BaseBuilderPacket("ADD;"+te.xCoord+","+te.yCoord+","+te.zCoord+";"));
				if (list != null)
				{
					String locations ="";
					for (int l = 0; l < list.length; l++)
					{
						locations += list[l];
						if (list.length > 1 && l != list.length - 1)
						{
							locations += ";";
						}
					}
					Rot.net.sendToServer(new BaseBuilderPacket("ADD;"+te.xCoord+","+te.yCoord+","+te.zCoord+";"+locations));
				}
				
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
				Rot.net.sendToServer(new BaseBuilderPacket("CLEAR;"+te.xCoord+","+te.yCoord+","+te.zCoord));
				break;
			}
		}
		else
		{
			String[] loc = button.displayString.split(",");
				if (list == null)
				{
					list = new String[1];
					list[0] = loc[0]+","+y+","+loc[1]+","+block;
				}
				else
				{
					boolean dupeObject = false;
					for (int l = 0; l < list.length; l++)
					{
						String[] listCoords = list[l].split(",");
						if (listCoords[0] == loc[0] && 
								Integer.parseInt(listCoords[1]) == this.y && 
								listCoords[2] == loc[1])
						{
							list[l] = loc[0]+","+y+","+loc[1]+","+block;
							dupeObject = true;
						}
					}
					if (!dupeObject)
					{
						String[] newPreList = new String[list.length+1];
						for (int l = 0; l < newPreList.length;l++)
						{
							if (l == newPreList.length-1)
								newPreList[l] = loc[0]+","+y+","+loc[1]+","+block;
							else
								newPreList[l] = list[l];
						}
						list = newPreList;
					}
				}
		}
	}
}
