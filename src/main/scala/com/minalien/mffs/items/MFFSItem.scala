package com.minalien.mffs.items

import net.minecraft.item.Item
import net.minecraft.client.renderer.texture.IIconRegister
import com.minalien.mffs.ModularForcefieldSystem

/**
 * Base class for all mod items.
 */
class MFFSItem(unlocalizedName: String) extends Item {
	setCreativeTab(ModularForcefieldSystem.creativeTab)
	setUnlocalizedName(unlocalizedName)

	/**
	 * Registers the item's texture based on its unlocalized name.
	 *
	 * @param   iconRegister Icon register Minecraft will used for stitching the texture atlas.
	 */
	override def registerIcons(iconRegister: IIconRegister) {
		itemIcon = iconRegister.registerIcon(s"${ModularForcefieldSystem.ASSET_DIR}:$getUnlocalizedName")
	}
}
