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

	object ForceEnergy {
		var forceEnergyPerForcicium = 100f
		var forciciumConsumptionCycle = 200
	}

	def load(configFile: Configuration) {
		//////////////////////////////////////////////////
		// World Gen
		//////////////////////////////////////////////////
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

		//////////////////////////////////////////////////
		// Force Energy
		//////////////////////////////////////////////////
		ForceEnergy.forceEnergyPerForcicium = configFile.get("ForceEnergy", "Force Energy Per Forcicium",
			ForceEnergy.forceEnergyPerForcicium, "Amount of Force Energy generated per Forcicium in the FE " +
				"Extractor").getDouble(ForceEnergy.forceEnergyPerForcicium).asInstanceOf[Float]

		ForceEnergy.forciciumConsumptionCycle = configFile.get("ForceEnergy", "Forcicium Consumption Cycle",
			ForceEnergy.forciciumConsumptionCycle, "Number of ticks it takes to consume one Forcicium")
			.getInt(ForceEnergy.forciciumConsumptionCycle)

		if(configFile.hasChanged)
			configFile.save()
	}
}
