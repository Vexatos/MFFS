package com.minalien.mffs.items

import net.minecraft.util.IIcon
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{ItemStack, Item}
import net.minecraft.client.renderer.texture.IIconRegister
import com.minalien.mffs.ModularForcefieldSystem
import com.minalien.mffs.items.CardType.CardType
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import com.minalien.mffs.tiles.{TileEntityFECapacitor, MFFSMachineTileEntity}
import com.minalien.mffs.items.cards.PositionalCardData
import net.minecraftforge.common.DimensionManager
import com.minalien.mffs.util.NBTTagCompoundHelper
import com.minalien.mffs.power.PowerMap

object CardType extends Enumeration {
	type CardType = Value

	val Blank, PowerLink, InfinitePower = Value
}

/**
 * MFFS Card System
 */
object ItemMFFSCard extends MFFSItem("mffscard") {
	setMaxStackSize(16)
	setHasSubtypes(true)

	private val icons: Array[IIcon] = new Array[IIcon](CardType.maxId)

	override def getSubItems(item: Item, creativeTab: CreativeTabs, subItems: java.util.List[_]) {
		val subItemList = subItems.asInstanceOf[java.util.List[ItemStack]]

		var tempCard: ItemStack = new ItemStack(ItemMFFSCard, 1, 0)
		subItemList.add(tempCard)

		tempCard = new ItemStack(ItemMFFSCard, 1, CardType.InfinitePower.id)
		subItemList.add(tempCard)
	}

	override def registerIcons(iconRegister: IIconRegister) {
		for(cardType: CardType <- CardType.values)
			icons(cardType.id) = iconRegister.registerIcon(s"${ModularForcefieldSystem.ASSET_DIR}:cards/$cardType")
	}

	override def getIconFromDamage(damage: Int): IIcon = {
		if(damage >= icons.length || damage < 0)
			return icons(0)

		icons(damage)
	}

	override def onItemUseFirst(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int,
	                            side: Int, nX: Float, nY: Float, nZ: Float): Boolean = {
		val cardType = getCardType(itemStack)

		cardType match {
			case CardType.Blank =>
				val tileEntity = world.getTileEntity(x, y, z).asInstanceOf[MFFSMachineTileEntity]

				if(tileEntity == null)
					return false

				if(tileEntity.isInstanceOf[TileEntityFECapacitor]) {
					val cardData = new PositionalCardData(world.provider.dimensionId, x, y, z)

					if(itemStack.stackSize == 1 && !player.capabilities.isCreativeMode) {
						itemStack.setItemDamage(CardType.PowerLink.id)
						cardData.saveToItemStack(itemStack)
					}
					else {
						val cardStack = new ItemStack(ItemMFFSCard, 1, CardType.PowerLink.id)
						cardData.saveToItemStack(cardStack)

						if(!player.capabilities.isCreativeMode)
							itemStack.stackSize -= 1

						if(!player.inventory.addItemStackToInventory(cardStack))
							player.dropPlayerItemWithRandomChoice(cardStack, false)
					}
				}

				!world.isRemote

			case _ =>
				false
		}
	}

	override def addInformation(itemStack: ItemStack, player: EntityPlayer, infoList: java.util.List[_],
	                            showAdvancedTooltips: Boolean) {
		val infoListAsString: java.util.List[String] = infoList.asInstanceOf[java.util.List[String]]
		val cardType: CardType = getCardType(itemStack)

		infoListAsString.add(s"Type: [§2$cardType§r]")

		cardType match {
			// Cards with positional data.
			case CardType.PowerLink =>
				val cardData: PositionalCardData = new PositionalCardData(0, 0, 0, 0)

				cardData.fromItemStack(itemStack)

				val isInDimension: Boolean = player.worldObj.provider.dimensionId == cardData.dimensionId
				val dimName: String = DimensionManager.getProvider(cardData.dimensionId).getDimensionName

				infoListAsString.add(s"Linked to: (§a${cardData.xCoord}§r,§a${cardData.yCoord}§r," +
					s"§a${cardData.zCoord}§r)")

				infoListAsString.add(s"Dim: §${if(isInDimension) "a" else "c" }$dimName§r")

				infoListAsString.add(s"§${if(PowerMap.isTileValid(cardData.dimensionId, cardData.xCoord,
					cardData.yCoord, cardData.zCoord)) "aValid" else "cInvalid" }")

				return

			// Default
			case _ => return
		}
	}

	def getCardType(itemStack: ItemStack): CardType = CardType(itemStack.getItemDamage)
}
