package org.example.domain.relationship

import java.util.UUID

data class Relationship(
    val id: UUID = UUID.randomUUID(),
    val character1Id: UUID,
    val character2Id: UUID,
    val type: RelationshipType,
    val affectionLevel: Int = 50 // -100 to 100
) {
}