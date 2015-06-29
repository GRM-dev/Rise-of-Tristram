package ca.grm.rot;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import ca.grm.rot.blocks.RotBlocks;
import ca.grm.rot.blocks.TileEntityBaseBuilder;
import ca.grm.rot.comms.BaseNodeRequestPacket;
import ca.grm.rot.comms.BaseNodeResponsePacket;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ClassResponsePacket;
import ca.grm.rot.comms.CommonProxy;
import ca.grm.rot.comms.CustomItemPacket;
import ca.grm.rot.comms.EnderPearlPacket;
import ca.grm.rot.events.RotEventDamage;
import ca.grm.rot.events.RotEventLivingUpdate;
import ca.grm.rot.gui.GuiHandler;
import ca.grm.rot.hooks.RotChestHook;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.managers.NetworkManager;
import ca.grm.rot.managers.RotEventManager;

@Mod(modid = Rot.MOD_ID, version = Rot.VERSION, name = Rot.MOD_NAME)
public class Rot
{

	@Instance(value = "RoT")
	public static Rot instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "ca.grm.rot.comms.ClientProxy", serverSide = "ca.grm.rot.comms.CommonProxy")
	public static CommonProxy proxy;

	public static final String MOD_ID = "RoT";
	public static final String MOD_NAME = "Rise of Tristram";
	public static final String VERSION = "1.0";

	// Packet network
	public static SimpleNetworkWrapper net;
	public static NetworkManager nm;

	public static final RotTab tabRot = new RotTab("tabRot");

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		proxy.registerKeyBindings();
		proxy.registerRenderers();
		RotChestHook.addChestHooks();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		RotEventManager.registerEvents();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		net = NetworkRegistry.INSTANCE.newSimpleChannel("rpcee");
		nm = new NetworkManager(net);

		// net.registerMessage(TextPacket.TextPacketHandler.class,
		// TextPacket.class, packetId++, Side.SERVER);

		GameRegistry.registerTileEntity(TileEntityBaseBuilder.class, MOD_ID + "baseNode"); // This
																							// needs
																							// to
																							// be
																							// renamed,
																							// but
																							// this
																							// and
																							// it's
																							// related
																							// objects
																							// are
																							// under
																							// heavy
																							// construction

		proxy.registerKeyBindings();

		RotItems.init();
		RotItems.register();

		RotBlocks.init();
		RotBlocks.register();

		RotBlocksItemsRecipes.init();

		GameRegistry.registerWorldGenerator(new RotWorldGenerator(), 1);
	}
}
