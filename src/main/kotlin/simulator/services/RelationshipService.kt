package org.example.simulator.services

import org.example.domain.characters.Character
import org.example.domain.characters.Human
import org.example.domain.characters.Sex
import org.example.domain.relationship.Relationship
import org.example.domain.relationship.RelationshipType
import org.example.simulator.WorldState
import java.util.UUID

class RelationshipService(
    val worldState: WorldState
) {

    fun getSpouseId(character: Character): UUID? {
        val marriage = worldState.relationships.find{
            it.type == RelationshipType.MARRIED
                    && (it.character1Id == character.id || it.character2Id == character.id)
        } ?: return null
        return if (marriage.character1Id == character.id) marriage.character2Id else marriage.character1Id
    }

    fun getSpouse(character: Character): Character? {
        val spouseId = getSpouseId(character) ?: return null
        return worldState.characters.find { it.id == spouseId && it.isAlive }
    }

    fun getSiblingIds(character: Character): List<UUID> {
        return worldState.relationships.filter {
            it.type == RelationshipType.SIBLING &&
                    (it.character1Id == character.id || it.character2Id == character.id)
        }.map {
            if (it.character1Id == character.id) it.character2Id else it.character1Id
        }
    }

    fun getSiblings(character: Character): List<Character> {
        val siblingIds = getSiblingIds(character)
        return worldState.characters.filter { siblingIds.contains(it.id) && it.isAlive }
    }

    fun getParentIds(character: Character): List<UUID> {
        return worldState.relationships.filter {
            it.type == RelationshipType.PARENT_CHILD && it.character2Id == character.id
        }.map { it.character1Id }
    }

    fun getParents(character: Character): List<Character> {
        val parentIds = getParentIds(character)
        return worldState.characters.filter { parentIds.contains(it.id) && it.isAlive }
    }

    fun getChildIds(character: Character): List<UUID> {
        return worldState.relationships.filter {
            it.type == RelationshipType.PARENT_CHILD && it.character1Id == character.id
        }.map { it.character2Id }
    }

    fun getChildren(character: Character): List<Character> {
        val childIds = getChildIds(character)
        return worldState.characters.filter { childIds.contains(it.id) && it.isAlive }
    }

    fun marry(character1: Character, character2: Character) {
        val newMarriage = Relationship(
            character1Id = character1.id,
            character2Id = character2.id,
            type = RelationshipType.MARRIED,
            affectionLevel = 80
        )
        worldState.relationships.add(newMarriage)
    }

    fun addParentChildAndSiblingsRelationships(parent: Character, child: Character) {
        val newRelationship = Relationship(
            character1Id = parent.id,
            character2Id = child.id,
            type = RelationshipType.PARENT_CHILD,
            affectionLevel = 80
        )
        worldState.relationships.add(newRelationship)

        val siblings = worldState.relationships.filter {
            it.type == RelationshipType.PARENT_CHILD && it.character1Id == parent.id && it.character2Id != child.id
        }.map { it.character2Id }

        siblings.forEach { siblingId ->
            val siblingRelationship = Relationship(
                character1Id = child.id,
                character2Id = siblingId,
                type = RelationshipType.SIBLING,
                affectionLevel = 80
            )
            worldState.relationships.add(siblingRelationship)
        }

    }

    private fun getFamilyIds(character: Character): Set<UUID> {
        val siblingIds = getSiblingIds(character)
        val parentIds = getParentIds(character)
        val childIds = getChildIds(character)

        return  (siblingIds + parentIds + childIds).toSet()
    }

        private fun getMarriedIds(worldState: WorldState): Set<UUID> {
            return worldState.relationships.filter { it.type == RelationshipType.MARRIED }
                .flatMap { listOf(it.character1Id, it.character2Id) }
                .toSet()
        }


    fun findMate(female: Human, worldState: WorldState): Human? {
        val familyIds = getFamilyIds(female)
        val marriedIds = getMarriedIds(worldState)

        return worldState.characters
            .asSequence()
            .filterIsInstance<Human>()
            .filter { male -> male.isAlive && male.sex == Sex.MALE && male.id != female.id }
            .filter { male -> male.id !in familyIds } // no incest
            .filter { male -> male.id !in marriedIds } // no betrayal
            .filter {male -> male.canReproduce()}
            .toList()
            .randomOrNull()
    }

}