package day5

import day5.models.RangeTransformer
import day5.models.ValuesRange
import utils.getDataFile
import java.lang.Long.max
import kotlin.math.min

fun main(args: Array<String>) {
    val isDemo = false
    val file = getDataFile(5, isDemo)

    val input = parseDay5Input(file.bufferedReader().readText())


    val result = part2(input)

    println(result)
}

fun part1(input: Day5Input): Long {

    var values = input.seeds.map { it }

    for (stepRanges in input.rangeTransformers) {
        values = values.map { nextValue(it, stepRanges) }
    }

    return values.min()

}

fun nextValue(value: Long, ranges: List<RangeTransformer>): Long {
    for (range in ranges) {
        if (value in range.sourceStart until range.sourceStart + range.length) {
            return value - range.sourceStart + range.destinationStart
        }
    }
    return value
}

fun part2(input: Day5Input): Long {
    var currentRanges = input.seedsRanges.map { it }.toMutableList()
    var transformedRanges = mutableListOf<ValuesRange>()

    for (stepTransformers in input.rangeTransformers) {

        while (currentRanges.isNotEmpty()) {
            val range = currentRanges.removeFirst()

            var i = 0
            var intersected = false

            // Could be optimized more by looping all new subranges instead of putting them back inside the current ones


            while (i < stepTransformers.size && !intersected) {
                val transformer = stepTransformers[i]



                val transformation = transformer.transform(range)


                if (transformation != null) {

                    intersected = true

                    val (intersection, transformed) = transformation
                    if (intersection.start > range.start) {
                        currentRanges.add(ValuesRange(range.start, intersection.start - range.start))
                    }

                    if (intersection.end < range.end) {
                        currentRanges.add(ValuesRange(intersection.end, range.end - intersection.end))
                    }

                    transformedRanges.add(transformed)
                }


                i++
            }

            if (!intersected) {
                transformedRanges.add(range)
            }


        }
        currentRanges = transformedRanges.map { it }.toMutableList()
        transformedRanges = mutableListOf<ValuesRange>()

    }


    return currentRanges.minOf { it.start }
}
