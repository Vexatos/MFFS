package com.minalien.mffs.power

import scala.collection.mutable
import com.minalien.mffs.network.{NetworkUtil, PacketBuilder}
import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.ModularForcefieldSystem
import net.minecraftforge.common.DimensionManager

/**
 * Stores current power stored in objects within the world.
 */
object PowerMap {
	class TileData(var numLinks: Int = 0, var currentEnergy: Float = 0f) {
	}

	/**
	 * (DimensionID, X, Y, Z)
	 */
	private val dataMap = new mutable.HashMap[(Int, Int, Int, Int), TileData]()

	def clearData() {
		dataMap.clear()
	}

	def hasEntryAtPoint(dimensionId: Int, x: Int, y: Int, z: Int): Boolean = dataMap contains (dimensionId, x, y, z)

	def getNumLinks(tileEntity: TileEntity): Int = getNumLinks(tileEntity.getWorldObj.provider.dimensionId,
		tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)
	
	def getNumLinks(dimensionId: Int, x: Int, y: Int, z: Int): Int = {
		if(dataMap contains (dimensionId, x, y, z))
			return (dataMap get (dimensionId, x, y, z)).get.numLinks

		0
	}

	def incNumLinks(tileEntity: TileEntity) {
		if(tileEntity != null && tileEntity.getWorldObj.provider != null)
			incNumLinks(tileEntity.getWorldObj.provider.dimensionId, tileEntity.xCoord, tileEntity.yCoord,
				tileEntity.zCoord)
	}

	def incNumLinks(dimensionId: Int, x: Int, y: Int, z: Int) {
		setNumLinks(dimensionId, x, y, z, getNumLinks(dimensionId, x, y, z) + 1)
	}

	def decNumLinks(tileEntity: TileEntity) {
		if(tileEntity != null && tileEntity.getWorldObj.provider != null)
			decNumLinks(tileEntity.getWorldObj.provider.dimensionId, tileEntity.xCoord, tileEntity.yCoord,
				tileEntity.zCoord)
	}

	def decNumLinks(dimensionId: Int, x: Int, y: Int, z: Int) {
		setNumLinks(dimensionId, x, y, z, Math.max(getNumLinks(dimensionId, x, y, z) - 1, 0))
	}

	def setNumLinks(dimensionId: Int, x: Int, y: Int, z: Int, numLinks: Int) {
		if(dataMap contains (dimensionId, x, y, z))
			dataMap((dimensionId, x, y, z)).numLinks = numLinks
		else
			dataMap += (dimensionId, x, y, z) -> new TileData(numLinks, 0f)

		if(ModularForcefieldSystem.proxy.isServer) {
			val linksPacket = PacketBuilder.BuildTileLinksUpdatePacket(dimensionId, x, y, z, numLinks)
			NetworkUtil.sendToAll(linksPacket)
		}
	}

	def getCurrentPower(tileEntity: TileEntity): Float = getCurrentPower(tileEntity.getWorldObj.provider.dimensionId,
		tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)

	def getCurrentPower(dimensionId: Int, x: Int, y: Int, z: Int): Float = {
		if(dataMap contains (dimensionId, x, y, z))
			return (dataMap get (dimensionId, x, y, z)).get.currentEnergy

		-1f
	}

	def setCurrentPower(tileEntity: TileEntity, currentEnergy: Float) {
		if(tileEntity != null && tileEntity.getWorldObj.provider != null)
			setCurrentPower(tileEntity.getWorldObj.provider.dimensionId, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, currentEnergy)
	}

	def setCurrentPower(dimensionId: Int, x: Int, y: Int, z: Int, currentEnergy: Float) {
		if(dataMap contains (dimensionId, x, y, z))
			dataMap((dimensionId, x, y, z)).currentEnergy = currentEnergy
		else
			dataMap += (dimensionId, x, y, z) -> new TileData(0, currentEnergy)

		if(ModularForcefieldSystem.proxy.isServer) {
			// Send an update packet.
			val energyPacket = PacketBuilder.BuildTileEnergyUpdatePacket(dimensionId, x, y, z, currentEnergy)
			NetworkUtil.sendToAll(energyPacket)
		}
	}
}
