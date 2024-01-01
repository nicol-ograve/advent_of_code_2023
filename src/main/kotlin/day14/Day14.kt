package day14

import shared.Matrix
import shared.Pattern
import shared.Point
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner


val positionsMap = HashMap<Int, ArrayList<Point>>()

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(14, if (isDemo) arrayOf("demo") else emptyArray())

    var rockId = 1
    val matrix: Matrix<Int> = Array(lines.size) { r ->
        Array(lines[r].length) { c ->
            when (lines[r][c]) {
                'O' -> {
                    positionsMap[rockId] = ArrayList()
                    rockId++
                }

                '#' -> -1
                '.' -> 0
                else -> throw Exception("Unknown")
            }
        }
    }

    val repetitions = 1000000000
    val demoRepetitions = 10000
    for (i in 0 until demoRepetitions) {

        loop(matrix, false)

    }


    val bestPatterns = mutableListOf<Pattern>()

    for (value in positionsMap.values) {
        val rockPatterns = findPatterns(value)

        if (rockPatterns.isNotEmpty()) {
            val best = rockPatterns.maxBy { it.length }
            if(best.first + best.length != best.second){
                println("WHAT")
            }
            bestPatterns.add(best)
        } else {
            println("No pattern")
        }

    }

    println(bestPatterns)
    println()

    println(countLoad(matrix))

}


fun countLoad(matrix: Matrix<Int>): Int {
    var load = 0

    val rows = matrix.size
    val columns = matrix[0].size

    for (c in 0 until columns) {
        for (r in 0 until rows) {
            if (matrix[r][c] > 0) {
                load += rows - r
            }
        }
    }

    return load
}


fun loop(matrix: Matrix<Int>, print: Boolean = true) {

    val rows = matrix.size
    val columns = matrix[0].size

    if (print) {
        matrix.print()
        println()
    }


    for (c in 0 until columns) {
        for (r in 0 until rows) {
            if (matrix[r][c] > 0) {
                val id = matrix[r][c]
                matrix[r][c] = 0
                var i = r - 1
                while (i >= 0 && matrix[i][c] == 0) {
                    i--
                }
                matrix[i + 1][c] = id
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }

    for (r in 0 until rows) {
        for (c in 0 until columns) {
            if (matrix[r][c] > 0) {
                val id = matrix[r][c]
                matrix[r][c] = 0
                var i = c - 1
                while (i >= 0 && matrix[r][i] == 0) {
                    i--
                }
                matrix[r][i + 1] = id
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }


    for (c in 0 until columns) {
        for (r in rows - 1 downTo 0) {
            if (matrix[r][c] > 0) {
                val id = matrix[r][c]
                matrix[r][c] = 0
                var i = r + 1
                while (i < rows && matrix[i][c] == 0) {
                    i++
                }
                matrix[i - 1][c] = id
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }



    for (r in 0 until rows) {
        for (c in columns - 1 downTo 0) {
            if (matrix[r][c] > 0) {
                val id = matrix[r][c]
                matrix[r][c] = 0
                var i = c + 1
                while (i < columns && matrix[r][i] == 0) {
                    i++
                }
                matrix[r][i - 1] = id
                positionsMap[id]!!.add(Point(r, i - 1))
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }


}

fun Array<Array<Int>>.print() {
    for (row in indices) {
        for (column in this[row].indices) {
            val value = this[row][column]
            print(if (value > 0) 'O' else if (value == 0) '.' else '#')
        }
        println()
    }
}