package day11

import shared.Point
import utils.getDataLines
import  shared.*
import kotlin.math.abs


const val expansionSize = 1000000

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(11, if (isDemo) arrayOf("demo") else emptyArray())


    val galaxies = readGalaxies(lines)
    val result = part1(galaxies)

    println(result)
}

fun readGalaxies(lines: List<String>): List<Point> {

    var expandedLines = mutableListOf<MutableList<Char>>()
    val emptyColumns = mutableListOf<Int>()
    val emptyRows = mutableListOf<Int>()

    var rowIndex = 0
    for (line in lines) {
        expandedLines.add(line.toCharArray().toMutableList())
        if (line.all { it == '.' }) {
            expandedLines.add(line.toCharArray().toMutableList())
            emptyRows.add(rowIndex)
        }
        rowIndex++
    }
    val columnsCount = lines[0].length

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

    val expandedRows = mutableListOf<Int>()
    val expandedColumns = mutableListOf<Int>()

    val expandedUniverse = Array(lines.size) { row ->
        Array(lines[row].length) { column ->
            lines[row][column]
        }
    }

    val galaxies = mutableListOf<Point>()

    var rowPointer = -1
    for (r in 0 until expandedUniverse.size) {

        if (emptyRows.contains(r)) {
            rowPointer += expansionSize
        } else {
            rowPointer++
        }

        var columnPointer = -1
        for (c in 0 until expandedUniverse[r].size) {


            if (emptyColumns.contains(c)) {
                columnPointer += expansionSize
            } else {
                columnPointer++
            }


            if (expandedUniverse[r][c] == '#') {
                galaxies.add(Point(rowPointer, columnPointer))
            }
        }
    }

    // expandedUniverse.print()
    println(galaxies)

    return galaxies

}


fun part1(galaxies: List<Point>): Long {
    var distanceSum = 0L
    for (i in 0 until galaxies.size) {
        for (j in i + 1 until galaxies.size) {
            val p1 = galaxies[i]
            val p2 = galaxies[j]
            distanceSum += abs(p1.x - p2.x) + abs(p1.y - p2.y)
        }
    }
    return distanceSum
}