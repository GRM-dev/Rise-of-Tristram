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

public class EventEntityDeath
{
	@SubscribeEvent
	public void onEntityDeath(LivingDropsEvent e)
	{
		// TODO This needs improvement, also needs better math
		if (e.entity instanceof EntityLiving)
		{
			EntityLiving entity = (EntityLiving) e.entity;
			if (ExtendMob.get(entity) != null && e.source.getEntity() instanceof EntityPlayer) // If the mob's killed by a player
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
			
			if (e.entity instanceof EntityPlayer) // The/a player died.
			{
				ExtendPlayer dplayer = ExtendPlayer.get((EntityPlayer) e.entity);
				if (dplayer.getGold() > 0)
				{
					dplayer.subtractGold((int)(dplayer.getGold() * 0.1)); // Take away 10% of the player's gold. Idc that I'm not rounding and I'm casting. Don't hate.
					EntityItem goldDrops = new EntityItem(entity.worldObj, entity.getPosition().getX(),
							entity.getPosition().getY(), entity.getPosition().getZ(), new ItemStack(
									RotItems.gold, (int)(dplayer.getGold() * 0.1)));
					e.drops.add(goldDrops);
				}
			}
		}

	}
}
