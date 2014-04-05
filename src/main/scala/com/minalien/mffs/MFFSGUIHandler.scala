package com.minalien.mffs

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 * Handles GUIs for MFFS.
 */
object MFFSGUIHandler extends IGuiHandler {
	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case _ =>
				null
		}
	}

	override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
		val tileEntity = world.getTileEntity(x, y, z)

		tileEntity match {
			case _ =>
				null
		}
	}
}
