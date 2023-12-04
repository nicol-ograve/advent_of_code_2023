package day4

import day4.models.Scratchcard
import utils.getDataLines

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(4, if (isDemo) arrayOf("demo") else emptyArray())

    val scratchCards = parseDay4Input(lines)
    val result = part2(scratchCards)

    println(result)
}

fun part1(cards: List<Scratchcard>): Int {
    return cards.sumOf { it.points }
}

fun part2(cards: List<Scratchcard>): Int {

    val copies = Array(cards.size) { 1 }

    for (i in cards.indices) {
        for(copy in 0 until copies[i]) {
            for (j in i + 1..i + cards[i].matches) {
                copies[j]++
            }
        }

    }

    return copies.sum()
}



