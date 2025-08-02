package com.card.unoshare.model

import androidx.compose.ui.Alignment

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:53
 * @description
 */
data class Player(
    val id: String,
    val name: String,
    val hand: MutableList<Card> = mutableListOf(),
    var isAI: Boolean = false,
    // Alignment.CenterStart,Alignment.BottomCenter,Alignment.CenterEnd
    val direction: Alignment
) {
    fun drawCard(card: Card) {
        hand.add(card)
    }

}
