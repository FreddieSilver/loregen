package org.loregen.writer

import org.loregen.domain.Event
import org.springframework.stereotype.Component

@Component
class LoreWriter {
    fun writeLore(events: List<Event>) {
        println("loregen -------------------------------------------- ")

        events.sortedBy { it.year }.forEach { event ->
            println(event.toText())
        }
    }
}
