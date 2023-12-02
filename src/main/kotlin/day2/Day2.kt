package day2

import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

val rgbLimits = arrayOf(12, 13, 14)

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(2, if (isDemo) arrayOf("demo") else emptyArray())

    val input = parseDay2Input(lines)
    val result = part2(input)

    println(result)
}

fun part1(input: List<CubesGame>): Int {
    return input.filter {
        it.records.all { record ->
            record.red <= rgbLimits[0]
                    && record.green <= rgbLimits[1]
                    && record.blue <= rgbLimits[2]
        }
    }.sumOf { it.id }
}

fun part2(input: List<CubesGame>): Int {
    return input.sumOf {
        val minRgb = arrayOf(
            it.records[0].red,
            it.records[0].green,
            it.records[0].blue,
            )

        it.records.subList(1, it.records.size).forEach {set ->
            if(set.red > minRgb[0]){
                minRgb[0] = set.red
            }
            if(set.green > minRgb[1]){
                minRgb[1] = set.green
            }
            if(set.blue > minRgb[2]){
                minRgb[2] = set.blue
            }
        }

        val minProd =  minRgb[0] * minRgb[1] * minRgb[2]
        minProd
    }
}