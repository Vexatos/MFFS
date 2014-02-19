package com.minalien.mffs.api

/**
 * Represents any object that has Force Energy.
 */
trait HasForceEnergy {
	protected var currentForceEnergy: Float = 0f

	def getCurrentForceEnergy: Float = currentForceEnergy
	def setCurrentForceEnergy(value: Float) {
		if(value < 0)
			currentForceEnergy = 0f
		else if(value > getForceEnergyCapacity)
			currentForceEnergy = getForceEnergyCapacity
		else
			currentForceEnergy = value
	}

	def getForceEnergyCapacity: Float
}
