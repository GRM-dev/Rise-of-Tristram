package ca.grm.rot.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
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
import ca.grm.rot.managers.RotClassManager;

public class RotEventTeamsClasses
{
	@SubscribeEvent
	public void EntityBlockBreakSpeed(BreakSpeed e)
	{
		if (ExtendPlayer.get(e.entityPlayer).pickedProfession.professionName == RotClassManager.professionMiner)
		{
			Material blockMat = e.state.getBlock().getMaterial();
			if (blockMat == Material.rock || blockMat == Material.clay || blockMat == Material.sand) e.newSpeed += 3f;
		}
		else if (ExtendPlayer.get(e.entityPlayer).pickedProfession.professionName == RotClassManager.professionFarmer)
		{
			Material blockMat = e.state.getBlock().getMaterial();
			if (blockMat == Material.grass || blockMat == Material.wood || blockMat == Material.plants || blockMat == Material.ground || blockMat == Material.leaves) e.newSpeed += 3f;
		}
		e.newSpeed += (MathHelper.clamp_float((ExtendPlayer.get(e.entityPlayer).getStrength() / 3),
				-0.2f, 3f));

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
			if (ExtendPlayer.get(e.harvester).pickedProfession.professionName == RotClassManager.professionMiner)
			{
				if (!e.isSilkTouching)
				{
					if (b == Blocks.coal_ore || b == Blocks.redstone_ore || b == Blocks.diamond_ore || b == Blocks.emerald_ore)
					{
						for (ItemStack is : e.drops)
						{
							is.stackSize += (e.world.rand.nextInt(4) + 1);
						}
					}
				}
			}
			else if (ExtendPlayer.get(e.harvester).pickedProfession.professionName == RotClassManager.professionFarmer)
			{
				if (b == Blocks.wheat || b == Blocks.carrots || b == Blocks.potatoes || b == Blocks.leaves || b == Blocks.leaves2 || b == Blocks.grass || b == Blocks.nether_wart)
				{
					if (b instanceof BlockCrops)
					{
						if (((Integer) e.state.getValue(BlockCrops.AGE)).intValue() >= 7)
						{
							for (ItemStack is : e.drops)
							{
								is.stackSize += (e.world.rand.nextInt(4) + 1);
							}
						}
					}
				}
				// /End of Farmer stuff
			}
			// End of profession checks
		}
	}

}
