package org.example.simulator.services

import org.example.domain.Faction
import org.example.domain.Event
import org.example.domain.Name
import org.example.simulator.WorldState
import org.example.simulator.engines.ProbabilityEngine.chanceForFactionToDeclareWar


class FactionService {

    fun simulateFactions(worldState: WorldState) {
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
                    startBattle(updatedFaction, enemy, worldState)
                }
            }
            worldState.factions[i] = updatedFaction
        }
    }

    private fun pickEnemy(faction: Faction, allFactions: List<Faction>): Faction? {
        return allFactions.filter { it.id != faction.id }.randomOrNull()
    }

    private fun startBattle(attacker: Faction, defender: Faction, worldState: WorldState) {
        val battleName = Name("Battle of the ${defender.name.value} Land")
        worldState.history.add(Event.Battle(worldState.currentYear, battleName, attacker, defender))
    }
}