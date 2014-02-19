package com.minalien.mffs.tiles

import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.minalien.mffs.items.ItemForcicium
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}

/**
 * Tile Entity responsible for the Force Energy Extractor.
 */
class TileEntityFEExtractor extends MFFSMachineTileEntity with ISidedInventory {
	val TAG_FORCICIUM_STACK = "FORCICIUM_STACK"

	override def getForceEnergyCapacity: Float = 1800.0f

	var forciciumStack: ItemStack = null

	override def readFromNBT(tagCompound: NBTTagCompound) {
		super.readFromNBT(tagCompound)

		val forciciumStackTag = tagCompound.getCompoundTag(TAG_FORCICIUM_STACK)
		if(forciciumStackTag != null)
			forciciumStack = ItemStack.loadItemStackFromNBT(forciciumStackTag)
	}

	override def writeToNBT(tagCompound: NBTTagCompound) {
		super.writeToNBT(tagCompound)

		if(forciciumStack != null) {
			val forciciumStackTag = new NBTTagCompound()
			forciciumStack.writeToNBT(forciciumStackTag)

			tagCompound.setTag(TAG_FORCICIUM_STACK, forciciumStackTag)
		}
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
				return itemStack.getItem == ItemForcicium
		}

		false
	}

	def getAccessibleSlotsFromSide(side: Int): Array[Int] = {
		Array.fill[Int](1)(0)
	}

	def canInsertItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = isItemValidForSlot(slot, itemStack)

	def canExtractItem(slot: Int, itemStack: ItemStack, side: Int): Boolean = false
}
