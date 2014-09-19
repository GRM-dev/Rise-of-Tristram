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
	private int gridXOffset = 0;
	private int gridZOffset = 0;
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
	private int INDEX_START = 18;
	private int indexCounter = INDEX_START;
	private int startLeft = 100, 
			startTop = 25;
	
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
		gridSize = gS + gridXOffset;
		
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
		int gx = startLeft - 3, gy = startTop - 3;
		int gw = (gridSize * 2) * cw + (cw + 6), gh = (gridSize * 2) * ch + (ch + 6);
		//Drawing Map boarders
		//Map 1
		drawTexturedModalRect(gx, gy, 
				0, 0, 
				gw / 2, gh / 2);// upper left
		drawTexturedModalRect(gx + gw / 2, gy, 
				227 - gw / 2, 0, 
				gw / 2, gh / 2);// upper right
		
		drawTexturedModalRect(gx, gy + gh / 2, 
				0, 226 - gh / 2, 
				gw / 2, gh / 2);// lower left
		drawTexturedModalRect(gx + gw / 2, gy + gh / 2, 
				227 - gw / 2, 226 - gh / 2, 
				gw / 2, gh / 2);// lower right

		this.buttonList.clear();
		//Start with main control buttons
		
		this.buttonList.add(new GuiButton(2, (startLeft + ((gridSize * 2) * cw)) + cw, (startTop + (gridSize * ch)), cw, ch, "-X"));//X left
		this.buttonList.add(new GuiButton(3, (startLeft + ((gridSize * 2) * cw)) + cw * 3, (startTop + (gridSize * ch)), cw, ch, "+X"));//X right
		
		this.buttonList.add(new GuiButton(4, (startLeft + ((gridSize * 2) * cw)) + cw * 2, (startTop + (gridSize * ch)) - ch, cw, ch, "-Z"));//Z up confusing should be 'forward'
		this.buttonList.add(new GuiButton(5, (startLeft + ((gridSize * 2) * cw)) + cw * 2, (startTop + (gridSize * ch)) + ch, cw, ch, "+Z"));//Z down confusing should be 'back'
		
		this.buttonList.add(new GuiButton(6, (startLeft + ((gridSize * 2) * cw)) + cw * 4, (startTop + (gridSize * ch)) - ch / 2, cw, ch, "Y1+"));//Y up
		this.buttonList.add(new GuiButton(7, (startLeft + ((gridSize * 2) * cw)) + cw * 4, (startTop + (gridSize * ch)) + ch / 2, cw, ch, "Y1-"));//Y down
		this.buttonList.add(new GuiButton(16, (startLeft + ((gridSize * 2) * cw)) + cw * 5, (startTop + (gridSize * ch)) - ch / 2, cw, ch, "Y2+"));//Y up
		this.buttonList.add(new GuiButton(17, (startLeft + ((gridSize * 2) * cw)) + cw * 5, (startTop + (gridSize * ch)) + ch / 2, cw, ch, "Y2-"));//Y down
		
		//Visual information on location
			this.drawString(fontRendererObj, "X: "+(xOffset+te.xCoord)+" offSet: "+xOffset, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) - ch, 0xFFFFFF);
			this.drawString(fontRendererObj, "Y1: "+(yOffset1+te.yCoord)+" offSet: "+yOffset1, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4), 0xFFFFFF);
			this.drawString(fontRendererObj, "Y2: "+(yOffset2+te.yCoord)+" offSet: "+yOffset2, (startLeft + ((gridSize * 2) * cw)) + cw * 11, (startTop + (gridSize * ch) + 4), 0xFFFFFF);
			this.drawString(fontRendererObj, "Z: "+(zOffset+te.zCoord)+" offSet: "+zOffset, (startLeft + ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) + ch, 0xFFFFFF);
		
		this.buttonList.add(new GuiButton(11, (startLeft + ((gridSize * 2) * cw)) + cw, startTop, 75, ch, "Grid Width +"));//prev block
		this.buttonList.add(new GuiButton(12, (startLeft + ((gridSize * 2) * cw)) + cw, startTop + ch, 75, ch, "Grid Width -"));//next block
		this.buttonList.add(new GuiButton(13, (startLeft + ((gridSize * 2) * cw)) + cw, startTop + ch * 2, 75, ch, "Grid Height +"));//prev block
		this.buttonList.add(new GuiButton(14, (startLeft + ((gridSize * 2) * cw)) + cw, startTop + ch * 3, 75, ch, "Grid Height -"));//next block
		
		this.buttonList.add(new GuiButton(15, (startLeft + ((gridSize * 2) * cw)) + cw, (startTop + ((gridSize * 2) * ch)) - ch * 3, 75, ch, selectionTitle[selectionMode]));//selection mode
		this.drawString(fontRendererObj, (AB == null ? "single Mode":(AB[0] == null ? "Point A not selected" : AB[0])).toString(), 
				(startLeft + ((gridSize * 2) * cw)) + cw * 8, (startTop + ((gridSize * 2) * ch)) - ch * 3, blockColor); // What block is selected
		
		this.buttonList.add(new GuiButton(0, (startLeft + ((gridSize * 2) * cw)) + cw, (startTop + ((gridSize * 2) * ch)) - ch * 2, 75, ch, "Start Building")); //right now does nothing, as it was hit and miss
		this.buttonList.add(new GuiButton(1, (startLeft + ((gridSize * 2) * cw)) + cw, (startTop + ((gridSize * 2) * ch)) - ch, 75, ch, "Send List")); //Adds the location based on x,y,z
		this.buttonList.add(new GuiButton(8, (startLeft + ((gridSize * 2) * cw)) + cw, (startTop + ((gridSize * 2) * ch)), 75, ch, "Clear")); //Clears all the locations
		
		this.buttonList.add(new GuiButton(9, (startLeft + ((gridSize * 2) * cw)) + cw + 75, startTop, 60, ch, "< Block"));//prev block
		this.buttonList.add(new GuiButton(10, (startLeft + ((gridSize * 2) * cw)) + cw * 6 + 75 + 60, startTop, 60, ch, "Block >"));//next block
		this.drawString(fontRendererObj, RotBlocks.blockTypeObjects[currentBlock].getLocalizedName(), (startLeft + ((gridSize * 2) * cw)) + cw + 75 + 60, startTop, blockColor); // What block is selected
		
		
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
				if (yOffset1 + te.yCoord == 255)break;
				else yOffset1++;
				break;
			case 7: // -Y down
				if (yOffset1 + te.yCoord == 0)break;
				else yOffset1--;
				break;
			case 8: // Clear, clears tileEntity list and this gui's List
				Rot.net.sendToServer(new BaseNodeRequestPacket(1,te.xCoord, te.yCoord, te.zCoord, 0, 0, 0, 0));
				locations.clear();
				break;
			case 9: // < moves block array left
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
			case 10: // > moves block array right
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
			int xB = ((GuiBaseNodeButton)button).x, zB = ((GuiBaseNodeButton)button).z;
			if (selectionMode == 1)// Range Mode
			{
				if (AB[0] == null)
				{						
					AB[0] = new Vector3f((float)xB, (float)this.yOffset1, (float)zB);
				}
				else if (AB[1] == null)
				{
					if (new Vector3f((float)xB, (float)this.yOffset1, (float)zB) != AB[0])
					{
						AB[1] = new Vector3f((float)xB, (float)this.yOffset1, (float)zB);
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
				addLocation(xB + te.xCoord, this.yOffset1 + te.yCoord, zB + te.zCoord);
			}			
		}
		updateButtons();
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
				Block worldBlock1 = te.getWorldObj().getBlock( x + te.xCoord + xOffset, yOffset1 + te.yCoord , z + te.zCoord + zOffset);
				Block worldBlock2 = te.getWorldObj().getBlock( x + te.xCoord + xOffset, yOffset2 + te.yCoord , z + te.zCoord + zOffset);
				String s1 = worldBlock1.equals(Blocks.air) ? "." : "+";		
				String s2 = worldBlock2.equals(Blocks.air) ? "." : "+";	
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
						((startLeft + (gridSize * cw)) + ((gridSize * cw) * 2) + cw + 3) + ((cw * x)), 
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
								if (ublt.y == this.yOffset2 + te.yCoord - depth1)
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
				coordButtons1[buttonArrayIndex].z = z + this.zOffset;
				coordButtons1[buttonArrayIndex].brightness = b1;
				coordButtons2[buttonArrayIndex].tex = t2;
				coordButtons2[buttonArrayIndex].x = x + this.xOffset;
				coordButtons2[buttonArrayIndex].z = z + this.zOffset;
				coordButtons2[buttonArrayIndex].brightness = b2;
				buttonArrayIndex++;
			}
		}
		indexCounter = INDEX_START;
	}
}
