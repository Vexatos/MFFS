package com.minalien.mffs.blocks

import net.minecraft.world.World
import com.minalien.mffs.tiles.TileEntityFEExtractor
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.util.ForgeDirection

/**
 * Force Energy Extractor
 */
object BlockFEExtractor extends MFFSMachineBlock("feextractor") {
	isRotationSensitive = true

	override def tileEntityClass = classOf[TileEntityFEExtractor]

	override def createNewTileEntity(world: World, metadata: Int) = new TileEntityFEExtractor

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, nX: Float,
	                              nY: Float, nZ: Float): Boolean = {
		rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))
	}
}
