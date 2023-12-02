package day2


fun parseDay2Input(lines: List<String>): List<CubesGame> {

    return lines.map { it ->
        val split = it.split(":")
        val gameId = split[0].replace("Game ", "").toInt()
        val records = split[1].split(";").map {recordString ->  mapRecord(recordString) }

        CubesGame(id = gameId, records = records)
    }
}

private fun mapRecord(recordString: String): CubesSet {
    var rgb = intArrayOf(0, 0, 0)
    recordString.split(",").forEach {
        val split = it.trim().split(" ")
        val count = split[0].toInt()
        when(split[1]) {
            "red" -> rgb[0]+=count
            "green" -> rgb[1]+=count
            "blue" -> rgb[2]+=count
        }
    }
    return CubesSet(
        red = rgb[0],
        green = rgb[1],
        blue = rgb[2],
    )
}