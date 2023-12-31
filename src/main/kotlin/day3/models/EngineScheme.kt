package day3.models

data class EnginePartNumber(
val x: Int, val startY: Int, val endY: Int, val number: Int
)

data class EngineSymbol(
    val x: Int, val y: Int, val symbol: Char
)

data class EngineScheme (
    val parts: List<EnginePartNumber>,
    val symbols: List<EngineSymbol>
) {
    private val symbolPositions: Map<Int,Map<Int,EngineSymbol>>

    init {
        val positions = mutableMapOf<Int,MutableMap<Int,EngineSymbol>>()

        symbols.forEach{

            if(positions[it.x] == null){
                positions[it.x] = mutableMapOf()
            }
            positions[it.x]!![it.y]=it
        }

        symbolPositions = positions
    }

    fun getSymbol(x: Int, y: Int): EngineSymbol? {
        return symbolPositions[x]?.get(y)
    }
}

