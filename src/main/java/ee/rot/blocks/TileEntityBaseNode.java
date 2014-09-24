package ee.rot.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import ee.rot.Rot;
import ee.rot.comms.BaseNodeRequestPacket;
import ee.rot.comms.BaseNodeResponsePacket;
import ee.rot.items.RotItems;
import ee.rot.libs.ExtendPlayer;
import ee.rot.libs.UtilityBlockLocationType;
import ee.rot.libs.UtilityNBTHelper;


public class TileEntityBaseNode extends TileEntity
{
	private int ACTION_CD = 25;
	private int cd = ACTION_CD;
	private float mana = 0;
	private float manaCap = 1600;
	private int range = 4;
	private int flag = 2;
	private boolean building = false;
	public int gX = 6, gZ = 6;
	public ArrayList locations = new ArrayList<UtilityBlockLocationType>();
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTag) 
	{
		super.writeToNBT(nbtTag);
		nbtTag.setFloat(Rot.MODID+"magicBaseMana", mana);
		nbtTag.setInteger(Rot.MODID+"magicBaseCd", cd);
		NBTTagList ls = new NBTTagList();
		if (locations.size() > 0)
		{
			for (int i = 0; i < locations.size();i++)
			{
				UtilityBlockLocationType u = (UtilityBlockLocationType)locations.get(i);
				NBTTagCompound l = new NBTTagCompound();
				int blockId = 0;
				for (int bt = 0; bt < RotBlocks.blockTypeObjects.length;bt++)
				{
					if (u.block.equals(RotBlocks.blockTypeObjects[bt]))
					{
						blockId = Block.getIdFromBlock(RotBlocks.blockTypeObjects[bt]);
						break;
					}
				}
				l.setString(Rot.MODID+"location", u.x+","+u.y+","+u.z+","+blockId);
				ls.appendTag(l);
			}			
			nbtTag.setTag(Rot.MODID+"locations", ls);
		}		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTag) 
	{
		super.readFromNBT(nbtTag);
		mana = nbtTag.getFloat(Rot.MODID+"magicBaseMana");
		cd = nbtTag.getInteger(Rot.MODID+"magicBaseCd");
		NBTTagList ls = nbtTag.getTagList(Rot.MODID+"locations", nbtTag.getId());
		for (int i = 0; i < ls.tagCount(); ++i) {
			NBTTagCompound l = ls.getCompoundTagAt(i);
			String[] ubltParts = l.getString(Rot.MODID+"location").split(",");
			locations.add(new UtilityBlockLocationType(
					Integer.parseInt(ubltParts[0]), 
					Integer.parseInt(ubltParts[1]), 
					Integer.parseInt(ubltParts[2]), 
					Block.getBlockById(Integer.parseInt(ubltParts[3]))
					));
		}
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
			if (building)
			{
				building = false;
				for (int l = 0; l < locations.size();l++)
				{
					UtilityBlockLocationType u = (UtilityBlockLocationType) locations.get(l);
					getWorldObj().setBlock(u.x, u.y, u.z, u.block);
				}
			}
			
			generateMana();
			

			if (cd == 0)
			{			
				Chunk ch = getWorldObj().getChunkFromBlockCoords(xCoord, yCoord);
				getWorldObj().getChunkProvider().loadChunk(ch.xPosition, ch.zPosition);
				restorePlayers();
				updateBlockStatus();
				cd = ACTION_CD;			
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
										ItemStack itemStack = tec.getStackInSlot(i);
										repairItem(itemStack, 15, 0.75f);
										//chargeItemStackWithMana(itemStack,2f);
										increaseStackSize(itemStack, 45.75f);
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
	
	public void addLocation(int x, int y, int z, Block block)
	{
		if (x == xCoord && y == yCoord && z == zCoord)return;
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y , z , block);
		if (locations.size() > 0)
		{
			for (int l = 0;l < locations.size();l++)
			{
				if (locations.get(l).equals(location))
				{
					locations.set(l, location);
					return;
				}				
			}			
		}
		locations.add(location);
	}
	
	public void addLocation(int x, int y, int z, Block block, int meta)
	{
		if (x == xCoord && y == yCoord && z == zCoord)return;
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y, z, block, meta);
		if (locations.size() > 0)
		{
			for (int l = 0;l < locations.size();l++)
			{
				if (locations.get(l).equals(location))
				{
					locations.set(l, location);
					return;
				}				
			}			
		}
		locations.add(location);
	}
	
	public void updateClient(EntityPlayerMP pmp)
	{
		if (!locations.isEmpty())
		{
			UtilityBlockLocationType ublt;
			for (int l = 0; l < locations.size(); l++)
			{
				ublt = (UtilityBlockLocationType) locations.get(l);
				Rot.net.sendTo(new BaseNodeResponsePacket(0,xCoord,yCoord,zCoord,ublt.x,ublt.y,ublt.z,Block.getIdFromBlock(ublt.block)), pmp);
			}
		}	
	}
	
	public void clearLocations()
	{
		locations.clear();
	}
	
	public void startBuilding()
	{
		building = true;
	}
	
	public void updateBlockStatus()
	{
        int metaData = (int) (8 - ((mana / manaCap * 100) / 12.5));
        if (metaData < 0) { metaData = 0; }
		getWorldObj().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metaData, flag);
	}
	
	public void generateMana()
	{
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
	}
	
	public void restorePlayers()
	{
		List players = getWorldObj().getEntitiesWithinAABB(EntityPlayer.class, this.getRenderBoundingBox().expand(range, range, range));
		Iterator iterator = players.iterator();
        EntityPlayer entityplayer;
        while (iterator.hasNext())
        {
            entityplayer = (EntityPlayer)iterator.next();
            ExtendPlayer props = ExtendPlayer.get(entityplayer);
            if (entityplayer.shouldHeal())
            {
            	if (mana > 1)
            	{
            		entityplayer.heal(.25f);
            		mana -= 1;            		
            	}
            }
            if (entityplayer.getFoodStats().needFood())
            {
            	if (mana > 1)
            	{
            		entityplayer.getFoodStats().addStats(1, 1f);
            		mana-=1;
            	}
            }
            /*if (props.needsMana())
            {
            	if (mana > 15)
            	{
            		props.regenMana(15f);
            		mana -= 15;            		
            	}
            }*/            
        }
        repairItemsOnPlayers(players);
	}
	
	public void repairItemsOnPlayers(List players)
	{
		Iterator iterator = players.iterator();
        EntityPlayer entityplayer;
        while (iterator.hasNext())
        {
            entityplayer = (EntityPlayer)iterator.next();
            for (int slot = 0; slot < entityplayer.inventory.getSizeInventory();slot++)
            {            	
            	repairItem(entityplayer.inventory.getStackInSlot(slot),3,1.25f);
            }
        }
	}
	
	public void repairItem(ItemStack item, int repairAmount,float manaPerPoint)
	{
		if (item != null)
		{
			if (mana < repairAmount * manaPerPoint)return;
			if (item.isItemDamaged() && !item.getItem().getHasSubtypes() && mana >= repairAmount * manaPerPoint)
			{
				if (item.getItemDamage() > repairAmount)
				{
					item.setItemDamage(item.getItemDamage() -repairAmount);
					mana -= repairAmount * manaPerPoint;
				}
				else if (item.getItemDamage() <= repairAmount)
				{
					mana -= item.getItemDamage() * manaPerPoint;
					item.setItemDamage(0);													
				}
				if (mana < repairAmount * manaPerPoint)return;
			}
		}
	}
	
	public void increaseStackSize(ItemStack item,float manaCost)
	{
		if (item != null)
		{
			if (item.getMaxStackSize() <= 4 && item.stackSize < 4 && !item.getItem().isDamageable() && mana >= manaCost*4)
			{
				item.stackSize++;
				mana -= manaCost;
			}
			if (item.stackSize < item.getMaxStackSize() && mana >= manaCost)
			{												
				item.stackSize++;
				mana -= manaCost;
			}
			if (mana < manaCost)return;
		}
	}
	
	public void chargeItemStackWithMana(ItemStack item,float manaGive)
	{
		if (item != null)
		{
			if (item.getItem().equals(RotItems.itemMana) && item.getItemDamage() == 2 && mana >= manaGive)
			{												
				float currentMana =	UtilityNBTHelper.getFloat(item, Rot.MODID+"itemMana");
				if (currentMana < 200)
				{
					currentMana += manaGive;
					if (currentMana > 200)
					{
						manaGive -= (currentMana - 200);
						currentMana = 200;
					}
					System.out.println(currentMana);
					UtilityNBTHelper.setFloat(item, Rot.MODID+"itemMana", currentMana);
					mana -= manaGive;
				}
			}
			if (mana < manaGive)return;
		}
	}
}
