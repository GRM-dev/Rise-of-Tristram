package ca.grm.rot.events;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class RotEventRenderLivingEvent {
	public void drawNameWithHealth()
	{
		Tessellator t = Tessellator.getInstance(); 
		WorldRenderer tw = t.getWorldRenderer();
		tw.startDrawingQuads();
		tw.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tw.addVertex(0,0,0);
		tw.addVertex(0,1,0);
		tw.addVertex(1,1,0);
		tw.addVertex(1,0,0);
		t.draw(); 
	}
	
	@SubscribeEvent
	public void handlerRenderLiving(RenderLivingEvent.Pre event)
	{
		drawNameWithHealth();
	}
}
