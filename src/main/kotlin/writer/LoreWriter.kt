package org.loregen.writer

import org.loregen.domain.Event
import org.springframework.stereotype.Component

@Component
class LoreWriter {
    fun writeLore(events: List<Event>) {
        println("loregen -------------------------------------------- ")

        events.sortedBy { it.year }.forEach { event ->
            val text = when (event) {
                is Event.Birth -> "In the year ${event.year}, ${event.character.name.toText()} was born into the world."
                is Event.Death -> "In the year ${event.year}, tragedy struck as ${event.character.name.toText()} died from ${event.cause} at age ${event.character.age}."
                is Event.Battle -> "In the bloody year of ${event.year} , ${event.attacker.name.value} declared war upon ${event.defender.name.value}."
                is Event.HavingBaby -> "In the year ${event.year}, ${event.partner1.name.toText()} and ${event.partner2.name.toText()} welcomed their child, ${event.child.name.toText()}, into the world."
                is Event.Marriage -> "In the year ${event.year}, ${event.partner1.name.toText()} (${event.partner1.age} years old) and ${event.partner2.name.toText()} (${event.partner2.age} years old) were united in marriage."
            }
            println(text)
        }
    }
}