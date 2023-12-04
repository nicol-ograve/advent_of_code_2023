package day3

import day3.models.EnginePartNumber
import day3.models.EngineScheme
import day3.models.EngineSymbol


fun parseDay3Input(lines: List<String>): EngineScheme {

    val parts = mutableListOf<EnginePartNumber>()
    val symbols = mutableListOf<EngineSymbol>()

    lines.forEachIndexed { x, line ->

        var currentPartNumber: String? = null

        line.forEachIndexed { y, char ->
            if(char.isDigit()) {
                // Number
                if(currentPartNumber == null) {
                    currentPartNumber = "$char"
                } else {
                    currentPartNumber += char
                }
            } else {
                // Empty cell or symbol
                if(currentPartNumber != null){
                    parts.add(
                        EnginePartNumber(
                            number = currentPartNumber?.toInt() ?: 0,
                            x = x,
                            startY = y - (currentPartNumber?.length ?:0),
                            endY = y - 1,
                        )
                    )
                    currentPartNumber = null
                }
                if (char != '.') {
                    // Symbol
                    symbols.add(
                        EngineSymbol(
                            x = x,
                            y = y,
                            symbol = char
                        )
                    )
                }

            }


        }
        if(currentPartNumber != null){
            parts.add(
                EnginePartNumber(
                    number = currentPartNumber?.toInt() ?: 0,
                    x = x,
                    startY = line.length - (currentPartNumber?.length ?:0),
                    endY = line.length - 1,
                )
            )
            currentPartNumber = null
        }

    }

    return EngineScheme(
        parts = parts,
        symbols = symbols
    )
}