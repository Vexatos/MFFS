package com.minalien.mffs.proxy

import cpw.mods.fml.client.registry.ClientRegistry
import com.minalien.mffs.tiles.{TileEntityFECapacitor, TileEntityFEExtractor}
import com.minalien.mffs.client.rendering.{FECapacitorRenderer, FEExtractorRenderer}

/**
 * Functionality executed only client-side.
 */
class ClientProxy extends CommonProxy {
	override def registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityFEExtractor], FEExtractorRenderer)
		ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityFECapacitor], FECapacitorRenderer)
	}
}
