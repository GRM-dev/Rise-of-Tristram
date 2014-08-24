package ee.rot.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import org.lwjgl.util.vector.Vector3f;


public class TileEntityItemGenerator extends TileEntity
{
	private int ACTION_CD = 25;
	private int cd = ACTION_CD;
	private float mana = 0;
	private float manaCap = 1600;
	private float manaCost = 45.5f;
	private int range = 4;
	private int flag = 2;
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTag) 
	{
		super.writeToNBT(nbtTag);
		nbtTag.setFloat("itemGenMana", mana);
		nbtTag.setInteger("itemGenCd", cd);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTag) 
	{
		super.readFromNBT(nbtTag);
		mana = nbtTag.getFloat("itemGenMana");
		cd = nbtTag.getInteger("itemGenCd");
	}
	
	@Override
	public boolean canUpdate() 
	{
		return true;
	}
	
	@Override
	public void updateEntity() 
	{
		if (!getWorldObj().isRemote)
		{
			//getWorldObj().getChunkProvider().loadChunk(xCoord, zCoord);
			int redStoneBlocks = 0;
			for (int y = -range; y <= range; y++)
			{
				for (int x = -range; x <= range; x++)
				{
					for (int z = -range; z <= range; z++)
					{
						if (getWorldObj().getBlock(x + xCoord, y + yCoord, z + zCoord).equals(Blocks.redstone_block))redStoneBlocks++;
					}
				}
			}
		if (mana < manaCap)mana += ((5.2755f + redStoneBlocks) / (60));			
		
		if (cd == 0)
		{			
			cd = ACTION_CD;
			System.out.println("Mana: "+ mana+"/"+manaCap);
            int metaData = (int) (8 - ((mana / manaCap * 100) / 12.5));
            if (metaData < 0) { metaData = 0; }
			System.out.println("Metadata: "+ metaData);
			getWorldObj().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metaData, flag);
			//getWorldObj().getBlock(xCoord, yCoord, zCoord).setLightLevel(metaData);
			System.out.println("----------");
			
				TileEntity te;
				for (int y = -range; y <= range; y++)
				{
					for (int x = -range; x <= range; x++)
					{
						for (int z = -range; z <= range; z++)
						{
							te = getWorldObj().getTileEntity(x + xCoord, y + yCoord, z + zCoord);
							if (te != null)
							{
								if (te instanceof TileEntityChest)
								{
									TileEntityChest tec = (TileEntityChest)te;
									for (int i = 0; i < tec.getSizeInventory(); i++)
									{
										if (tec.getStackInSlot(i) != null)
										{
											if (tec.getStackInSlot(i).isItemDamaged() && !tec.getStackInSlot(i).getItem().getHasSubtypes() && mana >= 5)
											{
												if (tec.getStackInSlot(i).getItemDamage() > 5)
												{
													tec.getStackInSlot(i).setItemDamage(tec.getStackInSlot(i).getItemDamage() -5);
													mana -= 5f;
												}
												tec.getStackInSlot(i).setItemDamage(tec.getStackInSlot(i).getItemDamage() -1);
												mana -= 1f;
											}
											if (tec.getStackInSlot(i).getMaxStackSize() <= 4 && tec.getStackInSlot(i).stackSize < 4 && !tec.getStackInSlot(i).getItem().isDamageable())
											{
												tec.getStackInSlot(i).stackSize++;
												mana -= manaCost;
											}
											if (tec.getStackInSlot(i).stackSize < tec.getStackInSlot(i).getMaxStackSize() && mana >= manaCost)
											{												
												tec.getStackInSlot(i).stackSize++;
												mana -= manaCost;
											}
											if (mana < manaCost)return;
										}
									}								
								}
							}
						}
					}
				}
		}
		else cd--;
		}
	}	
}
