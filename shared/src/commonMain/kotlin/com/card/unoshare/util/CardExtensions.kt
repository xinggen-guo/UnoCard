package com.card.unoshare.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardOffset

/**
 * @author xinggenguo
 * @date 2025/8/20 21:25
 * @description
 */

fun Offset.toCardOffset() = CardOffset(x, y)
fun CardOffset.toOffset() = Offset(x, y)

fun CardColor.toColor(): Color {
    return when (this) {
        CardColor.RED -> Color.Red
        CardColor.GREEN -> Color.Green
        CardColor.BLUE -> Color.Blue
        CardColor.YELLOW -> Color.Yellow
    }
}