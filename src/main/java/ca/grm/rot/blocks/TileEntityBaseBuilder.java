package ca.grm.rot.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.BaseNodeResponsePacket;
import ca.grm.rot.libs.UtilityBlockLocationType;
import ca.grm.rot.libs.UtilityFunctions;

public class TileEntityBaseBuilder extends TileEntity implements IUpdatePlayerListBox
{
	private int ACTION_CD = 25;
	private int cd = this.ACTION_CD;
	private int range = 4;
	private int flag = 2;
	private boolean building = false;
	public int gX = 6, gZ = 6;
	public ArrayList locations = new ArrayList<UtilityBlockLocationType>();

	public void addLocation(int x, int y, int z, Block block)
	{
		if ((x == this.getPos().getX()) && (y == this.getPos().getY()) && (z == this.getPos()
				.getZ())) { return; }
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y, z, block);
		if (this.locations.size() > 0)
		{
			for (int l = 0; l < this.locations.size(); l++)
			{
				if (this.locations.get(l).equals(location))
				{
					this.locations.set(l, location);
					return;
				}
			}
		}
		this.locations.add(location);
	}

	public void addLocation(int x, int y, int z, Block block, int meta)
	{
		if ((x == this.getPos().getX()) && (y == this.getPos().getY()) && (z == this.getPos()
				.getZ())) { return; }
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y, z, block, meta);
		if (this.locations.size() > 0)
		{
			for (int l = 0; l < this.locations.size(); l++)
			{
				if (this.locations.get(l).equals(location))
				{
					this.locations.set(l, location);
					return;
				}
			}
		}
		this.locations.add(location);
	}

	public void clearLocations()
	{
		this.locations.clear();
	}

	public void increaseStackSize(ItemStack item, float manaCost)
	{
		if (item != null)
		{
			if ((item.getMaxStackSize() <= 4) && (item.stackSize < 4) && !item.getItem()
					.isDamageable() /*&& (this.mana >= (manaCost * 4))*/)
			{
				item.stackSize++;
				//this.mana -= manaCost;
			}
			if ((item.stackSize < item.getMaxStackSize()) /*&& (this.mana >= manaCost)*/)
			{
				item.stackSize++;
				//this.mana -= manaCost;
			}
			//if (this.mana < manaCost) { return; }
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag)
	{
		super.readFromNBT(nbtTag);
		this.cd = nbtTag.getInteger(Rot.MOD_ID + "bbCd");
		NBTTagList ls = nbtTag.getTagList(Rot.MOD_ID + "locations", nbtTag.getId());
		for (int i = 0; i < ls.tagCount(); ++i)
		{
			NBTTagCompound l = ls.getCompoundTagAt(i);
			String[] ubltParts = l.getString(Rot.MOD_ID + "location").split(",");
			this.locations.add(new UtilityBlockLocationType(Integer.parseInt(ubltParts[0]), Integer
					.parseInt(ubltParts[1]), Integer.parseInt(ubltParts[2]), Block
					.getBlockById(Integer.parseInt(ubltParts[3]))));
		}
	}

	/*
	 * public void repairItem(ItemStack item, int repairAmount, float
	 * manaPerPoint) { if (item != null) { if (this.mana < (repairAmount *
	 * manaPerPoint)) { return; } if (item.isItemDamaged() &&
	 * !item.getItem().getHasSubtypes() && (this.mana >= (repairAmount *
	 * manaPerPoint))) { if (item.getItemDamage() > repairAmount) {
	 * item.setItemDamage(item.getItemDamage() - repairAmount); this.mana -=
	 * repairAmount * manaPerPoint; } else if (item.getItemDamage() <=
	 * repairAmount) { this.mana -= item.getItemDamage() * manaPerPoint;
	 * item.setItemDamage(0); } if (this.mana < (repairAmount * manaPerPoint)) {
	 * return; } } } }
	 */

	/*
	 * public void repairItemsOnPlayers(List players) { Iterator iterator =
	 * players.iterator(); EntityPlayer entityplayer; while (iterator.hasNext())
	 * { entityplayer = (EntityPlayer) iterator.next(); for (int slot = 0; slot
	 * < entityplayer.inventory.getSizeInventory(); slot++) {
	 * repairItem(entityplayer.inventory.getStackInSlot(slot), 3, 1.25f); } } }
	 */

	/*
	 * public void restorePlayers() { List players =
	 * getWorld().getEntitiesWithinAABB(EntityPlayer.class,
	 * this.getRenderBoundingBox().expand(this.range, this.range, this.range));
	 * Iterator iterator = players.iterator(); EntityPlayer entityplayer; while
	 * (iterator.hasNext()) { entityplayer = (EntityPlayer) iterator.next();
	 * ExtendPlayer props = ExtendPlayer.get(entityplayer); if
	 * (entityplayer.shouldHeal()) { if (this.mana > 1) {
	 * entityplayer.heal(.25f); this.mana -= 1; } } if
	 * (entityplayer.getFoodStats().needFood()) { if (this.mana > 1) {
	 * entityplayer.getFoodStats().addStats(1, 1f); this.mana -= 1; } } }
	 * repairItemsOnPlayers(players); }
	 */

	public void startBuilding()
	{
		this.building = true;
	}

	public void updateClient(EntityPlayerMP pmp)
	{
		if (!this.locations.isEmpty())
		{
			UtilityBlockLocationType ublt;
			for (int l = 0; l < this.locations.size(); l++)
			{
				ublt = (UtilityBlockLocationType) this.locations.get(l);
				Rot.net.sendTo(new BaseNodeResponsePacket(0, this.getPos().getX(), this.getPos()
						.getY(), this.getPos().getZ(), ublt.x, ublt.y, ublt.z, Block
						.getIdFromBlock(ublt.block)), pmp);
			}
		}
	}

	@Override
	public void update()
	{
		if (!getWorld().isRemote)
		{
			if (this.building)
			{
				this.building = false;
				for (int l = 0; l < this.locations.size(); l++)
				{
					UtilityBlockLocationType u = (UtilityBlockLocationType) this.locations.get(l);
					getWorld()
							.setBlockState(new BlockPos(u.x, u.y, u.z), u.block.getDefaultState());
				}
			}

			if (this.cd == 0)
			{
				this.cd = this.ACTION_CD;
				TileEntity te;
				for (int y = -this.range; y <= this.range; y++)
				{
					for (int x = -this.range; x <= this.range; x++)
					{
						for (int z = -this.range; z <= this.range; z++)
						{
							te = getWorld().getTileEntity(
									new BlockPos(x + this.getPos().getX(),
											y + this.getPos().getY(), z + this.getPos().getZ()));
							if (te != null)
							{
								if (te instanceof TileEntityChest)
								{
									TileEntityChest tec = (TileEntityChest) te;
									for (int i = 0; i < tec.getSizeInventory(); i++)
									{
										ItemStack itemStack = tec.getStackInSlot(i);
										// repairItem(itemStack, 15, 0.75f);
										// chargeItemStackWithMana(itemStack,2f);
										increaseStackSize(itemStack, 45.75f);
									}
								}
							}
						}
					}
				}
			}
			else
			{
				this.cd--;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag)
	{
		super.writeToNBT(nbtTag);
		nbtTag.setInteger(Rot.MOD_ID + "bbCd", this.cd);
		NBTTagList ls = new NBTTagList();
		if (this.locations.size() > 0)
		{
			for (int i = 0; i < this.locations.size(); i++)
			{
				UtilityBlockLocationType u = (UtilityBlockLocationType) this.locations.get(i);
				NBTTagCompound l = new NBTTagCompound();
				int blockId = 0;
				for (int bt = 0; bt < UtilityFunctions.blockTypeObjects.length; bt++)
				{
					if (u.block.equals(UtilityFunctions.blockTypeObjects[bt]))
					{
						blockId = Block.getIdFromBlock(UtilityFunctions.blockTypeObjects[bt]);
						break;
					}
				}
				l.setString(Rot.MOD_ID + "location", u.x + "," + u.y + "," + u.z + "," + blockId);
				ls.appendTag(l);
			}
			nbtTag.setTag(Rot.MOD_ID + "locations", ls);
		}
	}
}
