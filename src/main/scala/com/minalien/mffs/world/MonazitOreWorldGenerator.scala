package com.minalien.mffs.world

import cpw.mods.fml.common.IWorldGenerator
import net.minecraft.world.chunk.IChunkProvider
import java.util.Random
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenMinable
import com.minalien.mffs.blocks.BlockMonazitOre
import net.minecraft.init.Blocks
import com.minalien.mffs.ModConfig

/**
 * Performs world generation for Monazit Ore.
 */
object MonazitOreWorldGenerator extends IWorldGenerator {
	override def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkProvider,
	                      chunkProvider: IChunkProvider) {
		val shiftedChunkX = chunkX << 4
		val shiftedChunkZ = chunkZ << 4

		val monazitOreGenerator = new WorldGenMinable(BlockMonazitOre, 0, ModConfig.WorldGen.monazitOrePerVein, Blocks.stone)

		for(vein <- 1 to ModConfig.WorldGen.monazitOreVeinsPerChunk) {
			val x = shiftedChunkX + random.nextInt(16)
			val y = ModConfig.WorldGen.monazitOreMinHeight + random.nextInt(ModConfig.WorldGen.monazitOreMaxHeight -
				ModConfig.WorldGen.monazitOreMinHeight)
			val z = shiftedChunkZ + random.nextInt(16)

			monazitOreGenerator.generate(world, random, x, y, z)
		}
	}
}
