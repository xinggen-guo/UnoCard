package com.card.unoshare.model

import androidx.compose.ui.graphics.Color

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:51
 * @description
 */
enum class CardColor {
    RED, GREEN, BLUE, YELLOW;

    fun cardColorResourceName(): String {
        return when (this) {
            RED -> "r"
            GREEN -> "g"
            BLUE -> "b"
            YELLOW -> "y"
        }
    }

    companion object {
        val colorList: List<Color> = listOf(
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow
        )

        fun toColor(cardColor: CardColor): Color {
            return when (cardColor) {
                RED -> Color.Red
                GREEN -> Color.Green
                BLUE -> Color.Blue
                YELLOW -> Color.Yellow
            }
        }
    }
}