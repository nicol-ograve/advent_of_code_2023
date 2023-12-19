package day11

import shared.Point
import utils.getDataLines
import  shared.*
import kotlin.math.abs

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(11, if (isDemo) arrayOf("demo") else emptyArray())


    val galaxies = readGalaxies(lines)
    val result = part1(galaxies)

    println(result)
}

fun readGalaxies(lines: List<String>): List<Point> {

    var expandedLines = mutableListOf<MutableList<Char>>()

    for (line in lines) {
        expandedLines.add(line.toCharArray().toMutableList())
        if (line.all { it == '.' }) {
            expandedLines.add(line.toCharArray().toMutableList())
        }
    }
    val columnsCount = lines[0].length
    val emptyColumns = mutableListOf<Int>()

    for (c in 0 until columnsCount) {
        var empty = true
        for (r in lines.indices) {
            if (lines[r][c] != '.') {
                empty = false
            }
        }
        if (empty) {
            emptyColumns.add(c)
        }
    }

    for (c in emptyColumns.reversed()) {
        for (line in expandedLines) {
            line.add(c, '.')
        }
    }

    val expandedUniverse = Array(expandedLines.size) { row ->
        Array(expandedLines[row].size) { column ->
            expandedLines[row][column]
        }
    }

    val galaxies = mutableListOf<Point>()

    for (r in 0 until expandedUniverse.size) {
        for (c in 0 until expandedUniverse[r].size) {
            if (expandedUniverse[r][c] == '#') {
                galaxies.add(Point(r, c))
            }
        }
    }

    expandedUniverse.print()

    return galaxies

}


fun part1(galaxies: List<Point>): Int {
    var distanceSum = 0
    for (i in 0 until galaxies.size) {
        for (j in i + 1 until galaxies.size) {
            val p1 = galaxies[i]
            val p2 = galaxies[j]
            distanceSum += abs(p1.x - p2.x) + abs(p1.y - p2.y)
        }
    }
    return distanceSum
}