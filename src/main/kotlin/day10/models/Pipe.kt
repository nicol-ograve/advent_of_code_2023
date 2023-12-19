package day10.models

import shared.*

sealed class Tile(val symbol: Char) {
    override fun toString(): String {
        return symbol.toString()
    }
}

class Ground(var blocksAbove: Int = 0, var blocksBelow: Int = 0, var blocksLeft: Int = 0, var blocksRight: Int = 0) :
    Tile('.') {

    val isInternal: Boolean
        get() = blocksAbove % 2 != 0 && blocksBelow % 2 != 0 && blocksLeft % 2 != 0 && blocksRight % 2 != 0

    override fun toString(): String {
        if (isInternal) {
            return "I"
        }
        return "O"
    }
}

class Start : Tile('S')

sealed class Pipe(val entries: Array<Direction>, symbol: Char) : Tile(symbol) {
    class NSPipe() : Pipe(arrayOf(Up, Down), '|')
    class EWPipe() : Pipe(arrayOf(Left, Right), '-')
    class NEPipe() : Pipe(arrayOf(Up, Right), 'L')
    class NWPipe() : Pipe(arrayOf(Up, Left), 'J')
    class SWPipe() : Pipe(arrayOf(Down, Left), '7')
    class SEPipe() : Pipe(arrayOf(Down, Right), 'F')

}


fun parseTile(char: Char): Tile {
    return when (char) {
        'S' -> Start()
        '|' -> Pipe.NSPipe()
        '-' -> Pipe.EWPipe()
        'L' -> Pipe.NEPipe()
        'J' -> Pipe.NWPipe()
        '7' -> Pipe.SWPipe()
        'F' -> Pipe.SEPipe()
        '.' -> Ground()
        else -> throw Exception("Unknown tile $char")
    }
}


/*

| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 */
