package org.example.simulator.engines

import org.example.simulator.WorldState
import org.example.simulator.services.CharacterService
import org.example.simulator.services.FactionService

object SimulationEngine {
    private val characterService = CharacterService()
    private val factionService = FactionService()

    fun simulateYears(worldState: WorldState, years: Int) {
        repeat(years) {
            simulateYear(worldState)
        }
    }

    private fun simulateYear(worldState: WorldState) {
        worldState.currentYear++
        characterService.simulateCharacters(worldState)
        factionService.simulateFactions(worldState)
    }
}