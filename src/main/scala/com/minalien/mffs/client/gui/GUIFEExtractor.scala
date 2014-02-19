package com.minalien.mffs.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import com.minalien.mffs.tiles.TileEntityFEExtractor
import com.minalien.mffs.containers.ContainerFEExtractor
import net.minecraft.entity.player.InventoryPlayer
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation
import com.minalien.mffs.ModularForcefieldSystem

/**
 * GUI Container for the Force Energy Extractor.
 */
class GUIFEExtractor(tileEntity: TileEntityFEExtractor, playerInventory: InventoryPlayer)
		extends GuiContainer(new ContainerFEExtractor(tileEntity, playerInventory)) {
	override def drawGuiContainerBackgroundLayer(f: Float, i1: Int, i2: Int) {
		GL11.glColor4f(1f, 1f, 1f, 1f)
		mc.renderEngine.bindTexture(new ResourceLocation(s"${ModularForcefieldSystem.ASSET_DIR}:textures/gui/Extractor.png"))
		val x = (width - xSize) / 2
		val y = (height - ySize) / 2

		// Render the background.
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize)

		// Render the current extraction progress.

		// Render the energy buffer.
		val bufferWidth = 16
		val bufferHeight = 52
		val bufferX = 116
		val bufferY = 13
		val storedEnergyHeight = ((tileEntity.currentForceEnergy / tileEntity.getForceEnergyCapacity) * bufferHeight
			.asInstanceOf[Float]).asInstanceOf[Int]
		val storedBufferX = 177
		val storedBufferY = 1 + bufferHeight - storedEnergyHeight
		drawTexturedModalRect(x + bufferX, y + bufferY + storedBufferY - 1, storedBufferX, storedBufferY, bufferWidth,
			bufferHeight - storedBufferY + 1)
	}
}
