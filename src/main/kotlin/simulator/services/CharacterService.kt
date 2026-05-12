package org.loregen.simulator.services

import org.loregen.domain.characters.Character
import org.loregen.domain.characters.Human
import org.loregen.domain.characters.Sex
import org.loregen.simulator.characterBirth
import org.loregen.simulator.engines.rng.NameGenerator.generateFullName
import org.loregen.simulator.engines.rng.ProbabilityEngine.chance
import org.loregen.simulator.engines.rng.ProbabilityEngine.chanceForCharacterToFindLove
import org.loregen.simulator.engines.rng.ProbabilityEngine.chanceForHumanToHaveBaby
import org.loregen.simulator.state.WorldState
import org.springframework.stereotype.Service

// Character lifecycle rules live here.
@Service
class CharacterService(
    val worldState: WorldState,
    val relationshipService: RelationshipService,
    private val eventService: EventService
) {

    fun simulateCharacters() {
        val currentPopulationSize = worldState.characters.size
        val newBirths = (0 until currentPopulationSize).flatMap { index ->
            simulateCharacter(index)
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
            eventService.characterDeathEvent(character, "old age")
            return character.die()
        }
        return character
    }

    private fun handleRelationships(character: Character): List<Human> {
        val human = character as? Human ?: return emptyList()
        if (!human.canReproduce() || !human.isFemale()) return emptyList()

        val husband = resolveHusband(human) ?: return emptyList()

        return if (chanceForHumanToHaveBaby(human.age)) reproduce(husband, human) else emptyList()
    }

    private fun resolveHusband(female: Human): Human? {
        val existing = relationshipService.getSpouse(female) as? Human
        if (existing != null) return existing

        if (!chanceForCharacterToFindLove(female.age)) return null

        val candidate = relationshipService.findMate(female) ?: return null
        relationshipService.marry(female, candidate)
        eventService.characterMarriageEvent(female, candidate)
        return candidate
    }

    private fun reproduce(father: Human, mother: Human): List<Human> {
        val sex = if (chance(50.0)) Sex.MALE else Sex.FEMALE
        val child = Human(
            name = generateFullName(sex),
            sex = sex
        )
        eventService.charactersHavingBabyEvent(father, mother, child)
        relationshipService.addParentChildAndSiblingsRelationships(listOf(mother, father), child)
        return listOf(child)
    }

}