package com.minalien.mffs.network

import com.minalien.mffs.tiles.MFFSMachineTileEntity

/**
 * Packet Builder
 */
object PacketBuilder {
	def BuildTileEnergyUpdatePacket(machineTile: MFFSMachineTileEntity): TileEnergyUpdatePacket = {
		val packet = new TileEnergyUpdatePacket

		packet.dimensionId = machineTile.getWorldObj.provider.dimensionId
		packet.x = machineTile.xCoord
		packet.y = machineTile.yCoord
		packet.z = machineTile.zCoord
		packet.storedEnergy = machineTile.getCurrentForceEnergy

		packet
	}
}
