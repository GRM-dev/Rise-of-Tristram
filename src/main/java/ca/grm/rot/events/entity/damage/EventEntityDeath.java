package ca.grm.rot.events.entity.damage;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.items.RotItems;
import ca.grm.rot.managers.RotLootManager;

public class EventEntityDeath {
	@SubscribeEvent
	public void onEntityDeath(LivingDropsEvent e) {

		if (e.entity instanceof EntityPlayer) // The/a player died.
		{
			EntityPlayer player = (EntityPlayer) e.entity;
			ExtendPlayer dplayer = ExtendPlayer.get(player);
			if (dplayer.getGold() > 0) 
			{
				EntityItem goldDrops = new EntityItem(player.worldObj, player.getPosition().getX(), player.getPosition().getY(),player.getPosition().getZ(), new ItemStack(RotItems.gold,
								Math.round(dplayer.getGold() * 0.1f)));
				dplayer.subtractGold(Math.round(dplayer.getGold() * 0.1f));
				e.drops.add(goldDrops);
			}
		}

		// TODO This needs improvement, also needs better math
		if (e.entity instanceof EntityLiving) {
			EntityLiving entity = (EntityLiving) e.entity;

			if (ExtendMob.get(entity) != null
					&& e.source.getEntity() instanceof EntityPlayer) // If the mob's killed by a player
			{
				int monsterLevel = ExtendMob.get(entity).monsterRank;
				int lootRange = ExtendMob.get(entity).lootRange;
				EntityItem[] newDrops = RotLootManager.addLoot(lootRange,
						monsterLevel);
				if (newDrops != null)
					for (EntityItem ei : newDrops) {
						e.drops.add(ei);
					}

				// TODO use the getEntityItem function that takes an ItemStack.
				// See EntityItem.class
				if (!(e.entity instanceof EntityPlayer)) {
					EntityItem goldDrops = new EntityItem(entity.worldObj,
							entity.getPosition().getX(), entity.getPosition()
									.getY(), entity.getPosition().getZ(),
							new ItemStack(RotItems.gold,
									ExtendMob.get(entity).gold));
					e.drops.add(goldDrops);
				}
			}

		}

	}
}
