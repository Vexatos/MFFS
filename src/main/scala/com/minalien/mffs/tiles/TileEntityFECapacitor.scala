package com.minalien.mffs.tiles

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.minalien.mffs.items.{CardType, ItemMFFSCard}
import com.minalien.mffs.power.PowerMap
import com.minalien.mffs.items.cards.PositionalCardData

/**
 * Tile Entity responsible for the Force Energy Capacitor.
 */
class TileEntityFECapacitor extends MFFSMachineTileEntity with IInventory {
	override def getForceEnergyCapacity = 64000.0f

	override def updateEntity() {
		super.updateEntity()

		if(worldObj.isRemote || !isActive)
			return
	}

	override def readFromNBT(tagCompound: NBTTagCompound) {
		super.readFromNBT(tagCompound)
	}

	override def writeToNBT(tagCompound: NBTTagCompound) {
		super.writeToNBT(tagCompound)
	}

	def isItemValidForSlot(slot: Int, itemStack: ItemStack): Boolean = {
		if(itemStack == null)
			return true

		slot match {
			case 0 =>
				itemStack.getItem == ItemMFFSCard && ItemMFFSCard.getCardType(itemStack) == CardType.PowerLink &&
					PositionalCardData.getTileEntityAtLocation(itemStack) != this

			case _ =>
				false
		}
	}

	def closeInventory() {}

	def openInventory() {}

	def isUseableByPlayer(player: EntityPlayer): Boolean = {
		if(worldObj == null)
			return true

		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false

		player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0
	}

	override def getInventoryStackLimit: Int = 64

	override def hasCustomInventoryName: Boolean = false

	override def getInventoryName: String = "MFFS_FE_CAPACITOR"

	def setInventorySlotContents(slot: Int, itemStack: ItemStack) {
		slot match {
			case 0 =>
				if(powerLinkStack == null && itemStack != null && isItemValidForSlot(slot,
					itemStack) && !worldObj.isRemote)
					PowerMap.incNumLinks(PositionalCardData.getTileEntityAtLocation(itemStack))

				powerLinkStack = itemStack

			case _ =>
				return
		}
	}

	def getStackInSlotOnClosing(slot: Int): ItemStack = {
		slot match {
			case 0 =>
				powerLinkStack

			case _ =>
				null
		}
	}

	def decrStackSize(slot: Int, numRetrieved: Int): ItemStack = {
		slot match {
			case 0 =>
				if(powerLinkStack != null) {
					if(powerLinkStack.stackSize <= numRetrieved) {
						val retrievedStack = powerLinkStack
						if(!worldObj.isRemote && isItemValidForSlot(0, retrievedStack))
							PowerMap.decNumLinks(PositionalCardData.getTileEntityAtLocation(retrievedStack))
						powerLinkStack = null
						return retrievedStack
					}

					val retrievedStack = powerLinkStack.splitStack(numRetrieved)
					if(powerLinkStack.stackSize == 0) {
						if(!worldObj.isRemote && isItemValidForSlot(0, retrievedStack))
							PowerMap.decNumLinks(PositionalCardData.getTileEntityAtLocation(retrievedStack))
						powerLinkStack = null
					}

					return retrievedStack
				}

			case _ =>
				return null
		}

		null
	}

	override def getStackInSlot(slot: Int): ItemStack = getStackInSlotOnClosing(slot)

	override def getSizeInventory: Int = 1
}
