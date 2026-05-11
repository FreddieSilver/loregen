package org.example

import org.example.simulator.WorldState
import org.example.simulator.seedWorld
import org.example.simulator.engines.SimulationEngine.simulateYears
import org.example.writer.LoreWriter.writeLore


fun main() {
    val worldState = WorldState()
    worldState.seedWorld()

    simulateYears(worldState, 300)

    writeLore(worldState.history)

}