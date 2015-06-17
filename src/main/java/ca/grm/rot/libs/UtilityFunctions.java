package ca.grm.rot.libs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class UtilityFunctions
{

	/* THIS NEEDS TO BE MOVED */
	// TODO move these somewhere else
	public static Block[] blockTypeObjects = { Blocks.air, Blocks.planks, Blocks.cobblestone, Blocks.stone, Blocks.stonebrick, Blocks.glass, Blocks.glass_pane, Blocks.brick_block, Blocks.sandstone };
	public static int[] blockTypeColors = { 0xFF00CCFF, 0xFFFFBB00, 0xFFAAAAAA, 0xFFBBBBBB, 0xFFBBBBBB, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFAA0000, 0xFFFFBB00 };

	public static Entity getEntitesFromLine(Entity entity, double range)
	{
		//Entity entity = this.mc.getRenderViewEntity();
		Minecraft mc = Minecraft.getMinecraft();

        if (entity != null)
        {
            if (mc.theWorld != null)
            {
            	mc.mcProfiler.startSection("pick");
            	mc.pointedEntity = null;
                mc.objectMouseOver = entity.rayTrace(range, 0f);//partial tick time?
                double d1 = range;
                Vec3 vec3 = entity.getPositionEyes(0f);//partial tick time?

                if (mc.objectMouseOver != null)
                {
                    d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = entity.getLook(0f);//partial tick time?
                Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
                Entity pointedEntity = null;
                Vec3 vec33 = null;
                float f1 = 1.0F;
                List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand((double)f1, (double)f1, (double)f1));
                double d2 = d1;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity1 = (Entity)list.get(i);

                    if (entity1.canBeCollidedWith())
                    {
                        float f2 = entity1.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3))
                        {
                            if (0.0D < d2 || d2 == 0.0D)
                            {
                                pointedEntity = entity1;
                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null)
                        {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D)
                            {
                                if (entity1 == entity.ridingEntity && !entity.canRiderInteract())
                                {
                                    if (d2 == 0.0D)
                                    {
                                        pointedEntity = entity1;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                }
                                else
                                {
                                    pointedEntity = entity1;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null))
                {
                    mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        mc.pointedEntity = pointedEntity;
                    }
                }
                mc.mcProfiler.endSection();
                return pointedEntity;
            }
        }
		return null;
	}
	
	/**
	 * Will Scan for an item, and check to see if the correct amount is in a
	 * player's inventory
	 **/
	public static boolean checkForItemAndAmount(Item item, int amount, InventoryPlayer invPlayer)
	{
		boolean correctAmount = false;
		int amountCounter = 0;

		// Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory(); slot++)
		{
			if (invPlayer.getStackInSlot(slot) != null)
			{
				if (invPlayer.getStackInSlot(slot).getItem().equals(item) && !correctAmount)
				{
					// If the found stack matches the item and don't have the
					// correct amount
					amountCounter += invPlayer.getStackInSlot(slot).stackSize;
					if (amountCounter >= amount)
					{
						correctAmount = true;
						break;// no need to go through the rest of the loop,
								// found the item.
					}
				}
			}
		}
		// make sure that all the amounts were found
		if (!correctAmount) { return false; }
		// If one of the amounts didn't add up, return false.
		// This is assuming all amounts were found expect random error
		return true;
	}

	/**
	 * Will Scan for a list of items, and check to see if the correct amounts
	 * are in a player's inventory
	 **/
	public static boolean checkForItemsAndAmounts(ItemStack[] items, int[] amounts,
			InventoryPlayer invPlayer)
	{
		if (items.length != amounts.length)// Just to make sure you checked
											// right
		{
			System.out.println("items and amounts do not match up in lengths.");
			return false;
		}
		boolean[] correctAmount = new boolean[items.length];
		int[] amountCounter = new int[amounts.length];
		for (int i = 0; i < correctAmount.length; i++)
		{
			correctAmount[i] = false;
			amountCounter[i] = 0;
		}

		// Check the players inventory
		for (int slot = 0; slot < invPlayer.getSizeInventory(); slot++)
		{
			if (invPlayer.getStackInSlot(slot) != null)
			{
				// If something is in the slot check to see if it matches one of
				// the given items
				for (int item = 0; item < items.length; item++)
				{
					if (invPlayer.getStackInSlot(slot).equals(items[item]) && !correctAmount[item])
					{
						// If the found stack matches the item and don't have
						// the correct amount
						amountCounter[item] += invPlayer.getStackInSlot(slot).stackSize;
						if (amountCounter[item] >= amounts[item])
						{
							correctAmount[item] = true;
							break;// no need to go through the rest of the loop,
									// found the item.
						}
					}
				}
			}
		}

		// make sure that all the amounts were found
		for (int i = 0; i < correctAmount.length; i++)
		{
			if (!correctAmount[i]) { return false;
			// If one of the amounts didn't add up, return false.
			}
		}
		// This is asssuming all amounts were found expect random error
		return true;
	}
}
