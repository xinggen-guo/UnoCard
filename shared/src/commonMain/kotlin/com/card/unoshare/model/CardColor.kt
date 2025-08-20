package com.card.unoshare.model

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
}