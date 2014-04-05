package com.minalien.mffs

import cpw.mods.fml.common.{SidedProxy, Mod}
import cpw.mods.fml.common.Mod.{Instance, EventHandler}
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.GameRegistry
import com.minalien.mffs.blocks._
import com.minalien.mffs.items.{ItemMFFSCard, ItemForcicium}
import net.minecraft.creativetab.CreativeTabs
import com.minalien.mffs.world.MonazitOreWorldGenerator
import com.minalien.mffs.proxy.CommonProxy
import cpw.mods.fml.common.network.NetworkRegistry
import net.minecraftforge.common.config.Configuration
import com.minalien.mffs.network.{NetworkUtil, MFFSPacketHandler}
import net.minecraftforge.common.MinecraftForge

/**
 * Core mod object for the Modular Forcefield System mod.
 *
 * @author Ken 'Minalien' Murray
 *
 * @version 3.0
 */
@Mod(modid = "ModularForcefieldSystem", name = "Modular Forcefield System", version = "3.0", modLanguage = "scala")
@Instance("ModularForcefieldSystem")
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

	@SidedProxy(clientSide = "com.minalien.mffs.proxy.ClientProxy", serverSide = "com.minalien.mffs.proxy.CommonProxy")
	var proxy: CommonProxy = null

	/**
	 * Loads mod configuration details and uses the data to register blocks, items and OreDictionary entries.
	 *
	 * @param eventArgs Event arguments passed by Forge Mod Loader.
	 */
	@EventHandler
	def preInit(eventArgs: FMLPreInitializationEvent) {
		// Load configuration data.
		ModConfig.load(new Configuration(eventArgs.getSuggestedConfigurationFile))

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
		// Initialize Networking.
		NetworkUtil.channels = NetworkRegistry.INSTANCE.newChannel("MFFSClassic", MFFSPacketHandler)

		// Initialize TESRs.
		proxy.registerRenderers()

		// Initialize World Generation.
		if(ModConfig.WorldGen.enableMonazitOre)
			GameRegistry.registerWorldGenerator(MonazitOreWorldGenerator, 0)

		// Register the GUI Handler.
		NetworkRegistry.INSTANCE.registerGuiHandler(ModularForcefieldSystem, MFFSGUIHandler)

		MinecraftForge.EVENT_BUS.register(this)
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
	}

	/**
	 * Registers all items.
	 */
	def registerItems() {
		GameRegistry.registerItem(ItemForcicium, ItemForcicium.getUnlocalizedName)
		GameRegistry.registerItem(ItemMFFSCard, ItemMFFSCard.getUnlocalizedName)
	}
}
