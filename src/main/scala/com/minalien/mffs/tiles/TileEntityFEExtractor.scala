package com.minalien.mffs.tiles

import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.minalien.mffs.items.ItemForcicium

/**
 * Tile Entity responsible for the Force Energy Extractor.
 */
class TileEntityFEExtractor extends MFFSMachineTileEntity with ISidedInventory {
	override def getForceEnergyCapacity: Float = 1800.0f

	var forciciumStack: ItemStack = null

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
		if(isItemValidForSlot(slot, itemStack)) {
			forciciumStack = itemStack
		}
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
		slot match {
			case 0 =>
				return itemStack.getItem == ItemForcicium
		}

		false
	}

	def getAccessibleSlotsFromSide(side: Int): Array[Int] = {
		new Array[Int](0)
	}

	def canInsertItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = isItemValidForSlot(slot, itemStack)

	def canExtractItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = false
}
