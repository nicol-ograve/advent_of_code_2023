package day7.models

data class PokerPlayer(val cards: String, val bet: Int) {
    val hand = PokerHand(cards)
}