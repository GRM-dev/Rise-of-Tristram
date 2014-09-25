package ca.grm.rot.comms;

import ca.grm.rot.Rot;
import ca.grm.rot.gui.GuiExtendedPlayerStats;
import ca.grm.rot.items.ItemRendererSizeType;
import ca.grm.rot.items.ItemRendererSizeable;
import ca.grm.rot.items.RotItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(Rot.classKey);
		ClientRegistry.registerKeyBinding(Rot.customizeItemKey);
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
