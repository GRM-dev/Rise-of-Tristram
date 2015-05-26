package ca.grm.rot.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.extendprops.ExtendPlayer;

// First item I went out on a limb to make
public class ItemRelicRepair extends Item {
	
	private int		CD			= 60;
	private int		coolDown	= 0;
	private float	manaCost	= 1.5f;

	public ItemRelicRepair() {
		super();
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
			java.util.List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will slowly repair");
		par3List.add("non worn, non hotbar items");
		par3List.add("at the cost of " + this.manaCost + " mana.");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity,
			int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if (this.coolDown != 0) {
			this.coolDown--;
		}

		if ((this.coolDown == 0) && !par2World.isRemote) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			ExtendPlayer props = ExtendPlayer.get(player);
			this.coolDown = this.CD / 4;
			for (int i = 9; i < (player.inventory.getSizeInventory() - 4); i++) {
				ItemStack tool = player.inventory.getStackInSlot(i);
				if (tool != null) {
					if (tool.isItemDamaged()) {
						if (props.consumeMana(this.manaCost)) {
							this.coolDown = this.CD;
							if (tool.getItemDamage() > 1) {
								tool.setItemDamage(tool.getItemDamage() - 1);
							} else {
								tool.setItemDamage(0);
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}
}
