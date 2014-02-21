package com.minalien.mffs.network

import io.netty.buffer.ByteBuf
import com.minalien.mffs.ModularForcefieldSystem
import cpw.mods.fml.common.FMLLog
import com.minalien.mffs.power.PowerMap

/**
 * Updates a Tile Entity's Link Count
 */
class TileLinksUpdatePacket extends MFFSPacket {
	var dimensionId: Int = 0
	var x: Int = 0
	var y: Int = 0
	var z: Int = 0
	var numLinks: Int = 0

	override def read(data: ByteBuf) {
		if(ModularForcefieldSystem.proxy.isServer) {
			FMLLog.warning("Error: Received a TileLinksUpdatePacket from the client!")
			return
		}

		dimensionId = data.readInt()
		x = data.readInt()
		y = data.readInt()
		z = data.readInt()
		numLinks = data.readInt()

		PowerMap.setNumLinks(dimensionId, x, y, z, numLinks)
	}

	override def write(data: ByteBuf) {
		data.writeInt(dimensionId)
		data.writeInt(x)
		data.writeInt(y)
		data.writeInt(z)
		data.writeInt(numLinks)
	}
}
