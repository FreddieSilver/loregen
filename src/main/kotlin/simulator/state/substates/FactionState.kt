package org.loregen.simulator.state.substates

import org.loregen.domain.Faction
import org.springframework.stereotype.Component

@Component
class FactionState {
    val factions: MutableList<Faction> = mutableListOf()
}