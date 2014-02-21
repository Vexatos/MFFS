package com.minalien.mffs.tiles

import net.minecraft.nbt.NBTTagCompound

/**
 * Tile Entity responsible for the Force Energy Capacitor.
 */
class TileEntityFECapacitor extends MFFSMachineTileEntity {
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
}
