package day13

class MirrorLine(val chars: String, val value: Int) {
    fun differences(line: MirrorLine): List<Int> {
        return chars.mapIndexed { index, c -> if (line.chars[index] != c) index else null }.filterNotNull()
    }
}