package ca.grm.rot.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ca.grm.rot.extendprops.ExtendMob;
import ca.grm.rot.extendprops.ExtendPlayer;

public class RotGoldItem extends Item{
	public RotGoldItem()
	{
		super();
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;
			ExtendPlayer props = ExtendPlayer.get(player);
			props.addGold(stack.stackSize);
			for (int i = 0; i < stack.stackSize; i++)
			{
				player.inventory.consumeInventoryItem(RotItems.gold);
			}
		}
		else 
		{
			EntityLiving living = (EntityLiving)entityIn;
			ExtendMob props = ExtendMob.get(living);
			props.gold += stack.stackSize;
			for (int i = 0; i < stack.stackSize; i++)
			{
				
			}
		}
	}
}
