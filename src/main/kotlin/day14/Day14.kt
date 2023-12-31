package day14

import shared.Matrix
import shared.print
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(14, if (isDemo) arrayOf("demo") else emptyArray())

    val matrix: Matrix<Char> = Array(lines.size) { r ->
        Array(lines[r].length) { c ->
            lines[r][c]
        }
    }

    for (i in 0 until 1000000000) {

        loop(matrix, false)
        matrix.print()
            println()

    }

    println(countLoad(matrix))

}


fun countLoad(matrix: Matrix<Char>): Int {
    var load = 0

    val rows = matrix.size
    val columns = matrix[0].size

    for (c in 0 until columns) {
        for (r in 0 until rows) {
            if (matrix[r][c] == 'O') {
                load += rows - r
            }
        }
    }

    return load
}


fun loop(matrix: Matrix<Char>, print: Boolean = true) {

    val rows = matrix.size
    val columns = matrix[0].size

    if (print) {
        matrix.print()
        println()
    }


    for (c in 0 until columns) {
        for (r in 0 until rows) {
            if (matrix[r][c] == 'O') {
                matrix[r][c] = '.'
                var i = r - 1
                while (i >= 0 && matrix[i][c] == '.') {
                    i--
                }
                matrix[i + 1][c] = 'O'
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }

    for (r in 0 until rows) {
        for (c in 0 until columns) {
            if (matrix[r][c] == 'O') {
                matrix[r][c] = '.'
                var i = c - 1
                while (i >= 0 && matrix[r][i] == '.') {
                    i--
                }
                matrix[r][i + 1] = 'O'
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }


    for (c in 0 until columns) {
        for (r in rows - 1 downTo 0) {
            if (matrix[r][c] == 'O') {
                matrix[r][c] = '.'
                var i = r + 1
                while (i < rows && matrix[i][c] == '.') {
                    i++
                }
                matrix[i - 1][c] = 'O'
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }



    for (r in 0 until rows) {
        for (c in columns - 1 downTo 0) {
            if (matrix[r][c] == 'O') {
                matrix[r][c] = '.'
                var i = c + 1
                while (i < columns && matrix[r][i] == '.') {
                    i++
                }
                matrix[r][i - 1] = 'O'
            }
        }
    }

    if (print) {
        matrix.print()
        println()
    }


}

