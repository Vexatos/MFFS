package com.minalien.mffs.power

import scala.collection.mutable
import com.minalien.mffs.network.{NetworkUtil, PacketBuilder}
import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.ModularForcefieldSystem

/**
 * Stores current power stored in objects within the world.
 */
object PowerMap {
	/**
	 * (DimensionID, X, Y, Z)
	 */
	private val powerMap = new mutable.HashMap[(Int, Int, Int, Int), Float]()

	def hasEntryAtPoint(dimensionId: Int, x: Int, y: Int, z: Int): Boolean = powerMap contains (dimensionId, x, y, z)

	def getCurrentPower(dimensionId: Int, x: Int, y: Int, z: Int): Float = {
		if(powerMap contains (dimensionId, x, y, z))
			return (powerMap get (dimensionId, x, y, z)).get

		-1f
	}

	def setCurrentPower(tileEntity: TileEntity, currentEnergy: Float) {
		if(tileEntity != null && tileEntity.getWorldObj.provider != null) {
			setCurrentPower(tileEntity.getWorldObj.provider.dimensionId, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, currentEnergy)
		}
	}

	def setCurrentPower(dimensionId: Int, x: Int, y: Int, z: Int, currentEnergy: Float) {
		if(powerMap contains (dimensionId, x, y, z)) {
			powerMap((dimensionId, x, y, z)) = currentEnergy
		}

		powerMap += (dimensionId, x, y, z) -> currentEnergy

		if(ModularForcefieldSystem.proxy.isServer) {
			// Send an update packet.
			val energyPacket = PacketBuilder.BuildTileEnergyUpdatePacket(dimensionId, x, y, z, currentEnergy)
			NetworkUtil.sendToAll(energyPacket)
		}
	}
}
