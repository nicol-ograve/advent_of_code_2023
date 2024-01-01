package day14

import shared.Pattern
import shared.Point


fun findPatterns(list: ArrayList<Point>): Array<Pattern> {
    val patterns = mutableListOf<Pattern>()

    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            if (list[i] == (list[j])) {
                val pattern = findPattern(list, i, j)
                if (pattern.first + pattern.length == pattern.second) {
                    return arrayOf(pattern)
                }
                patterns.add(findPattern(list, i, j))
            }
        }
    }
    return if (patterns.isEmpty()) emptyArray() else patterns.toTypedArray()

}

fun findPattern(list: ArrayList<Point>, first: Int, second: Int): Pattern {
    var offset = 1
    while (first + offset < second && second + offset < list.size && list[first + offset] == (list[second + offset])) {
        offset++
    }
    return Pattern(first, second, offset)
}

