package org.loregen

import org.loregen.simulator.engines.SimulationEngine
import org.loregen.simulator.seedWorld
import org.loregen.simulator.state.WorldState
import org.loregen.writer.LoreWriter
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ApplicationRunner(
    private val worldState: WorldState,
    private val simulationEngine: SimulationEngine,
    private val loreWriter: LoreWriter
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        worldState.seedWorld()
        simulationEngine.simulateYears(300)
        loreWriter.writeLore(worldState.history)
    }
}