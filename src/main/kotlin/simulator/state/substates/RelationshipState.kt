package org.loregen.simulator.state.substates

import org.loregen.domain.relationship.Relationship
import org.springframework.stereotype.Component

@Component
class RelationshipState {
    val relationships: MutableList<Relationship> = mutableListOf()
}