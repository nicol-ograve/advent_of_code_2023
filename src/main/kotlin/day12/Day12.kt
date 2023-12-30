package day12

import day12.models.ConditionRecord
import utils.getDataLines

val part2 = true
val isDemo = false

fun main(args: Array<String>) {
    val input = getDataLines(12, if (isDemo) arrayOf("demo") else emptyArray())
        .map { ConditionRecord.fromRecordString(it) }

    //println(input)
    val result = part1(input)
    println(result)


}

fun part1(records: List<ConditionRecord>): Int {
    var time = System.currentTimeMillis()
    return records.sumOf {
        println("diff: " + (System.currentTimeMillis() - time))
        time = System.currentTimeMillis()
        val result = countCombinations(it)

        //  println(result)
        result
    }
}

data class Combination(
    val start: String,
    val remaining: String,
    val groups: MutableList<Int>,
    val breakRequired: Boolean = false,
    val currentGroup: Int? = null
)


var combinations = 0

data class Item(val springs: String, val groups: List<Int>, val original: String, val success: Boolean = false)

fun countCombinations(record: ConditionRecord): Int {

    var validCombinations = 0
    println(record)

    val remainingCombinations = mutableListOf(Item(record.springs, record.brokenGroups, record.springs))

    val done = HashSet<String>()

    while (remainingCombinations.isNotEmpty()) {
        val nextItem = remainingCombinations.removeFirst()

        val next = nextCombinations(nextItem)

        if (next != null) {
            if (next.size == 1 && next.first().success) {
                // if (!done.contains(next.first().original)) {
                    //println(next.first().original)
                    //done.add(next.first().original)
                //} else {
                //println(next.first().original)
                //}
                validCombinations++
            } else {
                remainingCombinations.addAll(next)
            }
        }

    }


    println(validCombinations)
    return validCombinations

}

fun nextCombinations(item: Item): List<Item>? {
    var (springs, groups, original) = item

    if (springs.length < groups.sum() + groups.size - 1) {
        return null
    }

    if (springs.isEmpty()) {
        combinations++
        return null
    }

    when (springs[0]) {
        '?' -> {
            val suffix = springs.substring(1)
            return listOf(
                Item(".$suffix", groups, original.replaceFirst('?', '.')),
                Item("#$suffix", groups, original.replaceFirst('?', '#'))
            )
        }

        '#' -> {
            if (groups.isEmpty()) {
                return null
            }
            val nextGroup = groups.first()
            for (i in 0 until nextGroup) {
                if (i >= springs.length || springs[i] == '.') {
                    return null
                }

                // TODO remove: debug only
                /*if (springs[i] == '?') {
                    original = original.replaceFirst('?', '#')
                }*/
            }

            springs = springs.substring(nextGroup)
            groups = groups.subList(1, groups.size)

            if (groups.isEmpty()) {
                if (springs.isEmpty() || !springs.contains('#')) {
                    // TODO remove
                    /*original = original.replace('?', '.')
                    println("Valid combination")
                    println(original)*/

                    combinations++
                    return listOf(Item("", emptyList(), original, true))
                }
                return null

            }

            if (springs.isEmpty() || springs[0] == '#') {
                return null
            }

            if (springs.isNotEmpty() && springs[0] == '?') {
                springs = ".${springs.substring(1)}"
                original = original.replaceFirst('?', '.')
            }

            return listOf(Item(springs, groups, original))

        }

        '.' -> {
// '.'
            while (springs.isNotEmpty() && springs[0] == '.') {
                springs = springs.substring(1)
            }
            return listOf(Item(springs, groups, original))

        }

        else -> throw Exception("Unknown char")
    }
}

fun findCombinations(record: ConditionRecord): Int {

    var validCombinations = 0
// println(record)

    val remainingCombinations = mutableListOf(Combination("", record.springs, record.brokenGroups.toMutableList()))

    while (remainingCombinations.isNotEmpty()) {
        var (start, remaining, groups, breakRequired, currentGroup) = remainingCombinations.removeFirst()

        var error = false


        while (!error && remaining.isNotEmpty() && (remaining.first() == '#' || remaining.first() == '.')) {

            if (remaining.first() == '#') {
                if (breakRequired) {
                    error = true
                } else if (currentGroup == null) {
                    if (groups.isEmpty()) {
                        error = true
                    } else {
                        currentGroup = groups[0] - 1
                        groups = if (groups.size == 1) mutableListOf() else groups.subList(1, groups.size)
                    }
                } else if (currentGroup == 0) {
                    error = true
                } else {
                    currentGroup--
                }

                start += remaining[0]
                remaining = remaining.substring(1)

                if (currentGroup != null && currentGroup == 0) {
                    currentGroup = null
                    breakRequired = true

                }

            } else if (remaining.first() == '.') {
                if (currentGroup != null && currentGroup > 0) {
                    error = true
                } else {
                    breakRequired = false
                    start += remaining[0]
                    remaining = remaining.substring(1)
                }
            }
        }



        if (!error) {

            if ((currentGroup == null || currentGroup == 0) && groups.isEmpty()) {
                if (remaining.isEmpty() || !remaining.contains("#")) {
                    validCombinations++
                }

            } else if (remaining.isNotEmpty()) {
                remainingCombinations.add(
                    Combination(
                        start,
                        ".${remaining.substring(1)}",
                        groups,
                        breakRequired,
                        currentGroup
                    )
                )
                if (!breakRequired) {
                    var suffix = remaining

                    var prefix: String? = null
                    if (currentGroup != null && currentGroup > 0) {
                        prefix = "#".repeat(currentGroup)
                        currentGroup = null
                    } else if (groups.size > 0) {
                        prefix = "#".repeat(groups[0])
                        groups = groups.subList(1, groups.size)
                    } else {
                        prefix = "#"
                    }

                    if (suffix.length >= prefix.length && !suffix.substring(0, prefix.length).contains(".")) {
                        suffix = suffix.substring(prefix.length)
                        remainingCombinations.add(Combination(start, "$suffix", groups, false, currentGroup))
                    }


                }
            }

        }


    }

    return validCombinations

}

fun findNextCombinations() {}