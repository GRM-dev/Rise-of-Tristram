package ca.grm.rot.events;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class RotEventRenderLivingEvent {
	public void drawNameWithHealth(Entity entity)
	{
		Tessellator t = Tessellator.getInstance(); 
		WorldRenderer tw = t.getWorldRenderer();
		tw.startDrawingQuads();
		GL11.glEnable(GL11.GL_BLEND);
		tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tw.addVertex(0,2,0);
		tw.addVertex(0,3,0);
		tw.addVertex(1,3,0);
		tw.addVertex(1,2,0);
		t.draw(); 
	}
	
	@SubscribeEvent
	public void handlerRenderLiving(RenderLivingEvent.Pre event)
	{
		drawNameWithHealth(event.entity);
	}
}
