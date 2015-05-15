package ca.grm.rot.events;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
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
import ca.grm.rot.libs.ExtendMob;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;

public class RotEventDamage
{

	private static float upscalePercent = 5f;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamage(LivingHurtEvent event)
	{
		// Offense
		if (event.source instanceof EntityDamageSource)
		{
			EntityDamageSource source = (EntityDamageSource) event.source;
			Random rand = new Random();
			if (source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source.getEntity();
				ExtendPlayer props = ExtendPlayer.get(player);
				float tempDmg = event.ammount * upscalePercent;
				if (event.source.getDamageType() == "player")
				{
					float finalMinDmg = 10 + tempDmg + props.getMinDmg()
							* (props.getStrength() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg()
							* (props.getStrength() + 100) / 100;
					event.ammount += (rand.nextInt((int) finalMaxDmg
							- (int) finalMinDmg) + (int) finalMinDmg);
				}
				else if (event.source.getDamageType() == "arrow")
				{
					float finalMinDmg = 10 + tempDmg + props.getMinDmg()
							* (props.getDexterity() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg()
							* (props.getDexterity() + 100) / 100;
					event.ammount += (rand.nextInt((int) finalMaxDmg
							- (int) finalMinDmg) + (int) finalMinDmg);
				}
			}
			else
			{
				if (source.getEntity() instanceof EntityLiving)
				{
					EntityLiving mob = (EntityLiving) source.getEntity();
					if (ExtendMob.get(mob) != null)
					{
						ExtendMob props = ExtendMob.get(mob);
						float tempDmg = event.ammount * upscalePercent;
						if (event.source.getDamageType() == "arrow")
						{
							float finalMinDmg = 10 + tempDmg + props.minDmg
									* (props.dexterity + 100) / 100;
							float finalMaxDmg = 20 + tempDmg + props.maxDmg
									* (props.dexterity + 100) / 100;
							event.ammount += (rand.nextInt((int) finalMaxDmg
									- (int) finalMinDmg) + (int) finalMinDmg);
						}
						else
						{
							float finalMinDmg = 10 + tempDmg + props.minDmg
									* (props.strength + 100) / 100;
							float finalMaxDmg = 20 + tempDmg + props.maxDmg
									* (props.strength + 100) / 100;
							event.ammount += (rand.nextInt((int) finalMaxDmg
									- (int) finalMinDmg) + (int) finalMinDmg);
						}
					}
					else event.ammount *= upscalePercent;
				}
			}
		}
		else event.ammount *= upscalePercent;

		// Defense
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			if (event.source instanceof EntityDamageSource) event.ammount -= props
					.getDefBonus();
			float adjustedMaxHP = (player.getMaxHealth() * upscalePercent)
					+ props.pickedClass.baseHp
					+ (props.getVitality() * props.pickedClass.hpPerVit);
			float adjustedHP = adjustedMaxHP - event.ammount;
			float adjustedHPPercent = 1f - (adjustedHP / adjustedMaxHP);
			float newDamage = player.getMaxHealth() * adjustedHPPercent;
			event.ammount = newDamage;
		}
		else
		{
			EntityLiving e = (EntityLiving) event.entity;
			if (ExtendMob.get(e) != null)
			{
				ExtendMob props = ExtendMob.get(e);
				if (event.source instanceof EntityDamageSource) event.ammount -= props.defBonus;
				float adjustedMaxHP = (e.getMaxHealth() * upscalePercent)
						+ (props.vitality * (props.monsterLevel * 2.5f));
				float adjustedHP = adjustedMaxHP - event.ammount;
				float adjustedHPPercent = 1f - (adjustedHP / adjustedMaxHP);
				float newDamage = e.getMaxHealth() * adjustedHPPercent;
				event.ammount = newDamage;
			}
			else
			{
				float adjustedMaxHP = (e.getMaxHealth() * upscalePercent);
				float adjustedHP = adjustedMaxHP - event.ammount;
				float adjustedHPPercent = 1f - (adjustedHP / adjustedMaxHP);
				float newDamage = e.getMaxHealth() * adjustedHPPercent;
				event.ammount = newDamage;
			}
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
