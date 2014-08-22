package ee.rot.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ee.rot.ExtendPlayerRot;
import ee.rot.RotEntityThrowableBomb;

public class ItemBomb extends Item 
{
	private int delayTime = 60;
	private int coolDown = 0;
	
	public ItemBomb() {
		super();
		
		setMaxStackSize(1);
		setMaxDamage(5);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		// TODO Auto-generated method stub
		
		if (!par2World.isRemote)
        {
			par2World.spawnEntityInWorld(new RotEntityThrowableBomb(par2World, par3EntityPlayer));
			par1ItemStack.damageItem(1, par3EntityPlayer);
        }
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
}
