package ca.grm.rot.gui.shop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;

public class GuiMerchantShopSlotButton extends GuiButton
{
	private boolean isToggled = false;
	public int buttonBoolIndex = 0;
	private ItemStack item = null;
	private TextureAtlasSprite tex = null;
	
	public GuiMerchantShopSlotButton (int buttonId, int x, int y, ItemStack item, boolean toggled, int booleanIndex)
	{
		super(buttonId, x, y, "");
		this.width = 18;
		this.height = 18;
		this.isToggled = toggled;
		this.item = item;
		this.buttonBoolIndex = booleanIndex;
		if (this.item != null)
		{
			tex = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(this.item).getTexture();
		}
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            //this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.hovered)
            {
                l = 16777120;
            }

            int color = 0x55FFFFFF;
            if (this.hovered)
            {
            	if (isToggled)color = 0x55FFFF00;
                else color = 0x5500FFFF;
            }
            if (isToggled)color = 0x5500FF00;
            else color = 0x550000FF;
            this.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + width - 1, this.yPosition + height - 1, color);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (tex != null) this.drawTexturedModalRect(this.xPosition + 1, this.yPosition + 1, tex, 16, 16);
            //this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }
}
