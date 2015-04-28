package ca.grm.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.libs.ExtendPlayer;

// First item I went out on a limb to make
public class ItemRelicHeal extends Item {
	
	private int		CD				= 30;
	private int		coolDown		= 0;
	private float	manaCostPassive	= 15f;
	private float	manaCostAction	= 20f;

	public ItemRelicHeal() {
		super();

		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
			List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will prevent the holder");
		par3List.add("from entering a state of starvation,");
		par3List.add("it will consume " + this.manaCostPassive + " mana");
		par3List.add("each time it has to prevent this.");
		par3List.add("");
		par3List.add("Using this Relic will heal the holder");
		par3List.add("at the cost of " + this.manaCostAction + " mana per use.");
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

		ExtendPlayer props = ExtendPlayer.get(player);
		if (player.shouldHeal() && props.consumeMana(this.manaCostAction)) {
			player.heal(1f);
		}
		return super
				.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity,
			int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

		if (this.coolDown == 0) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			if (player.getFoodStats().getFoodLevel() == 0) {
				this.coolDown = this.CD;
				ExtendPlayer props = ExtendPlayer.get(player);
				if (props.consumeMana(this.manaCostPassive)) {
					player.getFoodStats().addStats(1, 1f);
				}
			}
		} else {
			this.coolDown--;
		}
	}
}
