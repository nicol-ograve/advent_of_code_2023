package day5.models

import java.lang.Error
import kotlin.math.max
import kotlin.math.min

data class RangeTransformation(
    val intersection: ValuesRange,
    val transformed: ValuesRange
)
data class RangeTransformer(val destinationStart: Long, val sourceStart: Long, val length: Long) {
    val sourceEnd = sourceStart + length
    val destinationEnd = destinationStart + length

    fun transform(range: ValuesRange): RangeTransformation? {

        val intersectedRange = intersection(range) ?: return null

        if(intersectedRange.length == 0L){
            throw Error("ERRORE")
        }
       return RangeTransformation(
           intersection = intersectedRange,
           transformed = ValuesRange(intersectedRange.start - sourceStart + destinationStart, intersectedRange.length)
       )


    }

    fun intersection(range: ValuesRange): ValuesRange? {
        if (sourceStart >= range.end || sourceEnd <= range.start) {
            return null
        }

        val start = max(sourceStart, range.start)
        val end = min(sourceEnd, range.end)
        return ValuesRange(
            start, end - start
        )
    }
}

data class ValuesRange(val start: Long, val length: Long) {
    val end = start + length
}