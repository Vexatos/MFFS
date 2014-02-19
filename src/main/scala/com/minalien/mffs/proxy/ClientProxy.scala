package com.minalien.mffs.proxy

import cpw.mods.fml.client.registry.ClientRegistry
import com.minalien.mffs.tiles.TileEntityFEExtractor
import com.minalien.mffs.client.rendering.FEExtractorRenderer

/**
 * Functionality executed only client-side.
 */
class ClientProxy extends CommonProxy {
	override def registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityFEExtractor], FEExtractorRenderer)
	}

	override def isServer = false
}
