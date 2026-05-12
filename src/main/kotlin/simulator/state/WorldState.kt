package org.loregen.simulator.state

import org.loregen.domain.Event
import org.loregen.domain.Faction
import org.loregen.domain.characters.Character
import org.loregen.domain.relationship.Relationship
import org.loregen.simulator.state.substates.CharacterState
import org.loregen.simulator.state.substates.EventLog
import org.loregen.simulator.state.substates.FactionState
import org.loregen.simulator.state.substates.RelationshipState
import org.springframework.stereotype.Component

@Component
class WorldState(
    val characterState: CharacterState,
    val relationshipState: RelationshipState,
    val factionState: FactionState,
    val eventLog: EventLog,
) {
    var currentYear = 0

    val characters: MutableList<Character>
        get() = characterState.characters

    val relationships: MutableList<Relationship>
        get() = relationshipState.relationships

    val factions: MutableList<Faction>
        get() = factionState.factions

    val history: MutableList<Event>
        get() = eventLog.history
}
