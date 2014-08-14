package ee.rot.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.util.vector.Vector3f;


public class TileEntityBaseBuilder extends TileEntity implements IInventory
{

	private ItemStack[] inventory = new ItemStack[9];
	private ArrayList<Vector3f> locations = new ArrayList<Vector3f>();
	private int CD_TIME = 35;
	private int cd = CD_TIME;
	private boolean onOff = false;
	
	/*@Override
	public void closeChest(){}*/
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) 
	{
		super.writeToNBT(par1nbtTagCompound);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < getSizeInventory(); i++) 
		{
			  ItemStack itemstack = getStackInSlot(i);
			  if(itemstack != null) 
			  {
				  NBTTagCompound item = new NBTTagCompound();
				  
				  item.setByte("SlotBaseBuild", (byte) i);
				  itemstack.writeToNBT(item);
				  
				  list.appendTag(item);
			  }
			}
		String places = "";
		for (Vector3f v : locations)
		{
			if (places != "")places+=";";
			places += v.x +","+v.y+","+v.z;
		}	
		
		/*NBTTagString location = new NBTTagString(places);
		location.setName("BBplaces");
		list.appendTag(location);*/
		
		par1nbtTagCompound.setTag("ItemsBaseBuild", list);
	}
	
	/*@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) 
	{
		super.readFromNBT(par1nbtTagCompound);
		
		NBTTagList list = par1nbtTagCompound.getTagList("ItemsBaseBuild");

		  for(int i = 0; i < list.tagCount(); i++) {
		        NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
		        int slot = item.getByte("SlotBaseBuild");

		        if(slot >= 0 && slot < getSizeInventory()) {
		          setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
		        }
		  }
		  /*NBTTagString location = (NBTTagString) list.tagAt(getSizeInventory());
		  String[] places = location.data.split(";");
		  for (int i = 0; i < places.length; i++)
		  {
			  String[] coords = places[i].split(",");
			  addLocation(Float.parseFloat(coords[0]) - xCoord, 
					  Float.parseFloat(coords[1]) - yCoord, 
					  Float.parseFloat(coords[2]) - zCoord);
		  }*/
	//}

	/*@Override
	public ItemStack decrStackSize(int slot, int count) 
	{
		ItemStack itemstack = getStackInSlot(slot);

		if(itemstack != null) 
		{
			if(itemstack.stackSize <= count) 
			{
				setInventorySlotContents(slot, null);
			} 
			else 
			{
				itemstack = itemstack.splitStack(count);
				onInventoryChanged();
			}
		}
		return itemstack;
	}*/

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public int getSizeInventory() 
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		ItemStack itemstack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return itemstack;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) 
	{
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		inventory[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) 
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		//onInventoryChanged();
	}
	
	@Override
	public boolean canUpdate() 
	{
		return true;
	}
	
	@Override
	public void updateEntity() 
	{
		if (cd == 0)
		{
			cd = CD_TIME;
			addLocation(1,1,1);
			for(Vector3f v : locations)
			{
				if(!getWorldObj().isRemote)getWorldObj().setBlock((int)v.x, (int)v.y, (int)v.z, Blocks.bedrock);
			}
		}
		else cd--;
	}
	
	public void switchOnOff()
	{
		onOff = !onOff;
		if(onOff)System.out.println("on");
		else System.out.println("off");
		System.out.println(onOff);
	}
	
	public void clearList()
	{
		locations.clear();
	}
	
	public void addLocation(float i, float j, float k)
	{
		if (i + xCoord == xCoord && j + yCoord == yCoord && k + zCoord == zCoord)return;
		Vector3f location = new Vector3f(i + xCoord, j + yCoord, k + zCoord);
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

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
