package org.loregen.domain.relationship

import java.util.UUID

data class Relationship(
    val id: UUID = UUID.randomUUID(),
    val character1Id: UUID,
    val character2Id: UUID,
    val type: RelationshipType,
    // -100 to 100
    val affectionLevel: Int = 50,
)
