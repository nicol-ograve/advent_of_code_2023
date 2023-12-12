package day9

import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false

    val result = getDataLines(9, if (isDemo) arrayOf("demo") else emptyArray())
        .map { line -> line.split(" ").map { it.toLong() }.toLongArray() }
        .map { findDiff(it, false) }
        .sumOf { it }


    println(result)
}

fun findDiff(list: LongArray, forward: Boolean = true): Long {
    var allZeros = true

    val next = LongArray(list.size - 1) { i ->
        val diff = list[i + 1] - list[i]
        if (allZeros && diff != 0L) {
            allZeros = false
        }
        diff
    }

    if (forward) {
        val lastItem = list[list.size - 1]

        if (allZeros) {
            return lastItem
        }


        return findDiff(next, forward) + lastItem
    } else {
        val firstItem = list[0]

        if (allZeros) {
            return firstItem
        }

        return firstItem - findDiff(next, forward)
    }


}