package ca.grm.rot.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class RotEventRenderLiving {

	public void drawNameWithHealth(Entity entity, RendererLivingEntity renderer) {
		double x = (Minecraft.getMinecraft().thePlayer.posX - entity.posX) * -1;
		double y = (Minecraft.getMinecraft().thePlayer.posY - entity.posY) * -1;
		double z = (Minecraft.getMinecraft().thePlayer.posZ - entity.posZ) * -1;
		Tessellator t = Tessellator.getInstance();
		WorldRenderer tw = t.getWorldRenderer();
		tw.setTranslation(-0.5, 0, 0);
		GL11.glPushMatrix();
		GlStateManager.translate(x,y + entity.height + 0.5F, z);
		tw.startDrawingQuads();
		GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.rotate(-renderer.func_177068_d().playerViewY % 360, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderer.func_177068_d().playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		if (entity instanceof EntityLiving && ((EntityLiving) entity).canEntityBeSeen((Entity)Minecraft.getMinecraft().thePlayer) && !entity.isSneaking())
		{
			// Draw the grey background
			tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
	        tw.addVertexWithUV(0,0,0, 0, 0);
			tw.addVertexWithUV(0,0.25,0, 0, 0);
			tw.addVertexWithUV(1,0.25,0, 0, 0);
			tw.addVertexWithUV(1,0,0, 0, 0);
			// Draw the red overlay
			EntityLiving entityLiving = (EntityLiving)entity;
			float hpPercent = (entityLiving.getHealth() / entityLiving.getMaxHealth());
			hpPercent = 1-hpPercent; // Because we later do 0 + hpPercent. By doing this we're avoiding 20/20hp, all vertexes at x = 1. Instead 20/20 hp, hpPercent = 0.
			if (hpPercent < 0.01 && hpPercent > 0)
			{
				// This is so we dont get 0.000000000000000000004 of a red hp bar.
				hpPercent = 0;
			}
			tw.setColorRGBA_F(232.0F, 0.0F, 0.0F, 0.5F);
			tw.addVertexWithUV(1,0.25,0, 0, 0);
			tw.addVertexWithUV(1,0,0, 0, 0);
	        tw.addVertexWithUV(0+hpPercent,0,0, 0, 0);
			tw.addVertexWithUV(0+hpPercent,0.25,0, 0, 0);
			
		}
		t.draw(); 
		GlStateManager.enableTexture2D();
		tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
		tw.setTranslation(0, 0, 0);
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@SubscribeEvent
	public void handlerRenderLiving(RenderLivingEvent.Pre event) {
		drawNameWithHealth(event.entity, event.renderer);
	}
}
