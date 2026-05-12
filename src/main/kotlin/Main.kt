package org.loregen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoregenApplication

fun main(args: Array<String>) {
    runApplication<LoregenApplication>(*args)
}
