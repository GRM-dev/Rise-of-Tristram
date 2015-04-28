package ca.grm.rot;

import net.minecraft.creativetab.CreativeTabs;
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
import ca.grm.rot.blocks.TileEntityBaseNode;
import ca.grm.rot.comms.BaseNodeRequestPacket;
import ca.grm.rot.comms.BaseNodeResponsePacket;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ClassResponsePacket;
import ca.grm.rot.comms.CommonProxy;
import ca.grm.rot.comms.CustomItemPacket;
import ca.grm.rot.events.RotEventHandler;
import ca.grm.rot.events.RotStandardEventHandler;
import ca.grm.rot.gui.GuiHandler;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.CreativeTabsRoT;

@Mod(
		modid = Rot.MODID,
		version = Rot.VERSION,
		name = Rot.MODNAME)
public class Rot {
	
	@Instance(
			value = "RoT")
	public static Rot					instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(
			clientSide = "ca.grm.rot.comms.ClientProxy",
			serverSide = "ca.grm.rot.comms.CommonProxy")
	public static CommonProxy			proxy;
	
	// @NetworkMod(clientSideRequired=true, serverSideRequired=false, channels =
	// {"rot"}, packetHandler = OpenGuiPacket.class)
	
	public static final String			MODID				= "RoT";
	public static final String			MODNAME				= "Rise of Tristram";
	public static final String			VERSION				= "1.0";
	
	// Packets
	public static SimpleNetworkWrapper	net;
	private int							packetId			= 0;
	
	// Sending packets:
	/*
	 * MyMod.network.sendToServer(new MyMessage("foobar"));
	 * MyMod.network.sendTo(new SomeMessage(), somePlayer);
	 */
	
	public static CreativeTabs			tabRoT				= new CreativeTabsRoT("RoT");
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		proxy.registerKeyBindings();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new RotStandardEventHandler());
		MinecraftForge.EVENT_BUS.register(new RotEventHandler());
		proxy.registerRenderers();
		/*
		 * if
		 * (FMLCommonHandler.instance().getEffectiveSide().isClient())MinecraftForge
		 * .EVENT_BUS.register(new RotManaGui(Minecraft.getMinecraft()));
		 * if
		 * (FMLCommonHandler.instance().getEffectiveSide().isClient())MinecraftForge
		 * .EVENT_BUS.register(new RotStamGui(Minecraft.getMinecraft()));
		 */
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		net = NetworkRegistry.INSTANCE.newSimpleChannel("rpcee");
		// net.registerMessage(TextPacket.TextPacketHandler.class,
		// TextPacket.class, packetId++, Side.SERVER);
		net.registerMessage(BaseNodeRequestPacket.BaseNodeRequestPacketHandler.class,
				BaseNodeRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(BaseNodeResponsePacket.BaseNodeResponsePacketHandler.class,
				BaseNodeResponsePacket.class, this.packetId++, Side.CLIENT);
		
		net.registerMessage(ClassRequestPacket.ClassRequestPacketHandler.class,
				ClassRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(ClassResponsePacket.ClassResponsePacketHandler.class,
				ClassResponsePacket.class, this.packetId++, Side.CLIENT);
		
		net.registerMessage(CustomItemPacket.CustomItemPacketHandler.class,
				CustomItemPacket.class, this.packetId++, Side.SERVER);
		
		GameRegistry.registerTileEntity(TileEntityBaseNode.class, MODID + "baseNode"); // This
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
		
		RotBlocks.init();
		RotBlocks.registerBlocks();
		
		RotItems.init();
		RotItems.registerItems();
		
		RotRecipes.init();
	}
}
