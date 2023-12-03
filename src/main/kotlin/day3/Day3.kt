package day3

import utils.getDataLines
import utils.getDataScanner
import java.awt.Point
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(3, if (isDemo) arrayOf("demo") else emptyArray())

    val input = parseDay3Input(lines)

    val result = part2(input);

    println(result)
}

fun part1(scheme: EngineScheme): Int {
    val (parts) = scheme

    return parts.filter {
        isPartNearToASymbol(it, scheme)
    }.map {
        if (it.number == 783)
            println(it)
        it
    }
        .sumOf { it.number }

}
fun part2(scheme: EngineScheme): Int {
    val (parts) = scheme
    val starsMap = HashMap<EngineSymbol, MutableList<EnginePartNumber>>()

    parts.forEach {part ->
        getNearStars(part, scheme).forEach {
            if(!starsMap.containsKey(it)){
                starsMap[it] = mutableListOf()
            }

            starsMap[it]!!.add(part)
        }
    }

    return starsMap.keys.filter {
        starsMap[it]?.size == 2
    }.map {
        starsMap[it]!![0].number * starsMap[it]!![1].number
    }.sum()


}


fun getNearStars(part: EnginePartNumber, scheme: EngineScheme): List<EngineSymbol> {
    val nearCells = mutableListOf<Point>()
    if (part.x > 0) {
        for (y in part.startY - 1..part.endY + 1) {
            nearCells.add(Point(part.x - 1, y))
        }
    }

    for (y in part.startY - 1..part.endY + 1) {
        nearCells.add(Point(part.x + 1, y))
    }

    nearCells.add(Point(part.x, part.startY - 1))
    nearCells.add(Point(part.x, part.endY + 1))



    return nearCells.map { scheme.getSymbol(it.x, it.y) }.filter { it != null && it?.symbol == '*' } as List<EngineSymbol>

}

fun isPartNearToASymbol(part: EnginePartNumber, scheme: EngineScheme): Boolean {
    if (part.x > 0) {
        for (y in part.startY - 1..part.endY + 1) {
            if (scheme.getSymbol(part.x - 1, y) != null) {
                return true;
            }
        }
    }
    for (y in part.startY - 1..part.endY + 1) {
        if (scheme.getSymbol(part.x + 1, y) != null) {
            return true;
        }
    }

    return scheme.getSymbol(part.x, part.startY - 1) != null || scheme.getSymbol(part.x, part.endY + 1) != null

}