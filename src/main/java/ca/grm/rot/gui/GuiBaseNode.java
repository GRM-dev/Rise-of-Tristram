package ca.grm.rot.gui;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import ca.grm.rot.blocks.TileEntityBaseBuilder;
import ca.grm.rot.comms.BaseNodeRequestPacket;
import ca.grm.rot.libs.UtilityBlockLocationType;
import ca.grm.rot.libs.UtilityFunctions;

public class GuiBaseNode extends GuiContainer {
	public static final ResourceLocation	texture			= new ResourceLocation(
																	Rot.MOD_ID
																			.toLowerCase(),
																	"textures/gui/largeBase.png");

	private EntityPlayer					player;
	private TileEntityBaseBuilder				te;
	private int								cw				= 16;											// control
																											// Width
	private int								ch				= 16;											// control
																											// Height
																											
	// Grid Values
	private int								gridSize		= 0;
	private int								gS				= 5;
	private int								gridSizeOffset	= 0;
	private int								xOffset			= 0;
	private int								yOffset1		= 0, yOffset2 = 0;
	private int								zOffset			= 0;

	// Block Placement Values
	private int								currentBlock	= 0;
	private int								currentMeta		= 0;
	private int								blockColor		= UtilityFunctions.blockTypeColors[this.currentBlock];

	// Selection and List Values
	private ArrayList						locations		= new ArrayList<UtilityBlockLocationType>();
	private Boolean							listGotten		= false;
	private int								defaultColor	= 0x444444;
	private int								selectionMode	= 0;
	private String[]						selectionTitle	= {
			"Single", "Rectangle A - B"						};
	private Vector3f[]						AB				= new Vector3f[2];

	// Misc.
	private GuiBaseNodeButton[]				coordButtons1;
	private GuiBaseNodeButton[]				coordButtons2;
	private int								INDEX_START		= 17;
	private int								indexCounter	= this.INDEX_START;
	private int								startLeft		= this.cw * 2,
			startTop = this.ch * 2;
	private int teX, teY, teZ;

	public GuiBaseNode(TileEntityBaseBuilder tileEntity, EntityPlayer player) {
		super(new ContainerNull());

		this.player = player;
		this.te = tileEntity;
		this.locations = this.te.locations;
		this.xSize = 227;
		this.ySize = 226;
		this.teX = te.getPos().getX();
		this.teY = te.getPos().getY();
		this.teZ = te.getPos().getZ();
	}

	/** Button Clicks **/
	@Override
	protected void actionPerformed(GuiButton button) {
		// Anything below the generated buttons for grid clicking
		if (button.id < this.indexCounter) {
			switch (button.id) {
				case 0 : // Start Building
					Rot.net.sendToServer(new BaseNodeRequestPacket(2, this.teX,
							this.teY, this.teZ, 0, 0, 0, 0));
					break;
				case 1 : // Send List
					if (!this.locations.isEmpty()) {
						UtilityBlockLocationType ublt;
						for (int l = 0; l < this.locations.size(); l++) {
							ublt = (UtilityBlockLocationType) this.locations.get(l);
							Rot.net.sendToServer(new BaseNodeRequestPacket(0,
									this.teX, this.teY, this.teZ,
									ublt.x, ublt.y, ublt.z, Block
											.getIdFromBlock(ublt.block)));
						}
					}
					this.locations.clear();
					break;
				case 2 : // -X left/west
					Rot.net.sendToServer(new BaseNodeRequestPacket(1, this.teX,
							this.teY, this.teZ, 0, 0, 0, 0));
					this.locations.clear();
					break;
				case 3 : // +X right/east
					if (this.currentBlock == 0) {
						this.currentBlock = UtilityFunctions.blockTypeObjects.length - 1;
					} else {
						this.currentBlock--;
					}
					this.blockColor = UtilityFunctions.blockTypeColors[this.currentBlock];
					break;
				case 4 : // -Z forward/north
					if (this.currentBlock == (UtilityFunctions.blockTypeObjects.length - 1)) {
						this.currentBlock = 0;
					} else {
						this.currentBlock++;
					}
					this.blockColor = UtilityFunctions.blockTypeColors[this.currentBlock];
					break;
				case 5 : // +Z backwards/south
					this.gridSizeOffset++;
					break;
				case 6 : // +Y up
					this.gridSizeOffset--;
					break;
				case 7 : // -Y down
					this.selectionMode = this.selectionMode == 0 ? 1 : 0;
					if (this.selectionMode == 0) {
						this.AB = new Vector3f[2];
					}
					break;
				case 8 : // Clear, clears tileEntity list and this gui's List
					this.xOffset--;
					break;
				case 9 : // < moves block array left
					this.xOffset++;
					break;
				case 10 : // > moves block array right
					this.zOffset--;
					break;
				case 11 : // Increase grid width
					this.zOffset++;
					break;
				case 12 : // Decrease
					if ((this.yOffset1 + this.teY) == 255) {
						break;
					} else {
						this.yOffset1++;
					}
					break;
				case 13 :
					if ((this.yOffset1 + this.teY) == 0) {
						break;
					} else {
						this.yOffset1--;
					}
					break;
				case 14 :
					if ((this.yOffset2 + this.teY) == 255) {
						break;
					} else {
						this.yOffset2++;
					}
					break;
				case 15 :
					if ((this.yOffset2 + this.teY) == 0) {
						break;
					} else {
						this.yOffset2--;
					}
					break;
				case 16 :
					Rot.net.sendToServer(new BaseNodeRequestPacket(3, this.teX,
							this.teY, this.teZ, 0, 0, 0, 0));
					break;
			}
		}
		// Start code for generated buttons
		else {
			int xB = ((GuiBaseNodeButton) button).x, yB = ((GuiBaseNodeButton) button).y, zB = ((GuiBaseNodeButton) button).z;
			if (this.selectionMode == 1)// Range Mode
			{
				if (this.AB[0] == null) {
					this.AB[0] = new Vector3f(xB, yB, zB);
				} else if (this.AB[1] == null) {
					if (new Vector3f(xB, yB, zB) != this.AB[0]) {
						this.AB[1] = new Vector3f(xB, yB, zB);
					}
				}
				if ((this.AB[0] != null) && (this.AB[1] != null)) {
					int xh = (this.AB[0].x > this.AB[1].x ? (int) this.AB[0].x
							: (int) this.AB[1].x);
					int xl = (this.AB[0].x < this.AB[1].x ? (int) this.AB[0].x
							: (int) this.AB[1].x);

					int yh = (this.AB[0].y > this.AB[1].y ? (int) this.AB[0].y
							: (int) this.AB[1].y);
					int yl = (this.AB[0].y < this.AB[1].y ? (int) this.AB[0].y
							: (int) this.AB[1].y);

					int zh = (this.AB[0].z > this.AB[1].z ? (int) this.AB[0].z
							: (int) this.AB[1].z);
					int zl = (this.AB[0].z < this.AB[1].z ? (int) this.AB[0].z
							: (int) this.AB[1].z);
					for (int xs = xh; xs >= xl; xs--) {
						for (int zs = zh; zs >= zl; zs--) {
							for (int ys = yh; ys >= yl; ys--) {
								addLocation(xs + this.teX, ys + this.teY, zs
										+ this.teZ);
							}
						}
					}
					this.AB = new Vector3f[2];
				}

			} else// single select
			{
				addLocation(xB + this.teX, yB + this.teY, zB + this.teZ);
			}
		}
		updateButtons();
		this.updateScreen();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		this.gridSize = this.gS + this.gridSizeOffset;

		if (this.locations.isEmpty()) {
			this.locations = this.te.locations;
		}
		if (this.coordButtons1 == null) {
			updateButtons();
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int gx1 = this.startLeft - 3, gx2 = (this.startLeft + (this.gridSize * this.cw))
				+ (this.gridSize * this.cw) + this.cw + 3, gy1 = this.startTop - 3, gy2 = gy1
				+ (this.gridSize * this.ch) + (this.gridSize * this.ch) + this.ch + 6;
		int gw = ((this.gridSize * 2) * this.cw) + (this.cw + 6), gh = ((this.gridSize * 2) * this.ch)
				+ (this.ch + 6);
		// Drawing Map boarders
		// Map 1
		drawTexturedModalRect(gx1, gy1, 0, 0, gw / 2, gh / 2);// upper left
		drawTexturedModalRect(gx1 + (gw / 2), gy1, 227 - (gw / 2), 0, gw / 2, gh / 2);// upper
																						// right

		drawTexturedModalRect(gx1, gy1 + (gh / 2), 0, 226 - (gh / 2), gw / 2, gh / 2);// lower
																						// left
		drawTexturedModalRect(gx1 + (gw / 2), gy1 + (gh / 2), 227 - (gw / 2),
				226 - (gh / 2), gw / 2, gh / 2);// lower right

		// Map 2
		drawTexturedModalRect(gx2, gy1, 0, 0, gw / 2, gh / 2);// upper left
		drawTexturedModalRect(gx2 + (gw / 2), gy1, 227 - (gw / 2), 0, gw / 2, gh / 2);// upper
																						// right

		drawTexturedModalRect(gx2, gy1 + (gh / 2), 0, 226 - (gh / 2), gw / 2, gh / 2);// lower
																						// left
		drawTexturedModalRect(gx2 + (gw / 2), gy1 + (gh / 2), 227 - (gw / 2),
				226 - (gh / 2), gw / 2, gh / 2);// lower right
		
		this.buttonList.clear();
		// Start with main control buttons

		this.buttonList.add(new GuiButton(0, gx1, gy2, 75, this.ch, "Start Building")); // right
																						// now
																						// does
																						// nothing,
																						// as
																						// it
																						// was
																						// hit
																						// and
																						// miss
		this.buttonList.add(new GuiButton(1, gx1 + 75, gy2, 75, this.ch, "Send List")); // Adds
																						// the
																						// location
																						// based
																						// on
																						// x,y,z
		this.buttonList.add(new GuiButton(2, gx1 + (75 * 2), gy2, 75, this.ch, "Clear")); // Clears
																							// all
																							// the
																							// locations
		this.buttonList.add(new GuiButton(3, gx1, gy2 + this.ch, 60, this.ch, "< Block"));// prev
																							// block
		this.buttonList.add(new GuiButton(4, gx1 + 60, gy2 + this.ch, 60, this.ch,
				"Block >"));// next block
		this.buttonList.add(new GuiButton(5, gx1 + (75 * 3), gy2, 60, this.ch, "Grid +"));// prev
																							// block
		this.buttonList.add(new GuiButton(6, gx1 + (75 * 3) + 60, gy2, 60, this.ch,
				"Grid -"));// next block
		this.buttonList.add(new GuiButton(7, gx1 + (60 * 2), gy2 + this.ch, 90, this.ch,
				this.selectionTitle[this.selectionMode]));// selection mode
		this.buttonList.add(new GuiButton(16, gx1 + (60 * 2) + 90, gy2 + this.ch, 75,
				this.ch, "Get List"));// selection mode

		this.buttonList.add(new GuiButton(8, (gx1 + gx2 + gw) / 2, gy1 - this.ch,
				this.cw, this.ch, "<"));// X left
		this.buttonList.add(new GuiButton(9, ((gx1 + gx2 + gw) / 2) + (this.cw * 3), gy1
				- this.ch, this.cw, this.ch, ">"));// X right
		this.buttonList.add(new GuiButton(10, ((gx1 + gx2 + gw) / 2) + this.cw, gy1
				- this.ch, this.cw, this.ch, "^"));// Z 'forward'
		this.buttonList.add(new GuiButton(11, ((gx1 + gx2 + gw) / 2) + (this.cw * 2), gy1
				- this.ch, this.cw, this.ch, "v"));// Z 'back'
		this.buttonList.add(new GuiButton(12, gx1 - this.cw,
				(this.startTop + (this.gridSize * this.ch)) - (this.ch / 2), this.cw,
				this.ch, "Y1+"));// Y1 up
		this.buttonList.add(new GuiButton(13, gx1 - this.cw,
				(this.startTop + (this.gridSize * this.ch)) + (this.ch / 2), this.cw,
				this.ch, "Y1-"));// Y1 down
		this.buttonList.add(new GuiButton(14, gx2 + gw,
				(this.startTop + (this.gridSize * this.ch)) - (this.ch / 2), this.cw,
				this.ch, "Y2+"));// Y2 up
		this.buttonList.add(new GuiButton(15, gx2 + gw,
				(this.startTop + (this.gridSize * this.ch)) + (this.ch / 2), this.cw,
				this.ch, "Y2-"));// Y2 down
		
		// Visual information on location
		/*
		 * this.drawString(fontRendererObj, "OffSet: "+xOffset, (startLeft +
		 * ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) -
		 * ch, 0xFFFFFF);
		 * this.drawString(fontRendererObj, "OffSet: "+yOffset1, (startLeft +
		 * ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4),
		 * 0xFFFFFF);
		 * this.drawString(fontRendererObj, "OffSet: "+yOffset2, (startLeft +
		 * ((gridSize * 2) * cw)) + cw * 11, (startTop + (gridSize * ch) + 4),
		 * 0xFFFFFF);
		 * this.drawString(fontRendererObj, "OffSet: "+zOffset, (startLeft +
		 * ((gridSize * 2) * cw)) + cw * 6, (startTop + (gridSize * ch) + 4) +
		 * ch, 0xFFFFFF);
		 * this.drawString(fontRendererObj, (AB == null ? "single Mode":(AB[0]
		 * == null ? "Point A not selected" : AB[0])).toString(),
		 * (startLeft + ((gridSize * 2) * cw)) + cw * 8, (startTop + ((gridSize
		 * * 2) * ch)) - ch * 3, blockColor); // What block is selected
		 */
		
		this.drawString(this.fontRendererObj,
				UtilityFunctions.blockTypeObjects[this.currentBlock].getLocalizedName(), gx1,
				gy2 + (this.ch * 2), this.blockColor); // What block is selected
		
		for (GuiBaseNodeButton element : this.coordButtons1) {
			this.buttonList.add(element);
		}
		for (GuiBaseNodeButton element : this.coordButtons2) {
			this.buttonList.add(element);
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par1 == 'a') {
			this.xOffset--;
			updateButtons();
		} else if (par1 == 'd') {
			this.xOffset++;
			updateButtons();
		} else if (par1 == 'w') {
			this.zOffset--;
			updateButtons();
		} else if (par1 == 's') {
			this.zOffset++;
			updateButtons();
		} else if (par2 == this.mc.gameSettings.keyBindJump.getKeyCode()) {
			if ((this.yOffset1 + this.teY) == 255) {
				return;
			} else {
				this.yOffset1++;
			}
			updateButtons();
		} else if (par2 == Keyboard.KEY_DOWN) {
			if ((this.yOffset2 + this.teY) == 0) {
				return;
			} else {
				this.yOffset2--;
			}
			updateButtons();
		} else if (par2 == Keyboard.KEY_UP) {
			if ((this.yOffset2 + this.teY) == 255) {
				return;
			} else {
				this.yOffset2++;
			}
			updateButtons();
		} else if (par2 == this.mc.gameSettings.keyBindSneak.getKeyCode()) {
			if ((this.yOffset1 + this.teY) == 0) {
				return;
			} else {
				this.yOffset1--;
			}
			updateButtons();
		}

		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
			this.mc.thePlayer.closeScreen();
		}

	}
	
	private void addLocation(int x, int y, int z) {
		if (this.locations.isEmpty()) {
			this.locations.add(new UtilityBlockLocationType(x, y, z,
					UtilityFunctions.blockTypeObjects[this.currentBlock]));
		} else {
			boolean dupeObject = false;
			for (int l = 0; l < this.locations.size(); l++) {
				UtilityBlockLocationType ublt = (UtilityBlockLocationType) this.locations
						.get(l);
				if ((ublt.x == x) && (ublt.y == y) && (ublt.z == z)) {
					ublt.block = UtilityFunctions.blockTypeObjects[this.currentBlock];
					this.locations.set(l, ublt);
					dupeObject = true;
				}
			}
			// If the coordinate is fresh add it in
			if (!dupeObject) {
				this.locations.add(new UtilityBlockLocationType(x, y, z,
						UtilityFunctions.blockTypeObjects[this.currentBlock]));
			}
		}
	}

	// Updates the buttons ...startLeft + ((gridSizeX * 2) * cw)) + cw
	private void updateButtons() {
		if ((this.coordButtons1 == null)
				|| (this.coordButtons1.length != (((this.gridSize * 2) + 1) * ((this.gridSize * 2) + 1)))) {
			this.coordButtons1 = new GuiBaseNodeButton[((this.gridSize * 2) + 1)
					* ((this.gridSize * 2) + 1)];
		}

		if ((this.coordButtons2 == null)
				|| (this.coordButtons2.length != (((this.gridSize * 2) + 1) * ((this.gridSize * 2) + 1)))) {
			this.coordButtons2 = new GuiBaseNodeButton[((this.gridSize * 2) + 1)
					* ((this.gridSize * 2) + 1)];
		}

		int buttonArrayIndex = 0;
		UtilityBlockLocationType ublt;
		for (int x = this.gridSize; x >= -this.gridSize; x--) {
			for (int z = this.gridSize; z >= -this.gridSize; z--) {
//				IIcon t1 = null;
//				IIcon t2 = null;
				int c1 = this.defaultColor;
				int c2 = this.defaultColor;
				String s1 = "x";
				String s2 = "x";
				Block worldBlock1 = this.te.getWorld()
						.getBlockState(new BlockPos(x + this.teX + this.xOffset,
								this.yOffset1 + this.teY,
								z + this.teZ + this.zOffset)).getBlock();
				Block worldBlock2 = this.te.getWorld()
						.getBlockState(new BlockPos(x + this.teX + this.xOffset,
								this.yOffset2 + this.teY,
								z + this.teZ + this.zOffset)).getBlock();
				if (this.selectionMode == 1) {
					if ((this.AB != null) && (this.AB[0] != null)
							&& ((x + this.xOffset) == (int) this.AB[0].x)
							&& ((z + this.zOffset) == (int) this.AB[0].z)) {
						s1 = "X";
						s2 = "X";
					} else {
						s1 = worldBlock1.equals(Blocks.air) ? "." : "+";
						s2 = worldBlock2.equals(Blocks.air) ? "." : "+";
					}
				} else {
					s1 = worldBlock1.equals(Blocks.air) ? "." : "+";
					s2 = worldBlock2.equals(Blocks.air) ? "." : "+";
				}
				if (!this.locations.isEmpty()) {
					UtilityBlockLocationType ubltS;
					// Look through every Item of the list
					for (int ubltl = 0; ubltl < this.locations.size(); ubltl++) {
						ubltS = (UtilityBlockLocationType) this.locations.get(ubltl);
						if (ubltS.y == (this.yOffset1 + this.teY)) {
							if ((ubltS.x == (x + this.xOffset + this.teX))
									&& (ubltS.z == (z + this.zOffset + this.teZ))) {
								s1 = "*";
								c1 = ubltS.block.getRenderColor(ubltS.block.getDefaultState());
								//t1 = ubltS.block.getIcon(1, 0);
								break;
							}
						}
					}
					for (int ubltl = 0; ubltl < this.locations.size(); ubltl++) {
						ubltS = (UtilityBlockLocationType) this.locations.get(ubltl);
						if (ubltS.y == (this.yOffset2 + this.teY)) {
							if ((ubltS.x == (x + this.xOffset + this.teX))
									&& (ubltS.z == (z + this.zOffset + this.teZ))) {
								s2 = "*";
								c2 = ubltS.block.getRenderColor(ubltS.block.getDefaultState());
								//t2 = ubltS.block.getIcon(1, 0);
								break;
							}
						}
					}
				}
				this.coordButtons1[buttonArrayIndex] = new GuiBaseNodeButton(
						this.indexCounter++, (this.startLeft + (this.gridSize * this.cw))
								+ ((this.cw * x)),
						(this.startTop + (this.gridSize * this.ch)) + ((this.ch * z)),
						this.cw, this.ch, s1);

				this.coordButtons2[buttonArrayIndex] = new GuiBaseNodeButton(
						this.indexCounter++,
						((this.startLeft + (this.gridSize * this.cw))
								+ ((this.gridSize * this.cw) * 2) + this.cw + 6)
								+ ((this.cw * x)),
						(this.startTop + (this.gridSize * this.ch)) + ((this.ch * z)),
						this.cw, this.ch, s2);
				if (((x + this.xOffset) == 0) && (this.yOffset1 == 0)
						&& ((z + this.zOffset) == 0)) {
					this.coordButtons1[buttonArrayIndex].packedFGColour = 0x0000FF;
				} else {
					this.coordButtons1[buttonArrayIndex].packedFGColour = c1 == this.defaultColor
							? (worldBlock1.equals(Blocks.air) ? 0x00CCFF : worldBlock1
									.getRenderColor(worldBlock1.getDefaultState())) : c1;
				}
				if (((x + this.xOffset) == 0) && (this.yOffset2 == 0)
						&& ((z + this.zOffset) == 0)) {
					this.coordButtons2[buttonArrayIndex].packedFGColour = 0x0000FF;
				} else {
					this.coordButtons2[buttonArrayIndex].packedFGColour = c2 == this.defaultColor
							? (worldBlock2.equals(Blocks.air) ? 0x00CCFF : worldBlock2
									.getRenderColor(worldBlock2.getDefaultState())) : c2;
				}
				//float b1 = 1.0f, b2 = 1.0f;
				//int depth1 = 0, depth2 = 0;
				/**Added a shadow for giving off "depth" in the button menu**/
				/*
				while (t1 == null) {
					t1 = this.te
							.getWorldObj()
							.getBlock(x + this.te.xCoord + this.xOffset,
									(this.yOffset1 + this.te.yCoord) - depth1,
									z + this.te.zCoord + this.zOffset).getIcon(1, 0);
					if (t1 == null) {
						if (!this.locations.isEmpty()) {
							// Look through every Item of the list
							for (int ubltl = 0; ubltl < this.locations.size(); ubltl++) {
								ublt = (UtilityBlockLocationType) this.locations
										.get(ubltl);
								if (ublt.y == ((this.yOffset1 + this.te.yCoord) - depth1)) {
									if ((ublt.x == (x + this.xOffset + this.te.xCoord))
											&& (ublt.z == (z + this.zOffset + this.te.zCoord))) {
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
				while (t2 == null) {
					t2 = this.te
							.getWorldObj()
							.getBlock(x + this.te.xCoord + this.xOffset,
									(this.yOffset2 + this.te.yCoord) - depth2,
									z + this.te.zCoord + this.zOffset).getIcon(1, 0);
					if (t2 == null) {
						if (!this.locations.isEmpty()) {
							// Look through every Item of the list
							for (int ubltl = 0; ubltl < this.locations.size(); ubltl++) {
								ublt = (UtilityBlockLocationType) this.locations
										.get(ubltl);
								if (ublt.y == ((this.yOffset2 + this.te.yCoord) - depth2)) {
									if ((ublt.x == (x + this.xOffset + this.te.xCoord))
											&& (ublt.z == (z + this.zOffset + this.te.zCoord))) {
										t2 = ublt.block.getIcon(1, 0);
										break;
									}
								}
							}
						}
						MathHelper.clamp_float(b2 -= 0.2f, 0, 1f);
						depth2++;
					}
				}*/
				//this.coordButtons1[buttonArrayIndex].tex = t1;
				this.coordButtons1[buttonArrayIndex].x = x + this.xOffset;
				this.coordButtons1[buttonArrayIndex].y = this.yOffset1;
				this.coordButtons1[buttonArrayIndex].z = z + this.zOffset;
				//this.coordButtons1[buttonArrayIndex].brightness = b1;
				this.coordButtons1[buttonArrayIndex].block = worldBlock1;
				
				//this.coordButtons2[buttonArrayIndex].tex = t2;
				this.coordButtons2[buttonArrayIndex].x = x + this.xOffset;
				this.coordButtons2[buttonArrayIndex].y = this.yOffset2;
				this.coordButtons2[buttonArrayIndex].z = z + this.zOffset;
				//this.coordButtons2[buttonArrayIndex].brightness = b2;
				this.coordButtons2[buttonArrayIndex].block = worldBlock2;
				buttonArrayIndex++;
			}
		}
		this.indexCounter = this.INDEX_START;
		this.updateScreen();
	}
}
