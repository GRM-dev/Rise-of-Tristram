package ca.grm.rot.items;

import ca.grm.rot.libs.ExtendPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwordSoul extends ItemSword {
	
	public ItemSwordSoul(ToolMaterial mat) {
		super(mat);
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase target,
			EntityLivingBase attacker) {

		if (target.isEntityAlive()) {
			if (attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				ExtendPlayer props = ExtendPlayer.get(player);
				if (props.consumeMana(4f)) {
					// target.setFire(4);
					target.heal(-4);
					attacker.heal(2);
				}
			}
			is.damageItem(1, attacker);
			
		}
		
		if (!target.isEntityAlive()) {
			if (attacker.getHealth() < attacker.getMaxHealth()) {
				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					ExtendPlayer props = ExtendPlayer.get(player);
					props.regenMana(target.getMaxHealth() * 0.15f);
					props.regenStam(target.getMaxHealth() * 0.15f);
				}
				attacker.heal(target.getMaxHealth() * 0.05f);
			} else {
				if (is.getItemDamage() > 9) {
					is.setItemDamage(is.getItemDamage() - 9);
				} else {
					is.setItemDamage(0);
				}
			}
		}
		return true;
	}
	
}
