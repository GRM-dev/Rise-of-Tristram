package ee.rot.comms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;


import cpw.mods.fml.client.registry.RenderingRegistry;
import ee.rot.gui.RotManaGui;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() 
    {
    	MinecraftForge.EVENT_BUS.register(new RotManaGui(Minecraft.getMinecraft()));
    }
    
}
