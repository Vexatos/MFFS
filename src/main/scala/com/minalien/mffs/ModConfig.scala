package com.minalien.mffs

import net.minecraftforge.common.config.Configuration

/**
 * Stores all configuration data for MFFS.
 */
object ModConfig {
	object WorldGen {
		var enableMonazitOre = true

		var monazitOrePerVein = 8
		var monazitOreVeinsPerChunk = 5
		var monazitOreMinHeight = 10
		var monazitOreMaxHeight = 64
	}

	def load(configFile: Configuration) {
		WorldGen.enableMonazitOre = configFile.get("WorldGen", "Enable Monazit Ore", WorldGen.enableMonazitOre,
			"If this is disabled, the Force Energy Extractor recipe will be disabled as well!").getBoolean(WorldGen
			.enableMonazitOre)

		WorldGen.monazitOrePerVein = configFile.get("WorldGen", "Avg. Monazit Ore Blocks per Vein",
			WorldGen.monazitOrePerVein, "Amount of Monazit ore, on average, in each Monazit Vein.").getInt(WorldGen
			.monazitOrePerVein)

		WorldGen.monazitOreVeinsPerChunk = configFile.get("WorldGen", "Monazit Ore Veins per Chunk",
			WorldGen.monazitOreVeinsPerChunk, "Number of Monazit Ore veins to generate per chunk.").getInt(WorldGen
			.monazitOreVeinsPerChunk)

		WorldGen.monazitOreMinHeight = configFile.get("WorldGen", "Monazit Ore Minimum Height",
			WorldGen.monazitOreMinHeight, "Minimum height in the world for Monazit Ore veins to spawn.")
			.getInt(WorldGen.monazitOreMinHeight)

		WorldGen.monazitOreMaxHeight = configFile.get("WorldGen", "Monazit Ore Maximum Height",
			WorldGen.monazitOreMaxHeight, "Maximum height in the world for Monazit Ore veins tos pawn.")
			.getInt(WorldGen.monazitOreMaxHeight)

		if(configFile.hasChanged)
			configFile.save()
	}
}
