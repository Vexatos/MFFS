package com.minalien.mffs.tiles

import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.{Item, ItemBlock, ItemStack}
import net.minecraft.entity.player.EntityPlayer
import com.minalien.mffs.items.ItemForcicium
import net.minecraft.nbt.NBTTagCompound
import com.minalien.mffs.blocks.BlockForciciumBlock
import com.minalien.mffs.ModConfig
import com.minalien.mffs.network.{NetworkUtil, PacketBuilder}

/**
 * Tile Entity responsible for the Force Energy Extractor.
 */
class TileEntityFEExtractor extends MFFSMachineTileEntity with ISidedInventory {
	val TAG_FORCICIUM_STACK = "FORCICIUM_STACK"
	val TAG_FORCICIUM_CONSUMED = "FORCICIUM_CONSUMED"
	val TAG_CONSUMPTION_TICKS = "FORCICIUM_CONSUMPTION_TICKS"

	var forciciumStack: ItemStack = null
	var forciciumConsumed = 0
	var consumptionTicks = 0

	override def getForceEnergyCapacity = 1800.0f

	override def updateEntity() {
		super.updateEntity()

		if(worldObj.isRemote || !isActive)
			return

		if(forciciumConsumed == 0) {
			if(forciciumStack != null && isItemValidForSlot(0, forciciumStack) && getCurrentForceEnergy < getForceEnergyCapacity) {
				forciciumStack.getItem match {
					case ib: ItemBlock =>
						if(ib.field_150939_a == BlockForciciumBlock)
							forciciumConsumed = 9

					case i: Item =>
						if(i == ItemForcicium)
							forciciumConsumed = 1
				}

				forciciumStack.stackSize -= 1

				if(forciciumStack.stackSize == 0)
					setInventorySlotContents(0, null)
			}
		} else {
			consumptionTicks += 1

			if(consumptionTicks >= ModConfig.ForceEnergy.forciciumConsumptionCycle) {
				forciciumConsumed -= 1

				val currentEnergy = getCurrentForceEnergy + ModConfig.ForceEnergy.forceEnergyPerForcicium
				setCurrentForceEnergy(currentEnergy)

				consumptionTicks = 0
			}
		}
	}

	override def readFromNBT(tagCompound: NBTTagCompound) {
		super.readFromNBT(tagCompound)

		val forciciumStackTag = tagCompound.getCompoundTag(TAG_FORCICIUM_STACK)
		if(forciciumStackTag != null)
			forciciumStack = ItemStack.loadItemStackFromNBT(forciciumStackTag)

		forciciumConsumed = tagCompound.getInteger(TAG_FORCICIUM_CONSUMED)
		consumptionTicks = tagCompound.getInteger(TAG_CONSUMPTION_TICKS)
	}

	override def writeToNBT(tagCompound: NBTTagCompound) {
		super.writeToNBT(tagCompound)

		if(forciciumStack != null) {
			val forciciumStackTag = new NBTTagCompound()
			forciciumStack.writeToNBT(forciciumStackTag)

			tagCompound.setTag(TAG_FORCICIUM_STACK, forciciumStackTag)
		}

		tagCompound.setInteger(TAG_FORCICIUM_CONSUMED, forciciumConsumed)
		tagCompound.setInteger(TAG_CONSUMPTION_TICKS, consumptionTicks)
	}

	def getSizeInventory: Int = 1

	def getStackInSlot(slot: Int): ItemStack = forciciumStack

	def decrStackSize(slot: Int, numRetrieved: Int): ItemStack = {
		if(forciciumStack != null) {
			if(forciciumStack.stackSize <= numRetrieved) {
				val retrievedStack = forciciumStack
				forciciumStack = null
				return retrievedStack
			}

			val retrievedStack = forciciumStack.splitStack(numRetrieved)
			if(forciciumStack.stackSize == 0)
				forciciumStack = null

			return retrievedStack
		}

		null
	}

	def getStackInSlotOnClosing(slot: Int): ItemStack = forciciumStack

	def setInventorySlotContents(slot: Int, itemStack: ItemStack) = {
		forciciumStack = itemStack
	}

	def getInventoryName: String = "MFFS_FE_EXTRACTOR"

	def hasCustomInventoryName: Boolean = false

	def getInventoryStackLimit: Int = 64

	def isUseableByPlayer(player: EntityPlayer): Boolean = {
		if(worldObj == null)
			return true

		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false

		player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0
	}

	def openInventory(): Unit = {}

	def closeInventory(): Unit = {}

	def isItemValidForSlot(slot: Int, itemStack: ItemStack): Boolean = {
		if(itemStack == null)
			return true

		slot match {
			case 0 =>
				itemStack.getItem match {
					case ib: ItemBlock =>
						return ib.field_150939_a == BlockForciciumBlock

					case i: Item =>
						return i == ItemForcicium
				}
		}

		false
	}

	def getAccessibleSlotsFromSide(side: Int): Array[Int] = {
		Array.fill[Int](1)(0)
	}

	def canInsertItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = isItemValidForSlot(slot, itemStack)

	def canExtractItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = false
}
