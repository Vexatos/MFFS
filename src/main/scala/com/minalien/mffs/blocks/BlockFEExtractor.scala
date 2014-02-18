package com.minalien.mffs.blocks

import net.minecraft.world.World
import com.minalien.mffs.tiles.TileEntityFEExtractor

/**
 * Force Energy Extractor
 */
object BlockFEExtractor extends MFFSMachineBlock("feextractor") {
	isRotationSensitive = true

	override def tileEntityClass = classOf[TileEntityFEExtractor]

	override def createNewTileEntity(world: World, metadata: Int) = new TileEntityFEExtractor
}
