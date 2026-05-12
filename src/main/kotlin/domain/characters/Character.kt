package org.loregen.domain.characters

import org.loregen.domain.Faction
import java.util.UUID

sealed class Character(
    open val id: UUID,
    open val name: CharacterName,
    open val age: Int,
    open val isAlive: Boolean,
    open val faction: Faction?,
) {
    abstract fun ageOneYear(): Character

    abstract fun die(): Character

    abstract fun joinFaction(faction: Faction): Character

    abstract fun leaveFaction(): Character
}
