package ee.rot.gui;


import java.util.ArrayList;

import javax.vecmath.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import ee.rot.Rot;
import ee.rot.blocks.RotBlocks;
import ee.rot.blocks.TileEntityBaseNode;
import ee.rot.comms.BaseNodeRequestPacket;
import ee.rot.items.RotItems;
import ee.rot.libs.UtilityBlockLocationType;
import ee.rot.libs.UtilityNBTHelper;

public class GuiBaseNode extends GuiContainer
{	
	public static final ResourceLocation texture = 
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/largeBase.png");
	
	private EntityPlayer player;
	private TileEntityBaseNode te;
	private int cw = 16; //control Width
	private int ch = 16; //control Height
	
	//Grid Values
	private int gridSize = 0;
	private int gS = 5;
	private int gridSizeOffset = 0;
	private int xOffset = 0;
	private int yOffset1 = 0, yOffset2 = 0;
	private int zOffset = 0;
	
	//Block Placement Values
	private int currentBlock = 0;
	private int currentMeta = 0;
	private int blockColor = RotBlocks.blockTypeColors[currentBlock];
	
	//Selection and List Values
	private ArrayList locations = new ArrayList<UtilityBlockLocationType>();
	private Boolean listGotten = false;
	private int defaultColor = 0x444444;
	private int selectionMode = 0;
	private String[] selectionTitle = {"Single","Rectangle A - B"};
	private Vector3f[] AB = new Vector3f[2];
	
	//Misc.	
	private GuiBaseNodeButton[] coordButtons1;
	private GuiBaseNodeButton[] coordButtons2;
	private int INDEX_START = 17;
	private int indexCounter = INDEX_START;
	private int startLeft = cw * 2, 
			startTop = ch * 2;
	
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
			if (yOffset1 + te.yCoord == 255)return;
			else yOffset1++;
			updateButtons();
		}
		else if (par2 == Keyboard.KEY_DOWN)
		{
			if (yOffset2 + te.yCoord == 0)return;
			else yOffset2--;
			updateButtons();
		}
		else if (par2 == Keyboard.KEY_UP)
		{
			if (yOffset2 + te.yCoord == 255)return;
			else yOffset2++;
			updateButtons();
		}
		else if (par2 == this.mc.gameSettings.keyBindSneak.getKeyCode())
		{
			if (yOffset1 + te.yCoord == 0)return;
			else yOffset1--;
			updateButtons();
		}
		
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
		
	}
	
	public GuiBaseNode(TileEntityBaseNode tileEntity, EntityPlayer player)
	{
		super(new ContainerNull());
		
		this.player = player;
		te = tileEntity;
		this.locations = te.locations;
		xSize = 227;
		ySize = 226;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{			
		gridSize = gS + gridSizeOffset;
		
		if (locations.isEmpty())
		{
			locations = te.locations;
		}
		if (coordButtons1 == null)
		{
			updateButtons();
		}		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int gx1 = startLeft - 3, 
				gx2 = (startLeft + (gridSize * cw)) + (gridSize * cw) + cw + 3, 
				gy1 = startTop - 3,
				gy2 = gy1 + (gridSize * ch) + (gridSize * ch) + ch + 6;
		int gw = (gridSize * 2) * cw + (cw + 6), 
				gh = (gridSize * 2) * ch + (ch + 6);
		//Drawing Map boarders
		//Map 1
		drawTexturedModalRect(gx1, gy1, 
				0, 0, 
				gw / 2, gh / 2);// upper left
		drawTexturedModalRect(gx1 + gw / 2, gy1, 
				227 - gw / 2, 0, 
				gw / 2, gh / 2);// upper right
		
		drawTexturedModalRect(gx1, gy1 + gh / 2, 
				0, 226 - gh / 2, 
				gw / 2, gh / 2);// lower left
		drawTexturedModalRect(gx1 + gw / 2, gy1 + gh / 2, 
				227 - gw / 2, 226 - gh / 2, 
				gw / 2, gh / 2);// lower right
		
		//Map 2
		drawTexturedModalRect(gx2, gy1, 
				0, 0, 
				gw / 2, gh / 2);// upper left
		drawTexturedModalRect(gx2 + gw / 2, gy1, 
				227 - gw / 2, 0, 
				gw / 2, gh / 2);// upper right
		
		drawTexturedModalRect(gx2, gy1 + gh / 2, 
				0, 226 - gh / 2, 
				gw / 2, gh / 2);// lower left
		drawTexturedModalRect(gx2 + gw / 2, gy1 + gh / 2, 
				227 - gw / 2, 226 - gh / 2, 
				gw / 2, gh / 2);// lower right

		this.buttonList.clear();
		//Start with main control buttons
		
		this.buttonList.add(new GuiButton(0, gx1, gy2, 75, ch, "Start Building")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, gx1 + 75, gy2, 75, ch, "Send List")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(2, gx1 + 75 * 2, gy2, 75, ch, "Clear")); //Clears all the locations
		this.buttonList.add(new GuiButton(3, gx1, gy2 + ch, 60, ch, "< Block"));//prev block
		this.buttonList.add(new GuiButton(4, gx1 + 60, gy2 + ch, 60, ch, "Block >"));//next block
		this.buttonList.add(new GuiButton(5, gx1 + 75 * 3, gy2, 60, ch, "Grid +"));//prev block
		this.buttonList.add(new GuiButton(6, gx1 + 75 * 3 + 60, gy2, 60, ch, "Grid -"));//next block
		this.buttonList.add(new GuiButton(7, gx1 + 60 * 2, gy2 + ch, 90, ch, selectionTitle[selectionMode]));//selection mode
		this.buttonList.add(new GuiButton(16, gx1 + 60 * 2 + 90, gy2 + ch, 75, ch, "Get List"));//selection mode
		
		this.buttonList.add(new GuiButton(8, (gx1 + gx2 + gw) / 2, gy1 - ch, cw, ch, "<"));//X left
		this.buttonList.add(new GuiButton(9, (gx1 + gx2 + gw) / 2 + (cw * 3), gy1 - ch, cw, ch, ">"));//X right
		this.buttonList.add(new GuiButton(10, (gx1 + gx2 + gw) / 2 + cw, gy1 - ch, cw, ch, "^"));//Z 'forward'
		this.buttonList.add(new GuiButton(11, (gx1 + gx2 + gw) / 2 + (cw * 2), gy1 - ch, cw, ch, "v"));//Z 'back'
		this.buttonList.add(new GuiButton(12, gx1 - cw, (startTop + (gridSize * ch)) - ch / 2, cw, ch, "Y1+"));//Y1 up
		this.buttonList.add(new GuiButton(13, gx1 - cw, (startTop + (gridSize * ch)) + ch / 2, cw, ch, "Y1-"));//Y1 down
		this.buttonList.add(new GuiButton(14, gx2 + gw, (startTop + (gridSize * ch)) - ch / 2, cw, ch, "Y2+"));//Y2 up
		this.buttonList.add(new GuiButton(15, gx2 + gw, (startTop + (gridSize * ch)) + ch / 2, cw, ch, "Y2-"));//Y2 down
		
		
		
		//Visual information on location
			/*this.drawString(fontRendererObj, "OffSet: "+xOffset, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) - ch, 0xFFFFFF);
			this.drawString(fontRendererObj, "OffSet: "+yOffset1, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4), 0xFFFFFF);
			this.drawString(fontRendererObj, "OffSet: "+yOffset2, (startLeft + ((gridSize * 2) * cw)) + cw * 11, (startTop + (gridSize * ch) + 4), 0xFFFFFF);
			this.drawString(fontRendererObj, "OffSet: "+zOffset, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) + ch, 0xFFFFFF);

		this.drawString(fontRendererObj, (AB == null ? "single Mode":(AB[0] == null ? "Point A not selected" : AB[0])).toString(), 
				(startLeft + ((gridSize * 2) * cw)) + cw * 8, (startTop + ((gridSize * 2) * ch)) - ch * 3, blockColor); // What block is selected*/

		this.drawString(fontRendererObj, RotBlocks.blockTypeObjects[currentBlock].getLocalizedName(), gx1, gy2 + ch * 2, blockColor); // What block is selected
		
		
		for (int button = 0; button < coordButtons1.length;button++)
		{
			this.buttonList.add(coordButtons1[button]);
		}
		for (int button = 0; button < coordButtons2.length;button++)
		{
			this.buttonList.add(coordButtons2[button]);
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
				Rot.net.sendToServer(new BaseNodeRequestPacket(2,te.xCoord, te.yCoord, te.zCoord, 0, 0, 0, 0));
				break;
			case 1: // Send List
				if (!locations.isEmpty())
				{
					UtilityBlockLocationType ublt;
					for (int l = 0; l < locations.size(); l++)
					{
						ublt = (UtilityBlockLocationType) locations.get(l);
						Rot.net.sendToServer(new BaseNodeRequestPacket(0,te.xCoord,te.yCoord,te.zCoord,ublt.x,ublt.y,ublt.z,Block.getIdFromBlock(ublt.block)));
					}
				}			
				locations.clear();
				break;
			case 2: // -X left/west
				Rot.net.sendToServer(new BaseNodeRequestPacket(1,te.xCoord, te.yCoord, te.zCoord, 0, 0, 0, 0));
				locations.clear();
				break;
			case 3: // +X right/east
				if (currentBlock == 0)
				{
					currentBlock = RotBlocks.blockTypeObjects.length -1;
				}
				else
				{
					currentBlock--;
				}
				blockColor = RotBlocks.blockTypeColors[currentBlock];
				break;
			case 4: // -Z forward/north
				if (currentBlock == RotBlocks.blockTypeObjects.length -1)
				{
					currentBlock = 0;
				}
				else
				{
					currentBlock++;
				}
				blockColor = RotBlocks.blockTypeColors[currentBlock];
				break;
			case 5: // +Z backwards/south
				gridSizeOffset++;
				break;
			case 6: // +Y up
				gridSizeOffset--;
				break;
			case 7: // -Y down
				selectionMode = selectionMode == 0 ? 1 : 0;
				if (selectionMode == 0) AB = new Vector3f[2];
				break;
			case 8: // Clear, clears tileEntity list and this gui's List
				xOffset--;
				break;
			case 9: // < moves block array left
				xOffset++;
				break;
			case 10: // > moves block array right
				zOffset--;
				break;
			case 11: //Increase grid width
				zOffset++;
				break;
			case 12: //Decrease
				if (yOffset1 + te.yCoord == 255)break;
				else yOffset1++;
				break;
			case 13:
				if (yOffset1 + te.yCoord == 0)break;
				else yOffset1--;
				break;
			case 14:
				if (yOffset2 + te.yCoord == 255)break;
				else yOffset2++;
				break;
			case 15:
				if (yOffset2 + te.yCoord == 0)break;
				else yOffset2--;
				break;
			case 16:
				Rot.net.sendToServer(new BaseNodeRequestPacket(3,te.xCoord,te.yCoord,te.zCoord,0,0,0,0));
				break;
			}			
		}
		//Start code for generated buttons
		else
		{
			int xB = ((GuiBaseNodeButton)button).x, yB = ((GuiBaseNodeButton)button).y, zB = ((GuiBaseNodeButton)button).z;
			if (selectionMode == 1)// Range Mode
			{
				if (AB[0] == null)
				{						
					AB[0] = new Vector3f((float)xB, (float)yB, (float)zB);
				}
				else if (AB[1] == null)
				{
					if (new Vector3f((float)xB, (float)yB, (float)zB) != AB[0])
					{
						AB[1] = new Vector3f((float)xB, (float)yB, (float)zB);
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
								addLocation(xs + te.xCoord, ys + te.yCoord, zs + te.zCoord);								
							}
						}
					}
					AB = new Vector3f[2];
				}
				
			}
			else//single select
			{
				addLocation(xB + te.xCoord, yB + te.yCoord, zB + te.zCoord);
			}			
		}
		updateButtons();
		this.updateScreen();
	}	
	
	private void addLocation(int x, int y, int z)
	{
		if (locations.isEmpty())
		{
			locations.add(new UtilityBlockLocationType(x, y, z, RotBlocks.blockTypeObjects[currentBlock]));
		}
		else
		{
			boolean dupeObject = false;
			for (int l = 0; l < locations.size(); l++)
			{
				UtilityBlockLocationType ublt = (UtilityBlockLocationType) locations.get(l);
				if (ublt.x == x && ublt.y == y && ublt.z == z)
				{
					ublt.block = RotBlocks.blockTypeObjects[currentBlock];
					locations.set(l, ublt);
					dupeObject = true;
				}
			}
			//If the coordinate is fresh add it in
			if (!dupeObject)
			{
				locations.add(new UtilityBlockLocationType(x, y, z, RotBlocks.blockTypeObjects[currentBlock]));
			}
		}
	}
	
	//Updates the buttons ...startLeft + ((gridSizeX * 2) * cw)) + cw
	private void updateButtons()
	{
		if (coordButtons1 == null || coordButtons1.length != (gridSize * 2 + 1) * (gridSize * 2 + 1))
		{
			coordButtons1 = new GuiBaseNodeButton[(gridSize * 2 + 1) * (gridSize * 2 + 1)];
		}
		
		if (coordButtons2 == null || coordButtons2.length != (gridSize * 2 + 1) * (gridSize * 2 + 1))
		{
			coordButtons2 = new GuiBaseNodeButton[(gridSize * 2 + 1) * (gridSize * 2 + 1)];
		}
		
		int buttonArrayIndex = 0;
		UtilityBlockLocationType ublt;
		for (int x = gridSize; x >= -gridSize; x--)
		{
			for (int z = gridSize; z >= -gridSize;z--)
			{
				IIcon t1 = null;
				IIcon t2 = null;
				int c1 = defaultColor;	
				int c2 = defaultColor;
				String s1 = "x";
				String s2 = "x";
				Block worldBlock1 = te.getWorldObj().getBlock( x + te.xCoord + xOffset, yOffset1 + te.yCoord , z + te.zCoord + zOffset);
				Block worldBlock2 = te.getWorldObj().getBlock( x + te.xCoord + xOffset, yOffset2 + te.yCoord , z + te.zCoord + zOffset);
				if (selectionMode == 1)
				{
					if (AB != null && AB[0] != null && x + xOffset == (int)AB[0].x && z + zOffset == (int)AB[0].z)
					{
						s1 = "X";
						s2 = "X";
					}
					else
					{
						s1 = worldBlock1.equals(Blocks.air) ? "." : "+";		
						s2 = worldBlock2.equals(Blocks.air) ? "." : "+";
					}
				}
				else
				{
					s1 = worldBlock1.equals(Blocks.air) ? "." : "+";		
					s2 = worldBlock2.equals(Blocks.air) ? "." : "+";	
				}
				if (!locations.isEmpty())
				{
					UtilityBlockLocationType ubltS;
					//Look through every Item of the list
					for (int ubltl = 0; ubltl < locations.size(); ubltl++)
					{
						ubltS = (UtilityBlockLocationType) locations.get(ubltl);
						if (ubltS.y == this.yOffset1 + te.yCoord)
						{
							if (ubltS.x == x + this.xOffset + te.xCoord && ubltS.z == z + this.zOffset + te.zCoord)
							{
								s1 = "*";
								c1 = ubltS.block.getMapColor(0).colorValue;
								t1 = ubltS.block.getIcon(1, 0);								
								break;
							}
						}						
					}
					for (int ubltl = 0; ubltl < locations.size(); ubltl++)
					{
						ubltS = (UtilityBlockLocationType) locations.get(ubltl);
						if (ubltS.y == this.yOffset2 + te.yCoord)
						{
							if (ubltS.x == x + this.xOffset + te.xCoord && ubltS.z == z + this.zOffset + te.zCoord)
							{
								s2 = "*";
								c2 = ubltS.block.getMapColor(0).colorValue;
								t2 = ubltS.block.getIcon(1, 0);
								break;
							}
						}
					}
				}
				coordButtons1[buttonArrayIndex] = new GuiBaseNodeButton(indexCounter++, 
						(startLeft + (gridSize * cw)) + ((cw * x)), 
						(startTop + (gridSize * ch)) + ((ch * z)), 
						cw, 
						ch, 
						s1);
				
				coordButtons2[buttonArrayIndex] = new GuiBaseNodeButton(indexCounter++, 
						((startLeft + (gridSize * cw)) + ((gridSize * cw) * 2) + cw + 6) + ((cw * x)), 
						(startTop + (gridSize * ch)) + ((ch * z)), 
						cw, 
						ch, 
						s2);
				if (x + this.xOffset == 0 && yOffset1 == 0 && z + this.zOffset == 0)coordButtons1[buttonArrayIndex].packedFGColour = 0x0000FF;
				else coordButtons1[buttonArrayIndex].packedFGColour = c1 == defaultColor ? (worldBlock1.equals(Blocks.air) ? 0x00CCFF : worldBlock1.getMapColor(0).colorValue) : c1;
				if (x + this.xOffset == 0 && yOffset2 == 0 && z + this.zOffset == 0)coordButtons2[buttonArrayIndex].packedFGColour = 0x0000FF;
				else coordButtons2[buttonArrayIndex].packedFGColour = c2 == defaultColor ? (worldBlock2.equals(Blocks.air) ? 0x00CCFF : worldBlock2.getMapColor(0).colorValue) : c2;
				float b1 = 1.0f, b2 = 1.0f;
				int depth1 = 0, depth2 = 0;
				while (t1 == null)
				{
					t1 = te.getWorldObj().getBlock(x + te.xCoord + xOffset, yOffset1 + te.yCoord - depth1, z + te.zCoord + zOffset).getIcon(1, 0);					
					if (t1 == null)
					{
						if (!locations.isEmpty())
						{
							//Look through every Item of the list
							for (int ubltl = 0; ubltl < locations.size(); ubltl++)
							{
								ublt = (UtilityBlockLocationType) locations.get(ubltl);
								if (ublt.y == this.yOffset1 + te.yCoord - depth1)
								{
									if (ublt.x == x + this.xOffset + te.xCoord && ublt.z == z + this.zOffset + te.zCoord)
									{
										t1 = ublt.block.getIcon(1, 0);
										break;
									}
								}
							}
						}
						MathHelper.clamp_float(b1 -= 0.2f, 0, 1f);
						depth1++;
					}
				}
				while (t2 == null)
				{
					t2 = te.getWorldObj().getBlock(x + te.xCoord + xOffset, yOffset2 + te.yCoord - depth2, z + te.zCoord + zOffset).getIcon(1, 0);					
					if (t2 == null)
					{
						if (!locations.isEmpty())
						{
							//Look through every Item of the list
							for (int ubltl = 0; ubltl < locations.size(); ubltl++)
							{
								ublt = (UtilityBlockLocationType) locations.get(ubltl);
								if (ublt.y == this.yOffset2 + te.yCoord - depth2)
								{
									if (ublt.x == x + this.xOffset + te.xCoord && ublt.z == z + this.zOffset + te.zCoord)
									{
										t2 = ublt.block.getIcon(1, 0);
										break;
									}
								}
							}
						}
						MathHelper.clamp_float(b2 -= 0.2f, 0, 1f);
						depth2++;
					}
				}
				coordButtons1[buttonArrayIndex].tex = t1;
				coordButtons1[buttonArrayIndex].x = x + this.xOffset;
				coordButtons1[buttonArrayIndex].y = this.yOffset1;
				coordButtons1[buttonArrayIndex].z = z + this.zOffset;
				coordButtons1[buttonArrayIndex].brightness = b1;
				coordButtons2[buttonArrayIndex].tex = t2;
				coordButtons2[buttonArrayIndex].x = x + this.xOffset;
				coordButtons2[buttonArrayIndex].y = this.yOffset2;
				coordButtons2[buttonArrayIndex].z = z + this.zOffset;
				coordButtons2[buttonArrayIndex].brightness = b2;
				buttonArrayIndex++;
			}
		}
		indexCounter = INDEX_START;
		this.updateScreen();
	}
}
