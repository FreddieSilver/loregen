package org.example.domain.characters

import org.example.domain.Faction
import java.util.UUID

interface Character {
    val id: UUID
    val name: CharacterName
    val age: Int
    val isAlive: Boolean
    val faction: Faction?

    fun ageOneYear(): Character

    fun die(): Character

    fun joinFaction(faction: Faction): Character

    fun leaveFaction(): Character

}