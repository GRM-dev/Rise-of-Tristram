package ee.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;
import ee.rot.libs.UtilityNBTHelper;

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
			ExtendPlayer props = ExtendPlayer.get(player);
			//Mana and Stamina regeneration
			float timeMath = 3* 60 * 10;
			if (player.shouldHeal() && player.worldObj.getWorldTime() % 30 == 0)
				player.heal(0.05f + (props.getVitality()/20));
			
			if (player.isPlayerSleeping())
			{
				props.replenishMana();
				props.replenishStam();
			}
			props.regenMana((props.getCurrentClassName().equals(ExtendPlayer.classNames[2]) ? 20f : 5f) / timeMath + (props.getIntelligence() * 3)/60);
			if (player.isInWater())
			{
				props.regenStam((30f + props.getVitality() * 3) / 60 + ((player.experienceLevel)*2)/timeMath);				
			}
			else
			{
				props.regenStam((30f + props.getVitality() * 3) / timeMath + ((player.experienceLevel)*2)/timeMath);
			}
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
