package ee.rot.comms;


import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import ee.rot.Rot;
import ee.rot.gui.GuiExtendedPlayerStats;
import ee.rot.items.ItemRendererSizeType;
import ee.rot.items.ItemRendererSizeable;
import ee.rot.items.RotItems;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() 
    {
    	/*MinecraftForge.EVENT_BUS.register(new RotManaGui(Minecraft.getMinecraft()));
    	MinecraftForge.EVENT_BUS.register(new RotStamGui(Minecraft.getMinecraft()));*/
    	MinecraftForge.EVENT_BUS.register(new GuiExtendedPlayerStats(Minecraft.getMinecraft()));
    	//MinecraftForgeClient.registerItemRenderer(RotItems.itemSwordSoul, new ItemRendererScaled(.75f));
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponSwordSoul, new ItemRendererSizeable(0.5f));
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponSlash, new ItemRendererSizeType());
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponHack, new ItemRendererSizeType());
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponBlunt, new ItemRendererSizeType());
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponPierce, new ItemRendererSizeType());
    	MinecraftForgeClient.registerItemRenderer(RotItems.weaponStaffBlue, new ItemRendererSizeType());
    }
    
    @Override
    public void registerKeyBindings()
    {
    	ClientRegistry.registerKeyBinding(Rot.classKey);
    	ClientRegistry.registerKeyBinding(Rot.customizeItemKey);
    }
    
    public int addArmor(String armor)
    {
    	return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }
}
