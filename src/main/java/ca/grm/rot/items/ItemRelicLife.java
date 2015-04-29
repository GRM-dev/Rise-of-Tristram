package ca.grm.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ca.grm.rot.Rot;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityNBTHelper;

public class ItemRelicLife extends Item {
	
	private int		CD				= 40;
	private int		coolDown		= 0;
	private float	manaCostPassive	= 7f;
	private boolean	passiveUsed		= false;
	private IIcon[]	textures		= new IIcon[2];
	private int		levelBonus		= 12;

	public ItemRelicLife() {
		super();
		setMaxStackSize(1);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
			List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will slowly heal");
		par3List.add("the holder and prevent starvation.");
		par3List.add("Based on its current function it will use " + this.manaCostPassive
				+ " mana");
		par3List.add("Status: "
				+ UtilityNBTHelper.getString(par1ItemStack, Rot.MOD_ID + "function"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if (par2 == 0) {
			return 0xFFFFFF;
		} else {
			switch (par1ItemStack.getItemDamage()) {
				case 0 :
					return 0xFF5959;
				case 1 :
					return 0x599EFF;
				case 2 :
					return 0x59FF59;
				default :
					return 0xFFFFFF;
			}
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return this.textures[pass];
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ExtendPlayer props = ExtendPlayer.get(par3EntityPlayer);

		if (par1ItemStack.getItemDamage() < 2) {
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);
		} else {
			par1ItemStack.setItemDamage(0);
		}

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity,
			int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

		if (this.coolDown == 0) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			if (player.shouldHeal()) {
				player.heal(0.25f);
				this.passiveUsed = true;
			}
			if (player.getFoodStats().getFoodLevel() <= 1) {
				player.getFoodStats().addStats(1, 1f);
				this.passiveUsed = true;
			}
			ExtendPlayer props = ExtendPlayer.get(player);

			switch (par1ItemStack.getItemDamage()) {
				case 0 :// Heal
					UtilityNBTHelper.setString(par1ItemStack, Rot.MOD_ID + "function",
							"Is now focused on Healing.");
					if (player.shouldHeal()) {
						if (props.consumeMana(this.manaCostPassive)) {
							player.heal(1f);
							this.passiveUsed = true;
						}
					}
					break;
				case 1 :// Repair
					UtilityNBTHelper.setString(par1ItemStack, Rot.MOD_ID + "function",
							"Is now focused on Repairing.");
					for (int i = 9; i < player.inventory.getSizeInventory(); i++) {
						ItemStack tool = player.inventory.getStackInSlot(i);
						if (tool != null) {
							if (tool.isItemDamaged()) {
								if (props.consumeMana(this.manaCostPassive / 4)) {
									this.passiveUsed = true;
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
					break;
				case 2 :// Saturation
					UtilityNBTHelper.setString(par1ItemStack, Rot.MOD_ID + "function",
							"Is now focused on Saturation.");
					if (player.getFoodStats().needFood()) {
						if (props.consumeMana(this.manaCostPassive)) {
							player.getFoodStats().addStats(1, 1f);
							this.passiveUsed = true;
						}
					}
					break;
			}

			this.passiveUsed = false;
			this.coolDown = this.CD / 4;
			if (this.passiveUsed) {
				this.coolDown = this.CD;
			}
		} else {
			this.coolDown--;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		/*
		 * for (int i = 0;i < textures.length; i++)
		 * {
		 * textures[i] = ir.registerIcon(Rot.MODID+":"+"relicLife_"+i);
		 * }
		 */
		this.textures[0] = ir.registerIcon(Rot.MOD_ID + ":" + "relicBrooch");
		this.textures[1] = ir.registerIcon(Rot.MOD_ID + ":" + "relicBrooch_overLay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
