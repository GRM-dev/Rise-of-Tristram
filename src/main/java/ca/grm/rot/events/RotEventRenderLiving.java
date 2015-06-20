package ca.grm.rot.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;

public class RotEventRenderLiving
{

	public void drawNameWithHealth(Entity entity, RendererLivingEntity renderer)
	{
		double distance = 12;
		boolean renderBar = entity instanceof EntityLiving && ((EntityLiving) entity)
				.canEntityBeSeen((Entity) Minecraft.getMinecraft().thePlayer) && !entity
				.isSneaking();
		double x = (Minecraft.getMinecraft().thePlayer.posX - entity.posX) * -1;
		double y = (Minecraft.getMinecraft().thePlayer.posY - entity.posY) * -1;
		double z = (Minecraft.getMinecraft().thePlayer.posZ - entity.posZ) * -1;
		if ((x < 0 ? x * -1 : x) < distance && (y < 0 ? y * -1 : y) < distance && (z < 0 ? z * -1 : z) < distance)
		{
			float width = 1f, height = 0.1f, offset = .02f;
			Tessellator t = Tessellator.getInstance();
			WorldRenderer tw = t.getWorldRenderer();
			tw.setTranslation(0 - (width / 2), 0, 0);
			GL11.glPushMatrix();
			GlStateManager.translate(x, y + entity.height + (height * 2.5f), z);
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
			if (renderBar)
			{
				// Render Gold border
				// Upper-Left, Upper-Right, Lower-Right, Lower-Left
				// Top
				tw.setColorRGBA_F(1.0F, 0.7F, 0.0F, 0.65F);
				tw.addVertexWithUV(0, height + offset, 0, 0, 0);
				tw.addVertexWithUV(width, height + offset, 0, 0, 0);
				tw.addVertexWithUV(width + offset, height, 0, 0, 0);
				tw.addVertexWithUV(0 - offset, height, 0, 0, 0);

				// Right - has to be backwards because negatives reverse the
				// face.
				tw.addVertexWithUV(0 - offset, height, 0, 0, 0);
				tw.addVertexWithUV(0, height, 0, 0, 0);
				tw.addVertexWithUV(0, 0, 0, 0, 0);
				tw.addVertexWithUV(0 - offset, 0, 0, 0, 0);

				// Bottom
				tw.addVertexWithUV(0 - offset, 0, 0, 0, 0);
				tw.addVertexWithUV(width + offset, 0, 0, 0, 0);
				tw.addVertexWithUV(width, 0 - offset, 0, 0, 0);
				tw.addVertexWithUV(0, 0 - offset, 0, 0, 0);

				// Left
				tw.addVertexWithUV(width, height, 0, 0, 0);
				tw.addVertexWithUV(width + offset, height, 0, 0, 0);
				tw.addVertexWithUV(width + offset, 0, 0, 0, 0);
				tw.addVertexWithUV(width, 0, 0, 0, 0);

				// Draw the grey background
				tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.3F);
				tw.addVertexWithUV(0, height, 0, 0, 0);
				tw.addVertexWithUV(width, height, 0, 0, 0);
				tw.addVertexWithUV(width, 0, 0, 0, 0);
				tw.addVertexWithUV(0, 0, 0, 0, 0);
				// Draw the red overlay
				EntityLiving entityLiving = (EntityLiving) entity;
				float hpPercent = (entityLiving.getHealth() / entityLiving.getMaxHealth());
				hpPercent = 1 - hpPercent; // Because we later do 0 + hpPercent.
											// By
											// doing this we're avoiding
											// 20/20hp,
											// all vertexes at x = 1. Instead
											// 20/20
											// hp, hpPercent = 0.
				if (hpPercent < 0.01 && hpPercent > 0)
				{
					// This is so we dont get 0.000000000000000000004 of a red
					// hp
					// bar.
					hpPercent = 0;
				}
				tw.setColorRGBA_F(232.0F, 0.0F, 0.0F, 0.5F);
				tw.addVertexWithUV(width, height, 0, 0, 0);
				tw.addVertexWithUV(width, 0, 0, 0, 0);
				tw.addVertexWithUV(0 + hpPercent, 0, 0, 0, 0);
				tw.addVertexWithUV(0 + hpPercent, height, 0, 0, 0);

			}
			t.draw();
			GlStateManager.enableTexture2D();
			tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);	

			if (renderBar)
			{
				String stringToBeDrawn = "";
				if (ExtendMob.get((EntityLiving) entity) != null)
				{
					stringToBeDrawn = "(" + ExtendMob.get((EntityLiving) entity).monsterLevel + ") " + ExtendMob.get((EntityLiving) entity).prefix + " " + entity.getName() + " " + ExtendMob.get((EntityLiving) entity).suffix;
					GlStateManager.scale(0.02f, 0.02f, 0.02f);
					GlStateManager.rotate(180, 0, 0, 1);
					GlStateManager.translate(0 - ((float)stringToBeDrawn.length() * 2.75f), -15, 0);
				}
				else
				{
					GlStateManager.scale(0.02f, 0.02f, 0.02f);
					GlStateManager.rotate(180, 0, 0, 1);
					GlStateManager.translate(0 - ((float)entity.getName().length() * 2.75f), -15, 0);
				}
				if (stringToBeDrawn != "")
				{
					Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(stringToBeDrawn, (float) x, (float) y, 0xffffff);
				}
				else
				{
					Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(entity.getName(), (float) x, (float) y, 0xffffff);
				}
			}

			tw.setTranslation(0, 0, 0);
			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}
	
	public void sizeMobPrefixHandler(Entity entity, RendererLivingEntity renderer)
	{
		// Only run if the entity is ExtendMob and has the 'size' prefix
		if (ExtendMob.get((EntityLiving) entity) != null)
		{
			ExtendMob monster = ExtendMob.get((EntityLiving) entity);
			if (monster.prefix == "Big")
			{
				entity.width = 2;
				entity.height = 4;
			}
			else if (monster.prefix == "Huge")
			{
				entity.width = 2;
				entity.height = 4;
			}
			else if (monster.prefix == "Giant")
			{
				entity.width = 2;
				entity.height = 4;
			}
			else if (monster.prefix == "Massive")
			{
				entity.width = 2;
				entity.height = 4;
			}
		}
	}

	@SubscribeEvent
	public void handlerRenderLiving(RenderLivingEvent.Pre event)
	{
		drawNameWithHealth(event.entity, event.renderer);
		sizeMobPrefixHandler(event.entity, event.renderer);
	}
}
