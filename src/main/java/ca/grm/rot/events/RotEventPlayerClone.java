package ca.grm.rot.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.ClassRequestPacket;
import ca.grm.rot.comms.GoldRequestPacket;
import ca.grm.rot.comms.ProfessionRequestPacket;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;

public class RotEventPlayerClone
{
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if (event.wasDeath)
		{
			if (!event.entityPlayer.worldObj.isRemote)
			{
				ExtendPlayer old = ExtendPlayer.get(event.original);
				ExtendPlayer properties = ExtendPlayer.get(event.entityPlayer);
				properties.setValues(old.getValues());
				Rot.net.sendToServer(new GoldRequestPacket());
				Rot.net.sendToServer(new ClassRequestPacket(0));
				Rot.net.sendToServer(new ProfessionRequestPacket(0));
			}
		}
	}

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

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{

		if ((event.entity instanceof EntityPlayer) && (ExtendPlayer
				.get((EntityPlayer) event.entity) == null))
		{
			ExtendPlayer.register((EntityPlayer) event.entity);
		}
		if ((event.entity instanceof EntityZombie) && (ExtendMob.get((EntityZombie) event.entity) == null) || (event.entity instanceof EntitySkeleton) && (ExtendMob
				.get((EntitySkeleton) event.entity) == null) || (event.entity instanceof EntityEnderman) && (ExtendMob
				.get((EntityEnderman) event.entity) == null) || (event.entity instanceof EntitySpider) && (ExtendMob
				.get((EntitySpider) event.entity) == null) || (event.entity instanceof EntityCaveSpider) && (ExtendMob
				.get((EntityCaveSpider) event.entity) == null))
		{
			ExtendMob.register((EntityLiving) event.entity);
		}

	}
}
