package com.minalien.mffs.items.cards

import net.minecraft.item.ItemStack
import com.minalien.mffs.util.NBTTagCompoundHelper
import net.minecraft.tileentity.TileEntity
import com.minalien.mffs.items.{CardType, ItemMFFSCard}
import net.minecraftforge.common.DimensionManager

object PositionalCardData {
	def getTileEntityAtLocation(itemStack: ItemStack): TileEntity = {
		if(itemStack.getItem == ItemMFFSCard && ItemMFFSCard.getCardType(itemStack) == CardType.PowerLink) {
			val cardData = new PositionalCardData(0, 0, 0, 0)
			cardData.fromItemStack(itemStack)
			val world = DimensionManager.getWorld(cardData.dimensionId)

			if(world != null)
				world.getTileEntity(cardData.xCoord, cardData.yCoord, cardData.zCoord)
			else
				null
		}
		else
			null
	}
}

/**
 * Utility class for cards containing positional data.
 */
class PositionalCardData(var dimensionId: Int, var xCoord: Int, var yCoord: Int, var zCoord: Int) {
	val TAG_DIMENSION_ID = "POSITIONAL_CARD_DIMENSION_ID"
	val TAG_X_COORD = "POSITIONAL_CARD_X_COORD"
	val TAG_Y_COORD = "POSITIONAL_CARD_Y_COORD"
	val TAG_Z_COORD = "POSITIONAL_CARD_Z_COORD"

	def fromItemStack(itemStack: ItemStack) {
		val tagCompound = NBTTagCompoundHelper.getTagFromItemStack(itemStack)

		dimensionId = tagCompound.getInteger(TAG_DIMENSION_ID)
		xCoord = tagCompound.getInteger(TAG_X_COORD)
		yCoord = tagCompound.getInteger(TAG_Y_COORD)
		zCoord = tagCompound.getInteger(TAG_Z_COORD)
	}

	def saveToItemStack(itemStack: ItemStack) {
		val tagCompound = NBTTagCompoundHelper.getTagFromItemStack(itemStack)

		tagCompound.setInteger(TAG_DIMENSION_ID, dimensionId)
		tagCompound.setInteger(TAG_X_COORD, xCoord)
		tagCompound.setInteger(TAG_Y_COORD, yCoord)
		tagCompound.setInteger(TAG_Z_COORD, zCoord)
	}
}
