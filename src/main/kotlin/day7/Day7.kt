package day7

import day7.models.PokerMatch
import day7.models.PokerPlayer
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val scanner = getDataScanner(7, if (isDemo) arrayOf("demo") else emptyArray())

    val result = parseInput(scanner).players.sortedWith(
        Comparator { l, r -> l.hand.totalValue.compareTo(r.hand.totalValue) }
    )
        .foldIndexed(0) { index, acc, pokerPlayer -> acc + pokerPlayer.bet * (index + 1) }


    println(result)
}

fun parseInput(scanner: Scanner): PokerMatch {
    val players = mutableListOf<PokerPlayer>()
    while (scanner.hasNext()) {
        players.add(
            PokerPlayer(scanner.next(), scanner.nextInt())
        )
    }
    return PokerMatch(players)
}