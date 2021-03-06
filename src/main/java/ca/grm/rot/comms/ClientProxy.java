package ca.grm.rot.comms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.lwjgl.input.Keyboard;

import ca.grm.rot.Rot;
import ca.grm.rot.blocks.RotBlocks;
import ca.grm.rot.events.KeyHandleEvent;
import ca.grm.rot.events.RotEventRenderLiving;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.extendprops.ExtendVillager;
import ca.grm.rot.gui.skills.GuiExtendedPlayerStats;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.managers.RotShopTypeManager;

public class ClientProxy extends CommonProxy
{

	// TODO figure out why controls are being doubled
	public static KeyBinding classKey = new KeyBinding("Class Menu", Keyboard.KEY_Y, "keys.rot");
	public static KeyBinding customizeItemKey = new KeyBinding("Item Customization Menu",
			Keyboard.KEY_I, "keys.rot");
	public static KeyBinding skill1 = new KeyBinding("Active Skill 1", Keyboard.KEY_F, "keys.rot");
	public static KeyBinding skill2 = new KeyBinding("Active Skill 2", Keyboard.KEY_R, "keys.rot");

	@Override
	public void handleClassMessage(ClassResponsePacket message, MessageContext ctx)
	{
		try
		{
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setCurrentClass(message.classID);
			Rot.net.sendToServer(new ProfessionRequestPacket(0));
		}
		catch (NullPointerException e)
		{}
	}

	@Override
	public void handleProfessionMessage(ProfessionResponsePacket message, MessageContext ctx)
	{
		try
		{
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setCurrentProfession(
					message.professionID);
			Rot.net.sendToServer(new GoldRequestPacket());
		}
		catch (NullPointerException e)
		{}
	}

	@Override
	public void handleMobData(MobResponsePacket message, MessageContext ctx)
	{
		try
		{
			World world = Minecraft.getMinecraft().thePlayer.worldObj;
			Entity o = world.getEntityByID(message.entityID);
	        if(o.getEntityId() == message.entityID)                        
	        {
	        	if (o instanceof EntityLiving)
	        	{
	        		ExtendMob e = ExtendMob.get((EntityLiving)o);
	        		e.monsterLevel = message.monsterLevel;
	        		e.strength = message.strength;
	        		e.agility = message.agility;
	        		e.dexterity = message.dexterity;
	        		e.vitality = message.vitality;
	        		e.minDmg = message.minDmg;
	        		e.maxDmg = message.maxDmg;
	        		e.defBonus = message.defBonus;
	        		e.gold = message.gold;
	        		
	        		if (message.isBoss == 0)
	        		{
	        			e.isBoss = false;
	        		}
	        		else
	        		{
	        			e.isBoss = true;
	        		}
	        		
	        		e.prefix = message.prefix;
	        		e.bossPrefix2 = message.bossPrefix2;
	        		e.bossPrefix3 = message.bossPrefix3;
	        		e.bossPrefix4 = message.bossPrefix4;
	        		e.suffix = message.suffix;
	        		
	        		e.setHpRegenBonusPercent(message.hpRegenBonusPercent);
	        		e.needsUpdate = false;
	        	}
	        }
		}
		catch (NullPointerException e)
		{}
	}

	@Override
	public void handleGoldMessage(GoldResponsePacket message, MessageContext ctx)
	{
		try
		{
			ExtendPlayer.get(Minecraft.getMinecraft().thePlayer).setGold(message.gold);
		}
		catch (NullPointerException e)
		{}
	}

	public void handleVillagerData(VillagerResponsePacket message,MessageContext ctx) {
		try
		{
			World world = Minecraft.getMinecraft().thePlayer.worldObj;
			Entity o = world.getEntityByID(message.entityID);
	        if(o.getEntityId() == message.entityID)                        
	        {
	        	if (o instanceof EntityLiving)
	        	{
	        		ExtendVillager e = ExtendVillager.get((EntityLiving)o);
	        		e.shopType = RotShopTypeManager.getShopTypeFromIndex(message.villagerShopType);
	        		e.firstName = message.firstName;
	        		e.lastName = message.lastName;
	        		e.needsUpdate = false;
	        	}
	        }
		}
		catch (NullPointerException e)
		{}
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
			try
			{
				ExtendPlayer props = ExtendPlayer.get(player);
				Rot.net.sendToServer(new ClassRequestPacket(0));
				props.needsUpdate = false;
			}
			catch (Exception e)
			{}
		}
	}
}
