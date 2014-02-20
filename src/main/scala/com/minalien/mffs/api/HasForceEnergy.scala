package com.minalien.mffs.api

/**
 * Represents any object that has Force Energy.
 */
trait HasForceEnergy {
	def getCurrentForceEnergy: Float
	def getForceEnergyCapacity: Float

	def setCurrentForceEnergy(value: Float)
}
