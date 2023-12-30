package day12.models

import day12.part2


class ConditionRecord(val springs: String, val brokenGroups: List<Int>) {


    override fun toString(): String {
        return "$springs $brokenGroups"
    }

    companion object {
        fun fromRecordString(record: String): ConditionRecord {
            val parts = record.split(" ")
            val sequence = parts[0]
            val list = parts[1].split(',').map { it.toInt() }

            var param1 = ""
            val param2 = mutableListOf<Int>()

            for (i in 0 until if (part2) 5 else 1) {
                if (i > 0) {
                    param1 += "?"
                }
                param1 += sequence
                param2.addAll(list)
            }

            return ConditionRecord(param1, param2)
        }

    }
}