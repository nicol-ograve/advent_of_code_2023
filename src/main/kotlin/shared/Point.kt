package shared

import java.lang.Integer.min
import kotlin.math.max

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is Point) return false
        return other.x == x && other.y == y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    operator fun plus(offset: Point): Point = Point(offset.x + x, offset.y + y)
}

fun pointsBetween(start: Point, end: Point): List<Point> {

    val points = mutableListOf<Point>()

    if (start.x == end.x) {
        for (i in min(start.y, end.y) until max(start.y, end.y) + 1) {
            points.add(Point(start.x, i))
        }
    } else if (start.y == end.y) {
        for (i in min(start.x, end.x) until max(start.x, end.x) + 1) {
            points.add(Point(i, start.y))
        }
    } else {
        throw RuntimeException("No line between points")
    }

    return points


}