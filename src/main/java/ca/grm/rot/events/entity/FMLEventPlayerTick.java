package ca.grm.rot.events.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.libs.UtilItemStats;

public class FMLEventPlayerTick
{
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		// This event has an Entity variable, access it like this:
				// event.entity;

				// do something to player every update tick:
				
					EntityPlayer player = event.player;
					ExtendPlayer props = ExtendPlayer.get(event.player);
					if (props.needsUpdate && ((player.worldObj.getWorldTime() % 40) == 0))
					{
						Rot.proxy.updatePlayer(player);
					}
					/*
					 * if (Minecraft.getMinecraft() != null && player.worldObj.isRemote)
					 * { System.out.println(UtilityFunctions.getEntitesFromLine(player,
					 * 7f)); }
					 */
					UtilItemStats.handlePlayerStats(props, player);

					/*
					 * Thread thread = new ThreadTest(); if (!thread.isAlive())
					 * thread.start();
					 */

					// TODO attach this to Magic Hand skill passive
					/*
					 * if ((player.worldObj.getWorldTime() % 5) == 0) { int pullDistance
					 * = 7; List<EntityItem> entities =
					 * player.worldObj.getEntitiesWithinAABB(EntityItem.class, new
					 * AxisAlignedBB(player.getPosition().getX() + pullDistance, player
					 * .getPosition().getY() + pullDistance, player.getPosition().getZ()
					 * + pullDistance, player.getPosition() .getX() - pullDistance,
					 * player.getPosition().getY() - pullDistance, player.getPosition()
					 * .getZ() - pullDistance)); if (!entities.isEmpty()) { for
					 * (EntityItem ei : entities) { //
					 * MathHelper.clamp_float(p_76131_0_, -1f, 1); // ei.motionX = //
					 * (MathHelper.clamp_float(player.getPosition().getX() - //
					 * ei.getPosition().getX(), -0.1f, 0.1f)); // ei.motionY = //
					 * (MathHelper.clamp_float(player.getPosition().getY() - //
					 * ei.getPosition().getY(), -0.3f, 0.3f)); // ei.motionZ = //
					 * (MathHelper.clamp_float(player.getPosition().getZ() - //
					 * ei.getPosition().getZ(), -0.1f, 0.1f)); ei.motionX =
					 * (player.getPosition().getX() - ei.getPosition().getX() < 0 ?
					 * -0.05f : 0.05f); ei.motionY = (player.getPosition().getY() -
					 * ei.getPosition().getY() < 0 ? -0.3f : 0.3f); ei.motionZ =
					 * (player.getPosition().getZ() - ei.getPosition().getZ() < 0 ?
					 * -0.05f : 0.05f); } } }
					 */

					// Mana and Stamina regeneration
					float timeMath = 3 * 60 * 10;
					if (player.shouldHeal() && ((player.worldObj.getWorldTime() % 30) == 0))
					{
						if (!player.isPotionActive(Potion.hunger))
						{
							player.heal((((5 * props.pickedClass.hpPerVit) + props.getHealthRegen()) / timeMath));
						}
					}
					int notExhaustedFoodLevel = 8;
					int exhaustedFoodLevel = 3;

					// Eating will heal the player
					int foodLevel = player.getFoodStats().getFoodLevel();
					if (props.isExhausted)
					{
						if ((props.getCurrentStam() / props.getMaxStam()) > 0.15f)
						{
							props.isExhausted = false;
							player.getFoodStats().setFoodLevel(notExhaustedFoodLevel);
						}
						if (foodLevel != exhaustedFoodLevel)
						{
							exhaustedFoodLevel = 3;
							if (foodLevel > exhaustedFoodLevel)
							{
								if (!player.isPotionActive(Potion.hunger))
								{
									player.heal((foodLevel - exhaustedFoodLevel) / 4f);
								}
							}
							player.getFoodStats().setFoodLevel(exhaustedFoodLevel);
							player.getFoodStats().setFoodSaturationLevel(100f);
						}
					}
					else
					{
						if (foodLevel != notExhaustedFoodLevel)
						{
							if (foodLevel > notExhaustedFoodLevel)
							{
								if (!player.isPotionActive(Potion.hunger))
								{
									player.heal((foodLevel - notExhaustedFoodLevel) / 4f);
								}
							}

							player.getFoodStats().setFoodLevel(notExhaustedFoodLevel);
							player.getFoodStats().setFoodSaturationLevel(100f);
						}
					}

					if (player.isPlayerSleeping())
					{
						if (!player.isPotionActive(Potion.hunger))
						{
							player.heal(1f);
							props.replenishMana();
							props.replenishStam();
						}
					}
					props.regenMana((5f + (props.getManaRegen())) / timeMath);
					if (!player.isSprinting())
					{
						if (!player.isPotionActive(Potion.hunger))
						{
							props.regenStam(((30f + (props.getStamRegen())) / timeMath) + (((player.experienceLevel) * 4) / timeMath));
						}
					}
					else
					{
						if (!props.consumeStam(0.157f))
						{
							props.isExhausted = true;
							player.getFoodStats().setFoodLevel(exhaustedFoodLevel);
						}
					}
				
	}
}
