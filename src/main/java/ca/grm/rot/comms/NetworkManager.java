package ca.grm.rot.comms;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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
