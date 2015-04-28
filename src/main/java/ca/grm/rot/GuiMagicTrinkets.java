package ca.grm.rot;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiMagicTrinkets extends GuiContainer {

	/**
	 * x size of the inventory window in pixels. Defined as float, passed as int
	 */
	private float							xSize_lo;
	
	/**
	 * y size of the inventory window in pixels. Defined as float, passed as
	 * int.
	 */
	private float							ySize_lo;
	
	/**
	 * Normally I use '(ModInfo.MOD_ID, "textures/...")', but it can be done
	 * this way as well
	 */
	private static final ResourceLocation	iconLocation	= new ResourceLocation(
																	"tutorial:textures/gui/custom_inventory.png");
	
	/**
	 * Could use IInventory type to be more generic, but this way will save an
	 * import...
	 */
	private final InventoryMagicTrinkets	inventory;
	
	public GuiMagicTrinkets(Container c, EntityPlayer player, InventoryPlayer ip,
			InventoryMagicTrinkets imt) {
		super(new ContainerMagicTrinkets(player, ip, imt));
		this.inventory = imt;
	}
	
	/**
	 * This renders the player model in standard inventory position;
	 * copied straight from vanilla code but with renamed method parameters
	 */
	public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch,
			EntityLivingBase entity) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 50.0F);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = entity.renderYawOffset;
		float f3 = entity.rotationYaw;
		float f4 = entity.rotationPitch;
		float f5 = entity.prevRotationYawHead;
		float f6 = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
		entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, (float) entity.getYOffset(), 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F,
				1.0F);
		entity.renderYawOffset = f2;
		entity.rotationYaw = f3;
		entity.rotationPitch = f4;
		entity.prevRotationYawHead = f5;
		entity.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = par1;
		this.ySize_lo = par2;
	}
	
	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(iconLocation);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		drawPlayerModel(k + 51, l + 75, 30, k + 51 - this.xSize_lo, (l + 75) - 50
				- this.ySize_lo, this.mc.thePlayer);
	}
	
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// This method will simply draw inventory names on the screen - you
		// could do without it entirely
		// if that's not important to you, since we are overriding the default
		// inventory rather than
		// creating a specific type of inventory
		
		String s = this.inventory.hasCustomInventoryName() ? this.inventory
				.getInventoryName() : this.inventory.getInventoryName();
		// with the name "Custom Inventory", the 'Cu' will be drawn in the first
		// slot
		this.fontRendererObj.drawString(s,
				this.xSize - this.fontRendererObj.getStringWidth(s), 12, 4210752);
		// this just adds "Inventory" above the player's inventory below
		this.fontRendererObj.drawString(this.inventory.getInventoryName(), 80,
				this.ySize - 96, 4210752);
	}
}
