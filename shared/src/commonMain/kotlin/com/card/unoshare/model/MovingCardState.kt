package com.card.unoshare.model

import androidx.compose.ui.geometry.Offset

/**
 * @author xinggen.guo
 * @date 04/08/2025 21:19
 * @description
 */
class MovingCardState(
    val cards: List<Card>,
    val from: Offset,
    val to: Offset,
    val isVertical: Boolean?,
    val onEachCardArrive: (Card) -> Unit = {},
    val onComplete: () -> Unit = {},
)
