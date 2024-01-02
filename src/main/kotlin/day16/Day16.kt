package day16

import day16.input.parseDay16Input
import day16.solution.BeamTracker
import shared.print
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val input = parseDay16Input(
        getDataLines(16, if (isDemo) arrayOf("demo") else emptyArray())
    )

    input.print()

    val tracker = BeamTracker(input)
    val result = tracker.findEnergizedTilesWithBestBeam()

    println()
    println(result)
}