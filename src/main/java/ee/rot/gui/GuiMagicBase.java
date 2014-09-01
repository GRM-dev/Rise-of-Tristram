package ee.rot.gui;


import java.util.ArrayList;

import javax.vecmath.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import scala.collection.immutable.List;

import ee.rot.Rot;
import ee.rot.UtilityNBTHelper;
import ee.rot.blocks.ContainerNull;
import ee.rot.blocks.RotBlocks;
import ee.rot.blocks.TileEntityMagicBase;
import ee.rot.comms.BaseBuilderPacket;
import ee.rot.items.RotItems;

public class GuiMagicBase extends GuiContainer
{
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/magicBase.png");
	
	private EntityPlayer player;
	private TileEntityMagicBase te;
	private int cw = 12; //control Width
	private int ch = 12; //control Height
	
	//Grid Values
	private int gridSizeX = 0;
	private int gridSizeZ = 0;
	private int gX = 6, gZ = 6;
	private int gridXOffset = 0;
	private int gridZOffset = 0;
	private int xOffset = 0;
	private int yOffset = 0;
	private int zOffset = 0;
	
	//Block Placement Valuse
	private int currentBlock = 0;
	private int currentMeta = 0;
	private String block = RotBlocks.blockTypes[currentBlock];
	private int blockColor = RotBlocks.blockTypeColors[currentBlock];
	
	//Selection and List Values
	private String[] list;
	private Boolean listGotten = false;
	private int defaultColor = 0x444444;
	private int selectionMode = 0;
	private String[] selectionTitle = {"Single","Rectangle A - B"};
	private Vector3f[] AB = new Vector3f[2];
	
	//Misc.	
	private GuiBaseBuilderButton[] coordButtons;
	private int INDEX_START = 16;
	private int indexCounter = INDEX_START;
	private int startLeft = Minecraft.getMinecraft().displayWidth / (Minecraft.getMinecraft().displayWidth / 10), 
			startTop = Minecraft.getMinecraft().displayHeight / (Minecraft.getMinecraft().displayHeight / 16);
	
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par1 == 'a')
		{
			xOffset--;
			updateButtons();
		}
		else if (par1 == 'd')
		{
			xOffset++;
			updateButtons();
		}
		else if (par1 == 'w')
		{
			zOffset--;
			updateButtons();
		}
		else if (par1 == 's')
		{
			zOffset++;
			updateButtons();
		}
		else if (par2 == this.mc.gameSettings.keyBindJump.getKeyCode())
		{
			yOffset++;
			updateButtons();
		}
		else if (par2 == this.mc.gameSettings.keyBindSneak.getKeyCode())
		{
			yOffset--;
			updateButtons();
		}
		
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
		
	}
	
	public GuiMagicBase(TileEntityMagicBase tileEntity, EntityPlayer player)
	{
		super(new ContainerNull());
		
		this.player = player;
		te = tileEntity;
		xSize = 227;
		ySize = 226;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{	
		if (!listGotten)
		{
			if (player.getHeldItem() != null 
	    			&& player.getHeldItem().getItem().equals(RotItems.itemMana))
	    	{
	    		if (player.getHeldItem().getItemDamage() == 3)
	    		{
	    			if (UtilityNBTHelper.getString(player.getHeldItem(), Rot.MODID+"baseNodeListCoords").trim() != "")
	        		{
	    				list = UtilityNBTHelper.getString(player.getHeldItem(), Rot.MODID+"baseNodeListCoords").split(";");
	    				listGotten = true;
	        		}
	    		}
	    	}
		}
		
		startLeft = Minecraft.getMinecraft().displayWidth / (Minecraft.getMinecraft().displayWidth / 10); 
		startTop = Minecraft.getMinecraft().displayHeight / (Minecraft.getMinecraft().displayHeight / 16);
		
		gridSizeX = gX + gridXOffset;
		gridSizeZ = gZ + gridZOffset;
		
		if (coordButtons == null)
		{
			updateButtons();
		}
		
		
		/*int posX = (this.width) / 2;
		int posY = (this.height) / 2;
		int xBuffer = 29;*/
		GL11.glColor4f(1F, 1F, 1F, 1F);
		/*Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);*/

		this.buttonList.clear();
		//Start with main control buttons	
		
		this.buttonList.add(new GuiButton(2, (startLeft + ((gridSizeX * 2) * cw)) + cw, (startTop + (gridSizeZ * ch)), cw, ch, "-X"));//X left
		this.buttonList.add(new GuiButton(3, (startLeft + ((gridSizeX * 2) * cw)) + cw * 3, (startTop + (gridSizeZ * ch)), cw, ch, "+X"));//X right
		
		this.buttonList.add(new GuiButton(4, (startLeft + ((gridSizeX * 2) * cw)) + cw * 2, (startTop + (gridSizeZ * ch)) - ch, cw, ch, "-Z"));//Z up confusing should be 'forward'
		this.buttonList.add(new GuiButton(5, (startLeft + ((gridSizeX * 2) * cw)) + cw * 2, (startTop + (gridSizeZ * ch)) + ch, cw, ch, "+Z"));//Z down confusing should be 'back'
		
		this.buttonList.add(new GuiButton(6, (startLeft + ((gridSizeX * 2) * cw)) + cw * 4, (startTop + (gridSizeZ * ch)) - ch / 2, cw, ch, "Y+"));//Y up
		this.buttonList.add(new GuiButton(7, (startLeft + ((gridSizeX * 2) * cw)) + cw * 4, (startTop + (gridSizeZ * ch)) + ch / 2, cw, ch, "Y-"));//Y down
		
		//Visual information on location
				this.drawString(fontRendererObj, "X: "+(xOffset+te.xCoord)+" offSet: "+xOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) - ch, 0xFFFFFF);
				this.drawString(fontRendererObj, "Y: "+(yOffset+te.yCoord)+" offSet: "+yOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4), 0xFFFFFF);
				this.drawString(fontRendererObj, "Z: "+(zOffset+te.zCoord)+" offSet: "+zOffset, (startLeft + ((gridSizeX * 2) * cw)) + cw * 5, (startTop + (gridSizeZ * ch) + 4) + ch, 0xFFFFFF);
		
		this.buttonList.add(new GuiButton(11, (startLeft + ((gridSizeX * 2) * cw)) + cw, startTop, 75, ch, "Grid Width +"));//prev block
		this.buttonList.add(new GuiButton(12, (startLeft + ((gridSizeX * 2) * cw)) + cw, startTop + ch, 75, ch, "Grid Width -"));//next block
		this.buttonList.add(new GuiButton(13, (startLeft + ((gridSizeX * 2) * cw)) + cw, startTop + ch * 2, 75, ch, "Grid Height +"));//prev block
		this.buttonList.add(new GuiButton(14, (startLeft + ((gridSizeX * 2) * cw)) + cw, startTop + ch * 3, 75, ch, "Grid Height -"));//next block
		
		this.buttonList.add(new GuiButton(15, (startLeft + ((gridSizeX * 2) * cw)) + cw, (startTop + ((gridSizeZ * 2) * ch)) - ch * 3, 75, ch, selectionTitle[selectionMode]));//selection mode
		this.drawString(fontRendererObj, (AB == null ? "single Mode":(AB[0] == null ? "Point A not selected" : AB[0])).toString(), 
				(startLeft + ((gridSizeX * 2) * cw)) + cw * 8, (startTop + ((gridSizeZ * 2) * ch)) - ch * 3, blockColor); // What block is selected
		
		this.buttonList.add(new GuiButton(0, (startLeft + ((gridSizeX * 2) * cw)) + cw, (startTop + ((gridSizeZ * 2) * ch)) - ch * 2, 75, ch, "Start Building")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, (startLeft + ((gridSizeX * 2) * cw)) + cw, (startTop + ((gridSizeZ * 2) * ch)) - ch, 75, ch, "Send List")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(8, (startLeft + ((gridSizeX * 2) * cw)) + cw, (startTop + ((gridSizeZ * 2) * ch)), 75, ch, "Clear")); //Clears all the locations
		
		this.buttonList.add(new GuiButton(9, (startLeft + ((gridSizeX * 2) * cw)) + cw + 75, startTop, 60, ch, "< Block"));//prev block
		this.buttonList.add(new GuiButton(10, (startLeft + ((gridSizeX * 2) * cw)) + cw * 6 + 75 + 60, startTop, 60, ch, "Block >"));//next block
		this.drawString(fontRendererObj, block, (startLeft + ((gridSizeX * 2) * cw)) + cw + 75 + 60, startTop, blockColor); // What block is selected
		
		for (int button = 0; button < coordButtons.length;button++)
		{
			this.buttonList.add(coordButtons[button]);
		}
	}
	
	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) 
	{	
		//Anything below the generated buttons for grid clicking
		if (button.id < indexCounter)
		{
			switch (button.id)
			{
			case 0: //Start Building
				Rot.net.sendToServer(new BaseBuilderPacket("2;"+te.xCoord+","+te.yCoord+","+te.zCoord));
				break;
			case 1: // Send List
				if (list != null)
				{
					/*String locations ="";
					for (int l = 0; l < list.length; l++)
					{
						locations += list[l];
						if (list.length > 1 && l != list.length - 1)
						{
							locations += ";";
						}
					}*/
					int listIndex = 0;
					while (listIndex != list.length)
					{
						Rot.net.sendToServer(new BaseBuilderPacket("0;"+te.xCoord+","+te.yCoord+","+te.zCoord+";"+list[listIndex++]));	
					}
				}				
				break;
			case 2: // -X left/west
				xOffset--;
				break;
			case 3: // +X right/east
				xOffset++;
				break;
			case 4: // -Z forward/north
				zOffset--;
				break;
			case 5: // +Z backwards/south
				zOffset++;
				break;
			case 6: // +Y up
				yOffset++;
				break;
			case 7: // -Y down
				yOffset--;
				break;
			case 8: // Clear, clears tileEntity list and this gui's List
				Rot.net.sendToServer(new BaseBuilderPacket("1;"+te.xCoord+","+te.yCoord+","+te.zCoord));
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
			case 10: // > moves block array right
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
			case 15:
				selectionMode = selectionMode == 0 ? 1 : 0;
				if (selectionMode == 0) AB = new Vector3f[2];
				break;
			}
		}
		//Start code for generated buttons
		else
		{
			System.out.println("clicked a multi Button.");
			String[] loc = ((GuiBaseBuilderButton)button).coords.split(",");
			//If the list is blank, create the list and add first option
			//Yes I know I should just use an ArrayList/List but for some reason
			//when I tried to use it, I messed up or it messed up
			//so instead of wasting time figuring it out I just brute
			//forced my own arrayList.
			if (selectionMode == 1)// Range Mode
			{
				if (AB[0] == null)
				{						
					AB[0] = new Vector3f(Float.parseFloat(loc[0]), (float)this.yOffset, Float.parseFloat(loc[1]));
				}
				else if (AB[1] == null)
				{
					if (new Vector3f(Float.parseFloat(loc[0]), (float)this.yOffset, Float.parseFloat(loc[1])) != AB[0])
					{
						AB[1] = new Vector3f(Float.parseFloat(loc[0]), (float)this.yOffset, Float.parseFloat(loc[1]));
					}						
				}				
				if (AB[0] != null && AB[1] != null)
				{
					int xh = (AB[0].x > AB[1].x ? (int)AB[0].x : (int)AB[1].x);
					int xl = (AB[0].x < AB[1].x ? (int)AB[0].x : (int)AB[1].x);
					
					int yh = (AB[0].y > AB[1].y ? (int)AB[0].y : (int)AB[1].y);
					int yl = (AB[0].y < AB[1].y ? (int)AB[0].y : (int)AB[1].y);
					
					int zh = (AB[0].z > AB[1].z ? (int)AB[0].z : (int)AB[1].z);
					int zl = (AB[0].z < AB[1].z ? (int)AB[0].z : (int)AB[1].z);
					for (int xs = xh; xs >= xl; xs--)
					{
						for (int zs = zh; zs >= zl; zs--)
						{
							for (int ys = yh; ys >= yl; ys--)
							{
								if (list == null)
								{
									list = new String[1];
									list[0] = xs+","+ys+","+zs+","+block;
								}
								else
								{
									//First check to make sure that this coordinate is not in the list already
									boolean dupeObject = false;
									for (int l = 0; l < list.length; l++)
									{
										String[] listCoords = list[l].split(",");
										if (Integer.parseInt(listCoords[0]) == xs && 
												Integer.parseInt(listCoords[1]) == ys && 
														Integer.parseInt(listCoords[2]) == zs)
										{
											list[l] = xs+","+ys+","+zs+","+block;
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
												newPreList[l] = xs+","+ys+","+zs+","+block;
											else
												newPreList[l] = list[l];
										}
										list = newPreList;
									}
								}								
							}
						}
					}
					AB = new Vector3f[2];
				}
				
			}
			else//single select
			{
				System.out.println("clicked solo button.");
				if (list == null)
				{
					list = new String[1];
					list[0] = loc[0]+","+yOffset+","+loc[1]+","+block;
				}
				else
				{
					//First check to make sure that this coordinate is not in the list already
					boolean dupeObject = false;
					for (int l = 0; l < list.length; l++)
					{
						String[] listCoords = list[l].split(",");
						if (listCoords[0] == loc[0] && 
								Integer.parseInt(listCoords[1]) == this.yOffset && 
								listCoords[2] == loc[1])
						{
							list[l] = loc[0]+","+yOffset+","+loc[1]+","+block;
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
								newPreList[l] = loc[0]+","+yOffset+","+loc[1]+","+block;
							else
								newPreList[l] = list[l];
						}
						list = newPreList;
					}
				}
			}			
		}
		updateButtons();
	}	
	
	//Like the static reference inside this method look in "RotBlocks" for the arrays used
	//This is to get a single or pair of Letters to show on the GUI map
	private String getBlockLetter(int x,int y,int z,String s)
	{
		if (s.equals("X"))
		{
			boolean chosenBlocks = false;
			Block worldBlock = te.getWorldObj().getBlock(x, y, z);
			if (worldBlock.equals(Blocks.air))
			{
				s = ".";
			}
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
			if (s.equals("X"))
			{
				s = worldBlock.getLocalizedName().substring(0, 1);
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
			if (worldBlock.equals(Blocks.air))
			{
				c = 0x00FFFF;
			}
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
			if (c == defaultColor)
			{
				c = worldBlock.getMaterial().getMaterialMapColor().colorValue;
			}
		}
		return c;
	}
	
	//Updates the buttons
	private void updateButtons()
	{
		if (coordButtons == null || coordButtons.length != (gridSizeX * 2 + 1) * (gridSizeZ * 2 + 1))
		{
			coordButtons = new GuiBaseBuilderButton[(gridSizeX * 2 + 1) * (gridSizeZ * 2 + 1)];
		}
		
		int buttonArrayIndex = 0;		
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
						if (x + this.xOffset == Integer.parseInt(locCoords[0]) && z + this.zOffset == Integer.parseInt(locCoords[2]))
						{
							if (this.yOffset == Integer.parseInt(locCoords[1]))
							{								
								for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
								{
									if (locCoords[3].equals(RotBlocks.blockTypes[bt]))
									{
										s = RotBlocks.blockTypeLetters[bt] +"*";
										c = RotBlocks.blockTypeColors[bt];
										break;
									}
								}	
							}
						}
					}
				}
				coordButtons[buttonArrayIndex] = new GuiBaseBuilderButton(indexCounter++, 
						(startLeft + (gridSizeX * cw)) + ((cw * x)), 
						(startTop + (gridSizeZ * ch)) + ((ch * z)), 
						cw, 
						ch, 
						s = getBlockLetter(x + this.xOffset + te.xCoord, yOffset + te.yCoord, z + this.zOffset + te.zCoord, s) ,
						(x + this.xOffset)+","+(z + this.zOffset));
				if (x + this.xOffset == 0 && yOffset == 0 && z + this.zOffset == 0)coordButtons[buttonArrayIndex].packedFGColour = 0x0000FF;
				else coordButtons[buttonArrayIndex].packedFGColour = c = getBlockColor(x + this.xOffset + te.xCoord,yOffset + te.yCoord, z + this.zOffset + te.zCoord, c);
				buttonArrayIndex++;
			}
		}
		indexCounter = INDEX_START;
	}
}
