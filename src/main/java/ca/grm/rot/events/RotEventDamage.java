package ca.grm.rot.events;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.libs.ExtendMob;
import ca.grm.rot.libs.ExtendPlayer;

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
					float finalMinDmg = 10 + tempDmg + props.getMinDmg() * (props.getStrength() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg() * (props.getStrength() + 100) / 100;
					int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
					if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
					else event.ammount -= newDmg;
				}
				else if (event.source.getDamageType() == "arrow")
				{
					float finalMinDmg = 10 + tempDmg + props.getMinDmg() * (props.getDexterity() + 100) / 100;
					float finalMaxDmg = 20 + tempDmg + props.getMaxDmg() * (props.getDexterity() + 100) / 100;
					int newDmg = ((int) finalMaxDmg - (int) finalMinDmg) + (int) finalMinDmg;
					if (newDmg > 0) event.ammount += (rand.nextInt(newDmg));
					else event.ammount -= newDmg;
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
			if (event.source instanceof EntityDamageSource) event.ammount -= props.getDefBonus();
			float adjustedMaxHP = (player.getMaxHealth() * upscalePercent) + props.pickedClass.baseHp + (props
					.getVitality() * props.pickedClass.hpPerVit);
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
				float adjustedMaxHP = (e.getMaxHealth() * upscalePercent) + (props.vitality * (props.monsterLevel * 2.5f));
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

	@SubscribeEvent
	public void onEntityDeath(LivingDropsEvent e)
	{
		// TODO make drops based on monsterLevel
		if (e.entity instanceof EntityLiving)
		{
			EntityLiving entity = (EntityLiving) e.entity;
			if (ExtendMob.get(entity) != null && e.source.getEntity() instanceof EntityPlayer)
			{
				ItemStack[] lootList1 = new ItemStack[] { new ItemStack(Items.wooden_sword), new ItemStack(
						Items.stone_sword), new ItemStack(Items.wooden_pickaxe), new ItemStack(
						Items.wooden_axe), new ItemStack(Items.wooden_shovel), new ItemStack(
						Items.stone_pickaxe), new ItemStack(Items.stone_axe), new ItemStack(
						Items.stone_shovel), new ItemStack(Items.coal, (entity.worldObj.rand
						.nextInt(2) + 1)), new ItemStack(Items.stick, (entity.worldObj.rand
						.nextInt(5) + 1)), new ItemStack(Items.flint, (entity.worldObj.rand
						.nextInt(1) + 1)), new ItemStack(Items.clay_ball, (entity.worldObj.rand
						.nextInt(5) + 1)), new ItemStack(Items.leather_boots), new ItemStack(
						Items.leather_chestplate), new ItemStack(Items.leather_helmet), new ItemStack(
						Items.leather_leggings) };

				ItemStack[] lootList2 = new ItemStack[] { new ItemStack(Items.iron_sword), new ItemStack(
						Items.iron_shovel), new ItemStack(Items.iron_axe), new ItemStack(
						Items.iron_pickaxe), new ItemStack(Items.iron_ingot, (entity.worldObj.rand
						.nextInt(1) + 1)), new ItemStack(Items.saddle), new ItemStack(
						Items.iron_horse_armor), new ItemStack(Items.iron_helmet), new ItemStack(
						Items.iron_chestplate), new ItemStack(Items.iron_leggings), new ItemStack(
						Items.iron_boots), new ItemStack(Items.redstone, (entity.worldObj.rand
						.nextInt(2) + 1)), new ItemStack(Items.chainmail_helmet), new ItemStack(
						Items.chainmail_chestplate), new ItemStack(Items.chainmail_leggings), new ItemStack(
						Items.chainmail_boots) };

				ItemStack[] lootList3 = new ItemStack[] { new ItemStack(Items.golden_sword), new ItemStack(
						Items.golden_axe), new ItemStack(Items.golden_shovel), new ItemStack(
						Items.golden_pickaxe), new ItemStack(Items.diamond_sword), new ItemStack(
						Items.diamond_axe), new ItemStack(Items.diamond_shovel), new ItemStack(
						Items.diamond_pickaxe), new ItemStack(Items.golden_helmet), new ItemStack(
						Items.golden_chestplate), new ItemStack(Items.golden_leggings), new ItemStack(
						Items.golden_boots), new ItemStack(Items.diamond_helmet), new ItemStack(
						Items.diamond_chestplate), new ItemStack(Items.diamond_leggings), new ItemStack(
						Items.diamond_boots), new ItemStack(Items.glowstone_dust,
						(entity.worldObj.rand.nextInt(2) + 1)), new ItemStack(Items.emerald,
						(entity.worldObj.rand.nextInt(2) + 1)) };

				int monsterLevel = ExtendMob.get(entity).monsterLevel;
				float leftChancePointer = (0.3f - (0.05f * (monsterLevel - 1)));
				float rightChancePointer = (0.7f + (0.05f * (monsterLevel - 1)));
				float rngRoll = 0;
				switch (monsterLevel)
				{
				case 1:
				case 2:
					for (int i = 0; i < 50; i++)
					{
						rngRoll = entity.worldObj.rand.nextFloat();
						if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
						{
							leftChancePointer += (0.1f - (0.01f * (monsterLevel - 1)));
							rightChancePointer -= (0.1f - (0.01f * (monsterLevel - 1)));
							ItemStack item = lootList1[entity.worldObj.rand
														.nextInt(lootList1.length - 1)];
							if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemTool || item.getItem() instanceof ItemArmor)
								RotEventItems.applyItemStats(item, entity.worldObj.rand, monsterLevel);
							e.drops.add(new EntityItem(entity.worldObj,
									entity.getPosition().getX(), entity.getPosition().getY(),
									entity.getPosition().getZ(), item));
						}
						else break;
					}
					break;
				case 3:
				case 4:
					for (int i = 0; i < 50; i++)
					{
						rngRoll = entity.worldObj.rand.nextFloat();
						if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
						{
							leftChancePointer += (0.1f - (0.01f * (monsterLevel - 1)));
							rightChancePointer -= (0.1f - (0.01f * (monsterLevel - 1)));
							ItemStack item = lootList2[entity.worldObj.rand
														.nextInt(lootList2.length - 1)];
							if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemTool || item.getItem() instanceof ItemArmor)
								RotEventItems.applyItemStats(item, entity.worldObj.rand, monsterLevel);
							e.drops.add(new EntityItem(entity.worldObj,
									entity.getPosition().getX(), entity.getPosition().getY(),
									entity.getPosition().getZ(), item));
						}
						else break;
					}
					break;
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					for (int i = 0; i < 50; i++)
					{
						rngRoll = entity.worldObj.rand.nextFloat();
						if (rngRoll >= leftChancePointer && rngRoll <= rightChancePointer)
						{
							leftChancePointer += (0.1f - (0.001f * (monsterLevel - 1)));
							rightChancePointer -= (0.1f - (0.001f * (monsterLevel - 1)));
							ItemStack item = lootList3[entity.worldObj.rand
														.nextInt(lootList3.length - 1)];
							if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemTool || item.getItem() instanceof ItemArmor)
								RotEventItems.applyItemStats(item, entity.worldObj.rand, monsterLevel);
							e.drops.add(new EntityItem(entity.worldObj,
									entity.getPosition().getX(), entity.getPosition().getY(),
									entity.getPosition().getZ(), item));
						}
						else break;
					}
					break;
				}

			}
		}

	}

	@SubscribeEvent
	public void onEntityDeath(EnderTeleportEvent e)
	{
		if (e.entity instanceof EntityPlayer) e.attackDamage = 0;
	}

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event)
	{
		// TODO Create an Athletic Score in ExtendPlayer
		// Remember that so far we have only added ExtendedPlayer properties
		// so check if it's the right kind of entity first
		if (event.entity instanceof EntityPlayer)
		{
			ExtendPlayer props = ExtendPlayer.get((EntityPlayer) event.entity);

			// This 'if' statement just saves a little processing time and
			// makes it so we only deplete mana from a fall that would injure
			// the
			// player
			if (event.distance > 3.0F && props.getCurrentMana() > 0)
			{
				float reduceby = props.getCurrentStam() < (event.distance - 3.0F) ? props
						.getCurrentStam() : (event.distance - 3.0F);
				event.distance -= reduceby;

				// Cast reduceby to 'int' to match our method parameter
				props.consumeStam(reduceby);

				// System.out.println("[EVENT] Adjusted fall distance: " +
				// event.distance);
			}
		}
	}
}
