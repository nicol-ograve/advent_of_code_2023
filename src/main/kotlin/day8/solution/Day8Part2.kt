package day8.solution

import day8.models.Network

data class CrossedNode(val nodeId: String, val sequenceStep: Int, val step: Int)
data class Exit(val index: Int, val nodeId: String, val sequenceStep: Int, val step: Int)
data class Loop(val index: Int, val sequenceStep: Int, val initialStep: Int, val finalStep: Int)

val passedNodes = HashMap<String, MutableList<CrossedNode>>()
val exits = HashMap<Int, MutableList<Exit>>()
val loops = HashMap<Int, Loop>()

fun day8Part2(network: Network): Int {
    val (sequence, nodes) = network
    var sequenceStep = 0
    var currentNodes = nodes.values.filter { it.id.endsWith("A") }

    var steps = 0

    while (currentNodes.size > loops.size) {
        currentNodes = currentNodes.mapIndexed() { index, it ->


            if (it.id.endsWith("Z")) {
                if (exits[index] == null) {
                    exits[index] = mutableListOf()
                }
                val exit = Exit(index, it.id, sequenceStep, steps)

                exits[index]!!.add(exit)
            }

            val passedNodeId = it.id + index.toString()

            if (passedNodes[passedNodeId] == null) {
                passedNodes[passedNodeId] = mutableListOf(CrossedNode(it.id, sequenceStep, steps))
            } else {
                val crossedNode = passedNodes[passedNodeId]!!.firstOrNull { it.sequenceStep == sequenceStep }
                if (crossedNode != null) {
                    loops[index] = Loop(index, sequenceStep, crossedNode.step, steps)
                }
                //passedNodes[it.id]!!.add(sequenceStep)
                passedNodes[passedNodeId]!!.add(CrossedNode(it.id, sequenceStep, steps))
            }

            if (sequence[sequenceStep] == 'L') it.left else it.right
        }
        sequenceStep = (sequenceStep + 1) % sequence.length
        steps++

    }

    println(exits)
    println(loops)

    var exitSteps = exits.keys.map { exits[it]!![0].step.toLong() }
    val loopWidths = loops.keys.map { (loops[it]!!.finalStep - loops[it]!!.initialStep).toLong() }

    println(loopWidths)

    var attempts = 0

    println( exitSteps.fold(1L) { acc: Long, l: Long -> l * acc })
    do {
        exitSteps = exitSteps.mapIndexed { index, it -> it + loopWidths[index] }

        val allEquals =
            exitSteps.foldIndexed(true) { index: Int, acc: Boolean, i: Long -> acc && (index == 0 || i == exitSteps[index - 1]) }

        attempts++
    } while (!allEquals)



    // Loop widths are equal to exit steps, so the solution is the mcm of the exit steps


    return steps
}
