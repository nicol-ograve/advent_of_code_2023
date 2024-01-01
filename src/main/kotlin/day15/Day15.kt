package day15

import day15.models.InitCommand
import day15.models.Lens
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val line = getDataScanner(15, if (isDemo) arrayOf("demo") else emptyArray()).nextLine()


    part2(line)
}


fun part1(line: String) {
    val result = line.split(",").sumOf { hash(it) }

    println(result)
}

fun part2(line: String) {

    val boxes = HashMap<Int, MutableList<Lens>>()
    for (i in 0..255) {
        boxes[i] = mutableListOf()
    }

    line.split(",")
        .map { InitCommand.fromString(it) }
        .forEach {
            when (it) {
                is InitCommand.Remove -> {
                    boxes[it.boxNumber]!!.removeIf { lens -> lens.label == it.label }
                }

                is InitCommand.Replace -> {
                    val list = boxes[it.boxNumber]!!
                    val position = list.indexOfFirst { lens -> lens.label == it.label }
                    if (position < 0) {
                        list.add(Lens(it.label, it.focalLength))
                    } else {
                        list[position] = Lens(it.label, it.focalLength)
                    }
                }
            }
        }

    var sum = 0
    for (box in boxes) {

        box.value.forEachIndexed { index, lens ->
           sum +=  (box.key + 1) * lens.focalLength * (index + 1)
        }

    }

    println(sum)


}


fun hash(text: String): Int {
    var currentValue = 0

    return text.fold(0) { acc, c ->
        ((acc + c.toByte().toInt()) * 17) % 256
    }
}