package ee.rot.gui;


import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import scala.collection.immutable.List;

import ee.rot.Rot;
import ee.rot.blocks.ContainerNull;
import ee.rot.blocks.RotBlocks;
import ee.rot.blocks.TileEntityMagicBase;
import ee.rot.comms.BaseBuilderPacket;

public class GuiMagicBase extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/magicBase.png");
	
	private TileEntityMagicBase te;
	private int cw = 20; //control Width
	private int ch = 20; //control Height
	private int gridSizeX = 5;
	private int gridSizeZ = 5;
	private int gridXOffset = 0;
	private int gridZOffset = 0;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private int currentBlock = 0;
	private String block = RotBlocks.blockTypes[currentBlock];
	private int blockColor = RotBlocks.blockTypeColors[currentBlock];
	private String[] list;
	private int defaultColor = 0x444444;
	private int selectionMode = 0;
	private String[] selectionTitle = {"Single","Rectangle A - B"};
	
	public GuiMagicBase(TileEntityMagicBase tileEntity)
	{
		super(new ContainerNull());
		
		te = tileEntity;
		xSize = 227;
		ySize = 226;
	}	
	
	/**
	 * 
	 * 
	 * 
	 *If you value your sanity I'd suggest not going into the logic of how this works
	 *But if this warning doesn't scare you, proceed with caution. 
	 * 
	 * 
	 **/
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{		
		gridSizeX = 5 + gridXOffset;
		gridSizeZ = 5 + gridZOffset;
		
		int posX = (this.width) / 2;
		int posY = (this.height) / 2;
		int xBuffer = 29;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		this.buttonList.clear();
		//Start with main control buttons		
		this.buttonList.add(new GuiButton(0, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 3, 75, ch, "Start Building")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 4, 75, ch, "Send List")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(8, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 5, 75, ch, "Clear")); //Clears all the locations
		this.buttonList.add(new GuiButton(11, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 6, 75, ch, "Grid Width +"));//prev block
		this.buttonList.add(new GuiButton(12, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 7, 75, ch, "Grid Width -"));//next block
		this.buttonList.add(new GuiButton(13, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 8, 75, ch, "Grid Height +"));//prev block
		this.buttonList.add(new GuiButton(14, guiLeft + 5 + cw * 5, guiTop + 5 + ch * 9, 75, ch, "Grid Height -"));//next block
		
		//Location controls
		this.buttonList.add(new GuiButton(2, guiLeft + 5, guiTop + 5 + ch, cw, ch, "-X"));//X left
		this.buttonList.add(new GuiButton(3, guiLeft + 5 + cw * 2, guiTop + 5 + ch, cw, ch, "+X"));//X right
		
		this.buttonList.add(new GuiButton(6, guiLeft + 5 + cw, guiTop + 5 + ch * 3, cw, ch, "Y+"));//Y up
		this.buttonList.add(new GuiButton(7, guiLeft + 5 + cw, guiTop + 5 + ch * 4, cw, ch, "Y-"));//Y down
		
		this.buttonList.add(new GuiButton(4, guiLeft + 5 + cw, guiTop + 5, cw, ch, "-Z"));//Z up confusing should be 'forward'
		this.buttonList.add(new GuiButton(5, guiLeft + 5 + cw, guiTop + 5 + ch * 2, cw, ch, "+Z"));//Z down confusing should be 'back'
		
		this.buttonList.add(new GuiButton(9, guiLeft + 5 + cw * 5, guiTop + 5 + ch, cw + 10, ch, "< Block"));//prev block
		this.buttonList.add(new GuiButton(10, guiLeft + 25 + cw * 6, guiTop + 5 + ch, cw + 10, ch, "Block >"));//next block
		
		//Left Side coord buttons for placing blocks
		int counter = 15;
		for (int x = gridSizeX; x >= -gridSizeX; x--)
		{
			for (int z = gridSizeZ; z >= -gridSizeZ;z--)
			{
				int c = defaultColor;
				String s = "X";
				if (list != null)
				{
					//Each locations is a string of x,y,z,block
					String[] locCoords;
					//Look through every Item of the list
					for (int l = 0; l < list.length; l++)
					{			
						locCoords = list[l].split(",");
						if (x + this.x == Integer.parseInt(locCoords[0]) && z + this.z == Integer.parseInt(locCoords[2]))
						{
							if (this.y == Integer.parseInt(locCoords[1]))
							{								
								for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
								{
									if (locCoords[3].equals(RotBlocks.blockTypes[bt]))
									{
										s = RotBlocks.blockTypeLetters[bt] +"*";
										c = RotBlocks.blockTypeColors[bt];
										break;
									}
									else 
									{
										c = defaultColor;
									}
								}	
							}
						}
					}
				}
				//getBlockLetter(te.xCoord + x + this.x, te.yCoord + y, te.zCoord + z + this.z, "X")
				GuiButton b = new GuiBaseBuilderButton(counter++, 
						(10 + (gridSizeX * cw)) + ((cw + 1) * x), 
						(20 + (gridSizeZ * ch)) + ((ch + 1) * z), 
						cw, 
						ch, 
						getBlockLetter(te.xCoord + x + this.x, te.yCoord + y, te.zCoord + z + this.z, s) ,
						(x + this.x)+","+(z + this.z));
				if (x + this.x == 0 && y == 0 && z + this.z == 0)b.packedFGColour = 0x0000FF;
				else b.packedFGColour = getBlockColor(te.xCoord + x + this.x, te.yCoord + y, te.zCoord + z + this.z, c);
				this.buttonList.add(b);
			}
		}
		
		//Visual information on location
		this.drawString(fontRendererObj, "X: "+(x+te.xCoord)+" offSet: "+x, guiLeft + 5, guiTop + 5 + ch * 6, 0xFFFFFF);
		this.drawString(fontRendererObj, "Y: "+(y+te.yCoord)+" offSet: "+y, guiLeft + 5, guiTop + 5 + ch * 7, 0xFFFFFF);
		this.drawString(fontRendererObj, "Z: "+(z+te.zCoord)+" offSet: "+z, guiLeft + 5, guiTop + 5 + ch * 8, 0xFFFFFF);
		this.drawString(fontRendererObj, block, guiLeft + 5 + cw * 5, guiTop + 10, blockColor); // What block is selected
				
		//Start Rendering Off Screen Map
		String s1 = "X", s2 = "X", s3 = "X";//These are the Letters to show what block is where on the map
		int c1 = defaultColor, c2 = defaultColor, c3= defaultColor;//Colors for map letters
		//Start drawing the map GridX x GridZ (sounds weird that I am using x and z instead of x and y, but you'll see why)
		for (int x = gridSizeX; x >= -gridSizeX; x--)
		{
			for (int z = gridSizeZ; z >= -gridSizeZ;z--)
			{
				//If the list of locations has something
				if (list != null)
				{
					//Each locations is a string of x,y,z,block
					String[] locCoords;
					//Look through every Item of the list
					for (int l = 0; l < list.length; l++)
					{						
						//take the concatenated location string and split it, normally 4 elements 0-3
						locCoords = list[l].split(",");
						//If the locations x and z match the displayed x and z
						if (x + this.x == Integer.parseInt(locCoords[0]) && z + this.z == Integer.parseInt(locCoords[2]))
						{
							//Check to see if locations y is in the displayed view
							//Also not sure why I start bottom to top, worked on this very late, 6am late
							if (this.y - 1 == Integer.parseInt(locCoords[1])) //Check y level below
							{		
								for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
								{
									if (locCoords[3].equals(RotBlocks.blockTypes[bt]))
									{
										s1 = RotBlocks.blockTypeLetters[bt];
										c1 = RotBlocks.blockTypeColors[bt];
										break;
									}
								}								
							}
							if (this.y == Integer.parseInt(locCoords[1]))//Current y level
							{								
								for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
								{
									if (locCoords[3].equals(RotBlocks.blockTypes[bt]))
									{
										s2 = RotBlocks.blockTypeLetters[bt];
										c2 = RotBlocks.blockTypeColors[bt];
										break;
									}
								}	
							}
							if (this.y + 1 == Integer.parseInt(locCoords[1]))//Y level above
							{								
								for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
								{
									if (locCoords[3].equals(RotBlocks.blockTypes[bt]))
									{
										s3 = RotBlocks.blockTypeLetters[bt];
										c3 = RotBlocks.blockTypeColors[bt];
										break;
									}
								}										
							}
						}											
					}
				}
				//Show the Tile Entity, this is so when a player is confused why their clicks don't work on that location
				//It's because that is where the tileEntity is and it would be counter productive if it killed itself
				if (x + this.x == 0 && z + this.z == 0 && this.y - 1 == 0)
				{
					s1 = "T";
					c1 = 0x0000FF;
				}
				if (x + this.x == 0 && z + this.z == 0 && this.y == 0)
				{
					s2 = "T";
					c2 = 0x0000FF;
				}
				if (x + this.x == 0 && z + this.z == 0 && this.y + 1 == 0)
				{
					s3 = "T";
					c3 = 0x0000FF;
				}
				
				
				//Get current placed blocks;				
				s1 = getBlockLetter(te.xCoord + x + this.x, te.yCoord + y - 1, te.zCoord + z + this.z, s1);
				c1 = getBlockColor(te.xCoord + x + this.x, te.yCoord + y - 1, te.zCoord + z + this.z, c1);
				s2 = getBlockLetter(te.xCoord + x + this.x, te.yCoord + y, te.zCoord + z + this.z, s2);
				c2 = getBlockColor(te.xCoord + x + this.x, te.yCoord + y, te.zCoord + z + this.z, c2);
				s3 = getBlockLetter(te.xCoord + x + this.x, te.yCoord + y + 1, te.zCoord + z + this.z, s3);
				c3 = getBlockColor(te.xCoord + x + this.x, te.yCoord + y + 1, te.zCoord + z + this.z, c3);
				
				//Draw the map Location Status
				this.drawString(fontRendererObj, s1, (posX + 220) + (12 * x + 1), posY + 12 * z + 170, c1);// -1 y up
				this.drawString(fontRendererObj, s2, (posX + 220) + (12 * x + 1), posY + 12 * z, c2);
				this.drawString(fontRendererObj, s3, (posX + 220) + (12 * x + 1), posY + 12 * z - 170, c3);// +1 y down
				
				//Reset Defaults for next round
				s1 = "X";
				s2 = "X";
				s3 = "X";
				c1 = defaultColor;
				c2 = defaultColor;
				c3 = defaultColor;
			}
		}
	}
	
	
	
	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) 
	{	
		//Anything below the generated buttons for grid clicking
		if (button.id < 15)
		{
			switch (button.id)
			{
			case 0: //Start Building
				Rot.net.sendToServer(new BaseBuilderPacket("START;"+te.xCoord+","+te.yCoord+","+te.zCoord));
				break;
			case 1: // Send List
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
			case 2: // -X left/west
				x--;
				break;
			case 3: // +X right/east
				x++;
				break;
			case 4: // -Z forward/north
				z--;
				break;
			case 5: // +Z backwards/south
				z++;
				break;
			case 6: // +Y up
				y++;
				break;
			case 7: // -Y down
				y--;
				break;
			case 8: // Clear, clears tileEntity list and this gui's List
				Rot.net.sendToServer(new BaseBuilderPacket("CLEAR;"+te.xCoord+","+te.yCoord+","+te.zCoord));
				list = null;
				break;
			case 9: // < moves block array left
				if (currentBlock == 0)
				{
					currentBlock = RotBlocks.blockTypes.length -1;
				}
				else
				{
					currentBlock--;
				}
				block = RotBlocks.blockTypes[currentBlock];
				blockColor = RotBlocks.blockTypeColors[currentBlock];
				break;
			case 10: // > moces block array right
				if (currentBlock == RotBlocks.blockTypes.length -1)
				{
					currentBlock = 0;
				}
				else
				{
					currentBlock++;
				}
				block = RotBlocks.blockTypes[currentBlock];
				blockColor = RotBlocks.blockTypeColors[currentBlock];
				break;
			case 11: //Increase grid width
				gridXOffset++;
				break;
			case 12: //Decrease
				gridXOffset--;
				break;
			case 13: //Increase grid height
				gridZOffset++;
				break;
			case 14: //Decrease
				gridZOffset--;
				break;
			}
		}
		//Start code for generated buttons
		else
		{
			String[] loc = ((GuiBaseBuilderButton)button).coords.split(",");
			//If the list is blank, create the list and add first option
			//Yes I know I should just use an ArrayList/List but for some reason
			//when I tried to use it, I messed up or it messed up
			//so instead of wasting time figuring it out I just brute
			//forced my own arrayList.
			if (list == null)
			{
				list = new String[1];
				list[0] = loc[0]+","+y+","+loc[1]+","+block;
			}
			else
			{
				//First check to make sure that this coordinate is not in the list already
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
				//If the coordinate is fresh add it in
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
	
	//Like the static reference inside this method look in "RotBlocks" for the arrays used
	//This is to get a single or pair of Letters to show on the GUI map
	private String getBlockLetter(int x,int y,int z,String s)
	{
		if (s.equals("X"))
		{
			boolean chosenBlocks = false;
			Block worldBlock =
			te.getWorldObj().getBlock(x, y, z);
			for (int bt = 0; bt < RotBlocks.blockTypeObjects.length;bt++)
			{
				if (worldBlock.equals(RotBlocks.blockTypeObjects[bt]))
				{
					s = RotBlocks.blockTypeLetters[bt];
					chosenBlocks = true;
					break;
				}
			}
			if (!chosenBlocks)
			{
				for (int bt = 0; bt < RotBlocks.naturalBlockTypeObjects.length;bt++)
				{
					if (worldBlock.equals(RotBlocks.naturalBlockTypeObjects[bt]))
					{
						s = RotBlocks.naturalBlockTypeLetters[bt];
						chosenBlocks = true;
						break;
					}
				}
			}
		}
		return s;
	}
	
	//Like the above method only for getting a color to render with
	private int getBlockColor(int x, int y, int z, int c)
	{
		if (c == defaultColor)
		{
			boolean chosenBlocks = false;
			Block worldBlock =
			te.getWorldObj().getBlock(x, y, z);
			for (int bt = 0; bt < RotBlocks.blockTypeObjects.length;bt++)
			{
				if (worldBlock.equals(RotBlocks.blockTypeObjects[bt]))
				{
					c = RotBlocks.blockTypeColors[bt];
					chosenBlocks = true;
					break;
				}
			}
			if (!chosenBlocks)
			{
				for (int bt = 0; bt < RotBlocks.naturalBlockTypeObjects.length;bt++)
				{
					if (worldBlock.equals(RotBlocks.naturalBlockTypeObjects[bt]))
					{
						c = RotBlocks.naturalBlockTypeColors[bt];
						chosenBlocks = true;
						break;
					}
				}
			}
		}
		return c;
	}
}
