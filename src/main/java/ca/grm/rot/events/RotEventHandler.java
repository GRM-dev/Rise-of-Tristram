package ca.grm.rot.events;

import java.lang.reflect.Field;

import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ClassResponsePacket;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RotEventHandler {
	
	/*
	 * Weapon Modifiers:
	 * Sizes
	 * Giant + 2 Str + 2 dmg - 2 Agi
	 * Large - 1 Str + 1 dmg - 1 Agi
	 * Small - 1 dmg + 1 Agi
	 * Effects:
	 * Bloody chance to add wither
	 * Poisioned chance to posion
	 * Vampiric lifesteal
	 * Frosty chance to slow
	 */

	@SubscribeEvent
	public void EntityBlockBreakSpeed(BreakSpeed e) {
		if (ExtendPlayer.get(e.entityPlayer).getCurrentClassName()
				.equals(ExtendPlayer.classNames[4])) {
			e.newSpeed += (MathHelper.clamp_float((ExtendPlayer.get(e.entityPlayer)
					.getStrength() * 2), 0f, 30f));
		} else {
			e.newSpeed += (MathHelper.clamp_float((ExtendPlayer.get(e.entityPlayer)
					.getStrength() / 3), -0.2f, 9f));
		}

	}
	
	@SubscribeEvent(
			priority = EventPriority.HIGHEST)
	public void onDamage(LivingHurtEvent event) {
		// Offense
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource) event.source;
			if (source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) source.getEntity();

				if (event.source.getDamageType() == "player") {
					if (ExtendPlayer.get(player).getCurrentClassName()
							.equals(ExtendPlayer.classNames[1])) {
						event.ammount += MathHelper.clamp_float(ExtendPlayer.get(player)
								.getStrength() / 4f, 0f, 5.75f);
					} else {
						event.ammount += MathHelper.clamp_float(ExtendPlayer.get(player)
								.getStrength() / 6f, 0f, 2.5f);
					}
				} else if (event.source.getDamageType() == "arrow") {
					if (ExtendPlayer.get(player).getCurrentClassName()
							.equals(ExtendPlayer.classNames[5])) {
						event.ammount += MathHelper.clamp_float(ExtendPlayer.get(player)
								.getDexterity() / 6f, 0f, 4.5f);
					} else {
						event.ammount += MathHelper.clamp_float(ExtendPlayer.get(player)
								.getDexterity() / 8f, 0f, 2.5f);
					}
				}
			}
		}

		// Defence
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (ExtendPlayer.get(player).getCurrentClassName()
					.equals(ExtendPlayer.classNames[3])) {
				event.ammount = MathHelper.clamp_float(
						event.ammount - (ExtendPlayer.get(player).getVitality() / 4.5f),
						0.25f, 8f);
			} else {
				event.ammount = MathHelper.clamp_float(
						event.ammount - (ExtendPlayer.get(player).getVitality() / 7.5f),
						0.25f, 20f);
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		
		if ((event.entity instanceof EntityPlayer)
				&& (ExtendPlayer.get((EntityPlayer) event.entity) == null)) {
			ExtendPlayer.register((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityLivingBase) {
			/*
			 * ExtendLivingBaseRot props =
			 * ExtendLivingBaseRot.get((EntityLivingBase) event.entity);
			 * // Gives a random amount of gold between 0 and 15
			 * props.addGold(event.entity.worldObj.rand.nextInt(16));
			 * System.out.println("[LIVING BASE] Gold: " + props.getGold());
			 */
		}
	}

	@SubscribeEvent
	public void onItemToolTipUpdate(ItemTooltipEvent e) {
		int rank = UtilityNBTHelper.getInt(e.itemStack, Rot.MODID + "rankLevel"), str = UtilityNBTHelper
				.getInt(e.itemStack, Rot.MODID + "strModifier"), agi = UtilityNBTHelper
				.getInt(e.itemStack, Rot.MODID + "agiModifier"), inte = UtilityNBTHelper
				.getInt(e.itemStack, Rot.MODID + "intModifier"), vit = UtilityNBTHelper
				.getInt(e.itemStack, Rot.MODID + "vitModifier"), dex = UtilityNBTHelper
				.getInt(e.itemStack, Rot.MODID + "dexModifier");
		
		if ((e.itemStack.getItem() instanceof ItemTool)
				|| (e.itemStack.getItem() instanceof ItemSword)
				|| (e.itemStack.getItem() instanceof ItemArmor)
				|| (e.itemStack.getItem() instanceof ItemBow)) {
			if (rank != 0) {
				e.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);
			}
			if (str != 0) {
				e.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Strength Modifier: " + str);
			}
			if (agi != 0) {
				e.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Agility Modifier: " + agi);
			}
			if (inte != 0) {
				e.toolTip.add((inte > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Intelligence Modifier: " + inte);
			}
			if (vit != 0) {
				e.toolTip.add((vit > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Vitality Modifier: " + vit);
			}
			if (dex != 0) {
				e.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Dexterity Modifier: " + dex);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			// Adding stats to Equipment
			for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
				ItemStack is = player.inventory.getStackInSlot(slot);
				if (is != null) {
					Item i = is.getItem();
					if (i instanceof ItemArmor) {
						int rank = UtilityNBTHelper.getInt(is, Rot.MODID + "rankLevel");
						if (rank == 0) {
							rank = 1;
							UtilityNBTHelper
									.setInteger(is, Rot.MODID + "rankLevel", rank);
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "strModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "agiModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "intModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "vitModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "dexModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
						}
					}
					if ((i instanceof ItemSword) || (i instanceof ItemTool)) {
						int rank = UtilityNBTHelper.getInt(is, Rot.MODID + "rankLevel");
						if (rank == 0) {
							rank = 1;
							UtilityNBTHelper
									.setInteger(is, Rot.MODID + "rankLevel", rank);
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "strModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
							if (i instanceof ItemSword) {

							}
							// UtilityNBTHelper.setFloat(is, Rot.MODID+"size",
							// 0.45f * (player.worldObj.rand.nextInt(2) == 0 ? 1
							// : -1));
							// UtilityNBTHelper.setFloat(is, Rot.MODID+"size",
							// 0.65f * (player.worldObj.rand.nextInt(2) == 0 ? 1
							// : -1));
						}
					}
					if (i instanceof ItemBow) {
						int rank = UtilityNBTHelper.getInt(is, Rot.MODID + "rankLevel");
						if (rank == 0) {
							rank = 1;
							UtilityNBTHelper
									.setInteger(is, Rot.MODID + "rankLevel", rank);
							UtilityNBTHelper
									.setInteger(
											is,
											Rot.MODID + "dexModifier",
											player.worldObj.rand.nextInt(3)
													+ (rank * (player.worldObj.rand
															.nextInt(2) == 0 ? 1 : -1)));
						}
					}
				}
			}

			// Stat handling
			int strMod = 0, dexMod = 0, vitMod = 0, agiMod = 0, intMod = 0;
			ItemStack held = player.getEquipmentInSlot(0), armor1 = player
					.getEquipmentInSlot(1), armor2 = player.getEquipmentInSlot(2), armor3 = player
					.getEquipmentInSlot(3), armor4 = player.getEquipmentInSlot(4);

			if (held != null) {
				if ((held.getItem() instanceof ItemSword)
						|| (held.getItem() instanceof ItemTool)
						|| (held.getItem() instanceof ItemBow)) {
					strMod += UtilityNBTHelper.getInt(held, Rot.MODID + "strModifier");
					agiMod += UtilityNBTHelper.getInt(held, Rot.MODID + "agiModifier");
					intMod += UtilityNBTHelper.getInt(held, Rot.MODID + "intModifier");
					vitMod += UtilityNBTHelper.getInt(held, Rot.MODID + "vitModifier");
					dexMod += UtilityNBTHelper.getInt(held, Rot.MODID + "dexModifier");
				}
			}
			if (armor1 != null) {
				strMod += UtilityNBTHelper.getInt(armor1, Rot.MODID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor1, Rot.MODID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor1, Rot.MODID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor1, Rot.MODID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor1, Rot.MODID + "dexModifier");
			}
			if (armor2 != null) {
				strMod += UtilityNBTHelper.getInt(armor2, Rot.MODID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor2, Rot.MODID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor2, Rot.MODID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor2, Rot.MODID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor2, Rot.MODID + "dexModifier");
			}
			if (armor3 != null) {
				strMod += UtilityNBTHelper.getInt(armor3, Rot.MODID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor3, Rot.MODID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor3, Rot.MODID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor3, Rot.MODID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor3, Rot.MODID + "dexModifier");
			}
			if (armor4 != null) {
				strMod += UtilityNBTHelper.getInt(armor4, Rot.MODID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor4, Rot.MODID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor4, Rot.MODID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor4, Rot.MODID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor4, Rot.MODID + "dexModifier");
			}
			if (props.getDexterity() != (dexMod - props.getClassModifers()[4])) {
				props.setDexterity(dexMod);
			}
			if (props.getAgility() != (agiMod - props.getClassModifers()[1])) {
				props.setAgility(agiMod);
				ExtendPlayer.get(player).updateMoveSpeed();				
			}
			if (props.getIntelligence() != (intMod - props.getClassModifers()[2])) {
				props.setIntelligence(intMod);
			}
			if (props.getStrength() != (strMod - props.getClassModifers()[0])) {
				props.setStrength(strMod);
			}
			if (props.getVitality() != (vitMod - props.getClassModifers()[3])) {
				props.setVitality(vitMod);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		if (event.wasDeath) {
			if (!event.entityPlayer.worldObj.isRemote) {
				ExtendPlayer old = ExtendPlayer.get(event.original);
				ExtendPlayer properties = ExtendPlayer.get(event.entityPlayer);
				properties.setValues(old.getValues());
				Rot.net.sendToServer(new ClassRequestPacket("whatAmI"));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent e) {
		if (e.entity instanceof EntityPlayer) {
			if(!e.entity.worldObj.isRemote)
			{
				ExtendPlayer props = ExtendPlayer.get((EntityPlayer)e.entity);
				Rot.net.sendTo(new ClassResponsePacket(props.getCurrentClassName()), (EntityPlayerMP)e.entity);
			}
			//Rot.net.sendToServer(new ClassRequestPacket("whatAmI"));
		}
	}
	
	// @SubscribeEvent
	// public void onLivingFallEvent(LivingFallEvent event)
	// {
	// // Remember that so far we have only added ExtendedPlayer properties
	// // so check if it's the right kind of entity first
	// if (event.entity instanceof EntityPlayer)
	// {
	// ExtendPlayerRot props = ExtendPlayerRot.get((EntityPlayer) event.entity);
	//
	// // This 'if' statement just saves a little processing time and
	// // makes it so we only deplete mana from a fall that would injure the
	// player
	// if (event.distance > 3.0F && props.getCurrentMana() > 0)
	// {
	// // Some debugging statements so you can see what's happening
	// // System.out.println("[EVENT] Fall distance: " + event.distance);
	// // System.out.println("[EVENT] Current mana: " + props.getCurrentMana());
	//
	// /*
	// We need to make a local variable to store the amount to reduce both
	// the distance and mana, otherwise when we reduce one, we have no way
	// to tell by how much to reduce the other
	//
	// Alternatively, you could just try to consumeMana for the amount of the
	// fall distance and, if it returns true, set the fall distance to 0,
	// but today we're going for a cushioning effect instead.
	//
	// If you want mana to be used efficiently, you would only reduce the fall
	// distance by enough to reduce it to 3.0F (3 blocks), thus ensuring the
	// player will take no damage while minimizing mana consumed.
	//
	// Be sure you put (event.distance - 3.0F) in parentheses or you'll have a
	// nasty bug with your mana! It has to do with the way "x < y ? a : b"
	// parses parameters.
	// */
	// float reduceby = props.getCurrentMana() < (event.distance - 3.0F) ?
	// props.getCurrentMana() : (event.distance - 3.0F);
	// event.distance -= reduceby;
	//
	// // Cast reduceby to 'int' to match our method parameter
	// props.consumeMana(reduceby);
	//
	// // System.out.println("[EVENT] Adjusted fall distance: " +
	// event.distance);
	// }
	// }
	// }
	
}
