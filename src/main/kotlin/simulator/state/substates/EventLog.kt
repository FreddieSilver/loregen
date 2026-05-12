package org.loregen.simulator.state.substates

import org.loregen.domain.Event
import org.springframework.stereotype.Component

@Component
class EventLog {
    val history: MutableList<Event> = mutableListOf()
}
