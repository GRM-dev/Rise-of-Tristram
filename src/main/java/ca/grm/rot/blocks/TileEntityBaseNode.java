package ca.grm.rot.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.chunk.Chunk;
import ca.grm.rot.Rot;
import ca.grm.rot.comms.BaseNodeResponsePacket;
import ca.grm.rot.items.RotItemsOld;
import ca.grm.rot.libs.ExtendPlayer;
import ca.grm.rot.libs.UtilityBlockLocationType;
import ca.grm.rot.libs.UtilityNBTHelper;

public class TileEntityBaseNode extends TileEntity {
	private int			ACTION_CD	= 25;
	private int			cd			= this.ACTION_CD;
	private float		mana		= 0;
	private float		manaCap		= 1600;
	private int			range		= 4;
	private int			flag		= 2;
	private boolean		building	= false;
	public int			gX			= 6, gZ = 6;
	public ArrayList	locations	= new ArrayList<UtilityBlockLocationType>();

	public void addLocation(int x, int y, int z, Block block) {
		if ((x == this.xCoord) && (y == this.yCoord) && (z == this.zCoord)) { return; }
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y, z, block);
		if (this.locations.size() > 0) {
			for (int l = 0; l < this.locations.size(); l++) {
				if (this.locations.get(l).equals(location)) {
					this.locations.set(l, location);
					return;
				}
			}
		}
		this.locations.add(location);
	}

	public void addLocation(int x, int y, int z, Block block, int meta) {
		if ((x == this.xCoord) && (y == this.yCoord) && (z == this.zCoord)) { return; }
		UtilityBlockLocationType location = new UtilityBlockLocationType(x, y, z, block,
				meta);
		if (this.locations.size() > 0) {
			for (int l = 0; l < this.locations.size(); l++) {
				if (this.locations.get(l).equals(location)) {
					this.locations.set(l, location);
					return;
				}
			}
		}
		this.locations.add(location);
	}

	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public void chargeItemStackWithMana(ItemStack item, float manaGive) {
		if (item != null) {
			if (item.getItem().equals(RotItemsOld.itemMana) && (item.getItemDamage() == 2)
					&& (this.mana >= manaGive)) {
				float currentMana = UtilityNBTHelper.getFloat(item, Rot.MOD_ID
						+ "itemMana");
				if (currentMana < 200) {
					currentMana += manaGive;
					if (currentMana > 200) {
						manaGive -= (currentMana - 200);
						currentMana = 200;
					}
					System.out.println(currentMana);
					UtilityNBTHelper.setFloat(item, Rot.MOD_ID + "itemMana", currentMana);
					this.mana -= manaGive;
				}
			}
			if (this.mana < manaGive) { return; }
		}
	}

	public void clearLocations() {
		this.locations.clear();
	}

	public void generateMana() {
		int redStoneBlocks = 0;
		for (int y = -this.range; y <= this.range; y++) {
			for (int x = -this.range; x <= this.range; x++) {
				for (int z = -this.range; z <= this.range; z++) {
					if (getWorldObj().getBlock(x + this.xCoord, y + this.yCoord,
							z + this.zCoord).equals(Blocks.redstone_block)) {
						redStoneBlocks++;
					}
				}
			}
		}
		if (this.mana < this.manaCap) {
			this.mana += ((5.2755f + redStoneBlocks) / (60));
		}
	}

	public void increaseStackSize(ItemStack item, float manaCost) {
		if (item != null) {
			if ((item.getMaxStackSize() <= 4) && (item.stackSize < 4)
					&& !item.getItem().isDamageable() && (this.mana >= (manaCost * 4))) {
				item.stackSize++;
				this.mana -= manaCost;
			}
			if ((item.stackSize < item.getMaxStackSize()) && (this.mana >= manaCost)) {
				item.stackSize++;
				this.mana -= manaCost;
			}
			if (this.mana < manaCost) { return; }
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		this.mana = nbtTag.getFloat(Rot.MOD_ID + "magicBaseMana");
		this.cd = nbtTag.getInteger(Rot.MOD_ID + "magicBaseCd");
		NBTTagList ls = nbtTag.getTagList(Rot.MOD_ID + "locations", nbtTag.getId());
		for (int i = 0; i < ls.tagCount(); ++i) {
			NBTTagCompound l = ls.getCompoundTagAt(i);
			String[] ubltParts = l.getString(Rot.MOD_ID + "location").split(",");
			this.locations.add(new UtilityBlockLocationType(Integer
					.parseInt(ubltParts[0]), Integer.parseInt(ubltParts[1]), Integer
					.parseInt(ubltParts[2]), Block.getBlockById(Integer
					.parseInt(ubltParts[3]))));
		}
	}

	public void repairItem(ItemStack item, int repairAmount, float manaPerPoint) {
		if (item != null) {
			if (this.mana < (repairAmount * manaPerPoint)) { return; }
			if (item.isItemDamaged() && !item.getItem().getHasSubtypes()
					&& (this.mana >= (repairAmount * manaPerPoint))) {
				if (item.getItemDamage() > repairAmount) {
					item.setItemDamage(item.getItemDamage() - repairAmount);
					this.mana -= repairAmount * manaPerPoint;
				} else if (item.getItemDamage() <= repairAmount) {
					this.mana -= item.getItemDamage() * manaPerPoint;
					item.setItemDamage(0);
				}
				if (this.mana < (repairAmount * manaPerPoint)) { return; }
			}
		}
	}

	public void repairItemsOnPlayers(List players) {
		Iterator iterator = players.iterator();
		EntityPlayer entityplayer;
		while (iterator.hasNext()) {
			entityplayer = (EntityPlayer) iterator.next();
			for (int slot = 0; slot < entityplayer.inventory.getSizeInventory(); slot++) {
				repairItem(entityplayer.inventory.getStackInSlot(slot), 3, 1.25f);
			}
		}
	}

	public void restorePlayers() {
		List players = getWorldObj().getEntitiesWithinAABB(EntityPlayer.class,
				this.getRenderBoundingBox().expand(this.range, this.range, this.range));
		Iterator iterator = players.iterator();
		EntityPlayer entityplayer;
		while (iterator.hasNext()) {
			entityplayer = (EntityPlayer) iterator.next();
			ExtendPlayer props = ExtendPlayer.get(entityplayer);
			if (entityplayer.shouldHeal()) {
				if (this.mana > 1) {
					entityplayer.heal(.25f);
					this.mana -= 1;
				}
			}
			if (entityplayer.getFoodStats().needFood()) {
				if (this.mana > 1) {
					entityplayer.getFoodStats().addStats(1, 1f);
					this.mana -= 1;
				}
			}
			/*
			 * if (props.needsMana())
			 * {
			 * if (mana > 15)
			 * {
			 * props.regenMana(15f);
			 * mana -= 15;
			 * }
			 * }
			 */
		}
		repairItemsOnPlayers(players);
	}

	public void startBuilding() {
		this.building = true;
	}

	public void updateBlockStatus() {
		int metaData = (int) (8 - (((this.mana / this.manaCap) * 100) / 12.5));
		if (metaData < 0) {
			metaData = 0;
		}
		getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord,
				metaData, this.flag);
	}

	public void updateClient(EntityPlayerMP pmp) {
		if (!this.locations.isEmpty()) {
			UtilityBlockLocationType ublt;
			for (int l = 0; l < this.locations.size(); l++) {
				ublt = (UtilityBlockLocationType) this.locations.get(l);
				Rot.net.sendTo(
						new BaseNodeResponsePacket(0, this.xCoord, this.yCoord,
								this.zCoord, ublt.x, ublt.y, ublt.z, Block
										.getIdFromBlock(ublt.block)), pmp);
			}
		}
	}

	@Override
	public void updateEntity() {
		if (!getWorldObj().isRemote) {
			if (this.building) {
				this.building = false;
				for (int l = 0; l < this.locations.size(); l++) {
					UtilityBlockLocationType u = (UtilityBlockLocationType) this.locations
							.get(l);
					getWorldObj().setBlock(u.x, u.y, u.z, u.block);
				}
			}

			generateMana();
			
			if (this.cd == 0) {
				Chunk ch = getWorldObj()
						.getChunkFromBlockCoords(this.xCoord, this.yCoord);
				getWorldObj().getChunkProvider().loadChunk(ch.xPosition, ch.zPosition);
				restorePlayers();
				updateBlockStatus();
				this.cd = this.ACTION_CD;
				TileEntity te;
				for (int y = -this.range; y <= this.range; y++) {
					for (int x = -this.range; x <= this.range; x++) {
						for (int z = -this.range; z <= this.range; z++) {
							te = getWorldObj().getTileEntity(x + this.xCoord,
									y + this.yCoord, z + this.zCoord);
							if (te != null) {
								if (te instanceof TileEntityChest) {
									TileEntityChest tec = (TileEntityChest) te;
									for (int i = 0; i < tec.getSizeInventory(); i++) {
										ItemStack itemStack = tec.getStackInSlot(i);
										repairItem(itemStack, 15, 0.75f);
										// chargeItemStackWithMana(itemStack,2f);
										increaseStackSize(itemStack, 45.75f);
									}
								}
							}
						}
					}
				}
			} else {
				this.cd--;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setFloat(Rot.MOD_ID + "magicBaseMana", this.mana);
		nbtTag.setInteger(Rot.MOD_ID + "magicBaseCd", this.cd);
		NBTTagList ls = new NBTTagList();
		if (this.locations.size() > 0) {
			for (int i = 0; i < this.locations.size(); i++) {
				UtilityBlockLocationType u = (UtilityBlockLocationType) this.locations
						.get(i);
				NBTTagCompound l = new NBTTagCompound();
				int blockId = 0;
				for (int bt = 0; bt < RotBlocksOld.blockTypeObjects.length; bt++) {
					if (u.block.equals(RotBlocksOld.blockTypeObjects[bt])) {
						blockId = Block.getIdFromBlock(RotBlocksOld.blockTypeObjects[bt]);
						break;
					}
				}
				l.setString(Rot.MOD_ID + "location", u.x + "," + u.y + "," + u.z + ","
						+ blockId);
				ls.appendTag(l);
			}
			nbtTag.setTag(Rot.MOD_ID + "locations", ls);
		}
	}
}
