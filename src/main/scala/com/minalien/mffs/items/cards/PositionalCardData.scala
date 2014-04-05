package com.minalien.mffs.items.cards

import net.minecraft.item.ItemStack
import com.minalien.mffs.util.NBTTagCompoundHelper
import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.items.{CardType, ItemMFFSCard}
import net.minecraftforge.common.DimensionManager

object PositionalCardData {
	val TAG_DIMENSION_ID = "POSITIONAL_CARD_DIMENSION_ID"
	val TAG_X_COORD = "POSITIONAL_CARD_X_COORD"
	val TAG_Y_COORD = "POSITIONAL_CARD_Y_COORD"
	val TAG_Z_COORD = "POSITIONAL_CARD_Z_COORD"

	def getTileEntityAtLocation(itemStack: ItemStack): TileEntity = {
		if(itemStack.getItem == ItemMFFSCard) {
			val cardData = PositionalCardData.fromItemStack(itemStack)
			val world = DimensionManager.getWorld(cardData.dimensionId)

			if(world != null)
				world.getTileEntity(cardData.xCoord, cardData.yCoord, cardData.zCoord)
			else
				null
		}
		else
			null
	}

	def fromItemStack(itemStack: ItemStack): PositionalCardData = {
		val tagCompound = NBTTagCompoundHelper.getTagFromItemStack(itemStack)

		val dimensionId = tagCompound.getInteger(TAG_DIMENSION_ID)
		val xCoord = tagCompound.getInteger(TAG_X_COORD)
		val yCoord = tagCompound.getInteger(TAG_Y_COORD)
		val zCoord = tagCompound.getInteger(TAG_Z_COORD)

		new PositionalCardData(dimensionId, xCoord, yCoord, zCoord)
	}
}

/**
 * Utility class for cards containing positional data.
 */
class PositionalCardData(var dimensionId: Int, var xCoord: Int, var yCoord: Int, var zCoord: Int) {
	def saveToItemStack(itemStack: ItemStack) {
		val tagCompound = NBTTagCompoundHelper.getTagFromItemStack(itemStack)

		tagCompound.setInteger(PositionalCardData.TAG_DIMENSION_ID, dimensionId)
		tagCompound.setInteger(PositionalCardData.TAG_X_COORD, xCoord)
		tagCompound.setInteger(PositionalCardData.TAG_Y_COORD, yCoord)
		tagCompound.setInteger(PositionalCardData.TAG_Z_COORD, zCoord)
	}
}
