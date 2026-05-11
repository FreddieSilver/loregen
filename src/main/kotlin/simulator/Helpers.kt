package org.example.simulator

import org.example.domain.Faction
import org.example.domain.Event
import org.example.domain.Name
import org.example.domain.characters.Character
import org.example.domain.characters.Human
import org.example.domain.characters.Sex
import org.example.simulator.engines.NameGenerator.generateFullName

fun createFaction(name: String) =
    Faction(name = Name(name))

fun createHuman(sex: Sex, faction: Faction? = null) =
   Human(
       name = generateFullName(sex),
       sex = sex,
       faction = faction
   )


fun WorldState.seedWorld() {
//    val faction1 = createFaction("Red Clan")
//    val faction2 = createFaction("Blue Clan")
//    factions.addAll(listOf(faction1, faction2))

    val character1 = createHuman(Sex.FEMALE)
    val character2 = createHuman(Sex.MALE)
    val character3 = createHuman(Sex.MALE)
    val character4 = createHuman(Sex.FEMALE)

    characterBirth(character1)
    characterBirth(character2)
    characterBirth(character3)
    characterBirth(character4)
}

fun WorldState.characterBirth(character: Character) {
    characters.add(character)
    history.add(Event.Birth(currentYear, character))
}
