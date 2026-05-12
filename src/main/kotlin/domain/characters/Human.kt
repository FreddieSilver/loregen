package org.loregen.domain.characters

import org.loregen.domain.Faction
import org.loregen.simulator.engines.rng.ProbabilityEngine.chanceForHumanToDieOfOldAge
import java.util.UUID

class Human(
    override val id: UUID = UUID.randomUUID(),
    override val name: CharacterName,
    override val age: Int = 0,
    val sex: Sex,
    override val isAlive: Boolean = true,
    override val faction: Faction? = null,
) : Character(id, name, age, isAlive, faction) {
    override fun ageOneYear(): Human {
        return Human(id = id, name = name, age = age + 1, sex = sex, isAlive = isAlive, faction = faction)
    }

    override fun die(): Human {
        return Human(id = id, name = name, age = age, sex = sex, isAlive = false, faction = faction)
    }

    override fun joinFaction(faction: Faction): Human {
        return Human(id = id, name = name, age = age, sex = sex, isAlive = isAlive, faction = faction)
    }

    override fun leaveFaction(): Human {
        return Human(id = id, name = name, age = age, sex = sex, isAlive = isAlive, faction = null)
    }

    fun canReproduce(): Boolean = isAlive && age in 18..50

    fun isFemale(): Boolean = sex == Sex.FEMALE

    fun willDieOfOldAge(): Boolean = chanceForHumanToDieOfOldAge(age = age, sex = sex)
}
