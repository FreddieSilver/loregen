package org.example.simulator.engines

import org.example.simulator.WorldState
import org.example.simulator.services.CharacterService
import org.example.simulator.services.EventService
import org.example.simulator.services.FactionService
import org.example.simulator.services.RelationshipService

class SimulationEngine(
    val worldState: WorldState
) {
    private val relationshipService = RelationshipService(worldState)
    private val characterService = CharacterService(worldState, relationshipService)
    private val factionService = FactionService(worldState)

    fun simulateYears(years: Int) {
        repeat(years) {
            simulateYear()
        }
    }

    private fun simulateYear() {
        worldState.currentYear++
        characterService.simulateCharacters()
        factionService.simulateFactions()
    }
}