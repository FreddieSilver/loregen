package org.loregen.simulator.services

import org.loregen.domain.characters.Character
import org.loregen.domain.characters.Human
import org.loregen.domain.characters.Sex
import org.loregen.domain.relationship.Relationship
import org.loregen.domain.relationship.RelationshipType
import org.loregen.simulator.state.WorldState
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RelationshipService(
    val worldState: WorldState
) {

    fun getSpouseId(character: Character): UUID? {
        val marriage = worldState.relationships.find {
            it.type == RelationshipType.MARRIED &&
                (it.character1Id == character.id || it.character2Id == character.id)
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

    fun getParentIds(character: Character): List<UUID> {
        return worldState.relationships.filter {
            it.type == RelationshipType.PARENT_CHILD && it.character2Id == character.id
        }.map { it.character1Id }
    }

    fun getChildIds(character: Character): List<UUID> {
        return worldState.relationships.filter {
            it.type == RelationshipType.PARENT_CHILD && it.character1Id == character.id
        }.map { it.character2Id }
    }

    fun marry(character1: Character, character2: Character) {
        if (getSpouse(character1) != null || getSpouse(character2) != null) return
        if (hasRelationship(character1.id, character2.id, RelationshipType.MARRIED)) return

        val newMarriage = Relationship(
            character1Id = character1.id,
            character2Id = character2.id,
            type = RelationshipType.MARRIED,
            affectionLevel = 80
        )
        worldState.relationships.add(newMarriage)
    }

    fun addParentChildAndSiblingsRelationships(parents: List<Character>, child: Character) {
        parents.forEach { parent ->
            if (!hasRelationship(parent.id, child.id, RelationshipType.PARENT_CHILD)) {
                val parentChild = Relationship(
                    character1Id = parent.id,
                    character2Id = child.id,
                    type = RelationshipType.PARENT_CHILD,
                    affectionLevel = 80
                )
                worldState.relationships.add(parentChild)
            }

            val siblings = worldState.relationships
                .filter { it.type == RelationshipType.PARENT_CHILD && it.character1Id == parent.id && it.character2Id != child.id }
                .map { it.character2Id }

            siblings.forEach { siblingId ->
                if (!hasRelationship(child.id, siblingId, RelationshipType.SIBLING)) {
                    val siblingRelationship = Relationship(
                        character1Id = child.id,
                        character2Id = siblingId,
                        type = RelationshipType.SIBLING,
                        affectionLevel = 80
                    )
                    worldState.relationships.add(siblingRelationship)
                }
            }
        }
    }

    private fun getFamilyIds(character: Character): Set<UUID> {
        val siblingIds = getSiblingIds(character)
        val parentIds = getParentIds(character)
        val childIds = getChildIds(character)

        return (siblingIds + parentIds + childIds).toSet()
    }

    private fun getMarriedIds(): Set<UUID> {
        val aliveIds = worldState.characters.filter { it.isAlive }.map { it.id }.toSet()
        return worldState.relationships
            .asSequence()
            .filter { it.type == RelationshipType.MARRIED }
            .flatMap { sequenceOf(it.character1Id, it.character2Id) }
            .filter { it in aliveIds }
            .toSet()
    }

    private fun hasRelationship(character1Id: UUID, character2Id: UUID, type: RelationshipType): Boolean {
        return worldState.relationships.any {
            it.type == type &&
                ((it.character1Id == character1Id && it.character2Id == character2Id) ||
                    (it.character1Id == character2Id && it.character2Id == character1Id))
        }
    }

    fun findMate(female: Human): Human? {
        val familyIds = getFamilyIds(female)
        val marriedIds = getMarriedIds()

        return worldState.characters
            .asSequence()
            .filterIsInstance<Human>()
            .filter { male -> male.isAlive && male.sex == Sex.MALE && male.id != female.id }
            .filter { male -> male.id !in familyIds }
            .filter { male -> male.id !in marriedIds }
            .filter { male -> male.canReproduce() }
            .toList()
            .randomOrNull()
    }

}