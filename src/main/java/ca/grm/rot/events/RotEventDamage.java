package ca.grm.rot.events;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.UtilNBTHelper;
import ca.grm.rot.libs.UtilNBTKeys;
import ca.grm.rot.managers.RotLootManager;

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
			// If a player there are some extra features to use
			if (source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source.getEntity();
				// Apply Special Weapon Effects
				float dmgBoost = 0f;
				float dmgPrice = 0f;
				if (player.getHeldItem() != null) if (player.getHeldItem().getItem() instanceof ItemSword || player
						.getHeldItem().getItem() instanceof ItemTool || player.getHeldItem()
						.getItem() instanceof ItemBow)
				{
					ItemStack is = player.getHeldItem();
					// Poison and Wither
					float poisonLevel = UtilNBTHelper.getFloat(is, UtilNBTKeys.poison);
					if (poisonLevel != 0)
					{
						switch ((int) poisonLevel)
						{
						case 1:
							event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id,
									60, 1));
							break;
						case 2:
							event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id,
									100, 2));
							break;
						case 3:
							event.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id,
									100, 1));
							break;
						}
					}

					// Debuff Effects
					float sickLevel = UtilNBTHelper.getFloat(is, UtilNBTKeys.sickness);
					if (sickLevel != 0)
					{
						switch ((int) sickLevel)
						{
						case 1:
							event.entityLiving.addPotionEffect(new PotionEffect(
									Potion.confusion.id, 120, 1));
							break;
						case 2:
							event.entityLiving.addPotionEffect(new PotionEffect(
									Potion.confusion.id, 120, 2));
							event.entityLiving.addPotionEffect(new PotionEffect(
									Potion.moveSlowdown.id, 120, 1));
							break;
						case 3:
							event.entityLiving.addPotionEffect(new PotionEffect(
									Potion.confusion.id, 120, 3));
							event.entityLiving.addPotionEffect(new PotionEffect(
									Potion.moveSlowdown.id, 120, 2));
							event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id,
									120, 1));
							break;
						}
					}
					// Health Regeneration prevention/Hunger
					if (UtilNBTHelper.getBoolean(is, UtilNBTKeys.vile))
					{
						event.entityLiving.addPotionEffect(new PotionEffect(Potion.hunger.id, 120));
					}
					// Damage Boosts
					dmgBoost = UtilNBTHelper.getFloat(is, UtilNBTKeys.dmgEnhance);
					dmgPrice = UtilNBTHelper.getFloat(is, UtilNBTKeys.dmgPrice);
				}
				ExtendPlayer props = ExtendPlayer.get(player);
				float tempDmg = event.ammount * upscalePercent;// Take the
																// current
																// damage
																// whatever it
																// may be, and
																// scale it up
				// If the damage is melee which is tracked as "player"
				if (event.source.getDamageType() == "player")
				{
					float finalMinDmg = 10 + tempDmg + props.getMinDmg() * (props.getStrength() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg() * (props.getStrength() + 100) / 100;
					int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
					//TODO finish the bonus dmg and hp cost code, this is kinda working
					/*if (dmgBoost > 0f)
					{
						float healthPayment = newDmg * dmgPrice;
						System.out.println(healthPayment);
						System.out.println(((player.getMaxHealth() * upscalePercent) * (1f - ((props.getAdjustedMaxHealth() - healthPayment) / props
								.getAdjustedMaxHealth()))) * -1f);
						player.heal(((player.getMaxHealth() * upscalePercent) * (1f - ((props.getAdjustedMaxHealth() - healthPayment) / props
								.getAdjustedMaxHealth()))) * -1f);
						newDmg *= dmgBoost;
					}*/
					if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
					else event.ammount -= newDmg;
					if (props.getLifeSteal() != 0) player
							.heal((event.ammount * props.getLifeSteal())/10);
					if (props.getManaSteal() != 0) props.regenMana(event.ammount * props
							.getManaSteal());
				}
				// If the damage was from an arrow
				else if (event.source.getDamageType() == "arrow")
				{
					float finalMinDmg = 10 + tempDmg + props.getMinDmg() * (props.getDexterity() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg() * (props.getDexterity() + 100) / 100;
					int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
					if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
					else event.ammount -= newDmg;
					if (props.getLifeSteal() != 0) player
							.heal(event.ammount * props.getLifeSteal());
					if (props.getManaSteal() != 0) props.regenMana(event.ammount * props
							.getManaSteal());
				}
			}
			else
			// Now if what did the damage was not a player, but still a living
			// entity
			{
				if (source.getEntity() instanceof EntityLiving)
				{
					EntityLiving mob = (EntityLiving) source.getEntity();
					// If said mob has extra stats
					if (ExtendMob.get(mob) != null)
					{
						ExtendMob props = ExtendMob.get(mob);
						float tempDmg = event.ammount * upscalePercent;
						if (event.source.getDamageType() == "arrow")
						{
							float finalMinDmg = 10 + tempDmg + props.minDmg * (props.dexterity + 100) / 100;
							float finalMaxDmg = 20 + tempDmg + props.maxDmg * (props.dexterity + 100) / 100;
							int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
							if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
							else event.ammount -= newDmg;
						}
						else
						{
							float finalMinDmg = 10 + tempDmg + props.minDmg * (props.strength + 100) / 100;
							float finalMaxDmg = 20 + tempDmg + props.maxDmg * (props.strength + 100) / 100;
							int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
							if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
							else event.ammount -= newDmg;
						}

						// Handle suffixes for mobs
						if (props.suffix == "the Cursed")
						{
							event.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id,
									100, 1));
						}

						if (props.suffix == "who Targets Mana")
						{
							if (event.entity instanceof EntityPlayer)
							{
								ExtendPlayer player = ExtendPlayer.get((EntityPlayer) event.entity);
								player.consumeMana(75); // Could maybe change to
														// an amount based on
														// damage delt?
							}
						}

						if (props.suffix == "who Ruins Mana")
						{
							if (event.entity instanceof EntityPlayer)
							{
								ExtendPlayer player = ExtendPlayer.get((EntityPlayer) event.entity);
								player.consumeMana(300); // See above.
							}
						}
					}
					else event.ammount *= upscalePercent; // if no extra stats,
															// just upscale
				}
			}
		}
		else event.ammount *= upscalePercent; // If it is some other damage
												// source say falling, fire just
												// upscale the damage

		// Defense
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			if (event.source instanceof EntityDamageSource) event.ammount -= props.getDefBonus();
			float newDamage = player.getMaxHealth() * (1f - ((props.getAdjustedMaxHealth() - event.ammount) / props
					.getAdjustedMaxHealth()));
			event.ammount = newDamage < 0 ? 0 : newDamage;
		}
		else
		{
			EntityLiving e = (EntityLiving) event.entity;
			if (ExtendMob.get(e) != null)
			{
				ExtendMob props = ExtendMob.get(e);
				if (event.source instanceof EntityDamageSource) event.ammount -= props.defBonus;
				if (event.source.isFireDamage())
				{
					if (props.suffix == "the Heated")
					{
						event.ammount = 0; // Cancels all fire damage.
					}
				}
				float adjustedMaxHP = (e.getMaxHealth() * upscalePercent) + (props.vitality * (props.monsterLevel * 2.5f));
				float adjustedHP = adjustedMaxHP - event.ammount;
				float adjustedHPPercent = 1f - (adjustedHP / adjustedMaxHP);
				float newDamage = e.getMaxHealth() * adjustedHPPercent;
				event.ammount = newDamage < 0 ? 0 : newDamage;
			}
			else
			{
				float adjustedMaxHP = (e.getMaxHealth() * upscalePercent);
				float adjustedHP = adjustedMaxHP - event.ammount;
				float adjustedHPPercent = 1f - (adjustedHP / adjustedMaxHP);
				float newDamage = e.getMaxHealth() * adjustedHPPercent;
				event.ammount = newDamage < 0 ? 0 : newDamage;
			}
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDropsEvent e)
	{
		// TODO This needs improvement, also needs better math
		if (e.entity instanceof EntityLiving)
		{
			EntityLiving entity = (EntityLiving) e.entity;
			if (ExtendMob.get(entity) != null && e.source.getEntity() instanceof EntityPlayer)
			{
				int monsterLevel = ExtendMob.get(entity).monsterLevel;
				EntityItem[] newDrops = RotLootManager.addLoot(e.entity, monsterLevel);
				if (newDrops != null) for (EntityItem ei : newDrops)
				{
					e.drops.add(ei);
				}

				// TODO use the getEntityItem function that takes an ItemStack.
				// See EntityItem.class
				EntityItem goldDrops = new EntityItem(entity.worldObj, entity.getPosition().getX(),
						entity.getPosition().getY(), entity.getPosition().getZ(), new ItemStack(
								RotItems.gold, ExtendMob.get(entity).gold));
				e.drops.add(goldDrops);
			}
		}

	}

	@SubscribeEvent
	public void onEnderPearlTeleport(EnderTeleportEvent e)
	{
		if (e.entity instanceof EntityPlayer) e.attackDamage = 0;
	}

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			ExtendPlayer props = ExtendPlayer.get((EntityPlayer) event.entity);
			event.distance -= (props.getAthleticScore() / 10);
		}
	}

	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);

			if (player.isSprinting()) (player).motionY += (props.getAthleticScore() / 20);
		}
	}
}
