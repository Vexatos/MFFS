package com.minalien.mffs.tiles

import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.blocks.MFFSMachineBlock
import net.minecraft.nbt.NBTTagCompound

object MFFSMachineTileEntity {
	val NBT_TAG_FORCE_ENERGY_CURRENT = "FORCE_ENERGY_CURRENT"
	val NBT_TAG_FORCE_ENERGY_CAPACITY = "FORCE_ENERGY_CAPACITY"
}

/**
 * Represents a Tile Entity for any MFFS Machine.
 */
class MFFSMachineTileEntity extends TileEntity {
	/**
	 * Dictates the amount of Force Energy currently stored in the internal buffer.
	 */
	var currentForceEnergy: Float = 0.0f

	/**
	 * Performs redstone-based activation.
	 */
	override def updateEntity() {
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

		currentForceEnergy = tagCompound.getFloat(MFFSMachineTileEntity.NBT_TAG_FORCE_ENERGY_CURRENT)
	}

	override def writeToNBT(tagCompound: NBTTagCompound) {
		super.writeToNBT(tagCompound)

		tagCompound.setFloat(MFFSMachineTileEntity.NBT_TAG_FORCE_ENERGY_CURRENT, currentForceEnergy)
	}

	/**
	 * @return The amount of Force Energy capable of being stored in the internal buffer.
	 */
	def getForceEnergyCapacity: Float = 0.0f

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
