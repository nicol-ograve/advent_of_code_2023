package day5

import day5.models.RangeTransformer
import day5.models.ValuesRange

data class Day5Input(
    val seeds: List<Long>, val rangeTransformers: List<List<RangeTransformer>>,
    val seedsRanges: List<ValuesRange>
)

fun parseDay5Input(content: String): Day5Input {
    val blocks = content.split("\n\n")
    val seeds = blocks[0].substring(7).split(" ").map { it.toLong() }

    val seedRanges = mutableListOf<ValuesRange>()
    for (i in seeds.indices step 2) {
        seedRanges.add(ValuesRange(seeds[i], seeds[i + 1]))
    }

    val ranges = blocks.subList(1, blocks.size).map { block ->
        val lines = block.split("\n")
        lines.subList(1, lines.size).map { line ->

            val rangeParts = line.split(" ")
            RangeTransformer(rangeParts[0].toLong(), rangeParts[1].toLong(), rangeParts[2].toLong())

        }.sortedBy { it.sourceStart }
    }

    return Day5Input(seeds, ranges, seedRanges)
}