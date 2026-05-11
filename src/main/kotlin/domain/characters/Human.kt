package org.example.domain.characters

import org.example.domain.Faction
import org.example.simulator.engines.ProbabilityEngine.chanceForHumanToDieOfOldAge
import java.util.UUID

class Human(
    override val id: UUID = UUID.randomUUID(),
    override val name: CharacterName,
    override val age: Int = 0,
    val sex: Sex,
    override val isAlive: Boolean = true,
    override val faction: Faction? = null,
): Character {
    override fun ageOneYear(): Character {
        return Human(id = id, name = name, age = age + 1, sex = sex, isAlive = isAlive, faction = faction)
    }

    override fun die(): Character {
        return Human(id = id, name = name, age = age,sex = sex, isAlive = false, faction = faction)
    }

    override fun joinFaction(faction: Faction): Character {
        return Human(id = id, name = name, age = age,sex = sex, isAlive = isAlive, faction = faction)
    }

    override fun leaveFaction(): Character {
        return Human(id = id, name = name, age = age,sex = sex, isAlive = isAlive, faction = null)
    }

    fun canReproduce(): Boolean = isAlive && age in 18..50

    fun isMale(): Boolean = sex == Sex.MALE

    fun isFemale(): Boolean = sex == Sex.FEMALE


    fun willDieOfOldAge(): Boolean =
        chanceForHumanToDieOfOldAge(age = age, sex = sex)

}
