package day10.solution

import day10.models.Ground
import day10.models.*
import day10.models.Tile
import shared.*
import shared.inRange

class DistanceFinder(val tiles: Matrix<Tile>, val entryPoint: Point) {

    val distances: Matrix<Int?> = Array(tiles.size) { row ->
        Array(tiles[row].size) {
            null
        }
    }

    init {

        val entryPointType = findEntryPointType()

        tiles[entryPoint] = entryPointType

        distances[entryPoint] = 0


    }

    fun findDistance(): Int {

        //nextStep(entryPoint)
        var points = mutableListOf(entryPoint)

        while (points.isNotEmpty()) {
            var point = points.removeFirst()

            if (tiles[point] !is Pipe) {
                println(point)
                // printTiles(point.y - 2, point.x - 2)

                throw Exception("Not a pipe")
            } else {
                // Can be optimized by passing incoming direction and preventing from check again the previous point


                val currentDistance = distances[point]!! + 1
                (tiles[point] as Pipe).entries
                    // .filter { from == null || from.reverse != it }
                    .map {
                        it.moveFrom(point)
                    }.forEach {
                        val pointDistance = distances[it]
                        if (pointDistance == null || pointDistance > currentDistance) {
                            distances[it] = currentDistance

                            points.add(it)
                        }
                    }
            }
        }

        // printDistances()

        return distances.maxOf { row ->
            row.maxOf { it ?: 0 }
        }
    }

    private fun nextStep(point: Point, from: Direction? = null) {
        if (tiles[point] !is Pipe) {
            throw Exception("Not a pipe")
        }
        // Can be optimized by passing incoming direction and preventing from check again the previous point


        val currentDistance = distances[point]!! + 1
        (tiles[point] as Pipe).entries.filter { from == null || from.reverse != it }.map {
            it.moveFrom(point)
        }.forEach {
            val pointDistance = distances[it]
            if (pointDistance == null || pointDistance > currentDistance) {
                distances[it] = currentDistance

                nextStep(it)
            }
        }


    }


    enum class LineAreaType() {
        Ground, Block
    }

    data class LineArea(val start: Int, val end: Int, val type: LineAreaType)
    data class GroundCell(val index: Int, val blockBefore: Int, var blockAfter: Int)

    fun findInternalAreas(): Int {
        this.findDistance()

        printDistances()

        for (r in tiles.indices) {
            for (c in tiles[r].indices) {
                if (distances[r][c] == null) {
                    tiles[r][c] = Ground()
                }
            }
        }


        for (r in tiles.indices) {
            val rowGroundCells = mutableListOf<GroundCell>()

            var blocksCount = 0

            var wallOpener: Tile? = null

            for (c in tiles[r].indices) {
                val rowTile = tiles[r][c]

                when {
                    rowTile is Ground -> rowGroundCells.add(GroundCell(c, blocksCount, 0))
                    rowTile.symbol == '|' || (wallOpener?.symbol == 'L' && rowTile.symbol == '7') || (wallOpener?.symbol == 'F' && rowTile.symbol == 'J') -> {
                        rowGroundCells.forEach { it.blockAfter++ }
                        blocksCount++
                        wallOpener = null
                    }

                    (wallOpener?.symbol == 'L' && rowTile.symbol == 'J') || (wallOpener?.symbol == 'F' && rowTile.symbol == '7') -> {
                        wallOpener = null
                    }

                    rowTile.symbol == 'L' || rowTile.symbol == 'F' -> wallOpener = rowTile
                }


            }

            for (rowGroundCell in rowGroundCells) {
                val cellTile = tiles[r][rowGroundCell.index]
                if (cellTile is Ground) {
                    cellTile.blocksLeft = rowGroundCell.blockBefore
                    cellTile.blocksRight = rowGroundCell.blockAfter
                } else {
                    throw Exception("Not ground")
                }
            }
        }

        val columns = tiles[0].size
        for (c in 0 until columns) {
            val columnGroundCells = mutableListOf<GroundCell>()

            var blocksCount = 0

            var wallOpener: Tile? = null

            for (r in tiles.indices) {
                val columnTile = tiles[r][c]

                when {
                    columnTile is Ground -> columnGroundCells.add(GroundCell(r, blocksCount, 0))
                    columnTile.symbol == '-' || (wallOpener?.symbol == 'F' && columnTile.symbol == 'J') || (wallOpener?.symbol == '7' && columnTile.symbol == 'L') -> {
                        columnGroundCells.forEach { it.blockAfter++ }
                        blocksCount++
                        wallOpener = null
                    }

                    (wallOpener?.symbol == 'F' && columnTile.symbol == 'L') || (wallOpener?.symbol == '7' && columnTile.symbol == 'J') -> {
                        wallOpener = null
                    }

                    columnTile.symbol == 'F' || columnTile.symbol == '7' -> wallOpener = columnTile
                }
            }

            for (columnGroundCell in columnGroundCells) {
                val cellTile = tiles[columnGroundCell.index][c]
                if (cellTile is Ground) {
                    cellTile.blocksAbove = columnGroundCell.blockBefore
                    cellTile.blocksBelow = columnGroundCell.blockAfter
                } else {
                    throw Exception("Not ground")
                }
            }

        }


        return tiles.sumOf { row -> row.count { it is Ground && it.isInternal } }


    }


    private fun findEntryPointType(): Tile {

        val aboveTile = Up.moveFrom(entryPoint).let { if (tiles.inRange(it)) tiles[it] else null }
        val isUpOpen = aboveTile is Pipe && aboveTile.entries.contains(Down)

        val belowTile = Down.moveFrom(entryPoint).let { if (tiles.inRange(it)) tiles[it] else null }
        val isDownOpen = belowTile is Pipe && belowTile.entries.contains(Up)

        val leftTile = Left.moveFrom(entryPoint).let { if (tiles.inRange(it)) tiles[it] else null }
        val isLeftOpen = leftTile is Pipe && leftTile.entries.contains(Right)

        val rightTile = Right.moveFrom(entryPoint).let { if (tiles.inRange(it)) tiles[it] else null }
        val isRightOpen = rightTile is Pipe && rightTile.entries.contains(Left)


        return when {
            isUpOpen -> when {
                isDownOpen -> Pipe.NSPipe()
                isRightOpen -> Pipe.NEPipe()
                isLeftOpen -> Pipe.NWPipe()
                else -> throw Exception("Invalid entry point")
            }

            isDownOpen -> when {
                isRightOpen -> Pipe.SEPipe()
                isLeftOpen -> Pipe.SWPipe()
                else -> throw Exception("Invalid entry point")
            }

            isRightOpen -> Pipe.EWPipe()
            isLeftOpen -> Pipe.EWPipe()
            else -> throw Exception("Invalid entry point")
        }

    }


    private fun printDistances() {
        for (row in distances.indices) {
            for (column in distances[row].indices) {
                val value = distances[row][column]?.toString() ?: "."
                print(value.padStart(4, ' '))
            }
            println()
        }
    }

    private fun printTiles(startRow: Int = 0, startColumn: Int = 0, size: Int = 4) {
        for (row in startRow..startRow + size) {
            for (column in startColumn..startColumn + size) {
                val value = tiles[row][column].symbol.toString()
                print(value.padStart(2, ' '))
            }
            println()
        }
    }
}



