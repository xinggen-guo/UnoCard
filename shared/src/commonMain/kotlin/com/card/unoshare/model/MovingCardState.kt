package com.card.unoshare.model

import androidx.compose.ui.geometry.Offset

/**
 * @author xinggen.guo
 * @date 04/08/2025 21:19
 * @description
 */
data class MovingCardState(
    val card: Card,
    val from: Offset,
    val to: Offset,
    val onArrive: () -> Unit = {}
)
