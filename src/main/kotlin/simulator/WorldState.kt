package org.example.simulator

import org.example.domain.characters.Character
import org.example.domain.Faction
import org.example.domain.Event
import org.example.domain.relationship.Relationship

// state-only for the simulation
class WorldState {
    var currentYear = 0
    val characters = mutableListOf<Character>()
    val relationships = mutableListOf<Relationship>()
    val factions = mutableListOf<Faction>()
    val history = mutableListOf<Event>()
}