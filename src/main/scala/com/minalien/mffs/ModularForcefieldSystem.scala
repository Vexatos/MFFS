package com.minalien.mffs

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.GameRegistry
import com.minalien.mffs.blocks._
import com.minalien.mffs.items.ItemForcicium
import net.minecraft.creativetab.CreativeTabs
import com.minalien.mffs.world.MonazitOreWorldGenerator

/**
 * Core mod object for the Modular Forcefield System mod.
 *
 * @author Ken 'Minalien' Murray
 *
 * @version 3.0
 */
@Mod(modid = "ModularForcefieldSystem", name = "Modular Forcefield System", version = "3.0", modLanguage = "scala")
object ModularForcefieldSystem {
	/**
	 * Directory within resources/ where mod resources are located.
	 */
	val ASSET_DIR = "mffs"

	/**
	 * Creative tab containing all items and blocks in the mod.
	 */
	val creativeTab = new CreativeTabs("tabMFFS") {
		override def getTabIconItem = {
			ItemForcicium
		}
	}

	/**
	 * Loads mod configuration details and uses the data to register blocks, items and OreDictionary entries.
	 *
	 * @param eventArgs Event arguments passed by Forge Mod Loader.
	 */
	@EventHandler
	def preInit(eventArgs: FMLPreInitializationEvent) {
		// Register all mod blocks.
		registerBlocks()

		// Register all mod items.
		registerItems()
	}

	/**
	 * Begins general mod initialization.
	 *
	 * @param eventArgs Event arguments passed by Forge Mod Loader.
	 */
	@EventHandler
	def init(eventArgs: FMLInitializationEvent) {
		GameRegistry.registerWorldGenerator(MonazitOreWorldGenerator, 0)
	}

	/**
	 * Registers all blocks and their associated tile entities.
	 */
	def registerBlocks() {
		def registerMachineBlock(block: MFFSMachineBlock) {
			GameRegistry.registerBlock(block, block.getUnlocalizedName)

			if(block.tileEntityClass != null)
				GameRegistry.registerTileEntity(block.tileEntityClass, block.tileEntityClass.getCanonicalName)
		}

		// Register standard blocks.
		GameRegistry.registerBlock(BlockMonazitOre, BlockMonazitOre.getUnlocalizedName)
		GameRegistry.registerBlock(BlockForciciumBlock, BlockForciciumBlock.getUnlocalizedName)

		// Register machine blocks.
		registerMachineBlock(BlockFEExtractor)
	}

	/**
	 * Registers all items.
	 */
	def registerItems() {
		GameRegistry.registerItem(ItemForcicium, ItemForcicium.getUnlocalizedName)
	}
}
