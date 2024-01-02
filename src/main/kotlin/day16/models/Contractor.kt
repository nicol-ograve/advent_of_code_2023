package day16.models

import shared.*

sealed class Contractor {
    abstract fun reflectBeam(beam: Beam): ReflectedBeam
}

object EmptySpace : Contractor() {
    override fun reflectBeam(beam: Beam): ReflectedBeam {
        return ReflectedBeam.SingleBeam(Beam(beam.direction.moveFrom(beam.position), beam.direction))
    }

    override fun toString(): String {
        return "."
    }
}

// Straight = /, Reversed = \
class Mirror(private val straight: Boolean) : Contractor() {
    override fun reflectBeam(beam: Beam): ReflectedBeam {
        val reflectedDirection = when (beam.direction) {
            Up -> if (straight) Right else Left
            Down -> if (straight) Left else Right
            Left -> if (straight) Down else Up
            Right -> if (straight) Up else Down
            else -> throw Exception("Invalid direction")
        }
        return ReflectedBeam.SingleBeam(Beam(reflectedDirection.moveFrom(beam.position), reflectedDirection))
    }

    override fun toString(): String {
        return if (straight) "/" else "\\"
    }
}

// Horizontal = -, Vertical = |
class Splitter(private val horizontal: Boolean) : Contractor() {
    override fun reflectBeam(beam: Beam): ReflectedBeam {
        return when (beam.direction) {
            Up, Down -> if (horizontal)
                ReflectedBeam.SplitBeam(
                    Beam(Left.moveFrom(beam.position), Left),
                    Beam(Right.moveFrom(beam.position), Right),
                )
            else
                ReflectedBeam.SingleBeam(Beam(beam.direction.moveFrom(beam.position), beam.direction))
            Left, Right -> if (!horizontal)
                ReflectedBeam.SplitBeam(
                    Beam(Up.moveFrom(beam.position), Up),
                    Beam(Down.moveFrom(beam.position), Down),
                )
            else
                ReflectedBeam.SingleBeam(Beam(beam.direction.moveFrom(beam.position), beam.direction))
            else -> throw Exception("Invalid direction")
        }
    }

    override fun toString(): String {
        return if (horizontal) "-" else "|"
    }

}

sealed class ReflectedBeam {
    class SingleBeam(val beam: Beam) : ReflectedBeam()
    class SplitBeam(val leftBeam: Beam, val rightBeam: Beam) : ReflectedBeam()
}