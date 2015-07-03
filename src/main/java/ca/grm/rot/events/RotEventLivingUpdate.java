package ca.grm.rot.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.MobRequestPacket;
import ca.grm.rot.comms.VillagerRequestPacket;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.extendprops.ExtendVillager;
import ca.grm.rot.libs.UtilNBTHelper;
import ca.grm.rot.libs.UtilNBTKeys;

public class RotEventLivingUpdate
{
	// TODO use a livingJumpEvent to alter jumpheight? also like the fall event,
	// needs Athletic Score

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		// This event has an Entity variable, access it like this:
		// event.entity;

		// do something to player every update tick:
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			if (props.needsUpdate && ((player.worldObj.getWorldTime() % 40) == 0))
			{
				Rot.proxy.updatePlayer(player);
			}
			/*
			 * if (Minecraft.getMinecraft() != null && player.worldObj.isRemote)
			 * { System.out.println(UtilityFunctions.getEntitesFromLine(player,
			 * 7f)); }
			 */
			handlePlayerStats(props, player);

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

		// Start of other Entities Updates events
		else if (event.entity instanceof EntityIronGolem)
		{
			EntityIronGolem golem = (EntityIronGolem) event.entity;
			if (!golem.isPotionActive(Potion.hunger))
			{
				golem.heal(0.05f);
			}
			if (!golem.worldObj.isRemote)
			{
				float r = golem.worldObj.rand.nextFloat();
				if ((r >= 0.5554f) && (r <= 0.5556f))
				{
					ItemStack[] itemStacks = new ItemStack[3];
					itemStacks[0] = new ItemStack(Items.pumpkin_seeds);
					itemStacks[1] = new ItemStack(Items.clay_ball);
					itemStacks[2] = new ItemStack(Items.flint);
					EntityItem i = new EntityItem(golem.worldObj, golem.posX, golem.posY,
							golem.posZ, itemStacks[golem.worldObj.rand.nextInt(3)]);
					golem.worldObj.spawnEntityInWorld(i);
				}
			}
		}
		else if (event.entity instanceof EntityVillager)
		{
			EntityVillager villager = (EntityVillager) event.entity;
			EntityLiving e = (EntityLiving) event.entity;
			ExtendVillager ev = ExtendVillager.get(e);

			if (ev != null)
			{
				if (e.worldObj.isRemote && ev.needsUpdate && ((e.worldObj.getWorldTime() % 40) == 0))
				{
					Rot.net.sendToServer(new VillagerRequestPacket(e.getEntityId()));
				}
			}
			if (!villager.isPotionActive(Potion.hunger))
			{
				villager.heal(0.075f);
			}
			if (!villager.worldObj.isRemote)
			{
				float r = villager.worldObj.rand.nextFloat();
				if ((r >= 0.5554f) && (r <= 0.5556f))
				{
					int itemNumber = 0;
					ItemStack[] itemStacks = new ItemStack[1];

					switch (villager.getProfession())
					{
					case 0:// farmer
						itemStacks = new ItemStack[3];
						itemStacks[0] = new ItemStack(Items.emerald);
						itemStacks[1] = new ItemStack(Items.wheat);
						itemStacks[2] = new ItemStack(Items.cookie);
						break;
					case 1: // lab
						itemStacks = new ItemStack[2];
						itemStacks[0] = new ItemStack(Items.emerald);
						itemStacks[1] = new ItemStack(Items.paper);
						break;
					case 2:// purple
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
					case 4: // butcher
						itemStacks = new ItemStack[3];
						itemStacks[0] = new ItemStack(Items.emerald);
						itemStacks[1] = new ItemStack(Items.beef);
						itemStacks[2] = new ItemStack(Items.porkchop);
						break;
					default:
						itemStacks[0] = new ItemStack(Items.emerald);
						break;

					/*
					 * emerald for all 0 farmer brown (wheat, cookie) 1 White
					 * Lab Coat (paper) 2 purple coat (redstone dust) 3
					 * blacksmith (coal, iron ingot, gold nugget) 4 Butcher (raw
					 * beef, raw porkchop)
					 */
					}
					if (itemStacks.length == 1)
					{
						itemNumber = 0;
					}
					else
					{
						itemNumber = villager.worldObj.rand.nextInt(itemStacks.length);
					}
					EntityItem i = new EntityItem(villager.worldObj, villager.posX, villager.posY,
							villager.posZ, itemStacks[itemNumber]);
					villager.worldObj.spawnEntityInWorld(i);
				}
			}
		}
		else if (event.entity instanceof EntityWolf)
		{
			EntityWolf wolf = (EntityWolf) event.entity;
			if (!wolf.isPotionActive(Potion.hunger))
			{
				wolf.heal(0.05f);
			}
		}
		else if (event.entity instanceof EntityHorse)
		{
			EntityHorse horse = (EntityHorse) event.entity;
			if (!horse.isPotionActive(Potion.hunger))
			{
				horse.heal(0.05f);
			}
		}
		else
		{
			if (event.entity instanceof EntityArmorStand) // ArmorStands don't
															// do anything.
			{}
			else
			// Everything that isn't an ArmorStand, Horse, Wolf, Villager or
			// IronGolem and of course not a player.
			{
				EntityLiving e = (EntityLiving) event.entity;
				ExtendMob em = ExtendMob.get(e);

				if (em != null)
				{
					if (e.worldObj.isRemote && em.needsUpdate && ((e.worldObj.getWorldTime() % 40) == 0))
					{
						Rot.net.sendToServer(new MobRequestPacket(e.getEntityId()));
					}
				}
				if (e.hurtResistantTime != 5)
				{
					e.hurtResistantTime = 5;
					e.arrowHitTimer = 1;
				}
				if (!e.isPotionActive(Potion.hunger))
				{
					if (em != null)
					{
						e.heal(0.0025f + em.getHpRegenBonusPercent());
					}
					else
					{
						e.heal(0.0025f);
					}

				}
				if (em != null && !e.worldObj.isRemote)
				{
					if (em.suffix == "the Cold")
					{
						// Deal with placing snow
						int i = MathHelper.floor_double(e.posX);
						int j = MathHelper.floor_double(e.posY);
						int k = MathHelper.floor_double(e.posZ);

						for (int l = 0; l < 4; ++l)
						{
							i = MathHelper
									.floor_double(e.posX + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
							j = MathHelper.floor_double(e.posY);
							k = MathHelper
									.floor_double(e.posZ + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
							if (e.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock()
									.getMaterial() == Material.air && Blocks.snow_layer
									.canPlaceBlockAt(e.worldObj, new BlockPos(i, j, k)))
							{
								e.worldObj.setBlockState(new BlockPos(i, j, k), Blocks.snow_layer
										.getDefaultState());
							}
						}

						// Extinguish if it's on fire.
						if (e.isBurning())
						{
							e.extinguish();
						}
					}
				}
				else if (em != null && e.worldObj.isRemote)
				{
					// Render Particles
					if (em.suffix == "the Heated")
					{
						renderParticle(e, EnumParticleTypes.FLAME);
					}
					else if (em.suffix == "Full of Life")
					{
						renderParticle(e, EnumParticleTypes.HEART);
					}
					else if (em.suffix == "the Cursed")
					{
						renderParticle(e, EnumParticleTypes.SPELL_WITCH);
					}
				}
			}
		}
	}

	public void renderParticle(EntityLiving e, EnumParticleTypes ep)
	{
		if (e.worldObj.rand.nextInt(25) == 0)
		{
			int i = MathHelper.floor_double(e.posX);
			int j = MathHelper.floor_double(e.posY - 0.20000000298023224D);
			int k = MathHelper.floor_double(e.posZ);
			IBlockState iblockstate = e.worldObj.getBlockState(new BlockPos(i, j, k));
			Block block = iblockstate.getBlock();

			if (block.getMaterial() != Material.air)
			{
				double xCoord = (e.posX + ((double) e.worldObj.rand.nextFloat() - 0.5D) * ((double) e.width + 0.5D));
				double yCoord = e.getEntityBoundingBox().minY + ((double) e.worldObj.rand
						.nextFloat());
				double zCoord = (e.posZ + ((double) e.worldObj.rand.nextFloat() - 0.5D) * ((double) e.width + 0.5D));
				double xOffset = ((double) e.worldObj.rand.nextFloat() - 0.5D) * 0.1D;
				double yOffset = 0.1D;
				double zOffset = ((double) e.worldObj.rand.nextFloat() - 0.5D) * 0.1D;

				e.worldObj.spawnParticle(ep, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset,
						new int[] { Block.getStateId(iblockstate) });
			}
		}
	}

	/** Collect NBT tags that are common from held and worn items **/
	private void getBasicStats(ItemStack is, int[] listCollect, String[] ListSearch)
	{
		for (int i = 0; i < listCollect.length; i++)
		{
			listCollect[i] += UtilNBTHelper.getInt(is, ListSearch[i]);
		}
	}

	private void getBonusStats(ItemStack is, float[] listCollect, String[] ListSearch)
	{
		for (int i = 0; i < listCollect.length; i++)
		{
			listCollect[i] += UtilNBTHelper.getFloat(is, ListSearch[i]);
		}
	}

	private void selfRepair(ItemStack item)
	{
		if (UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairing) > 1f)
		{
			if (UtilNBTHelper.getFloat(item, UtilNBTKeys.selfRepairTime) == 0)
			{
				item.setItemDamage(item.getItemDamage() - 1);
				UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, UtilNBTHelper.getFloat(
						item, UtilNBTKeys.selfRepairing) * 20);
			}
			else
			{
				UtilNBTHelper.setFloat(item, UtilNBTKeys.selfRepairTime, UtilNBTHelper.getFloat(
						item, UtilNBTKeys.selfRepairTime) - 1);
			}
		}
	}

	private void handlePlayerStats(ExtendPlayer props, EntityPlayer player)
	{
		// Stat handling
		// int strMod = 0, dexMod = 0, vitMod = 0, agiMod = 0, intMod = 0,
		// minDmg = 0, maxDmg = 0, defBonus = 0;
		int[] stats = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] statsS = new String[] { UtilNBTKeys.strStat, UtilNBTKeys.dexStat, UtilNBTKeys.vitStat, UtilNBTKeys.agiStat, UtilNBTKeys.intStat, UtilNBTKeys.minDmgStat, UtilNBTKeys.maxDmgStat, UtilNBTKeys.defStat };
		float[] bonusStats = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		String[] bonusStatsS = new String[] { UtilNBTKeys.lifeSteal, UtilNBTKeys.manaSteal, UtilNBTKeys.manaStat, UtilNBTKeys.stamStat, UtilNBTKeys.lifeStat, UtilNBTKeys.manaRegenStat, UtilNBTKeys.stamRegenStat, UtilNBTKeys.lifeRegenStat };
		// float lifeSteal = 0, manaSteal = 0;
		// float bonusMana = 0, bonusStam = 0, bonusHealth = 0, bonusManaRegen =
		// 0, bonusStamRegen = 0, bonusHealthRegen = 0;
		ItemStack held = player.getEquipmentInSlot(0), armor1 = player.getEquipmentInSlot(1), armor2 = player
				.getEquipmentInSlot(2), armor3 = player.getEquipmentInSlot(3), armor4 = player
				.getEquipmentInSlot(4);

		if (held != null)
		{
			if ((held.getItem() instanceof ItemSword) || (held.getItem() instanceof ItemTool) || (held
					.getItem() instanceof ItemBow))
			{
				getBasicStats(held, stats, statsS);
				getBonusStats(held, bonusStats, bonusStatsS);
				selfRepair(held);
			}
		}
		if (armor1 != null)
		{
			getBasicStats(armor1, stats, statsS);
			getBonusStats(armor1, bonusStats, bonusStatsS);
			selfRepair(armor1);
		}
		if (armor2 != null)
		{
			getBasicStats(armor2, stats, statsS);
			getBonusStats(armor2, bonusStats, bonusStatsS);
			selfRepair(armor2);
		}
		if (armor3 != null)
		{
			getBasicStats(armor3, stats, statsS);
			getBonusStats(armor3, bonusStats, bonusStatsS);
			selfRepair(armor3);
		}
		if (armor4 != null)
		{
			getBasicStats(armor4, stats, statsS);
			getBonusStats(armor4, bonusStats, bonusStatsS);
			selfRepair(armor4);
		}

		if (props.getStrength() != (stats[0] - props.getClassModifers()[0]))
		{
			props.setStrength(stats[0]);
		}
		if (props.getDexterity() != (stats[1] - props.getClassModifers()[4]))
		{
			props.setDexterity(stats[1]);
		}
		if (props.getVitality() != (stats[2] - props.getClassModifers()[3]))
		{
			props.setVitality(stats[2]);
		}
		if (props.getAgility() != (stats[3] - props.getClassModifers()[1]))
		{
			props.setAgility(stats[3]);
			ExtendPlayer.get(player).updateAthleticScore();
		}
		if (props.getIntelligence() != (stats[4] - props.getClassModifers()[2]))
		{
			props.setIntelligence(stats[4]);
		}

		if (props.getMinDmg() != stats[5])
		{
			props.setMinDmg(stats[5]);
		}
		if (props.getMaxDmg() != stats[6])
		{
			props.setMaxDmg(stats[6]);
		}
		if (props.getDefBonus() != stats[7])
		{
			props.setDefBonus(stats[7]);
		}
		if (props.getLifeSteal() != bonusStats[0])
		{
			props.setLifeSteal(bonusStats[0]);
		}
		if (props.getManaSteal() != bonusStats[1])
		{
			props.setManaSteal(bonusStats[1]);
		}

		if (props.getBonusMana() != bonusStats[2])
		{
			props.setBonusMana(bonusStats[2]);
		}
		if (props.getBonusStam() != bonusStats[3])
		{
			props.setBonusStam(bonusStats[3]);
		}
		if (props.getBonusHealth() != bonusStats[4])
		{
			props.setBonusHealth(bonusStats[4]);
		}

		if (props.getManaRegen() != bonusStats[5])
		{
			props.setManaRegen(bonusStats[5]);
		}
		if (props.getStamRegen() != bonusStats[6])
		{
			props.setStamRegen(bonusStats[6]);
		}
		if (props.getHealthRegen() != bonusStats[7])
		{
			props.setHealthRegen(bonusStats[7]);
		}
	}
}
