package ca.grm.rot.events.entity;

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

public class EventEntityLivingUpdate
{
	// TODO use a livingJumpEvent to alter jumpheight? also like the fall event,
	// needs Athletic Score

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		// This event has an Entity variable, access it like this:
		// event.entity;
		if (event.entity instanceof EntityPlayer)
		{

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
}
