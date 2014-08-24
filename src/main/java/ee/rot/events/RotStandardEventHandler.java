package ee.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;
import ee.rot.UtilityNBTHelper;

public class RotStandardEventHandler 
{
	// In your TutEventHandler class - the name of the method doesn't matter
	// Only the Event type parameter is what's important (see below for explanations of some types)	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		// This event has an Entity variable, access it like this:
		//event.entity;
	
		// do something to player every update tick:
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayerRot props = ExtendPlayerRot.get(player);
			//Mana and Stamina regeneration
			float timeMath = 3* 60 * 10;
			if (player.isPlayerSleeping())
			{
				props.replenishMana();
				props.replenishStam();
			}
			if (player.isInWater())
			{
				props.regenMana((5f + props.getIntelligence() * 2) / timeMath + (player.experienceLevel)/timeMath);
				props.regenStam((30f + props.getVitality() * 3) / 60 + ((player.experienceLevel)*2)/timeMath);				
			}
			else
			{
				props.regenMana((5f + props.getIntelligence() * 2) / timeMath + (player.experienceLevel)/timeMath);
				props.regenStam((30f + props.getVitality() * 3) / timeMath + ((player.experienceLevel)*2)/timeMath);
			}	
			//Adding stats to Equipment
			for (int slot = 0; slot < player.inventory.getSizeInventory();slot++)
			{
				ItemStack is = player.inventory.getStackInSlot(slot);
				if (is != null)
				{
					if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemArmor)
					{
						int rank = UtilityNBTHelper.getInt(is, Rot.MODID+"rankLevel");
						if (rank == 0)
						{
							rank = MathHelper.clamp_int((int)(Math.random() * 100) / 50, 1, 5);
							UtilityNBTHelper.setInteger(is, Rot.MODID+"rankLevel", rank);
							UtilityNBTHelper.setInteger(is, Rot.MODID+"strModifier", player.worldObj.rand.nextInt(3) + rank * (player.worldObj.rand.nextInt(1) == 0 ? 1 : -1));
							UtilityNBTHelper.setInteger(is, Rot.MODID+"intModifier", player.worldObj.rand.nextInt(3) + rank * (player.worldObj.rand.nextInt(1) == 0 ? 1 : -1));
							UtilityNBTHelper.setInteger(is, Rot.MODID+"vitModifier", player.worldObj.rand.nextInt(3) + rank * (player.worldObj.rand.nextInt(1) == 0 ? 1 : -1));
							UtilityNBTHelper.setInteger(is, Rot.MODID+"dexModifier", player.worldObj.rand.nextInt(3) + rank * (player.worldObj.rand.nextInt(1) == 0 ? 1 : -1));
						}
					}
				}
			}
			
			//Stat handling
			int strMod = 0, dexMod = 0, vitMod = 0, intMod = 0;
			ItemStack held = player.getEquipmentInSlot(0), 
					armor1 = player.getEquipmentInSlot(1), 
					armor2 = player.getEquipmentInSlot(2),
					armor3 = player.getEquipmentInSlot(3),
					armor4 = player.getEquipmentInSlot(4);
			
			if (held != null && held.getItem() instanceof ItemSword)
			{
				strMod += UtilityNBTHelper.getInt(held, Rot.MODID+"strModifier");
				intMod += UtilityNBTHelper.getInt(held, Rot.MODID+"intModifier");
				vitMod += UtilityNBTHelper.getInt(held, Rot.MODID+"vitModifier");
				dexMod += UtilityNBTHelper.getInt(held, Rot.MODID+"dexModifier");
			}
			if (armor1 != null)
			{
				strMod += UtilityNBTHelper.getInt(armor1, Rot.MODID+"strModifier");
				intMod += UtilityNBTHelper.getInt(armor1, Rot.MODID+"intModifier");
				vitMod += UtilityNBTHelper.getInt(armor1, Rot.MODID+"vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor1, Rot.MODID+"dexModifier");
			}
			if (armor2 != null)
			{
				strMod += UtilityNBTHelper.getInt(armor2, Rot.MODID+"strModifier");
				intMod += UtilityNBTHelper.getInt(armor2, Rot.MODID+"intModifier");
				vitMod += UtilityNBTHelper.getInt(armor2, Rot.MODID+"vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor2, Rot.MODID+"dexModifier");
			}
			if (armor3 != null)
			{
				strMod += UtilityNBTHelper.getInt(armor3, Rot.MODID+"strModifier");
				intMod += UtilityNBTHelper.getInt(armor3, Rot.MODID+"intModifier");
				vitMod += UtilityNBTHelper.getInt(armor3, Rot.MODID+"vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor3, Rot.MODID+"dexModifier");
			}
			if (armor4 != null)
			{
				strMod += UtilityNBTHelper.getInt(armor4, Rot.MODID+"strModifier");
				intMod += UtilityNBTHelper.getInt(armor4, Rot.MODID+"intModifier");
				vitMod += UtilityNBTHelper.getInt(armor4, Rot.MODID+"vitModifier");
				dexMod += UtilityNBTHelper.getInt(armor4, Rot.MODID+"dexModifier");
			}
			props.setDexterity(dexMod);
			props.setIntelligence(intMod);
			props.setStrength(strMod);
			props.setVitality(vitMod);
			
			/*if (player.isSprinting())
			{
				if (props.getDexterity() > 7 && props.getDexterity() < 11)
				{
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 0, true));
					player.addPotionEffect(new PotionEffect(Potion.jump.id, 1, 0, true));
				}
				else if (props.getDexterity() > 11)
				{
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 1, true));
					player.addPotionEffect(new PotionEffect(Potion.jump.id, 1, 1, true));
				}				
			}*/		
			
		}
		else if (event.entity instanceof EntityIronGolem)
		{
			EntityIronGolem golem = (EntityIronGolem) event.entity;
			golem.heal(0.05f);
			if (!golem.worldObj.isRemote)
			{
				float r = golem.worldObj.rand.nextFloat();
				if (r >= 0.5554f && r <= 0.5556f)
				{
					ItemStack[] itemStacks = new ItemStack[3];
					itemStacks[0] = new ItemStack(Items.pumpkin_seeds);
					itemStacks[1] = new ItemStack(Items.clay_ball);
					itemStacks[2] = new ItemStack(Items.flint);
					EntityItem i = new EntityItem(golem.worldObj, golem.posX, golem.posY, golem.posZ, itemStacks[golem.worldObj.rand.nextInt(3)]);
					golem.worldObj.spawnEntityInWorld(i);
				}
			}
		}
		else if (event.entity instanceof EntityVillager)
		{
			EntityVillager villager = (EntityVillager) event.entity;
			villager.heal(0.075f);
			if (!villager.worldObj.isRemote)
			{
				float r = villager.worldObj.rand.nextFloat();
				if (r >= 0.5554f && r <= 0.5556f)
				{					
					int itemNumber = 0;
					ItemStack[] itemStacks = new ItemStack[1];					
					
					switch (villager.getProfession())
					{
						case 0://farmer
							itemStacks = new ItemStack[3];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.wheat);
							itemStacks[2] = new ItemStack(Items.cookie);
							break;
						case 1: //lab
							itemStacks = new ItemStack[2];	
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.paper);
							break;
						case 2://purple
							itemStacks = new ItemStack[2];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.redstone);
							break;
						case 3:// blacksmith
							itemStacks = new ItemStack[4];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.coal);
							itemStacks[2] = new ItemStack(Items.iron_ingot);
							itemStacks[3] = new ItemStack(Items.gold_nugget);
							break;
						case 4: //butcher
							itemStacks = new ItemStack[3];
							itemStacks[0] = new ItemStack(Items.emerald);
							itemStacks[1] = new ItemStack(Items.beef);
							itemStacks[2] = new ItemStack(Items.porkchop);
							break;
						default:
							itemStacks[0] = new ItemStack(Items.emerald);
							break;
							
							/*emerald for all
							0 farmer brown (wheat, cookie)
							1 White Lab Coat (paper)
							2 purple coat (redstone dust)
							3 blacksmith (coal, iron ingot, gold nugget)
							4 Butcher (raw beef, raw porkchop)*/
					}
					if (itemStacks.length == 1)
					{
						itemNumber = 0;
					}
					else
					{
						itemNumber = villager.worldObj.rand.nextInt(itemStacks.length);
					}					
					EntityItem i = new EntityItem(villager.worldObj, villager.posX, villager.posY, villager.posZ, itemStacks[itemNumber]);
					villager.worldObj.spawnEntityInWorld(i);
				}
			}
		}
		else if (event.entity instanceof EntityWolf)
		{
			EntityWolf wolf = (EntityWolf) event.entity;
			wolf.heal(0.05f);
		}
		else if (event.entity instanceof EntityHorse)
		{
			EntityHorse horse = (EntityHorse) event.entity;
			horse.heal(0.05f);
		}
		else
		{
			EntityLiving e = (EntityLiving) event.entity;
			if(e.hurtResistantTime != 5)
			{
				e.hurtResistantTime = 5;
				e.arrowHitTimer = 1;
			}
			e.heal(0.0025f);
		}
	}
}
