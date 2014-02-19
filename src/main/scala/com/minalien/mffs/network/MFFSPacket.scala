package com.minalien.mffs.network

import io.netty.buffer.ByteBuf

/**
 * Represents the base MFFS Packet.
 */
abstract class MFFSPacket {
	def read(data: ByteBuf)

	def write(data: ByteBuf)
}
