package shared


sealed interface Direction {
    fun moveFrom(startPoint: Point, times: Int = 1): Point
    val reverse: Direction
}

object Up : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(y = startPoint.y - times)
    }

    override val reverse: Direction
        get() = Down
}

object Down : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(y = startPoint.y + times)
    }

    override val reverse: Direction
        get() = Up
}

object Left : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1)
    }

    override val reverse: Direction
        get() = Right
}

object Right : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1)
    }

    override val reverse: Direction
        get() = Left
}


object DownRight : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1, y = startPoint.y - 1)
    }

    override val reverse: Direction
        get() = TopLeft
}


object DownLeft : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1, y = startPoint.y - 1)
    }

    override val reverse: Direction
        get() = TopRight
}

object TopLeft : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1, y = startPoint.y + 1)
    }

    override val reverse: Direction
        get() = DownRight
}

object TopRight : Direction {
    override fun moveFrom(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1, y = startPoint.y + 1)
    }

    override val reverse: Direction
        get() = DownLeft
}

