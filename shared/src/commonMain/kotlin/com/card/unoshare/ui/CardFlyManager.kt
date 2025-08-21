package com.card.unoshare.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import com.card.unoshare.model.Card
import com.card.unoshare.model.MovingCardState

/**
 * @author xinggen.guo
 * @date 05/08/2025 15:36
 * @description
 */
object CardFlyManager {
    var movingCardState = mutableStateOf<MovingCardState?>(null)

    fun start(card: Card, from: Offset, to: Offset, cardFace: Boolean, onArrive: () -> Unit = {}) {
        val cards = mutableListOf(card)
        start(cards, from, to, isVertical = null, cardFace, onEachCardArrive = {

        }, onArrive)
    }

    fun start(
        cards: List<Card>,
        from: Offset,
        to: Offset,
        isVertical: Boolean?,
        cardFace: Boolean,
        onEachCardArrive: (Card) -> Unit,
        onArrive: () -> Unit
    ) {
        movingCardState.value =
            MovingCardState(cards, from, to, isVertical = isVertical, cardFace = cardFace, onEachCardArrive = onEachCardArrive, onArrive)
    }

    fun clear() {
        movingCardState.value = null
    }
}