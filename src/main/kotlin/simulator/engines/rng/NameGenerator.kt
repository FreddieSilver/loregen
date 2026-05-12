package org.loregen.simulator.engines.rng

import org.loregen.domain.Name
import org.loregen.domain.characters.CharacterName
import org.loregen.domain.characters.Sex

object NameGenerator {
    private val maleNames =
        listOf("Dhruva", "Erick", "João", "Finn", "Próstata", "Brandon", "Eddard", "Robb", "Jon", "Tyrion")
    private val femaleNames =
        listOf("Bea", "Anabela", "Sansa", "Arya", "Catelyn", "Daenerys", "Cersei", "Margaery", "Ygritte", "Brienne")

    private val surnames =
        listOf(
            "Smith", "Johnson", "Brown", "Taylor", "Anderson",
            "Galvão", "Silva", "Costa", "Pereira", "Oliveira",
        )

    fun generateFirstName(sex: Sex): Name {
        val names = if (sex == Sex.MALE) maleNames else femaleNames
        return Name(names.random())
    }

    fun generateLastName(): Name {
        return Name(surnames.random())
    }

    fun generateFullName(sex: Sex): CharacterName {
        val firstName = generateFirstName(sex)
        val lastName = generateLastName()
        return CharacterName(null, firstName, lastName)
    }
}
