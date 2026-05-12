package org.loregen.domain

import java.util.UUID

data class Faction(
    val id: UUID = UUID.randomUUID(),
    val name: Name,
    val power: Int = 0,
    val wealth: Int = 0,
) {
    fun increasePower(amount: Int): Faction {
        return copy(power = power + amount)
    }

    fun decreasePower(amount: Int): Faction {
        return copy(power = power - amount)
    }

    fun increaseWealth(amount: Int): Faction {
        return copy(wealth = wealth + amount)
    }

    fun decreaseWealth(amount: Int): Faction {
        return copy(wealth = wealth - amount)
    }
}
