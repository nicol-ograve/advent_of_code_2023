package day13

import shared.Matrix
import shared.Point


class MirrorsSolver(private val matrix: Matrix<Char>) {

    fun solve(): Int {

        val rowValues = mutableListOf<MirrorLine>()

        for (r in matrix.indices) {
            var rowString = ""
            for (c in matrix[r].indices) {
                rowString += matrix[r][c]
            }
            val value = rowString.toInt(2)
            rowValues.add(MirrorLine(rowString, value))

        }


        val columns = matrix[0].size
        val columnValues = mutableListOf<MirrorLine>()

        for (c in 0 until columns) {
            var columnString = ""

            for (r in 0 until matrix.size) {
                columnString += matrix[r][c]
            }

            val value = columnString.toInt(2)
            columnValues.add(MirrorLine(columnString, value))

        }

        val simmetricRows = findSmudge(rowValues)
        val simmetricColumns = findSmudge(columnValues)


        val rowSmudges = simmetricRows.sumOf { it * 100 }
        val columnSmudges = simmetricColumns.sum()

        if(rowSmudges > 0 && columnSmudges > 0){
            println("ASJLKJDLKJS")
        }


        return rowSmudges + columnSmudges
    }

    private fun findSymmetries(list: List<MirrorLine>): List<Int> {
        val symmetries = mutableListOf<Int>()
        for (i in 0 until list.size - 1) {
            var simmetric = true
            var j = 1
            while (i + j < list.size && (i - j + 1) >= 0 && simmetric) {
                if (list[i + j].value != list[i - j + 1].value) {
                    simmetric = false
                }
                j++
            }

            if (simmetric) {
                symmetries.add(i + 1)
            }
        }
        return symmetries
    }

    private fun findSmudge(list: MutableList<MirrorLine>): List<Int> {

        for (i in 0 until list.size - 1) {
            var differences = 0
            var j = 1
            while (i + j < list.size && (i - j + 1) >= 0 && differences <= 1) {
                val diffs = list[i + j].differences(list[i - j + 1])
                differences += diffs.size
                j++
            }

            if (differences == 1) {
                return listOf(i + 1)
            }
        }
        return emptyList()
    }
}