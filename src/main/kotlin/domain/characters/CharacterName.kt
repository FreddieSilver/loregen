package org.example.domain.characters

import org.example.domain.Name

data class CharacterName(
    val title: Name? = null,
    val firstName: Name,
    val lastName: Name
){

    fun toText(): String {
        return "${firstName.value} ${lastName.value}"
    }
}