package com.minalien.mffs.tiles

import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.blocks.MFFSMachineBlock

/**
 * Represents a Tile Entity for any MFFS Machine.
 */
class MFFSMachineTileEntity extends TileEntity {
	/**
	 * Performs redstone-based activation.
	 */
	override def updateEntity() {
		// Check the redstone signal.
		val currentlyActive = isActive
		if(!currentlyActive && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0)
			setActive(true)
		else if(currentlyActive && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) == 0)
			setActive(false)
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
