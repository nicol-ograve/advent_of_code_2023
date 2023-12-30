package day13

import day5.parseDay5Input
import shared.Matrix
import utils.getDataFile
import utils.getDataLines
import utils.getDataScanner
import java.io.File
import java.util.Scanner
import kotlin.math.max

fun main(args: Array<String>) {
    val isDemo = false

    val file = getDataFile(13, isDemo)
    val input = file.bufferedReader().readText()

    val groups = input.split("\n\n").map { group ->
        val lines = group.split("\n")
        Array(lines.size) { row ->
            val line = lines[row]
            Array(line.length) { column ->
                if (line[column] == '#') '1' else '0'
            }
        }

    }


    val result = groups.sumOf {
        MirrorsSolver(it).solve()
    }

    println(result)

}
