package com.minalien.mffs.network

import com.minalien.mffs.tiles.MFFSMachineTileEntity
import com.minalien.mffs.power.PowerMap

/**
 * Packet Builder
 */
object PacketBuilder {
	def buildTileEnergyUpdatePacket(machineTile: MFFSMachineTileEntity): TileEnergyUpdatePacket =
		buildTileEnergyUpdatePacket(machineTile.getWorldObj.provider.dimensionId, machineTile.xCoord,
			machineTile.yCoord, machineTile.zCoord, machineTile.getCurrentForceEnergy)

	def buildTileEnergyUpdatePacket(dimensionId: Int, x: Int, y: Int, z: Int, energy: Float): TileEnergyUpdatePacket = {
		val packet = new TileEnergyUpdatePacket

		packet.dimensionId = dimensionId
		packet.x = x
		packet.y = y
		packet.z = z
		packet.storedEnergy = energy

		packet
	}

	def buildTileLinksUpdatePacket(machineTile: MFFSMachineTileEntity): TileLinksUpdatePacket = buildTileLinksUpdatePacket(machineTile.getWorldObj.provider.dimensionId, machineTile.xCoord,
			machineTile.yCoord, machineTile.zCoord, PowerMap.getNumLinks(machineTile))

	def buildTileLinksUpdatePacket(dimensionId: Int, x: Int, y: Int, z: Int, numLinks: Int): TileLinksUpdatePacket = {
		val packet = new TileLinksUpdatePacket

		packet.dimensionId = dimensionId
		packet.x = x
		packet.y = y
		packet.z = z
		packet.numLinks = numLinks

		packet
	}

	def buildTileDeletedPacket(dimensionId: Int, x: Int, y: Int, z: Int): TileDeletedPacket = {
		val packet = new TileDeletedPacket

		packet.dimensionId = dimensionId
		packet.x = x
		packet.y = y
		packet.z = z

		packet
	}
}
