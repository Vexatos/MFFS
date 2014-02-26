package com.minalien.mffs

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.minalien.mffs.tiles.{TileEntityFECapacitor, TileEntityFEExtractor}
import com.minalien.mffs.containers.{ContainerFECapacitor, ContainerFEExtractor}
import com.minalien.mffs.client.gui.{GUIFECapacitor, GUIFEExtractor}

/**
 * Handles GUIs for MFFS.
 */
object MFFSGUIHandler extends IGuiHandler {
	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case extractor: TileEntityFEExtractor =>
				new GUIFEExtractor(extractor, player.inventory)

			case capacitor: TileEntityFECapacitor =>
				new GUIFECapacitor(capacitor, player.inventory)

			case _ =>
				null
		}
	}

	override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case extractor: TileEntityFEExtractor =>
				new ContainerFEExtractor(extractor, player.inventory)

			case capacitor: TileEntityFECapacitor =>
				new ContainerFECapacitor(capacitor, player.inventory)

			case _ =>
				null
		}
	}
}
