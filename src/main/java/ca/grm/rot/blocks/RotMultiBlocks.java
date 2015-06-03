package ca.grm.rot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ca.grm.rot.Rot;

public class RotMultiBlocks extends Item{
	
	public static Block galenaOre;
	
	public static void init()
	{
		galenaOre = new GalenaOreBlock(Material.rock).setUnlocalizedName("galenaOre").setCreativeTab(Rot.tabRot).setHardness(9).setResistance(4);
	}
	
	public static void register()
	{
		GameRegistry.registerBlock(galenaOre, galenaOre.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(galenaOre);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Rot.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	// TODO Finish this shit. TODO <- Make this make more sense.
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (true)
		{
			
		}
	}
}
