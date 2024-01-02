package day16.input

import day16.models.Contractor
import day16.models.EmptySpace
import day16.models.Mirror
import day16.models.Splitter
import shared.Matrix

fun parseDay16Input(lines: List<String>): Matrix<Contractor> {
    return lines.map { line ->
        line.map {
            when (it) {
                '.' -> EmptySpace
                '\\' -> Mirror(false)
                '/' -> Mirror(true)
                '|' -> Splitter(false)
                '-' -> Splitter(true)
                else -> throw Exception("Unknown symbol")
            }
        }.toTypedArray()
    }.toTypedArray()
}