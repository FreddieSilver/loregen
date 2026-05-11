package org.example.simulator.services

import org.example.domain.Event
import org.example.domain.Name
import org.example.domain.characters.Character
import org.example.domain.characters.CharacterName
import org.example.domain.characters.Human
import org.example.domain.characters.Sex
import org.example.simulator.WorldState
import org.example.simulator.characterBirth
import org.example.simulator.engines.ProbabilityEngine.chance
import org.example.simulator.engines.ProbabilityEngine.chanceForCharacterToFindLove
import org.example.simulator.engines.ProbabilityEngine.chanceForHumanToHaveBaby


// character rules live here
class CharacterService {

    private val relationshipService = RelationshipService()

    fun simulateCharacters(worldState: WorldState) {
        // simulate one character at a time
        val newBirths = mutableListOf<Human>()

        for (i in worldState.characters.indices) {
            val character = worldState.characters[i]
            if (character.isAlive) {
                var updatedCharacter = character.ageOneYear()

                // the old will perish
                if (character is Human && character.willDieOfOldAge()) {
                    updatedCharacter = character.die()
                    characterDeathEvent(updatedCharacter, "old age", worldState)
                }
                worldState.characters[i] = updatedCharacter

                val updatedHuman = updatedCharacter as? Human
                if (updatedHuman != null && updatedHuman.canReproduce() && updatedHuman.isFemale()){
                    var husband = relationshipService.getSpouse(updatedHuman, worldState) as? Human
                    // marry
                    if (husband == null && chanceForCharacterToFindLove(updatedHuman.age)) {
                        val newHusband = relationshipService.findMate(updatedHuman, worldState)
                        if (newHusband != null) {
                            relationshipService.marry(updatedHuman, newHusband, worldState)
                            husband = newHusband
                            characterMarriageEvent(updatedHuman, husband, worldState)
                        }
                    }
                    // reproduce
                    if (husband != null && chanceForHumanToHaveBaby(updatedHuman.age)) {
                        reproduce(husband, updatedHuman, worldState, newBirths)
                    }

                }
            }
        }
        newBirths.forEach { worldState.characterBirth(it) }
    }

    private fun reproduce(father: Human, mother: Human, worldState: WorldState, newBirths: MutableList<Human>) {
        val childSex = if (chance(50.0)) Sex.MALE else Sex.FEMALE
        val firstNames = if (childSex == Sex.MALE) {
            listOf("Dhruva", "Erick", "João", "Finn", "Próstata")
        } else {
            listOf("Bea", "Anabela", "Sansa", "Arya", "Catelyn")
        }
        val childName = CharacterName(
            firstName = Name(firstNames.random()),
            lastName = father.name.lastName
        )
        val child = Human(name = childName, sex = childSex)
        charactersHavingBabyEvent(father, mother, child, worldState)
        newBirths.add(child)
        relationshipService.addParentChildAndSiblingsRelationships(mother, child, worldState)
    }

    private fun charactersHavingBabyEvent(father: Human, mother: Human, child: Human, worldState: WorldState){
        worldState.history.add(Event.HavingBaby(worldState.currentYear, father, mother, child))
    }


    private fun characterDeathEvent(character: Character, cause: String, worldState: WorldState) {
        worldState.history.add(Event.Death(worldState.currentYear, character, cause))
    }

    private fun characterMarriageEvent(partner1: Character, partner2: Character, worldState: WorldState) {
        worldState.history.add(Event.Marriage(worldState.currentYear, partner1, partner2))
    }
}