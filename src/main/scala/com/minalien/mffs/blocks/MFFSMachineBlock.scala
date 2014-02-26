package com.minalien.mffs.blocks

import net.minecraft.block.{Block, BlockContainer}
import net.minecraft.block.material.Material
import com.minalien.mffs.ModularForcefieldSystem
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraft.world.World
import com.minalien.mffs.power.PowerMap

object MFFSMachineBlock {
	/**
	 * Metadata bit mask used to determine if the machine is currently active.
	 */
	final val ACTIVE_MASK = 0x00000008

	/**
	 * Metadata bit mask used to determine the machine's current rotation.
	 */
	final val ROTATION_MASK = 0x00000007
}

/**
 * Base functionality provided for all Machine blocks.
 */
abstract class MFFSMachineBlock(unlocalizedName: String) extends BlockContainer(Material.iron) {
	setCreativeTab(ModularForcefieldSystem.creativeTab)
	setHardness(3f)
	setResistance(5f)
	setBlockName(unlocalizedName)

	/**
	 * Face icon to use when the machine is currently active.
	 */
	var activeIcon: IIcon = null

	/**
	 * Stores the icons used for the non-face sides of the machine when it is active (1) or inactive (0).
	 */
	var sideIcons: Array[IIcon] = new Array(2)

	/**
	 * Determines whether or not the machine is rotation-sensitive.
	 */
	var isRotationSensitive: Boolean = false

	/**
	 * Registers the block's texture based on its unlocalized name.
	 *
	 * @param   iconRegister Icon register Minecraft will use when stitching the texture atlas.
	 */
	override def registerBlockIcons(iconRegister: IIconRegister) {
		blockIcon = iconRegister.registerIcon(s"${ModularForcefieldSystem
			.ASSET_DIR}:machines/$getUnlocalizedName/Inactive")
		activeIcon = iconRegister.registerIcon(s"${ModularForcefieldSystem
			.ASSET_DIR}:machines/$getUnlocalizedName/Active")

		if(isRotationSensitive) {
			sideIcons(0) = iconRegister.registerIcon(s"${ModularForcefieldSystem
				.ASSET_DIR}:machines/$getUnlocalizedName/SideInactive")
			sideIcons(1) = iconRegister.registerIcon(s"${ModularForcefieldSystem
				.ASSET_DIR}:machines/$getUnlocalizedName/SideActive")
		}
	}

	/**
	 * Retrieves the icon for the given side based on metadata, rotation and active state.
	 *
	 * @param side      Side of the block the icon is being retrieved for.
	 * @param metadata  Metadata for the current block.
	 */
	override def getIcon(side: Int, metadata: Int): IIcon = {
		if(isRotationSensitive && side != (metadata & MFFSMachineBlock.ROTATION_MASK))
			return sideIcons(if((metadata & MFFSMachineBlock.ACTIVE_MASK) == MFFSMachineBlock.ACTIVE_MASK) 1 else 0)

		if((metadata & MFFSMachineBlock.ACTIVE_MASK) == MFFSMachineBlock.ACTIVE_MASK)
			return activeIcon

		blockIcon
	}

	/**
	 * @return Class representing the tile entity associated with this block.
	 */
	def tileEntityClass: Class[_ <: TileEntity] = null

	/**
	 * Rotates the block to face the specified ForgeDirection.
	 */
	override def rotateBlock(world: World, x: Int, y: Int, z: Int, newDirection: ForgeDirection): Boolean = {
		if(!isRotationSensitive || world.isRemote)
			return false

		var metadata = world.getBlockMetadata(x, y, z)
		val isActive = metadata & MFFSMachineBlock.ACTIVE_MASK
		val dir = newDirection.ordinal()

		metadata = isActive | dir

		world.setBlockMetadataWithNotify(x, y, z, metadata, 2)

		true
	}

	override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, unk: Int) {
		PowerMap.deleteTile(world.provider.dimensionId, x, y, z)

		super.breakBlock(world, x, y, z, block, unk)
	}
}
