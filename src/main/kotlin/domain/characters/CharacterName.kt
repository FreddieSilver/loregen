package org.loregen.domain.characters

import org.loregen.domain.Name

data class CharacterName(
    val title: Name? = null,
    val firstName: Name,
    val lastName: Name,
) {
    fun toText(): String {
        return "${firstName.value} ${lastName.value}"
    }
}
