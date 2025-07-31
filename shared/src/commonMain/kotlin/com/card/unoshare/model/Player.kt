package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:53
 * @description
 */
data class Player(
    val id: String,
    val name: String,
    val hand: MutableList<Card> = mutableListOf(),
    var isAI: Boolean = false
) {
    fun drawCard(card: Card) {
        hand.add(card)
    }

    fun playCard(index: Int): Card? {
        return if (index in hand.indices) hand.removeAt(index) else null
    }

    fun handSize(): Int = hand.size
}
