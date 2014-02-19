package com.minalien.mffs.network

import java.util
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.network.{FMLOutboundHandler, FMLEmbeddedChannel}
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget
import net.minecraft.world.World
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint

/**
 * Provides utility methods for Networking.
 */
object NetworkUtil {
	var channels: util.EnumMap[Side, FMLEmbeddedChannel] = null

	def sendToAll(packet: MFFSPacket) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.ALL)
		channels.get(Side.SERVER).writeOutbound(packet)
	}

	def sendToAllInArea(packet: MFFSPacket, world: World, x: Int, y: Int, z: Int, distance: Int) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.ALLAROUNDPOINT)

		val targetPoint = new TargetPoint(world.provider.dimensionId, x, y, z, distance)
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(targetPoint)
	}

	def sendToPlayer(player: EntityPlayer, packet: MFFSPacket) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.PLAYER)
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player)
		channels.get(Side.SERVER).writeOutbound(packet)
	}

	def sendToServer(packet: MFFSPacket) {
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER)
		channels.get(Side.CLIENT).writeOutbound(packet)
	}
}
