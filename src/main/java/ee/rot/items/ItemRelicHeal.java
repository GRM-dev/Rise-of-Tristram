package ee.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.ExtendPlayer;

//First item I went out on a limb to make
public class ItemRelicHeal extends Item {

	private int CD = 30;
	private int coolDown = 0;
	private float manaCostPassive = 15f;
	private float manaCostAction = 20f;
	
	public ItemRelicHeal() {
		super();
		
		setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		if (coolDown == 0)
		{
			EntityPlayer player = (EntityPlayer)par3Entity;
			if (player.getFoodStats().getFoodLevel() == 0)
			{
				coolDown = CD;
				ExtendPlayer props = ExtendPlayer.get(player);
				if (props.consumeMana(manaCostPassive))
				{
					player.getFoodStats().addStats(1, 1f);
				}
			}
		}
		else coolDown--;		
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		
		ExtendPlayer props = ExtendPlayer.get(player);
		if (player.shouldHeal() && props.consumeMana(manaCostAction))
		{
			player.heal(1f);
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY,
				hitZ);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will prevent the holder");
		par3List.add("from entering a state of starvation,");
		par3List.add("it will consume " + manaCostPassive + " mana");
		par3List.add("each time it has to prevent this.");
		par3List.add("");
		par3List.add("Using this Relic will heal the holder");
		par3List.add("at the cost of " + manaCostAction + " mana per use.");
	}
}
