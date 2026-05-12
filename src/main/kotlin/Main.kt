package org.example

import org.example.simulator.WorldState
import org.example.simulator.engines.SimulationEngine
import org.example.simulator.seedWorld
import org.example.writer.LoreWriter.writeLore


fun main() {
    val worldState = WorldState()
    worldState.seedWorld()

    val simulationEngine = SimulationEngine(worldState)
    simulationEngine.simulateYears(300)

    writeLore(worldState.history)

}