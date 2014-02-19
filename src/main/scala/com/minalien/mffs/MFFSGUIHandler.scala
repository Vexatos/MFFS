package com.minalien.mffs

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.minalien.mffs.tiles.TileEntityFEExtractor
import com.minalien.mffs.containers.ContainerFEExtractor
import com.minalien.mffs.client.gui.GUIFEExtractor

/**
 * Handles GUIs for MFFS.
 */
object MFFSGUIHandler extends IGuiHandler {
	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case te: TileEntityFEExtractor =>
				return new GUIFEExtractor(tileEntity.asInstanceOf[TileEntityFEExtractor], player.inventory)
		}

		null
	}

	override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case te: TileEntityFEExtractor =>
				return new ContainerFEExtractor(tileEntity.asInstanceOf[TileEntityFEExtractor], player.inventory)
		}

		null
	}
}
