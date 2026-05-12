package org.loregen.domain

import org.loregen.domain.characters.Character

sealed class Event {
    abstract val year: Int

    data class Birth(
        override val year: Int,
        val character: Character
    ) : Event()

    data class Death(
        override val year: Int,
        val character: Character,
        val cause: String
    ) : Event()

    data class Battle(
        override val year: Int,
        val name: Name,
        val attacker: Faction,
        val defender: Faction,
    ) : Event()

    data class Marriage(
        override val year: Int,
        val partner1: Character,
        val partner2: Character
    ) : Event()

    data class HavingBaby(
        override val year: Int,
        val partner1: Character,
        val partner2: Character,
        val child: Character
    ) : Event()
}