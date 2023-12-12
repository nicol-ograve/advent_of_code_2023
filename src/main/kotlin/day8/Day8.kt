package day8

import day8.models.Network
import day8.models.NetworkNode
import day8.solution.day8Part2
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val scanner = getDataScanner(8, if (isDemo) arrayOf("demo") else emptyArray())

    val network = readInput(scanner)
    val result = day8Part2(network)

    println(result)
}


private fun part1(network: Network): Int {
    val (sequence, nodes) = network
    var sequenceStep = 0
    var node = nodes["AAA"]!!

    var steps = 0

    while (node.id != "ZZZ") {


        node = if (sequence[sequenceStep] == 'L') node.left else node.right
        sequenceStep = (sequenceStep + 1) % sequence.length
        steps++


    }

    return steps
}


private fun readInput(scanner: Scanner): Network {
    val sequence = scanner.next()

    val neighborsMap = HashMap<String, Pair<String, String>>()
    val nodesMap = HashMap<String, NetworkNode>()

    while (scanner.hasNext()) {
        val id = scanner.next()

        // Ignore '='
        scanner.next()

        val left = scanner.next().substring(1, 4)
        val right = scanner.next().substring(0, 3)

        neighborsMap[id] = Pair(left, right)
        nodesMap[id] = NetworkNode(id)

    }


    neighborsMap.forEach { entry ->
        nodesMap[entry.key]!!.setNeighbors(
            nodesMap[entry.value.first]!!,
            nodesMap[entry.value.second]!!
        )
    }

    return Network(sequence, nodesMap)

}