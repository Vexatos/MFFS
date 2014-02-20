package com.minalien.mffs.network

import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.FMLLog
import net.minecraftforge.common.DimensionManager
import com.minalien.mffs.power.PowerMap
import com.minalien.mffs.ModularForcefieldSystem

/**
 * Updates a Tile Entity's Stored & Capacity for Force Energy.
 */
class TileEnergyUpdatePacket extends MFFSPacket {
	var dimensionId: Int = 0
	var x: Int = 0
	var y: Int = 0
	var z: Int = 0
	var storedEnergy: Float = 0f

	override def read(data: ByteBuf) {
		if(ModularForcefieldSystem.proxy.isServer) {
			FMLLog.warning("Error: Received a TileEnergyUpdatePacket from the client!")
			return
		}

		dimensionId = data.readInt()
		x = data.readInt()
		y = data.readInt()
		z = data.readInt()
		storedEnergy = data.readFloat()

		PowerMap.setCurrentPower(dimensionId, x, y, z, storedEnergy)

	}

	override def write(data: ByteBuf) {
		data.writeInt(dimensionId)
		data.writeInt(x)
		data.writeInt(y)
		data.writeInt(z)
		data.writeFloat(storedEnergy)
	}
}
