package ca.grm.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;

public class RotEventLivingUpdate {
	// In your TutEventHandler class - the name of the method doesn't matter
	// Only the Event type parameter is what's important (see below for
	// explanations of some types)
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		
		if ((event.entity instanceof EntityPlayer)
				&& (ExtendPlayer.get((EntityPlayer) event.entity) == null)) {
			ExtendPlayer.register((EntityPlayer) event.entity);
		}
	}
	/*if (event.entity instanceof EntityLivingBase) {
	*
	 * ExtendLivingBaseRot props =
	 * ExtendLivingBaseRot.get((EntityLivingBase) event.entity);
	 * // Gives a random amount of gold between 0 and 15
	 * props.addGold(event.entity.worldObj.rand.nextInt(16));
	 * System.out.println("[LIVING BASE] Gold: " + props.getGold());
	 *
}*/
	
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		// This event has an Entity variable, access it like this:
		// event.entity;
		
		// do something to player every update tick:
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			if (props.needsUpdate)
			{
				Rot.proxy.updatePlayerClass(player);
			}
			handlePlayerStats(props, player);
			// Mana and Stamina regeneration
			float timeMath = 3 * 60 * 10;
			if (player.shouldHeal() && ((player.worldObj.getWorldTime() % 30) == 0)) {
				player.heal(0.05f + (props.getVitality() / 20));
			}

			if (player.isPlayerSleeping()) {
				props.replenishMana();
				props.replenishStam();
			}
			props.regenMana(((props.getCurrentClassName().equals(
					ExtendPlayer.classNames[2]) ? 20f : 5f) / timeMath)
					+ ((props.getIntelligence() * 3) / 60));
			if (player.isInWater()) {
				props.regenStam(((30f + (props.getVitality() * 3)) / 60)
						+ (((player.experienceLevel) * 2) / timeMath));
			} else {
				props.regenStam(((30f + (props.getVitality() * 3)) / timeMath)
						+ (((player.experienceLevel) * 2) / timeMath));
			}
		} else if (event.entity instanceof EntityIronGolem) {
			EntityIronGolem golem = (EntityIronGolem) event.entity;
			golem.heal(0.05f);
			if (!golem.worldObj.isRemote) {
				float r = golem.worldObj.rand.nextFloat();
				if ((r >= 0.5554f) && (r <= 0.5556f)) {
					ItemStack[] itemStacks = new ItemStack[3];
					itemStacks[0] = new ItemStack(Items.pumpkin_seeds);
					itemStacks[1] = new ItemStack(Items.clay_ball);
					itemStacks[2] = new ItemStack(Items.flint);
					EntityItem i = new EntityItem(golem.worldObj, golem.posX, golem.posY,
							golem.posZ, itemStacks[golem.worldObj.rand.nextInt(3)]);
					golem.worldObj.spawnEntityInWorld(i);
				}
			}
		} else if (event.entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) event.entity;
			villager.heal(0.075f);
			if (!villager.worldObj.isRemote) {
				float r = villager.worldObj.rand.nextFloat();
				if ((r >= 0.5554f) && (r <= 0.5556f)) {
					int itemNumber = 0;
					ItemStack[] itemStacks = new ItemStack[1];
					
					switch (villager.getProfession()) {
						case 0 :// farmer
							itemStacks = new ItemStack[3];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.wheat);
							itemStacks[2] = new ItemStack(Items.cookie);
							break;
						case 1 : // lab
							itemStacks = new ItemStack[2];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.paper);
							break;
						case 2 :// purple
							itemStacks = new ItemStack[2];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.redstone);
							break;
						case 3 :// blacksmith
							itemStacks = new ItemStack[4];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.coal);
							itemStacks[2] = new ItemStack(Items.iron_ingot);
							itemStacks[3] = new ItemStack(Items.gold_nugget);
							break;
						case 4 : // butcher
							itemStacks = new ItemStack[3];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.beef);
							itemStacks[2] = new ItemStack(Items.porkchop);
							break;
						default :
							itemStacks[0] = new ItemStack(Items.emerald);
							break;
					
					/*
					 * emerald for all
					 * 0 farmer brown (wheat, cookie)
					 * 1 White Lab Coat (paper)
					 * 2 purple coat (redstone dust)
					 * 3 blacksmith (coal, iron ingot, gold nugget)
					 * 4 Butcher (raw beef, raw porkchop)
					 */
					}
					if (itemStacks.length == 1) {
						itemNumber = 0;
					} else {
						itemNumber = villager.worldObj.rand.nextInt(itemStacks.length);
					}
					EntityItem i = new EntityItem(villager.worldObj, villager.posX,
							villager.posY, villager.posZ, itemStacks[itemNumber]);
					villager.worldObj.spawnEntityInWorld(i);
				}
			}
		} else if (event.entity instanceof EntityWolf) {
			EntityWolf wolf = (EntityWolf) event.entity;
			wolf.heal(0.05f);
		} else if (event.entity instanceof EntityHorse) {
			EntityHorse horse = (EntityHorse) event.entity;
			horse.heal(0.05f);
		} else {
			if (event.entity instanceof EntityArmorStand){}
			else
			{
				EntityLiving e = (EntityLiving) event.entity;
				if (e.hurtResistantTime != 5) {
					e.hurtResistantTime = 5;
					e.arrowHitTimer = 1;
				}
				e.heal(0.0025f);
			}
		}
	}
	
	private void handlePlayerStats(ExtendPlayer props, EntityPlayer player)
	{
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
