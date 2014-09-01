package ee.rot.comms;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import ee.rot.Rot;
import ee.rot.gui.RotManaGui;
import ee.rot.gui.RotStamGui;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() 
    {
    	MinecraftForge.EVENT_BUS.register(new RotManaGui(Minecraft.getMinecraft()));
    	MinecraftForge.EVENT_BUS.register(new RotStamGui(Minecraft.getMinecraft()));
    }
    
    @Override
    public void registerKeyBindings()
    {
    	ClientRegistry.registerKeyBinding(Rot.classKey);
    }
}
