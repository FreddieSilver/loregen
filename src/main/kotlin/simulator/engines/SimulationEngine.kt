package org.loregen.simulator.engines

import org.loregen.simulator.services.CharacterService
import org.loregen.simulator.services.FactionService
import org.loregen.simulator.state.WorldState
import org.springframework.stereotype.Service

@Service
class SimulationEngine(
    val worldState: WorldState,
    private val characterService: CharacterService,
    private val factionService: FactionService,
) {
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
