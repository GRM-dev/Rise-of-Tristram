package ca.grm.rot.items;

import java.util.List;

import ca.grm.rot.libs.ExtendPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemManaConverter extends Item {
	
	private int	delayTime	= 60;
	private int	coolDown	= 0;

	public ItemManaConverter() {
		super();

		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
			List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		// par3List.add("");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ExtendPlayer props = ExtendPlayer.get(par3EntityPlayer);
		if (par3EntityPlayer.inventory.getFirstEmptyStack() > 0) {
			if (props.consumeMana(60f)) {
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(
						RotItems.itemMana, 1, 1));
			}
		}

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
}
