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
			new ResourceLocation(Rot.MODID.toLowerCase(), "textures/gui/magicBase.png");
	
	private EntityPlayer player;
	private TileEntityBaseNode te;
	private int cw = 16; //control Width
	private int ch = 16; //control Height
	
	//Grid Values
	private int gridSizeX = 0;
	private int gridSizeZ = 0;
	private int gX = 7, gZ = 7;
	private int gridXOffset = 0;
	private int gridZOffset = 0;
	private int xOffset = 0;
	private int yOffset = 0;
	private int zOffset = 0;
	
	//Block Placement Values
	private int currentBlock = 0;
	private int currentMeta = 0;
	private String block = RotBlocks.blockTypes[currentBlock];
	private int blockColor = RotBlocks.blockTypeColors[currentBlock];
	
	//Selection and List Values
	private ArrayList locations = new ArrayList<UtilityBlockLocationType>();
	private Boolean listGotten = false;
	private int defaultColor = 0x444444;
	private int selectionMode = 0;
	private String[] selectionTitle = {"Single","Rectangle A - B"};
	private Vector3f[] AB = new Vector3f[2];
	
	//Misc.	
	private GuiBaseNodeButton[] coordButtons;
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
			if (yOffset + te.yCoord == 255)return;
			else yOffset++;
			updateButtons();
		}
		else if (par2 == this.mc.gameSettings.keyBindSneak.getKeyCode())
		{
			if (yOffset + te.yCoord == 0)return;
			else yOffset--;
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
		startLeft = Minecraft.getMinecraft().displayWidth / (Minecraft.getMinecraft().displayWidth / 10); 
		startTop = Minecraft.getMinecraft().displayHeight / (Minecraft.getMinecraft().displayHeight / 16);
		
		gridSizeX = gX + gridXOffset;
		gridSizeZ = gZ + gridZOffset;
		
		if (coordButtons == null)
		{
			updateButtons();
		}		
		GL11.glColor4f(1F, 1F, 1F, 1F);

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
				Rot.net.sendToServer(new BaseNodeRequestPacket(2,te.xCoord, te.yCoord, te.zCoord, 0, 0, 0, null));
				break;
			case 1: // Send List
				if (!locations.isEmpty())
				{
					int listIndex = 0;
					UtilityBlockLocationType ublt;
					for (int l = 0; l < locations.size(); l++)
					{
						ublt = (UtilityBlockLocationType) locations.get(l);
						System.out.println("Sending: "+ublt.x+";"+ublt.y+";"+ublt.z);
						Rot.net.sendToServer(new BaseNodeRequestPacket(0,te.xCoord,te.yCoord,te.zCoord,ublt.x,ublt.y,ublt.z,new ItemStack(ublt.block)));
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
				if (yOffset + te.yCoord == 255)break;
				else yOffset++;
				break;
			case 7: // -Y down
				if (yOffset + te.yCoord == 0)break;
				else yOffset--;
				break;
			case 8: // Clear, clears tileEntity list and this gui's List
				Rot.net.sendToServer(new BaseNodeRequestPacket(1,te.xCoord, te.yCoord, te.zCoord, 0, 0, 0, null));
				locations.clear();
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
			int xB = ((GuiBaseNodeButton)button).x, zB = ((GuiBaseNodeButton)button).z;
			if (selectionMode == 1)// Range Mode
			{
				if (AB[0] == null)
				{						
					AB[0] = new Vector3f((float)xB, (float)this.yOffset, (float)zB);
				}
				else if (AB[1] == null)
				{
					if (new Vector3f((float)xB, (float)this.yOffset, (float)zB) != AB[0])
					{
						AB[1] = new Vector3f((float)xB, (float)this.yOffset, (float)zB);
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
				addLocation(xB + te.xCoord, this.yOffset + te.yCoord, zB + te.zCoord);
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
	
	//Updates the buttons
	private void updateButtons()
	{
		if (coordButtons == null || coordButtons.length != (gridSizeX * 2 + 1) * (gridSizeZ * 2 + 1))
		{
			coordButtons = new GuiBaseNodeButton[(gridSizeX * 2 + 1) * (gridSizeZ * 2 + 1)];
		}
		
		int buttonArrayIndex = 0;
		UtilityBlockLocationType ublt;
		for (int x = gridSizeX; x >= -gridSizeX; x--)
		{
			for (int z = gridSizeZ; z >= -gridSizeZ;z--)
			{
				IIcon t = null;
				int c = defaultColor;	
				Block worldBlock = te.getWorldObj().getBlock( x + te.xCoord + xOffset, yOffset + te.yCoord , z + te.zCoord + zOffset);
				String s = worldBlock.equals(Blocks.air) ? "." : "+";				
				if (!locations.isEmpty())
				{
					//Look through every Item of the list
					for (int ubltl = 0; ubltl < locations.size(); ubltl++)
					{
						ublt = (UtilityBlockLocationType) locations.get(ubltl);
						if (ublt.y == this.yOffset + te.yCoord)
						{
							if (ublt.x == x + this.xOffset + te.xCoord && ublt.z == z + this.zOffset + te.zCoord)
							{
								s = "*";
								c = ublt.block.getMapColor(0).colorValue;
								t = ublt.block.getIcon(1, 0);
								break;
							}
						}
					}
				}
				coordButtons[buttonArrayIndex] = new GuiBaseNodeButton(indexCounter++, 
						(startLeft + (gridSizeX * cw)) + ((cw * x)), 
						(startTop + (gridSizeZ * ch)) + ((ch * z)), 
						cw, 
						ch, 
						s);
				if (x + this.xOffset == 0 && yOffset == 0 && z + this.zOffset == 0)coordButtons[buttonArrayIndex].packedFGColour = 0x0000FF;
				else coordButtons[buttonArrayIndex].packedFGColour = c == defaultColor ? (worldBlock.equals(Blocks.air) ? 0x00CCFF : worldBlock.getMapColor(0).colorValue) : c;				
				float b = 1.0f;
				int depth = 0;
				while (t == null)
				{
					t = te.getWorldObj().getBlock(x + te.xCoord + xOffset, yOffset + te.yCoord - depth, z + te.zCoord + zOffset).getIcon(1, 0);					
					if (t == null)
					{
						MathHelper.clamp_float(b -= 0.2f, 0, 1f);
						depth++;
					}
				}
				coordButtons[buttonArrayIndex].tex = t;
				coordButtons[buttonArrayIndex].x = x + this.xOffset;
				coordButtons[buttonArrayIndex].z = z + this.zOffset;
				coordButtons[buttonArrayIndex].brightness = b;
				buttonArrayIndex++;
			}
		}
		indexCounter = INDEX_START;
	}
}
