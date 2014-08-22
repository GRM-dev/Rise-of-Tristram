package ee.rot.items;

import java.util.List;

import scala.Console;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;

public class ItemRelicLife extends Item {

	private int CD = 40;
	private int coolDown = 0;
	private float manaCostPassive = 7f;
	private float manaCostAction = 10f;
	private boolean passiveUsed = false;
	private String function = "";
	private IIcon[] textures = new IIcon[3];
	private int levelBonus = 12;
	
	public ItemRelicLife() 
	{
		super();		
		setMaxStackSize(1);
		setMaxDamage(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) 
	{
		for (int i = 0;i < textures.length; i++)
		{
			textures[i] = ir.registerIcon(Rot.MODID+":"+"relicLife_"+i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) 
	{
		// TODO Auto-generated method stub
		return textures[par1];
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		if (coolDown == 0)
		{
			EntityPlayer player = (EntityPlayer)par3Entity;
			if (player.shouldHeal())
			{
				player.heal(0.25f);
				passiveUsed = true;
			}	
			if (player.getFoodStats().getFoodLevel() <= 1)
			{
				player.getFoodStats().addStats(1, 1f);
				passiveUsed = true;				
			}
			ExtendPlayerRot props = ExtendPlayerRot.get(player);
			
			switch (par1ItemStack.getItemDamage())
			{
				case 0://Heal
					function = "Is now focused on Healing.";
					if (player.shouldHeal())
					{
						if (props.consumeMana(manaCostPassive / 2 - (0.5f * (player.experienceLevel / levelBonus))))
						{
							player.heal(1f);
							passiveUsed = true;
						}					
					}	
					break;
				case 1://Repair
					function = "Is now focused on Repairing.";
					for (int i = 9; i < player.inventory.getSizeInventory();i++)
					{
						ItemStack tool = player.inventory.getStackInSlot(i);
						if (tool != null)
						{
							if (tool.isItemDamaged())
							{							
								if (props.consumeMana(manaCostPassive / 2 - (0.5f * (player.experienceLevel / levelBonus))))
								{
									passiveUsed = true;
									if (tool.getItemDamage() > 1) 
										tool.setItemDamage(tool.getItemDamage() - 1);
									else 
									{
										tool.setItemDamage(0);
										break;
									}
								}
								else break;
							}
						}
					}
					break;
				case 2://Saturation
					function = "Is now focused on Saturation.";
					if (player.getFoodStats().needFood())
					{
						if (props.consumeMana(manaCostPassive - (0.5f * (player.experienceLevel / levelBonus))))
						{
							player.getFoodStats().addStats(1, 1f);
							passiveUsed = true;
						}
					}
					break;
			}
			
			passiveUsed = false;
			coolDown = CD / 4;
			if (passiveUsed) coolDown = CD;
		}
		else coolDown--;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ExtendPlayerRot props = ExtendPlayerRot.get(par3EntityPlayer);
		
		if (par1ItemStack.getItemDamage() < 2)par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()+1);
		else par1ItemStack.setItemDamage(0);
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add("This Relic will slowly heal");
		par3List.add("the holder and prevent starvation.");
		par3List.add("Based on its current function it will use "+ (manaCostPassive - (0.5f * (par2EntityPlayer.experienceLevel / levelBonus))) + " mana");
		par3List.add("Status: "+function);
		par3List.add("");
		par3List.add("Mana cost reduction based on level "+(0.5f * (par2EntityPlayer.experienceLevel / levelBonus))+" every "+levelBonus+" levels");
	}
}
