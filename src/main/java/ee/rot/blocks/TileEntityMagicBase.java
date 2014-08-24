package ee.rot.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import ee.rot.ExtendPlayerRot;
import ee.rot.Rot;
import ee.rot.UtilityBlockLocationType;
import ee.rot.UtilityNBTHelper;
import ee.rot.items.RotItems;


public class TileEntityMagicBase extends TileEntity
{
	private int ACTION_CD = 25;
	private int cd = ACTION_CD;
	private float mana = 0;
	private float manaCap = 1600;
	private int range = 4;
	private int flag = 2;
	private boolean building = false;
	private ArrayList locations = new ArrayList<UtilityBlockLocationType>();
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTag) 
	{
		super.writeToNBT(nbtTag);
		nbtTag.setFloat(Rot.MODID+"magicBaseMana", mana);
		nbtTag.setInteger(Rot.MODID+"magicBaseCd", cd);
		if (locations.size() > 0)
		{
			String locationsS = "";
			for (int i = 0; i < locations.size();i++)
			{
				UtilityBlockLocationType u = (UtilityBlockLocationType)locations.get(i);
				String blockS = "";
				for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
				{
					if (u.block.equals(RotBlocks.blockTypeObjects[bt]))
					{
						blockS = RotBlocks.blockTypes[bt];
						break;
					}
				}
				locationsS += u.x+","+u.y+","+u.z+","+blockS;
				if (i != locations.size() -1)
				{
					locationsS += ";";
				}
			}
			nbtTag.setString(Rot.MODID+"magicBaseLocations", locationsS);
		}		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTag) 
	{
		super.readFromNBT(nbtTag);
		mana = nbtTag.getFloat(Rot.MODID+"magicBaseMana");
		cd = nbtTag.getInteger(Rot.MODID+"magicBaseCd");
		String locationsS = nbtTag.getString(Rot.MODID+"magicBaseLocations");
		if (locationsS != "")
		{
			String[] locationS = locationsS.split(";");
			Block blockB = null;
			locations.clear();
			for (int i = 0; i < locationS.length;i++)
			{
				String[] uS = locationS[i].split(",");
				for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
				{
					if (uS[3].equals(RotBlocks.blockTypes[bt]))
					{
						blockB = RotBlocks.blockTypeObjects[bt];
						break;
					}
				}	
				locations.add(new UtilityBlockLocationType(Integer.parseInt(uS[0]), Integer.parseInt(uS[1]), Integer.parseInt(uS[2]), blockB));
			}
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
				shareManaAndRepairItems();
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
		if (x + xCoord == xCoord && y + yCoord == yCoord && z + zCoord == zCoord)return;
		UtilityBlockLocationType location = new UtilityBlockLocationType(x + xCoord, y + yCoord, z + zCoord, block);
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
	
	public void shareManaAndRepairItems()
	{
		List players = getWorldObj().getEntitiesWithinAABB(EntityPlayer.class, this.getRenderBoundingBox().expand(range, range, range));
		Iterator iterator = players.iterator();
        EntityPlayer entityplayer;
        while (iterator.hasNext())
        {
            entityplayer = (EntityPlayer)iterator.next();
            ExtendPlayerRot props = ExtendPlayerRot.get(entityplayer);
            if (props.needsMana())
            {
            	if (mana > 1)
            	{
            		props.regenMana(1f);
            		mana -= 1;            		
            	}
            }
            //Save the locations list on the special parchment
            if (entityplayer.getHeldItem() != null 
        			&& entityplayer.getHeldItem().getItem().equals(RotItems.itemMana))
        	{
        		if (entityplayer.getHeldItem().getItemDamage() == 3)
        		{
        			if (locations.size() > 0)
        			{
        				String locationsS = "";
        				for (int i = 0; i < locations.size();i++)
        				{
        					UtilityBlockLocationType u = (UtilityBlockLocationType)locations.get(i);
        					String blockS = "";
        					for (int bt = 0; bt < RotBlocks.blockTypes.length;bt++)
        					{
        						if (u.block.equals(RotBlocks.blockTypeObjects[bt]))
        						{
        							blockS = RotBlocks.blockTypes[bt];
        							break;
        						}
        					}
        					locationsS += (u.x - xCoord)+","+(u.y - yCoord)+","+(u.z - zCoord)+","+blockS;
        					if (i != locations.size() -1)
        					{
        						locationsS += ";";
        					}
        				}
        				if (UtilityNBTHelper.getString(entityplayer.getHeldItem(), Rot.MODID+"baseNodeListCoords") != locationsS)
        				{
        					UtilityNBTHelper.setString(entityplayer.getHeldItem(), Rot.MODID+"baseNodeListCoords", locationsS);
        				}
        			}
        			else
        			{
        				UtilityNBTHelper.setString(entityplayer.getHeldItem(), Rot.MODID+"baseNodeListCoords", "");
        			}
        		}
        	}
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
