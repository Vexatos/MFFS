package com.minalien.mffs.network

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec
import io.netty.channel.ChannelHandlerContext
import io.netty.buffer.ByteBuf

/**
 * Handles all packets on the MFFS Channel.
 */
object MFFSPacketHandler extends FMLIndexedMessageToMessageCodec[MFFSPacket] {
	override def encodeInto(channelHandlerContext: ChannelHandlerContext, packet: MFFSPacket, data: ByteBuf) {
		packet.write(data)
	}

	override def decodeInto(channelHandlerContext: ChannelHandlerContext, data: ByteBuf, packet: MFFSPacket) {
		// Read Packet Data
		packet.read(data)

		try {

		}
		catch {
			case ex: Exception =>
				ex.printStackTrace()
		}
	}
}
