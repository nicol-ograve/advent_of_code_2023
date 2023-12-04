package day4

import day4.models.Scratchcard

val lineSplitRegex = Regex(": | \\|")
val numbersSplitRegex = Regex("\\s+")
fun parseDay4Input(lines: List<String>): List<Scratchcard> {
    return lines.map {line ->
        val parts = line.split(lineSplitRegex)

        val id = parts[0].replace("Card", "").trim().toInt()
        val winningNumbers = parts[1].trim().split(numbersSplitRegex).map { it.toInt() }
        val numbers = parts[2].trim().split(numbersSplitRegex).map { it.toInt() }

        Scratchcard(id, winningNumbers, numbers)
    }
}