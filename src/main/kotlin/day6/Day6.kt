package day6

import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val scanner = getDataScanner(6, if (isDemo) arrayOf("demo") else emptyArray())

    part2(scanner)


}


fun part1(scanner: Scanner) {
    val times = mutableListOf<Int>()
    val distances = mutableListOf<Int>()

    scanner.next()
    while (scanner.hasNextInt()) {
        times.add(scanner.nextInt())
    }
    scanner.next()
    while (scanner.hasNextInt()) {
        distances.add(scanner.nextInt())
    }

    var combinations = 1
    for (i in times.indices) {
        val count = boatButtonCombinations(times[i], distances[i])
        combinations *= count
    }


    println(combinations)
}


fun part2(scanner: Scanner) {

    scanner.next()
    val time = scanner.nextLine().replace(" ", "").toLong()
    scanner.next()
    val distance = scanner.nextLine().replace(" ", "").toLong()

    val combinations = boatButtonCombinations(time, distance)

    println(combinations)

}


fun boatButtonCombinations(time: Int, distance: Int): Int {

    var i = 1
    while (i * (time - i) <= distance) i++

    return time - i * 2 + 1
}

fun boatButtonCombinations(time: Long, distance: Long): Long {

    var i = 1
    while (i * (time - i) <= distance) i++

    return time - i * 2 + 1
}