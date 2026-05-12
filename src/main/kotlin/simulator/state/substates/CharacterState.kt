package org.loregen.simulator.state.substates

import org.loregen.domain.characters.Character
import org.springframework.stereotype.Component

@Component
class CharacterState {
    val characters: MutableList<Character> = mutableListOf()
}
