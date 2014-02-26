package com.minalien.mffs.tiles

import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.blocks.MFFSMachineBlock
import net.minecraft.nbt.NBTTagCompound
import com.minalien.mffs.api.HasForceEnergy
import com.minalien.mffs.power.PowerMap
import net.minecraft.item.ItemStack
import com.minalien.mffs.items.cards.PositionalCardData
import com.minalien.mffs.items.{CardType, ItemMFFSCard}

object MFFSMachineTileEntity {
	val NBT_TAG_FORCE_ENERGY_CURRENT = "FORCE_ENERGY_CURRENT"
	val NBT_TAG_FORCE_ENERGY_CAPACITY = "FORCE_ENERGY_CAPACITY"
}

/**
 * Represents a Tile Entity for any MFFS Machine.
 */
abstract class MFFSMachineTileEntity extends TileEntity with HasForceEnergy {
	val TAG_POWER_LINK_STACK = "POWER_LINK_STACK"

	private var _currentFE: Float = 0
	private var _initialized: Boolean = false

	var powerLinkStack: ItemStack = null

	def getCurrentForceEnergy = PowerMap.getCurrentPower(worldObj.provider.dimensionId, xCoord, yCoord, zCoord)

	def setCurrentForceEnergy(value: Float) {
		var energyVal = Math.max(value, 0)
		energyVal = Math.min(value, getForceEnergyCapacity)

		PowerMap.setCurrentPower(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, energyVal)
	}

	/**
	 * Performs redstone-based activation.
	 */
	override def updateEntity() {
		if(worldObj.isRemote)
			return

		if(!_initialized)
			initialize()

		// Check the redstone signal.
		val currentlyActive = isActive
		if(!currentlyActive && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0)
			setActive(active = true)
		else if(currentlyActive && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) == 0)
			setActive(active = false)
	}

	/**
	 * Reads the state of the Tile Entity from an NBT Tag Compound.
	 */
	override def readFromNBT(tagCompound: NBTTagCompound) {
		super.readFromNBT(tagCompound)

		_currentFE = tagCompound.getFloat(MFFSMachineTileEntity.NBT_TAG_FORCE_ENERGY_CURRENT)

		val powerLinkStackTag = tagCompound.getCompoundTag(TAG_POWER_LINK_STACK)
		if(powerLinkStackTag != null)
			powerLinkStack = ItemStack.loadItemStackFromNBT(powerLinkStackTag)
	}

	override def writeToNBT(tagCompound: NBTTagCompound) {
		super.writeToNBT(tagCompound)

		tagCompound.setFloat(MFFSMachineTileEntity.NBT_TAG_FORCE_ENERGY_CURRENT, getCurrentForceEnergy)

		if(powerLinkStack != null) {
			val powerLinkStackTag = new NBTTagCompound
			powerLinkStack.writeToNBT(powerLinkStackTag)

			tagCompound.setTag(TAG_POWER_LINK_STACK, powerLinkStackTag)
		}
	}

	def initialize() {
		PowerMap.setCurrentPower(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, _currentFE)

		if(powerLinkStack != null && powerLinkStack.getItem == ItemMFFSCard && ItemMFFSCard.getCardType
			(powerLinkStack) == CardType.PowerLink)
			PowerMap.incNumLinks(PositionalCardData.getTileEntityAtLocation(powerLinkStack))

		_initialized = true
	}

	/**
	 * Activates or deactivates the machine.
	 */
	def setActive(active: Boolean) {
		var metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord)

		if(active)
			metadata |= MFFSMachineBlock.ACTIVE_MASK
		else
			metadata &= MFFSMachineBlock.ROTATION_MASK

		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2)
	}

	/**
	 * @return Whether or not the machine is active, based on metadata.
	 */
	def isActive: Boolean = {
		val metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord)

		(metadata & MFFSMachineBlock.ACTIVE_MASK) == MFFSMachineBlock.ACTIVE_MASK
	}
}
