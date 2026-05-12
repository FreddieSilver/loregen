package org.example.simulator.services

import org.example.domain.Event
import org.example.domain.Faction
import org.example.domain.Name
import org.example.domain.characters.Character
import org.example.domain.characters.Human
import org.example.simulator.WorldState

object EventService {

    fun charactersHavingBabyEvent(father: Human, mother: Human, child: Human, worldState: WorldState){
        worldState.history.add(Event.HavingBaby(worldState.currentYear, father, mother, child))
    }

    fun characterDeathEvent(character: Character, cause: String, worldState: WorldState) {
        worldState.history.add(Event.Death(worldState.currentYear, character, cause))
    }

    fun characterMarriageEvent(partner1: Character, partner2: Character, worldState: WorldState) {
        worldState.history.add(Event.Marriage(worldState.currentYear, partner1, partner2))
    }

    fun startBattleEvent(attacker: Faction, defender: Faction, worldState: WorldState) {
        val battleName = Name("Battle of the ${defender.name.value} Land")
        worldState.history.add(Event.Battle(worldState.currentYear, battleName, attacker, defender))
    }
}