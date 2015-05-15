package ca.grm.rot.events;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.RotClassManager;

public class RotEventTeamsClasses
{
	@SubscribeEvent
	public void EntityBlockBreakSpeed(BreakSpeed e)
	{
		if (ExtendPlayer.get(e.entityPlayer).pickedProfession.professionName == RotClassManager.professionMiner)
		{
			e.newSpeed += (MathHelper.clamp_float(
					(ExtendPlayer.get(e.entityPlayer).getStrength() / 1.5f),
					0f, 30f));
		}
		else
		{
			e.newSpeed += (MathHelper.clamp_float(
					(ExtendPlayer.get(e.entityPlayer).getStrength() / 3),
					-0.2f, 9f));
		}
	}

	@SubscribeEvent
	public void BlockBreak(BlockEvent.HarvestDropsEvent e)
	{
		/*
		 * System.out.println(e.state); System.out.println(e.state.getBlock());
		 * for (ItemStack is : e.drops) { System.out.println(is.getItem());
		 * System.out.println(is.getDisplayName()); }
		 * System.out.println(e.dropChance + " drop chance");
		 * System.out.println(e.harvester + " who broke it");
		 */
	}

}
