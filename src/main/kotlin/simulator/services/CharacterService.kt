package org.example.simulator.services

import org.example.domain.characters.Character
import org.example.domain.characters.Human
import org.example.domain.characters.Sex
import org.example.simulator.WorldState
import org.example.simulator.characterBirth
import org.example.simulator.engines.NameGenerator.generateFullName
import org.example.simulator.engines.ProbabilityEngine.chance
import org.example.simulator.engines.ProbabilityEngine.chanceForCharacterToFindLove
import org.example.simulator.engines.ProbabilityEngine.chanceForHumanToHaveBaby
import org.example.simulator.services.EventService.characterDeathEvent
import org.example.simulator.services.EventService.characterMarriageEvent
import org.example.simulator.services.EventService.charactersHavingBabyEvent


// character rules live here
class CharacterService(
    val worldState: WorldState,
    val relationshipService: RelationshipService
) {

    fun simulateCharacters() {
        val newBirths = worldState.characters.indices.flatMap {
            index -> simulateCharacter(index)
        }

        newBirths.forEach { worldState.characterBirth(it) }
    }

    private fun simulateCharacter(index: Int): List<Human> {
        val character = worldState.characters[index].takeIf { it.isAlive } ?: return emptyList()

        val aged = character.ageOneYear()
        val updated = dieIfOld(aged)
        worldState.characters[index] = updated

        return handleRelationships(updated)
    }

    private fun dieIfOld(character: Character): Character {
        if (character is Human && character.willDieOfOldAge()) {
            characterDeathEvent(character, "old age", worldState)
            return character.die()
        }
        return character
    }

    private fun handleRelationships(character: Character): List<Human> {
        val human = character as? Human ?: return emptyList()
        if (!human.canReproduce() || !human.isFemale()) return emptyList()

        val husband = resolveHusband(human) ?: return emptyList()

        return if (chanceForHumanToHaveBaby(human.age)) reproduce(husband, human, worldState)
        else emptyList()
    }

    private fun resolveHusband(female: Human): Human? {
        val existing = relationshipService.getSpouse(female) as? Human
        if (existing != null) return existing

        if (!chanceForCharacterToFindLove(female.age)) return null

        val candidate = relationshipService.findMate(female, worldState) ?: return null
        relationshipService.marry(female, candidate)
        characterMarriageEvent(female, candidate, worldState)
        return candidate
    }



    private fun reproduce(father: Human, mother: Human, worldState: WorldState): List<Human> {
        val sex = if (chance(50.0)) Sex.MALE else Sex.FEMALE
        val child = Human(
            name = generateFullName(sex),
            sex = sex
        )
        charactersHavingBabyEvent(father, mother, child, worldState)
        relationshipService.addParentChildAndSiblingsRelationships(mother, child)
        return listOf(child)
    }


}