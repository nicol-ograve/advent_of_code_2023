package day7.models

enum class PokerHandType(val value: Long) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    DoublePair(3),
    Pair(2),
    HighCard(1),
}