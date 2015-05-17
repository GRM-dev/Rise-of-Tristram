package ca.grm.rot.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
			Material blockMat = e.state.getBlock().getMaterial();
			if (blockMat == Material.rock || blockMat == Material.clay || blockMat == Material.ground) e.newSpeed += (MathHelper
					.clamp_float((ExtendPlayer.get(e.entityPlayer).getStrength() / 1.5f), 0f, 30f));
		}
		else if (ExtendPlayer.get(e.entityPlayer).pickedProfession.professionName == RotClassManager.professionFarmer)
		{
			Material blockMat = e.state.getBlock().getMaterial();
			if (blockMat == Material.grass || blockMat == Material.wood || blockMat == Material.plants || blockMat == Material.leaves) e.newSpeed += (MathHelper
					.clamp_float((ExtendPlayer.get(e.entityPlayer).getStrength() / 1.5f), 0f, 30f));
		}
		else
		{
			e.newSpeed += (MathHelper.clamp_float(
					(ExtendPlayer.get(e.entityPlayer).getStrength() / 3), -0.2f, 9f));
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
		if (e.harvester instanceof EntityPlayer)
		{
			Block b = e.state.getBlock();
			if (b == Blocks.coal_ore || b == Blocks.redstone_ore || b == Blocks.diamond_ore) if (ExtendPlayer
					.get(e.harvester).pickedProfession.professionName == RotClassManager.professionMiner)
			{
				for (ItemStack is : e.drops)
				{
					is.stackSize += (e.world.rand.nextInt(4) + 1);
				}
				e.drops.add(new ItemStack(Items.diamond));
			}
		}
	}

}
