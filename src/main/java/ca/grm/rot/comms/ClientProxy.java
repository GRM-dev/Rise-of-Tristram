package ca.grm.rot.comms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.lwjgl.input.Keyboard;

import ca.grm.rot.Rot;
import ca.grm.rot.blocks.RotBlocks;
import ca.grm.rot.events.KeyHandleEvent;
import ca.grm.rot.events.RotEventRenderLiving;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.gui.GuiExtendedPlayerStats;
import ca.grm.rot.items.RotItems;

public class ClientProxy extends CommonProxy
{

	//TODO figure out why controls are being doubled
	public static KeyBinding classKey = new KeyBinding("Class Menu", Keyboard.KEY_Y, "keys.rot");
	public static KeyBinding customizeItemKey = new KeyBinding("Item Customization Menu",
			Keyboard.KEY_I, "keys.rot");
	public static KeyBinding skill1 = new KeyBinding("Active Skill 1",
			Keyboard.KEY_F, "keys.rot");
	public static KeyBinding skill2 = new KeyBinding("Active Skill 2",
			Keyboard.KEY_R, "keys.rot");

	@Override
	public void handleClassMessage(ClassResponsePacket message, MessageContext ctx)
	{
		System.out.println("got a response about changing to: " + message.classID);
		try
		{
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setCurrentClass(message.classID);
		}
		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void handleProfessionMessage(ProfessionResponsePacket message, MessageContext ctx)
	{
		System.out.println("got a response about changing to: " + message.professionID);
		try
		{
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setCurrentProfession(message.professionID);
		}
		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/*
	 * @Override public int addArmor(String armor) { return
	 * RenderingRegistry.addNewArmourRendererPrefix(armor); }
	 */

	@Override
	public void registerKeyBindings()
	{
		FMLCommonHandler.instance().bus().register(new KeyHandleEvent());
		ClientRegistry.registerKeyBinding(classKey);
		ClientRegistry.registerKeyBinding(customizeItemKey);
		ClientRegistry.registerKeyBinding(skill1);
		ClientRegistry.registerKeyBinding(skill2);
	}

	@Override
	public void registerRenderers()
	{
		RotBlocks.registerRenders();
		RotItems.registerRenders();
		/*
		 * MinecraftForge.EVENT_BUS.register(new
		 * RotManaGui(Minecraft.getMinecraft()));
		 * MinecraftForge.EVENT_BUS.register(new
		 * RotStamGui(Minecraft.getMinecraft()));
		 */
		MinecraftForge.EVENT_BUS.register(new GuiExtendedPlayerStats(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new RotEventRenderLiving());
		// MinecraftForgeClient.registerItemRenderer(RotItems.itemSwordSoul, new
		// ItemRendererScaled(.75f));
		/*
		 * MinecraftForgeClient.registerItemRenderer(RotItemsOld.weaponSwordSoul,
		 * new ItemRendererSizeable(0.5f));
		 * MinecraftForgeClient.registerItemRenderer(RotItemsOld.weaponSlash,
		 * new ItemRendererSizeType());
		 * MinecraftForgeClient.registerItemRenderer(RotItemsOld.weaponHack, new
		 * ItemRendererSizeType());
		 * MinecraftForgeClient.registerItemRenderer(RotItemsOld.weaponBlunt,
		 * new ItemRendererSizeType());
		 * MinecraftForgeClient.registerItemRenderer(RotItemsOld.weaponPierce,
		 * new ItemRendererSizeType());
		 * MinecraftForgeClient.registerItemRenderer
		 * (RotItemsOld.weaponStaffBlue, new ItemRendererSizeType());
		 */
	}

	@Override
	public void updatePlayer(EntityPlayer player)
	{
		if (Minecraft.getMinecraft().thePlayer != null)
		{
			try{
			ExtendPlayer props = ExtendPlayer.get(player);
			Rot.net.sendTo(new ClassResponsePacket(props.getCurrentClassIndex()),
					(EntityPlayerMP) player);
			Rot.net.sendTo(new ProfessionResponsePacket(props.getCurrentProfessionIndex()),
					(EntityPlayerMP) player);
			props.needsUpdate = false;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
