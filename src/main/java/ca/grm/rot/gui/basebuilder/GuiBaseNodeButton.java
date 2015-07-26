package ca.grm.rot.gui.basebuilder;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ca.grm.rot.libs.UtilityFunctions;

public class GuiBaseNodeButton extends GuiButton
{

	public int x, y, z;
	public Block block;
	private int color = 0xFFFFFFFF;
	public boolean canSeeSky;
	public float alpha = 1.0f;
	public TextureAtlasSprite tex;

	public GuiBaseNodeButton(int buttonId, int x, int y, int widthIn, int heightIn,
			String buttonText)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.visible)
		{
			FontRenderer fontrenderer = mc.fontRendererObj;
			GlStateManager.color(1 - (alpha != 1 ? alpha / 2: 0),1 - (alpha != 1 ? alpha / 2: 0),1, alpha);
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int k = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);

			mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

			if (tex != null) this
					.drawTexturedModalRect(this.xPosition, this.yPosition, tex, 16, 16);

			this.mouseDragged(mc, mouseX, mouseY);
			int l = 14737632;

			if (!this.enabled)
			{
				l = 10526880;
			}
			else if (this.hovered)
			{
				l = 16777120;
			}

			if (this.hovered || this.displayString == "X") this.drawCenteredString(fontrenderer,
					this.displayString, this.xPosition + this.width / 2,
					this.yPosition + (this.height - 8) / 2, l);
		}
	}

}
