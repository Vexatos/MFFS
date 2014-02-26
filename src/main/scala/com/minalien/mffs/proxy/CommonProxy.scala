package com.minalien.mffs.proxy

import net.minecraftforge.common.DimensionManager


/**
 * Functionality common to both Server & Client.
 */
class CommonProxy {
	def registerRenderers() {}

	def isServer = !DimensionManager.getWorld(0).isRemote
}
