package ca.grm.rot.events.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;
import ca.grm.rot.extendprops.ExtendVillager;

public class EventEntityJoin
{
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityPlayer)
		{
			if (!e.entity.worldObj.isRemote)
			{
				ExtendPlayer props = ExtendPlayer.get((EntityPlayer) e.entity);
				props.needsUpdate = true;
			}
		}
		else if (e.entity instanceof EntityVillager)
		{
			EntityLiving villager = (EntityLiving) e.entity;
			if (!villager.worldObj.isRemote) // Only roll on server side.
			{
				if (ExtendVillager.get(villager) != null)
				{
					ExtendVillager.get(villager).rollExtendVillager();
				}
			}
		}
		else if (e.entity instanceof EntityLiving)
		{
			EntityLiving mob = (EntityLiving) e.entity;
			if (!mob.worldObj.isRemote) // Only roll on server side.
			{
				// Roll some stats
				if (ExtendMob.get(mob) != null)
				{
					if (ExtendMob.get(mob).monsterLevel == 0)
					{
						int depth = 0;
						BlockPos depthChecker = new BlockPos(mob.getPosition());
						for (int i = 0; i < 200; i++)
						{
							if (mob.worldObj.canBlockSeeSky(depthChecker)) break;
							depth++;
							depthChecker = new BlockPos(mob.getPosition().getX(), mob.getPosition()
									.getY() + i, mob.getPosition().getZ());
						}

						ExtendMob.get(mob).rollExtendMob(depth);
					}
				}
			}
		}
	}
}
