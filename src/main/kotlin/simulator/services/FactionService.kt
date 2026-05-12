package org.loregen.simulator.services

import org.loregen.domain.Faction
import org.loregen.simulator.engines.rng.ProbabilityEngine.chanceForFactionToDeclareWar
import org.loregen.simulator.state.WorldState
import org.springframework.stereotype.Service

@Service
class FactionService(
    val worldState: WorldState,
    private val eventService: EventService,
) {
    fun simulateFactions() {
        // Simulate faction growth, alliances, and conflicts
        for (i in worldState.factions.indices) {
            val faction = worldState.factions[i]
            // factions make money every year
            var updatedFaction = faction.increaseWealth(25)

            // if they have enough money they have a chance to attack another faction
            if (chanceForFactionToDeclareWar(updatedFaction)) {
                val enemy = pickEnemy(updatedFaction, worldState.factions)
                if (enemy != null) {
                    // cost of war
                    updatedFaction = updatedFaction.decreaseWealth(75)
                    eventService.startBattleEvent(updatedFaction, enemy)
                }
            }
            worldState.factions[i] = updatedFaction
        }
    }

    private fun pickEnemy(
        faction: Faction,
        allFactions: List<Faction>,
    ): Faction? {
        return allFactions.filter { it.id != faction.id }.randomOrNull()
    }
}
