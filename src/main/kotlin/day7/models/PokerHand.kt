package day7.models

const val withJolly = true

val cardsValuesMap = mapOf(
    Pair('A', 14L),
    Pair('K', 13),
    Pair('Q', 12),
    Pair('J', if (withJolly) 1 else 11),
    Pair('T', 10),
    Pair('9', 9),
    Pair('8', 8),
    Pair('7', 7),
    Pair('6', 6),
    Pair('5', 5),
    Pair('4', 4),
    Pair('3', 3),
    Pair('2', 2),
)

class PokerHand(val cards: String) {
    val type: PokerHandType = if (withJolly) getHandTypeWithJolly(cards) else getHandType(cards)
    val typeValue = type.value
    val cardsValue = getCardValue(cards)

    val totalValue = typeValue * 10000000000L + cardsValue
}

private fun getCardValue(cards: String): Long {
    return cardsValuesMap[cards[0]]!! * 100000000L + cardsValuesMap[cards[1]]!! * 1000000L + cardsValuesMap[cards[2]]!! * 10000 + cardsValuesMap[cards[3]]!! * 100 + cardsValuesMap[cards[4]]!!

}


private fun getHandTypeWithJolly(cards: String): PokerHandType {
    val map = HashMap<Char, Int>()
    cards.forEach { c -> map[c] = map[c]?.inc() ?: 1 }
    val jollies = map['J'] ?: 0

    map.remove('J')

    return when (jollies) {
        4, 5 -> PokerHandType.FiveOfAKind
        3 -> if (map.keys.size == 1) PokerHandType.FiveOfAKind else PokerHandType.FourOfAKind
        2 -> return when (map.keys.size) {
            1 -> PokerHandType.FiveOfAKind
            2 -> PokerHandType.FourOfAKind
            else -> PokerHandType.ThreeOfAKind
        }

        1 -> if (map.containsValue(4)) PokerHandType.FiveOfAKind
            else if (map.containsValue(3)) PokerHandType.FourOfAKind
            else if(map.keys.size == 2) PokerHandType.FullHouse
            else if (map.containsValue(2)) PokerHandType.ThreeOfAKind
            else PokerHandType.Pair

        else -> getHandType(cards)
    }
}

private fun getHandType(cards: String): PokerHandType {
    val map = HashMap<Char, Int>()
    cards.forEach { c -> map[c] = map[c]?.inc() ?: 1 }

    return when (map.keys.size) {
        1 -> PokerHandType.FiveOfAKind
        2 -> if (map.containsValue(4)) PokerHandType.FourOfAKind else PokerHandType.FullHouse
        3 -> if (map.containsValue(3)) PokerHandType.ThreeOfAKind else PokerHandType.DoublePair
        4 -> PokerHandType.Pair
        else -> PokerHandType.HighCard
    }
}