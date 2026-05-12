package org.loregen.simulator.services

import org.loregen.domain.Event
import org.loregen.domain.Faction
import org.loregen.domain.Name
import org.loregen.domain.characters.Character
import org.loregen.domain.characters.Human
import org.loregen.simulator.state.WorldState
import org.springframework.stereotype.Service

@Service
class EventService(
    private val worldState: WorldState
) {

    fun charactersHavingBabyEvent(father: Human, mother: Human, child: Human) {
        worldState.history.add(Event.HavingBaby(worldState.currentYear, father, mother, child))
    }

    fun characterDeathEvent(character: Character, cause: String) {
        worldState.history.add(Event.Death(worldState.currentYear, character, cause))
    }

    fun characterMarriageEvent(partner1: Character, partner2: Character) {
        worldState.history.add(Event.Marriage(worldState.currentYear, partner1, partner2))
    }

    fun startBattleEvent(attacker: Faction, defender: Faction) {
        val battleName = Name("Battle of the ${defender.name.value} Land")
        worldState.history.add(Event.Battle(worldState.currentYear, battleName, attacker, defender))
    }
}