package com.minalien.mffs.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import com.minalien.mffs.ModularForcefieldSystem

/**
 * Monazit Ore Block.
 */
object BlockMonazitOre extends Block(Material.rock) {
	setCreativeTab(ModularForcefieldSystem.creativeTab)
	setHardness(3f)
	setResistance(5f)
	setBlockName("monazitOre")
	setHarvestLevel("pickaxe", 2)

	/**
	 * Registers the block's texture.
	 */
	override def registerBlockIcons(iconRegister: IIconRegister) = {
		blockIcon = iconRegister.registerIcon(s"${ModularForcefieldSystem.ASSET_DIR}:$getUnlocalizedName")
	}
}
