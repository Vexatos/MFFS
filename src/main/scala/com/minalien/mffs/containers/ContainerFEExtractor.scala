package com.minalien.mffs.containers

import net.minecraft.inventory.{Slot, Container}
import net.minecraft.entity.player.{InventoryPlayer, EntityPlayer}
import com.minalien.mffs.tiles.TileEntityFEExtractor
import net.minecraft.item.ItemStack

/**
 * Container behind the Force Energy Extractor.
 */
class ContainerFEExtractor(tileEntity: TileEntityFEExtractor, playerInventory: InventoryPlayer) extends Container {
	final val SLOT_FORCICIUM = 0
	final val SLOT_POWER_LINK = 1

	final val INVENTORY_SLOT_COUNT = 2

	// Bind the Container Inventory
	addSlotToContainer(new Slot(tileEntity, SLOT_FORCICIUM, 44, 31))
	addSlotToContainer(new Slot(tileEntity, SLOT_POWER_LINK, 154, 6))

	// Bind the Player Inventory
	for(y <- 0 to 2) {
		for(x <- 0 to 8) {
			addSlotToContainer(new Slot(playerInventory, x + (y * 9) + 9, 8 + (x * 18), 84 + (y * 18)))
		}
	}

	// Bind the Player's Hotbar
	for(x <- 0 to 8)
		addSlotToContainer(new Slot(playerInventory, x, 8 + (x * 18), 142))

	override def canInteractWith(player: EntityPlayer): Boolean = tileEntity.isUseableByPlayer(player)

	override def transferStackInSlot(player: EntityPlayer, slot: Int): ItemStack = {
		var stack: ItemStack = null
		val slotObj = inventorySlots.get(slot).asInstanceOf[Slot]

		if(slotObj != null && slotObj.getHasStack) {
			val stackInSlot = slotObj.getStack
			stack = stackInSlot.copy()

			// Container -> Player Inventory
			if(slot < INVENTORY_SLOT_COUNT) {
				if(!mergeItemStack(stackInSlot, 0, 35, true))
					return null
			}
			else if(!mergeItemStack(stackInSlot, 0, INVENTORY_SLOT_COUNT, false))
				return null

			if(stackInSlot.stackSize == 0)
				slotObj.putStack(null)
			else
				slotObj.onSlotChanged()

			if(stackInSlot.stackSize == stack.stackSize)
				return null

			slotObj.onPickupFromSlot(player, stackInSlot)
		}

		stack
	}
}
