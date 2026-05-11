package org.example.simulator.engines

import org.example.domain.Faction
import org.example.domain.characters.Sex
import kotlin.random.Random

object ProbabilityEngine {

    fun chanceForCharacterToFindLove(age: Int): Boolean {
        val baseChance = when {
            age < 18 -> 0.0
            age < 30 -> (age - 18) * 0.5
            age < 50 -> 60.0 - (age - 30) * 0.2
            else -> 20.0 - (age - 50) * 0.1
        }.coerceIn(0.0, 100.0)

        return chance(baseChance)
    }

    fun chanceForHumanToHaveBaby(age: Int): Boolean {
        val baseChance = when {
            age < 18 -> 0.0
            age < 25 -> 10.0
            age < 35 -> 15.0
            age < 45 -> 5.0
            else -> 0.0
        }
        return chance(baseChance)
    }


    fun chanceForHumanToDieOfOldAge(age: Int, sex: Sex): Boolean {
        val baseChance = when {
            age < 50 -> 0.0
            age < 70 -> (age - 50) * 0.5
            age < 85 -> 25.0 + (age - 70) * 2.5
            else -> 62.5 + (age - 85) * 5.0
        }

        val sexModifier = if (sex == Sex.MALE) 1.2 else 0.9
        val finalChance = (baseChance * sexModifier).coerceIn(0.0, 100.0)

        return chance(finalChance)
    }

    fun chanceForFactionToDeclareWar(attackingFaction: Faction): Boolean {
        val baseChance = 0.01
        val newChance = baseChance + (attackingFaction.wealth / 10000.0)
        return chance(newChance)

    }

    fun chance(percent: Double): Boolean {
        return Random.nextDouble() < percent / 100.0
    }
}