package com.minalien.mffs.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * Provides utility functions related to NBT Tags.
 *
 * @author Thunderdark
 */
object NBTTagCompoundHelper {
	/**
	 * Retrieves the NBTTagCompound from the item stack. If the stack does not yet have one, one is created for it.
	 *
	 * @param itemStack Item stack whose tag compound should be set/retrieved
	 * @return          NBTTagCompound associated with the item stack.
	 */
	def getTagFromItemStack(itemStack: ItemStack): NBTTagCompound = {
		var compound = itemStack.getTagCompound

		if(compound == null) {
			compound = new NBTTagCompound
			itemStack.setTagCompound(compound)
		}

		compound
	}
}