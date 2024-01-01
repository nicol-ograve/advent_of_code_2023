package day15.models

import day15.hash

sealed class InitCommand(val label: String) {

    var boxNumber: Int
        private set

    class Replace(label: String,  val focalLength: Int) : InitCommand(label)
    class Remove(label: String) : InitCommand(label)

    init {
        this.boxNumber = hash(label)
    }

    companion object {
        fun fromString(text: String): InitCommand {
            return when {
                text.endsWith('-') -> Remove(text.removeSuffix("-"))
                else -> {
                    val split = text.split("=")
                    Replace(split[0], split[1].toInt())
                }
            }
        }
    }
}
