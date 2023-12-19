package day10

import day10.models.Pipe
import day10.models.Pipe.NSPipe
import day10.models.Start
import day10.models.Tile
import day10.models.parseTile
import day10.solution.DistanceFinder
import shared.*
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val lines = getDataLines(10, if (isDemo) arrayOf("demo") else emptyArray())

    lateinit var entryPoint: Point

    val matrix: Matrix<Tile> = Array(lines.size) { row ->
        Array(lines[row].length) { column ->
            val tile = parseTile(lines[row][column])
            if(tile is Start){
                entryPoint = Point(column, row)
            }
            tile
        }
    }


    val distanceFinder = DistanceFinder(matrix, entryPoint)
    val result = distanceFinder.findInternalAreas()



    matrix.print()

     println(result)
}

