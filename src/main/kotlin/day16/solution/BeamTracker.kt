package day16.solution

import day16.models.Beam
import day16.models.Contractor
import day16.models.ReflectedBeam
import shared.*

class BeamTracker(private val matrix: Matrix<Contractor>) {

    fun findEnergizedTilesWithDefaultBeam(): Int {
        return findEnergizedTiles(Beam(Point(0, 0), Right))
    }

    fun findEnergizedTilesWithBestBeam(): Int {
        val beams = mutableListOf<Beam>()

        val rows = matrix.size
        val columns = matrix[0].size

        for (r in 0 until rows) {
            beams.add(Beam(Point(0, r), Right))
            beams.add(Beam(Point(columns - 1, r), Left))
        }
        for (c in 0 until columns) {
            beams.add(Beam(Point(c, 0), Down))
            beams.add(Beam(Point(c, rows - 1), Up))
        }

        return beams.maxOf { findEnergizedTiles(it) }
    }

    fun findEnergizedTiles(startBeam: Beam): Int {

        val energizedTiles = matrix.map {
            it.map { '.' }.toTypedArray()
        }.toTypedArray()

        val crossedFrom = matrix.map { row ->
            row.map { mutableListOf<Direction>() }.toTypedArray()
        }.toTypedArray()

        val beams = mutableListOf(startBeam)
        fun enqueueBeam(beam: Beam) {
            if (matrix.inRange(beam.position)) {

                if (!crossedFrom[beam.position].contains(beam.direction)) {
                    beams.add(beam)
                    crossedFrom[beam.position].add(beam.direction)
                }
            }
        }

        while (beams.isNotEmpty()) {
            val beam = beams.removeFirst()

            val contractor = matrix[beam.position]

            energizedTiles[beam.position] = '#'

            val reflection = contractor.reflectBeam(beam)

            if (reflection is ReflectedBeam.SplitBeam) {
                enqueueBeam(reflection.leftBeam)
                enqueueBeam(reflection.rightBeam)
            } else if (reflection is ReflectedBeam.SingleBeam) {
                enqueueBeam(reflection.beam)
            }

        }

        return energizedTiles.sumOf {
            it.sumOf { energized -> if (energized == '#') 1 else 0 as Int }
        }
    }

}