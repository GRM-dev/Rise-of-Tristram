package ca.grm.rot.managers;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ca.grm.rot.comms.BaseNodeRequestPacket;
import ca.grm.rot.comms.BaseNodeResponsePacket;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ClassResponsePacket;
import ca.grm.rot.comms.CustomItemPacket;
import ca.grm.rot.comms.EnderPearlPacket;
import ca.grm.rot.comms.MobRequestPacket;
import ca.grm.rot.comms.MobResponsePacket;
import ca.grm.rot.comms.MobResponsePacket.MobResponsePacketHandler;
import ca.grm.rot.comms.ProfessionRequestPacket;
import ca.grm.rot.comms.ProfessionResponsePacket;
import ca.grm.rot.comms.TNTPacket;

public class NetworkManager
{
	// Sending packets:
	/*
	 * MyMod.network.sendToServer(new MyMessage("foobar"));
	 * MyMod.network.sendTo(new SomeMessage(), somePlayer);
	 */
	private int packetId = 0;
	private SimpleNetworkWrapper net;

	public NetworkManager(SimpleNetworkWrapper netty)
	{
		this.net = netty;
		net.registerMessage(BaseNodeRequestPacket.BaseNodeRequestPacketHandler.class,
				BaseNodeRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(BaseNodeResponsePacket.BaseNodeResponsePacketHandler.class,
				BaseNodeResponsePacket.class, this.packetId++, Side.CLIENT);

		net.registerMessage(MobRequestPacket.MobRequestHandler.class,
				MobRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(MobResponsePacket.MobResponsePacketHandler.class,
				MobResponsePacket.class, this.packetId++, Side.CLIENT);
		
		net.registerMessage(ClassRequestPacket.ClassRequestPacketHandler.class,
				ClassRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(ClassResponsePacket.ClassResponsePacketHandler.class,
				ClassResponsePacket.class, this.packetId++, Side.CLIENT);
		
		net.registerMessage(ProfessionRequestPacket.ProfessionRequestPacketHandler.class,
				ProfessionRequestPacket.class, this.packetId++, Side.SERVER);
		net.registerMessage(ProfessionResponsePacket.ProfessionResponsePacketHandler.class,
				ProfessionResponsePacket.class, this.packetId++, Side.CLIENT);

		net.registerMessage(CustomItemPacket.CustomItemPacketHandler.class, CustomItemPacket.class,
				this.packetId++, Side.SERVER);
		net.registerMessage(EnderPearlPacket.EnderPearlPacketHandler.class, EnderPearlPacket.class,
				this.packetId++, Side.SERVER);
		net.registerMessage(TNTPacket.TNTPacketHandler.class, TNTPacket.class,
				this.packetId++, Side.SERVER);
	}

}
