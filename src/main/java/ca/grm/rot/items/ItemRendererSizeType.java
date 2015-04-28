package ca.grm.rot.items;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector3f;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;

public class ItemRendererSizeType implements IItemRenderer {
	private final boolean		isEntity;
	private final boolean		noEntityTranslation;
	private static final int	toolIcons	= 10;

	public ItemRendererSizeType() {
		this(false, false);
	}

	public ItemRendererSizeType(boolean isEntity) {
		this(isEntity, false);
	}

	public ItemRendererSizeType(boolean isEntity, boolean noEntityTranslation) {
		this.isEntity = isEntity;
		this.noEntityTranslation = noEntityTranslation;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// return type == ItemRenderType.EQUIPPED || type ==
		// ItemRenderType.EQUIPPED_FIRST_PERSON;
		if (!item.hasTagCompound()) { return false; }
		
		switch (type) {
			case ENTITY :
				return true;
			case EQUIPPED :
				GL11.glTranslatef(0.03f, 0F, -0.09375F);
			case EQUIPPED_FIRST_PERSON :
				return !this.isEntity;
			case INVENTORY :
				return true;
			default :
			case FIRST_PERSON_MAP :
				return false;
		}
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Item tool = item.getItem();
		WeaponSlash fs = null;
		WeaponHack fh = null;
		WeaponBlunt fb = null;
		WeaponPierce fp = null;
		WeaponStaff staff = null;

		if (tool instanceof WeaponSlash) {
			fs = (WeaponSlash) tool;
		} else if (tool instanceof WeaponHack) {
			fh = (WeaponHack) tool;
		} else if (tool instanceof WeaponBlunt) {
			fb = (WeaponBlunt) tool;
		} else if (tool instanceof WeaponPierce) {
			fp = (WeaponPierce) tool;
		} else if (tool instanceof WeaponStaff) {
			staff = (WeaponStaff) tool;
		}

		boolean isInventory = type == ItemRenderType.INVENTORY;
		int iconParts = toolIcons;
		int[] partColors = null;
		float f5;
		float f6;
		float f7;
		IIcon[] parts = new IIcon[iconParts];
		if (fs != null) {
			parts = fs.getIcons(item);
			iconParts = parts.length;
			partColors = fs.getLayerColors(item);
		} else if (fh != null) {
			parts = fh.getIcons(item);
			iconParts = parts.length;
			partColors = fh.getLayerColors(item);
		} else if (fb != null) {
			parts = fb.getIcons(item);
			iconParts = parts.length;
			partColors = fb.getLayerColors(item);
		} else if (fp != null) {
			parts = fp.getIcons(item);
			iconParts = parts.length;
			partColors = fp.getLayerColors(item);
		} else if (staff != null) {
			parts = staff.getIcons(item);
			iconParts = parts.length;
			partColors = staff.getLayerColors(item);
		} else {
			Entity ent = null;
			if (data.length > 1) {
				ent = (Entity) data[1];
			}
			
			IIcon[] tempParts = new IIcon[iconParts];
			label : {
				if (!isInventory && (ent instanceof EntityPlayer)) {
					EntityPlayer player = (EntityPlayer) ent;
					ItemStack itemInUse = player.getItemInUse();
					if (itemInUse != null) {
						int useCount = player.getItemInUseCount();
						for (int i = iconParts; i-- > 0;) {
							tempParts[i] = tool.getIcon(item, i, player, itemInUse,
									useCount);
						}
						break label;
					}
				}
				for (int i = iconParts; i-- > 0;) {
					tempParts[i] = tool.getIcon(item, i);
				}
			}

			int count = 0;
			
			for (int i = 0; i < iconParts; ++i) {
				IIcon part = tempParts[i];
				if (part == null) {
					++count;
				} else {
					parts[i - count] = part;
				}
			}
			iconParts -= count;
			
			if (iconParts <= 0) {
				iconParts = 1;
				// TODO: assign default sprite
				// parts = new Icon[]{ defaultSprite };
			}
		}
		
		Tessellator tess = Tessellator.instance;
		float[] xMax = new float[iconParts];
		float[] yMin = new float[iconParts];
		float[] xMin = new float[iconParts];
		float[] yMax = new float[iconParts];
		float depth = 1f / 16f;
		
		float[] width = new float[iconParts];
		float[] height = new float[iconParts];
		float[] xDiff = new float[iconParts];
		float[] yDiff = new float[iconParts];
		float[] xSub = new float[iconParts];
		float[] ySub = new float[iconParts];
		for (int i = 0; i < iconParts; ++i) {
			IIcon icon = parts[i];
			xMin[i] = icon.getMinU();
			xMax[i] = icon.getMaxU();
			yMin[i] = icon.getMinV();
			yMax[i] = icon.getMaxV();
			width[i] = icon.getIconWidth();
			height[i] = icon.getIconHeight();
			xDiff[i] = xMin[i] - xMax[i];
			yDiff[i] = yMin[i] - yMax[i];
			xSub[i] = (0.5f * (xMax[i] - xMin[i])) / width[i];
			ySub[i] = (0.5f * (yMax[i] - yMin[i])) / height[i];
		}
		GL11.glPushMatrix();
		
		if (type == ItemRenderType.INVENTORY) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			tess.startDrawingQuads();
			for (int i = 0; i < iconParts; ++i) {				
				if (partColors != null) {
					try {
						f5 = ((partColors[i] >> 16) & 255) / 255.0F;
						f6 = ((partColors[i] >> 8) & 255) / 255.0F;
						f7 = (partColors[i] & 255) / 255.0F;
						tess.setColorOpaque_F(f5, f6, f7);
					}
					catch (IndexOutOfBoundsException e) {
						System.out.println(e.getMessage());
						System.out.println(item.getDisplayName());
					}
				}
				tess.addVertexWithUV(0, 16, 0, xMin[i], yMax[i]);
				tess.addVertexWithUV(16, 16, 0, xMax[i], yMax[i]);
				tess.addVertexWithUV(16, 0, 0, xMax[i], yMin[i]);
				tess.addVertexWithUV(0, 0, 0, xMin[i], yMin[i]);				
			}
			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		} else {
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			String size = UtilityNBTHelper.getString(item, Rot.MODID + "weaponSize");
			switch (type) {
				case EQUIPPED_FIRST_PERSON :
					break;
				case EQUIPPED :
					// GL11.glTranslatef(0, -4 / 16f, 0);
					String weaponType = UtilityNBTHelper.getString(item, Rot.MODID
							+ "weaponType");
					float weaponSize = 1.0f;
					Vector3f trans3f = new Vector3f(-0.15f, 0f, 0f);
					
					if (weaponType.equals("slash") || weaponType.equals("blunt")) {
						if (size.equals("small")) {
							trans3f.x = 0.1f;
							trans3f.y = -0.025f;
							weaponSize = 0.75f;
						} else if (size.equals("big")) {
							trans3f.x = -0.425f;
							trans3f.y = -0.175f;
							weaponSize = 1.35f;
						} else if (size.equals("large")) {
							trans3f.x = -0.6749f;
							trans3f.y = -0.25f;
							weaponSize = 1.65f;
						} else if (size.equals("great")) {
							trans3f.x = -0.949f;
							trans3f.y = -0.35f;
							weaponSize = 2.0f;
						} else {
							trans3f.x = -0.0999f;
							trans3f.y = -0.075f;
						}
					} else if (weaponType.equals("hack")) {
						if (size.equals("small")) {
							trans3f.x = -0.175f;
							trans3f.y = -0.15f;
							weaponSize = 1.1f;
						} else if (size.equals("big")) {
							trans3f.x = -0.55f;
							trans3f.y = -0.2f;
							weaponSize = 1.55f;
						} else if (size.equals("large")) {
							trans3f.x = -0.899f;
							trans3f.y = -0.3f;
							weaponSize = 1.95f;
						} else if (size.equals("great")) {
							trans3f.x = -1.299f;
							trans3f.y = -0.4f;
							weaponSize = 2.4f;
						} else {
							trans3f.x = -0.35f;
							trans3f.y = -0.15f;
							weaponSize = 1.3f;
						}
					} else if (weaponType.equals("pierce")) {
						if (size.equals("big")) {
							trans3f.x = -0.4f;
							trans3f.y = -0.35f;
							weaponSize = 1.55f;
						} else if (size.equals("large")) {
							trans3f.x = -0.6749f;
							trans3f.y = -0.55f;
							weaponSize = 1.95f;
						} else if (size.equals("great")) {
							trans3f.x = -1.049f;
							trans3f.y = -0.649f;
							weaponSize = 2.4f;
						} else {
							trans3f.x = -0.35f;
							trans3f.y = -0.25f;
							weaponSize = 1.3f;
						}
					} else if (weaponType.equals("staff")) {
						trans3f.x = -0.25f;
						trans3f.y = -0.55f;
						weaponSize = 1.55f;
					}
					
					GL11.glTranslatef(trans3f.x, trans3f.y, trans3f.z);
					GL11.glScalef(weaponSize, weaponSize, 1.15f);
					break;
				case ENTITY :
					if (!this.noEntityTranslation) {
						GL11.glTranslatef(-0.5f, 0f, depth); // correction of
																// the rotation
																// point when
																// items lie on
																// the ground
					}
					break;
				default :
			}
			
			// one side
			tess.startDrawingQuads();
			tess.setNormal(0, 0, 1);
			for (int i = 0; i < iconParts; ++i) {
				if (partColors != null) {
					f5 = ((partColors[i] >> 16) & 255) / 255.0F;
					f6 = ((partColors[i] >> 8) & 255) / 255.0F;
					f7 = (partColors[i] & 255) / 255.0F;
					tess.setColorOpaque_F(f5, f6, f7);
				}
				tess.addVertexWithUV(0, 0, 0, xMax[i], yMax[i]);
				tess.addVertexWithUV(1, 0, 0, xMin[i], yMax[i]);
				tess.addVertexWithUV(1, 1, 0, xMin[i], yMin[i]);
				tess.addVertexWithUV(0, 1, 0, xMax[i], yMin[i]);
			}
			tess.draw();
			
			// other side
			tess.startDrawingQuads();
			tess.setNormal(0, 0, -1);
			for (int i = 0; i < iconParts; ++i) {
				if (partColors != null) {
					f5 = ((partColors[i] >> 16) & 255) / 255.0F;
					f6 = ((partColors[i] >> 8) & 255) / 255.0F;
					f7 = (partColors[i] & 255) / 255.0F;
					tess.setColorOpaque_F(f5, f6, f7);
				}
				tess.addVertexWithUV(0, 1, -depth, xMax[i], yMin[i]);
				tess.addVertexWithUV(1, 1, -depth, xMin[i], yMin[i]);
				tess.addVertexWithUV(1, 0, -depth, xMin[i], yMax[i]);
				tess.addVertexWithUV(0, 0, -depth, xMax[i], yMax[i]);
			}
			tess.draw();
			
			// make it have "depth"
			tess.startDrawingQuads();
			tess.setNormal(-1, 0, 0);
			float pos;
			float iconPos;
			
			for (int i = 0; i < iconParts; ++i) {
				float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
				for (int k = 0, e = (int) w; k < e; ++k) {
					pos = k / w;
					iconPos = (m + (d * pos)) - s;
					if (partColors != null) {
						f5 = ((partColors[i] >> 16) & 255) / 255.0F;
						f6 = ((partColors[i] >> 8) & 255) / 255.0F;
						f7 = (partColors[i] & 255) / 255.0F;
						tess.setColorOpaque_F(f5, f6, f7);
					}
					tess.addVertexWithUV(pos, 0, -depth, iconPos, yMax[i]);
					tess.addVertexWithUV(pos, 0, 0, iconPos, yMax[i]);
					tess.addVertexWithUV(pos, 1, 0, iconPos, yMin[i]);
					tess.addVertexWithUV(pos, 1, -depth, iconPos, yMin[i]);
				}
			}
			
			tess.draw();
			tess.startDrawingQuads();
			tess.setNormal(1, 0, 0);
			float posEnd;
			
			for (int i = 0; i < iconParts; ++i) {
				float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
				float d2 = 1f / w;
				for (int k = 0, e = (int) w; k < e; ++k) {
					pos = k / w;
					iconPos = (m + (d * pos)) - s;
					posEnd = pos + d2;
					if (partColors != null) {
						f5 = ((partColors[i] >> 16) & 255) / 255.0F;
						f6 = ((partColors[i] >> 8) & 255) / 255.0F;
						f7 = (partColors[i] & 255) / 255.0F;
						tess.setColorOpaque_F(f5, f6, f7);
					}
					tess.addVertexWithUV(posEnd, 1, -depth, iconPos, yMin[i]);
					tess.addVertexWithUV(posEnd, 1, 0, iconPos, yMin[i]);
					tess.addVertexWithUV(posEnd, 0, 0, iconPos, yMax[i]);
					tess.addVertexWithUV(posEnd, 0, -depth, iconPos, yMax[i]);
				}
			}
			
			tess.draw();
			tess.startDrawingQuads();
			tess.setNormal(0, 1, 0);
			
			for (int i = 0; i < iconParts; ++i) {
				float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
				float d2 = 1f / h;
				for (int k = 0, e = (int) h; k < e; ++k) {
					pos = k / h;
					iconPos = (m + (d * pos)) - s;
					posEnd = pos + d2;
					if (partColors != null) {
						f5 = ((partColors[i] >> 16) & 255) / 255.0F;
						f6 = ((partColors[i] >> 8) & 255) / 255.0F;
						f7 = (partColors[i] & 255) / 255.0F;
						tess.setColorOpaque_F(f5, f6, f7);
					}
					tess.addVertexWithUV(0, posEnd, 0, xMax[i], iconPos);
					tess.addVertexWithUV(1, posEnd, 0, xMin[i], iconPos);
					tess.addVertexWithUV(1, posEnd, -depth, xMin[i], iconPos);
					tess.addVertexWithUV(0, posEnd, -depth, xMax[i], iconPos);
				}
			}
			
			tess.draw();
			tess.startDrawingQuads();
			tess.setNormal(0, -1, 0);
			
			for (int i = 0; i < iconParts; ++i) {
				float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
				for (int k = 0, e = (int) h; k < e; ++k) {
					pos = k / h;
					iconPos = (m + (d * pos)) - s;
					if (partColors != null) {
						f5 = ((partColors[i] >> 16) & 255) / 255.0F;
						f6 = ((partColors[i] >> 8) & 255) / 255.0F;
						f7 = (partColors[i] & 255) / 255.0F;
						tess.setColorOpaque_F(f5, f6, f7);
					}
					tess.addVertexWithUV(1, pos, 0, xMin[i], iconPos);
					tess.addVertexWithUV(0, pos, 0, xMax[i], iconPos);
					tess.addVertexWithUV(0, pos, -depth, xMax[i], iconPos);
					tess.addVertexWithUV(1, pos, -depth, xMin[i], iconPos);
				}
			}
			
			tess.draw();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return handleRenderType(item, type)
				& (helper.ordinal() < ItemRendererHelper.EQUIPPED_BLOCK.ordinal());
	}
}
