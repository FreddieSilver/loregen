package org.loregen.simulator

import org.loregen.domain.Event
import org.loregen.domain.Faction
import org.loregen.domain.Name
import org.loregen.domain.characters.Character
import org.loregen.domain.characters.Human
import org.loregen.domain.characters.Sex
import org.loregen.simulator.engines.rng.NameGenerator.generateFullName
import org.loregen.simulator.state.WorldState

fun createFaction(name: String) = Faction(name = Name(name))

fun createHuman(
    sex: Sex,
    faction: Faction? = null,
) = Human(
    name = generateFullName(sex),
    sex = sex,
    faction = faction,
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
