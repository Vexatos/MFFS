package com.minalien.mffs.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import com.minalien.mffs.ModularForcefieldSystem

/**
 * Forcicium Storage Block.
 */
object BlockForciciumBlock extends Block(Material.rock) {
	setCreativeTab(ModularForcefieldSystem.creativeTab)
	setHardness(3f)
	setResistance(5f)
	setBlockName("forciciumBlock")
	setHarvestLevel("pickaxe", 2)

	/**
	 * Registers the block's texture.
	 */
	override def registerBlockIcons(iconRegister: IIconRegister) = {
		blockIcon = iconRegister.registerIcon(s"${ModularForcefieldSystem.ASSET_DIR}:$getUnlocalizedName")
	}
}
