package utils

import java.io.File
import java.util.Scanner


val getDataFile = { day: Int, isDemo: Boolean ->
     File("src/main/kotlin/day$day/data${if(isDemo) "_demo" else ""}.txt")
}

val getDataScanner = { day: Int, args: Array<String> ->
    val isDemo = args.isNotEmpty() && args[0] == "demo"
    Scanner(getDataFile(day, isDemo))
}


val getDataLines = { day: Int, args: Array<String> ->
    val scanner = getDataScanner(day, args)
    val list = mutableListOf<String>()

    while (scanner.hasNextLine()){
        list.add(scanner.nextLine())
    }

    list.toList()
}
