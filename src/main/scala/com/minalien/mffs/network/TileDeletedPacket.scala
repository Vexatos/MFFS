package com.minalien.mffs.network

import io.netty.buffer.ByteBuf
import com.minalien.mffs.ModularForcefieldSystem
import com.minalien.mffs.power.PowerMap

/**
 * Deletes a tile's data from PowerMap.
 */
class TileDeletedPacket extends MFFSPacket {
	var dimensionId: Int = 0
	var x: Int = 0
	var y: Int = 0
	var z: Int = 0

	override def read(data: ByteBuf) {
		if(ModularForcefieldSystem.proxy.isServer)
			return

		dimensionId = data.readInt()
		x = data.readInt()
		y = data.readInt()
		z = data.readInt()

		PowerMap.deleteTile(dimensionId, x, y, z)
	}

	override def write(data: ByteBuf) {
		data.writeInt(dimensionId)
		data.writeInt(x)
		data.writeInt(y)
		data.writeInt(z)
	}
}
