package ca.grm.rot.comms;

import org.lwjgl.input.Keyboard;

import ca.grm.rot.Rot;
import ca.grm.rot.gui.GuiExtendedPlayerStats;
import ca.grm.rot.items.ItemRendererSizeType;
import ca.grm.rot.items.ItemRendererSizeable;
import ca.grm.rot.items.RotItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding			classKey			= new KeyBinding(
			"Class Menu",
			Keyboard.KEY_Y,
			"keys.rot");
	public static KeyBinding			customizeItemKey	= new KeyBinding(
			"Item Customization Menu",
			Keyboard.KEY_I,
			"keys.rot");
	
	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(classKey);
		ClientRegistry.registerKeyBinding(customizeItemKey);
	}
	
	@Override
	public void registerRenderers() {
		/*
		 * MinecraftForge.EVENT_BUS.register(new
		 * RotManaGui(Minecraft.getMinecraft()));
		 * MinecraftForge.EVENT_BUS.register(new
		 * RotStamGui(Minecraft.getMinecraft()));
		 */
		MinecraftForge.EVENT_BUS.register(new GuiExtendedPlayerStats(Minecraft
				.getMinecraft()));
		// MinecraftForgeClient.registerItemRenderer(RotItems.itemSwordSoul, new
		// ItemRendererScaled(.75f));
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponSwordSoul,
				new ItemRendererSizeable(0.5f));
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponSlash,
				new ItemRendererSizeType());
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponHack,
				new ItemRendererSizeType());
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponBlunt,
				new ItemRendererSizeType());
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponPierce,
				new ItemRendererSizeType());
		MinecraftForgeClient.registerItemRenderer(RotItems.weaponStaffBlue,
				new ItemRendererSizeType());
	}
}
