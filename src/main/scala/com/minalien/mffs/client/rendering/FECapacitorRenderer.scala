package com.minalien.mffs.client.rendering

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.tiles.TileEntityFECapacitor
import net.minecraftforge.common.util.ForgeDirection
import com.minalien.mffs.blocks.MFFSMachineBlock
import org.lwjgl.opengl.GL11

/**
 * TESR responsible for rendering the Force Energy Capacitor.
 */
object FECapacitorRenderer extends TileEntitySpecialRenderer {
	override def renderTileEntityAt(tileEntity: TileEntity, x: Double, y: Double, z: Double, w: Float) {
		if(!tileEntity.isInstanceOf[TileEntityFECapacitor])
			return

		val tile = tileEntity.asInstanceOf[TileEntityFECapacitor]
		val metadata = tileEntity.getWorldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)
		val direction = ForgeDirection.getOrientation(metadata & MFFSMachineBlock.ROTATION_MASK)

		GL11.glPushMatrix()

		GL11.glPolygonOffset(-10, -10)
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL)
		GL11.glDisable(GL11.GL_LIGHTING)
		GL11.glTranslatef(x.asInstanceOf[Float], y.asInstanceOf[Float], z.asInstanceOf[Float])

		val displayOffset = 1.0f / 16.0f
		val displaySize = 1 - (2.0f / 16.0f)

		direction match {
			case ForgeDirection.DOWN =>
				GL11.glTranslatef(1, 1, 0)
				GL11.glRotatef(180, 1, 0, 0)
				GL11.glRotatef(180, 0, 1, 0)

			case ForgeDirection.SOUTH =>
				GL11.glTranslatef(0, 1, 0)
				GL11.glRotatef(90, 1, 0, 0)

			case ForgeDirection.NORTH =>
				GL11.glTranslatef(1, 1, 1)
				GL11.glRotatef(180, 0, 1, 0)
				GL11.glRotatef(90, 1, 0, 0)

			case ForgeDirection.EAST =>
				GL11.glTranslatef(0, 1, 1)
				GL11.glRotatef(90, 0, 1, 0)
				GL11.glRotatef(90, 1, 0, 0)

			case ForgeDirection.WEST =>
				GL11.glTranslatef(1, 1, 0)
				GL11.glRotatef(-90, 0, 1, 0)
				GL11.glRotatef(90, 1, 0, 0)

			case _ =>
		}

		GL11.glTranslatef(displayOffset + (displaySize / 2.0f), 1f, displayOffset + (displaySize / 2.0f))
		GL11.glRotatef(-90, 1, 0, 0)
		GL11.glColor4f(1f, 1f, 1f, 1f)

		val fontRenderer = this.func_147498_b()
		val header = "FE Capacitor"
		val maxWidth = Math.max(fontRenderer.getStringWidth(header), 1) + 4
		val lineHeight = fontRenderer.FONT_HEIGHT + 2

		val scaleX = displaySize / maxWidth
		val scaleY = displaySize / lineHeight
		val scale = Math.min(scaleX, scaleY)
		GL11.glScalef(scale, -scale, scale)
		GL11.glDepthMask(false)

		var offsetX: Int = 0
		var offsetY: Int = 0

		val realSize = Math.floor(displaySize / scale).asInstanceOf[Int]

		if(scaleX < scaleY) {
			offsetX = 2
			offsetY = (realSize - lineHeight) / 2
		}
		else {
			offsetX = (realSize - maxWidth) / 2
			offsetY = 0
		}

		fontRenderer.drawString(header, offsetX - (realSize / 2), offsetY - (realSize / 2) + (-2 *
			lineHeight) - 1, 1)

		val forceEnergy = tile.getCurrentForceEnergy
		val feCapacity = tile.getForceEnergyCapacity
		fontRenderer.drawString(s"FE: ${((forceEnergy / feCapacity) * 100).asInstanceOf[Int]}%", offsetX - (realSize / 2), offsetY - (realSize / 2) +
			lineHeight, 1)
		fontRenderer.drawString(s"$forceEnergy", offsetX - (realSize / 2), offsetY - (realSize / 2) + (2 *
			lineHeight), 1)
		fontRenderer.drawString(s"/$feCapacity", offsetX - (realSize / 2), offsetY - (realSize / 2) + (3 *
			lineHeight), 1)

		GL11.glDepthMask(true)
		GL11.glEnable(GL11.GL_LIGHTING)
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL)

		GL11.glPopMatrix()
	}
}
