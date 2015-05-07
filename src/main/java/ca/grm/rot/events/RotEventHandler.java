package ca.grm.rot.events;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.ClassResponsePacket;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;

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

	/*public void updateItemToolTipOld(ItemTooltipEvent i)
	{
		// This is all the code that was in 'onItemToolTipUpdate'
		// THIS IS NOT AN EVENT.
		int rank = UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "rankLevel"), str = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "strModifier"), agi = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "agiModifier"), inte = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "intModifier"), vit = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "vitModifier"), dex = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "dexModifier");
		
		if ((i.itemStack.getItem() instanceof ItemTool)
				|| (i.itemStack.getItem() instanceof ItemSword)
				|| (i.itemStack.getItem() instanceof ItemArmor)
				|| (i.itemStack.getItem() instanceof ItemBow)) {
			if (rank != 0) {
				i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);
			}
			if (str != 0) {
				i.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Strength Modifier: " + str);
			}
			if (agi != 0) {
				i.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Agility Modifier: " + agi);
			}
			if (inte != 0) {
				i.toolTip.add((inte > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Intelligence Modifier: " + inte);
			}
			if (vit != 0) {
				i.toolTip.add((vit > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Vitality Modifier: " + vit);
			}
			if (dex != 0) {
				i.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Dexterity Modifier: " + dex);
			}
		}
	}
	
	public void onItemPickupApplyOld(ItemPickupEvent e)
	{
		ItemStack is = e.pickedUp.getEntityItem();
		Item i = is.getItem();
		World w = e.pickedUp.worldObj;
		// Adding stats to Equipment
				if (i instanceof ItemArmor) {
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "strModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "agiModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "intModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "vitModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "dexModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
					}
				}
				if ((i instanceof ItemSword) || (i instanceof ItemTool)) {
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "strModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
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
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "dexModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
					}
				}	
	}*/
	
	/*public void onLivingUpdateEventOld(LivingUpdateEvent event){
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			// Update Class
			if (props.needsUpdate)
			{
				if (Minecraft.getMinecraft().thePlayer != null)
				{
					Rot.net.sendTo(new ClassResponsePacket(props.getCurrentClassIndex()), (EntityPlayerMP)player);
					props.needsUpdate = false;
				}
			}
			
			// Adding stats to Equipment
						for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
							ItemStack is = player.inventory.getStackInSlot(slot);
							if (is != null) {
								Item i = is.getItem();
								if (i instanceof ItemArmor) {
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "strModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "agiModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "intModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "vitModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "dexModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
									}
								}
								if ((i instanceof ItemSword) || (i instanceof ItemTool)) {
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "strModifier",
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
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "dexModifier",
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
					strMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "strModifier");
					agiMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "agiModifier");
					intMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "intModifier");
					vitMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "vitModifier");
					dexMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "dexModifier");
				}
			}
			if (armor1 != null) {
				strMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "dexModifier");
			}
			if (armor2 != null) {
				strMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "dexModifier");
			}
			if (armor3 != null) {
				strMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "dexModifier");
			}
			if (armor4 != null) {
				strMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "strModifier");
				agiMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "agiModifier");
				intMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "intModifier");
				vitMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "dexModifier");
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
	}*/
	
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

		// Defense
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
	public void onItemToolTipUpdate(ItemTooltipEvent i) {
		// This is here incase we wanna do the old stuff. Basically a backup.
		//updateItemToolTipOld(e);
		int rank = UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "rankLevel"), str = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "strModifier"), agi = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "agiModifier"), inte = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "intModifier"), vit = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "vitModifier"), dex = UtilityNBTHelper
				.getInt(i.itemStack, Rot.MOD_ID + "dexModifier");
		
				str += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
				if (i.itemStack.getItem() instanceof ItemArmor) {
					agi += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					inte += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					vit += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
					dex += UtilityNBTHelper.getInt(i.itemStack, Rot.MOD_ID + "qualityModifier");
				}
				 String quality = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "quality");
				 String qualityDisplay = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "qualityDisplay");
				 String magicModifierPrefix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierPrefix");
				 String magicModifierSuffix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierSuffix");
				 String magicModifierDescriptionPrefix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierDescriptionPrefix");
				 String magicModifierDescriptionSuffix = UtilityNBTHelper.getString(i.itemStack, Rot.MOD_ID + "magicModifierDescriptionSuffix");
				 if (qualityDisplay == "Crude" || qualityDisplay == "Cracked" || qualityDisplay == "Damaged")
				 {
					 qualityDisplay = EnumChatFormatting.RED + qualityDisplay;
					 System.out.println("Changed Formatting!");
				 }
				 else if (qualityDisplay == "Normal")
				 {
					 qualityDisplay = EnumChatFormatting.YELLOW + qualityDisplay;
				 }
				 else if (qualityDisplay == "Superior")
				 {
					 qualityDisplay = EnumChatFormatting.GREEN + qualityDisplay;
				 }
				 else if (qualityDisplay == "Magic")
				 {
					 qualityDisplay = EnumChatFormatting.AQUA + qualityDisplay;
				 }
		
		if ((i.itemStack.getItem() instanceof ItemTool)
				|| (i.itemStack.getItem() instanceof ItemSword)
				|| (i.itemStack.getItem() instanceof ItemArmor)
				|| (i.itemStack.getItem() instanceof ItemBow)) {
			if (quality != "")
			{
				i.toolTip.add(EnumChatFormatting.WHITE + "Quality: " + EnumChatFormatting.LIGHT_PURPLE + magicModifierPrefix + qualityDisplay + EnumChatFormatting.LIGHT_PURPLE + magicModifierSuffix);
			}	
			if (rank != 0) {
				i.toolTip.add(EnumChatFormatting.YELLOW + "Rank: " + rank);
			}
			if (str != 0) {
				i.toolTip.add((str > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Strength Modifier: " + str);
			}
			if (agi != 0) {
				i.toolTip.add((agi > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Agility Modifier: " + agi);
			}
			if (inte != 0) {
				i.toolTip.add((inte > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Intelligence Modifier: " + inte);
			}
			if (vit != 0) {
				i.toolTip.add((vit > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Vitality Modifier: " + vit);
			}
			if (dex != 0) {
				i.toolTip.add((dex > 0 ? EnumChatFormatting.GREEN
						: EnumChatFormatting.RED) + "Dexterity Modifier: " + dex);
			}
			if (magicModifierPrefix != "")
			{
				i.toolTip.add(""); // Blank line for spacing.
				i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + "Prefix Effect: " + EnumChatFormatting.GOLD + magicModifierDescriptionPrefix);
			}
			if (magicModifierSuffix != "")
			{
				i.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + "Suffix Effect: " + EnumChatFormatting.GOLD + magicModifierDescriptionSuffix);
			}
		}
	}

	@SubscribeEvent
	public void onItemPickup(ItemPickupEvent e)
	{
		/*// This is here incase we wanna do the old stuff. Basically a backup.
		//onItemPickupApplyOld(e);
		
		ItemStack is = e.pickedUp.getEntityItem();
		Item i = is.getItem();
		World w = e.pickedUp.worldObj;
		Random random = new Random();
		// Adding stats to Equipment

				if (i instanceof ItemArmor) {
					String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
					int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
					String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
					if (quality == "")
					{
						// Low, Normal, Superior, Magic.
						// 30% = Low, all stats -1
						// 30% = Normal, +/- 0
						// 30% = Superior, all stats +1
						// 10% = Magic, all stats +2 w/ random magic effect
						int q = random.nextInt(100);
						int qd = random.nextInt(3);
						if (q >= 00 && q <= 29)
						{
							// Low
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
							if (qd == 0)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
							}
							else if (qd == 1)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
							}
							else if (qd == 2)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
							}
						}
						else if (q >= 30 && q <= 59)
						{
							// Normal
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
						}
						else if (q >= 60 && q <= 89)
						{
							// Superior
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
						}
						else if (q >= 90 && q <= 99)
						{
							// Magic
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
						}			
					}
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "strModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "agiModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "intModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "vitModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "dexModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
					}
				}
				if ((i instanceof ItemSword) || (i instanceof ItemTool)) {
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
					int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
					String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
					if (quality == "")
					{
						// Low, Normal, Superior, Magic.
						// 30% = Low, all stats -1
						// 30% = Normal, +/- 0
						// 30% = Superior, all stats +1
						// 10% = Magic, all stats +2 w/ random magic effect
						int q = random.nextInt(100);
						int qd = random.nextInt(3);
						if (q >= 00 && q <= 29)
						{
							// Low
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
							if (qd == 0)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
							}
							else if (qd == 1)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
							}
							else if (qd == 2)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
							}
						}
						else if (q >= 30 && q <= 59)
						{
							// Normal
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
						}
						else if (q >= 60 && q <= 89)
						{
							// Superior
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
						}
						else if (q >= 90 && q <= 99)
						{
							// Magic
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
						}			
					}
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "strModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
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
					String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
					int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
					String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
					if (quality == "")
					{
						// Low, Normal, Superior, Magic.
						// 30% = Low, all stats -1
						// 30% = Normal, +/- 0
						// 30% = Superior, all stats +1
						// 10% = Magic, all stats +2 w/ random magic effect
						int q = random.nextInt(100);
						int qd = random.nextInt(3);
						if (q >= 00 && q <= 29)
						{
							// Low
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
							if (qd == 0)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
							}
							else if (qd == 1)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
							}
							else if (qd == 2)
							{
								UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
							}
						}
						else if (q >= 30 && q <= 59)
						{
							// Normal
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
						}
						else if (q >= 60 && q <= 89)
						{
							// Superior
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
						}
						else if (q >= 90 && q <= 99)
						{
							// Magic
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
							UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
							UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
						}			
					}
					int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
					if (rank == 0) {
						rank = 1;
						UtilityNBTHelper
								.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
						UtilityNBTHelper
								.setInteger(
										is,
										Rot.MOD_ID + "dexModifier",
										w.rand.nextInt(3)
												+ (rank * (w.rand
														.nextInt(2) == 0 ? 1 : -1)));
					}
				}*/
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		// This is here incase we wanna do the old stuff. Basically a backup.
		//onLivingUpdateEventOld(event);
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			// Update Class
			if (props.needsUpdate)
			{
				Rot.proxy.updatePlayerClass(player);
			}
			Random random = new Random();
			// Adding stats to Equipment
						for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
							ItemStack is = player.inventory.getStackInSlot(slot);
							if (is != null) {
								Item i = is.getItem();
								if (i instanceof ItemArmor) {
									String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
									int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
									String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
									String magicModifierPrefix = UtilityNBTHelper.getString(is, Rot.MOD_ID + "magicModifierPrefix");
									int magicModifierPrefixLevel = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "magicModifierPrefixLevel");
									String magicModifierSuffix = UtilityNBTHelper.getString(is, Rot.MOD_ID + "magicModifierSuffix");
									int magicModifierSuffixLevel = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "magicModifierSuffixLevel");
									if (quality == "")
									{
										// Low, Normal, Superior, Magic.
										// 30% = Low, all stats -1
										// 30% = Normal, +/- 0
										// 30% = Superior, all stats +1
										// 10% = Magic, all stats +2 w/ random magic effect
										int q = random.nextInt(100);
										int qd = random.nextInt(3);
										if (q >= 00 && q <= 29)
										{
											// Low
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
											if (qd == 0)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
											}
											else if (qd == 1)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
											}
											else if (qd == 2)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
											}
										}
										else if (q >= 30 && q <= 59)
										{
											// Normal
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
										}
										else if (q >= 60 && q <= 89)
										{
											// Superior
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
										}
										else if (q >= 90 && q <= 99)
										{
											// Magic
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
											// Armor Magics; 40% Prefix, 40% Suffix, 20% Both.
											// This means there's a 4% chance of getting a magic item with a prefix, same for suffix, and 2% for both.
											
											// Prefixes
											// Sturdy = + 1 Vit
											// Crimson = +1 Fire Resist
											// Glimmering = +1 Light Radius
											// Lizard's = +1 Int/Mana regen
											// Rugged = +1 Stam regen
											
											// Suffixes
											// Balance = +1 Vit
											// Health = +1 health regen
											// Warding = +1 Magic Resist
											// Self Repair = 1 +dura every 33 seconds
											// Skill = +1 Dex
											// Energy = +1 stam
											// Jackal = +1 vit
											
											int psb = random.nextInt(10);
											if (psb >= 0 && psb <= 3)
											{
												// Apply prefix only
												int p = random.nextInt(5);
												switch (p)
												{
												case (0):
													// Sturdy
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Sturdy");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (1):
													// Crimson
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Crimson");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (2):
													// Glimmering
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Glimmering");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (3):
													// Lizard's
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Lizard's");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (4):
													// Rugged
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Rugged");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												}
											}
											else if (psb >= 4 && psb <= 7)
											{
												// Apply suffix only
												int s = random.nextInt(7);
												switch(s)
												{
												case (0):
													// Balance
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Balance");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (1):
													// Health
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Health");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (2):
													// Warding
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Warding");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (3):
													// Self Repair
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Self Repair");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (4):
													// Skill
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Skill");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (5):
													// Energy
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Energy");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (6):
													// Jackal
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Jackal");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												}
											}
											else if (psb >= 8 && psb <= 9)
											{
												// Apply both
												
												// Apply prefix only
												int p = random.nextInt(5);
												switch (p)
												{
												case (0):
													// Sturdy
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Sturdy");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (1):
													// Crimson
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Crimson");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (2):
													// Glimmering
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Glimmering");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (3):
													// Lizard's
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Lizard's");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												case (4):
													// Rugged
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierPrefix", "Rugged");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierPrefixLevel", 1);
												}
												
												// Apply suffix
												int s = random.nextInt(7);
												switch(s)
												{
												case (0):
													// Balance
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Balance");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (1):
													// Health
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Health");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (2):
													// Warding
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Warding");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (3):
													// Self Repair
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Self Repair");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (4):
													// Skill
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Skill");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (5):
													// Energy
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Energy");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												case (6):
													// Jackal
													UtilityNBTHelper.setString(is, Rot.MOD_ID + "magicModifierSuffix", "Jackal");
													UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "magicModifierSuffixLevel", 1);
												}
											}
										}			
									}
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "strModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "agiModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "intModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "vitModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "dexModifier",
														player.worldObj.rand.nextInt(3)
																+ (rank * (player.worldObj.rand
																		.nextInt(2) == 0 ? 1 : -1)));
									}
								}
								if ((i instanceof ItemSword) || (i instanceof ItemTool)) {
									String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
									int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
									String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
									if (quality == "")
									{
										// Low, Normal, Superior, Magic.
										// 30% = Low, all stats -1
										// 30% = Normal, +/- 0
										// 30% = Superior, all stats +1
										// 10% = Magic, all stats +2 w/ random magic effect
										int q = random.nextInt(100);
										int qd = random.nextInt(3);
										if (q >= 00 && q <= 29)
										{
											// Low
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
											if (qd == 0)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
											}
											else if (qd == 1)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
											}
											else if (qd == 2)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
											}
										}
										else if (q >= 30 && q <= 59)
										{
											// Normal
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
										}
										else if (q >= 60 && q <= 89)
										{
											// Superior
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
										}
										else if (q >= 90 && q <= 99)
										{
											// Magic
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
										}			
									}
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "strModifier",
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
									String quality = UtilityNBTHelper.getString(is, Rot.MOD_ID + "quality");
									int qualityModifier = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "qualityModifier");
									String qualityDisplay = UtilityNBTHelper.getString(is, Rot.MOD_ID + "qualityDisplay");
									if (quality == "")
									{
										// Low, Normal, Superior, Magic.
										// 30% = Low, all stats -1
										// 30% = Normal, +/- 0
										// 30% = Superior, all stats +1
										// 10% = Magic, all stats +2 w/ random magic effect
										int q = random.nextInt(100);
										int qd = random.nextInt(3);
										if (q >= 00 && q <= 29)
										{
											// Low
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Low");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", -1);
											if (qd == 0)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Cracked");
											}
											else if (qd == 1)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Damaged");
											}
											else if (qd == 2)
											{
												UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Crude");
											}
										}
										else if (q >= 30 && q <= 59)
										{
											// Normal
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Normal");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 0);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Normal");
										}
										else if (q >= 60 && q <= 89)
										{
											// Superior
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Superior");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 1);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Superior");
										}
										else if (q >= 90 && q <= 99)
										{
											// Magic
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "quality", "Magic");
											UtilityNBTHelper.setInteger(is, Rot.MOD_ID + "qualityModifier", 2);
											UtilityNBTHelper.setString(is, Rot.MOD_ID + "qualityDisplay", "Magic");
										}			
									}
									int rank = UtilityNBTHelper.getInt(is, Rot.MOD_ID + "rankLevel");	
									if (rank == 0) {
										rank = 1;
										UtilityNBTHelper
												.setInteger(is, Rot.MOD_ID + "rankLevel", rank);
										UtilityNBTHelper
												.setInteger(
														is,
														Rot.MOD_ID + "dexModifier",
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
					strMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "strModifier");
					strMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "qualityModifier");
					agiMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "agiModifier");
					intMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "intModifier");
					vitMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "vitModifier");
					dexMod += UtilityNBTHelper.getInt(held, Rot.MOD_ID + "dexModifier");
				}
			}
			if (armor1 != null) {
				strMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "strModifier");
				strMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "qualityModifier");
				agiMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "agiModifier");
				agiMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "qualityModifier");
				intMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "intModifier");
				intMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "qualityModifier");
				vitMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "vitModifier");
				vitMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "qualityModifier");
				dexMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "dexModifier");
				dexMod += UtilityNBTHelper.getInt(armor1, Rot.MOD_ID + "qualityModifier");
			}
			if (armor2 != null) {
				strMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "strModifier");
				strMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "qualityModifier");
				agiMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "agiModifier");
				agiMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "qualityModifier");
				intMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "intModifier");
				intMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "qualityModifier");
				vitMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "vitModifier");
				vitMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "qualityModifier");
				dexMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "dexModifier");
				dexMod += UtilityNBTHelper.getInt(armor2, Rot.MOD_ID + "qualityModifier");
			}
			if (armor3 != null) {
				strMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "strModifier");
				strMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "qualityModifier");
				agiMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "agiModifier");
				agiMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "qualityModifier");
				intMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "intModifier");
				intMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "qualityModifier");
				vitMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "vitModifier");
				vitMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "qualityModifier");
				dexMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "dexModifier");
				dexMod += UtilityNBTHelper.getInt(armor3, Rot.MOD_ID + "qualityModifier");
			}
			if (armor4 != null) {
				strMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "strModifier");
				strMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "qualityModifier");
				agiMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "agiModifier");
				agiMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "qualityModifier");
				intMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "intModifier");
				intMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "qualityModifier");
				vitMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "vitModifier");
				vitMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "qualityModifier");
				dexMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "dexModifier");
				dexMod += UtilityNBTHelper.getInt(armor4, Rot.MOD_ID + "qualityModifier");
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
				Rot.net.sendToServer(new ClassRequestPacket(0));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent e) {
		if (e.entity instanceof EntityPlayer) {
			if(!e.entity.worldObj.isRemote)
			{
				ExtendPlayer props = ExtendPlayer.get((EntityPlayer)e.entity);
				props.needsUpdate = true;
				/*if (props != null)
				Rot.net.sendTo(new ClassResponsePacket(props.getCurrentClassIndex()), (EntityPlayerMP)e.entity);
				else System.out.println("something fucked up getting the props");*/
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
