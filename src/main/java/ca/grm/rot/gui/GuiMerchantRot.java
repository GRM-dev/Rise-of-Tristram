package ca.grm.rot.gui;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.Rot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiMerchantRot extends GuiContainer{
	public static final ResourceLocation texture = new ResourceLocation(Rot.MOD_ID.toLowerCase(),
			"textures/gui/rotShop.png");

	private EntityPlayer player;
	public GuiMerchantRot(EntityPlayer player) 
	{
		super(new ContainerMerchantPlayer(player.inventory, player));
		this.player = player;//174x192
		this.xSize = 173;
		this.ySize = 165;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX, int mouseY) 
	{
		// Begin drawing the background
		this.mc.getTextureManager().bindTexture(texture);
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize,  ySize);
		
		// Other goodies
        this.buttonList.clear();
        this.buttonList.add(new GuiMerchantPurchaseButton(0, this.guiLeft + 5, this.guiTop + 62, "Purchase"));
	}
	
	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			this.mc.thePlayer.closeScreen();
		}
	}
	
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
