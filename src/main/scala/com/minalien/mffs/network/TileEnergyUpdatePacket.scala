package com.minalien.mffs.network

import io.netty.buffer.ByteBuf
import com.minalien.mffs.api.HasForceEnergy
import cpw.mods.fml.common.FMLLog
import net.minecraftforge.common.DimensionManager

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
		dimensionId = data.readInt()
		x = data.readInt()
		y = data.readInt()
		z = data.readInt()
		storedEnergy = data.readFloat()

		val tileEntity = DimensionManager.getWorld(dimensionId).getTileEntity(x, y, z).asInstanceOf[HasForceEnergy]
		if(tileEntity == null) {
			FMLLog.warning(s"Received MFFS:TileEnergyUpdatePacket for a TileEntity that does not exist!")
			return
		}

		tileEntity.setCurrentForceEnergy(storedEnergy)
	}

	override def write(data: ByteBuf) {
		data.writeInt(dimensionId)
		data.writeInt(x)
		data.writeInt(y)
		data.writeInt(z)
		data.writeFloat(storedEnergy)
	}
}
