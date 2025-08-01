package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:51
 * @description card base information
 */
data class Card(
    var color: CardColor,
    val type: CardType,
    val number: Int? = null
) {
    fun isWild(): Boolean = type == CardType.WILD || type == CardType.WILD_DRAW_FOUR

    fun displayText(): String {
        return if (type == CardType.NUMBER)
            "${color.name}:${number}"
        else
            "${color.name}:${type.name.take(3)}"
    }

    fun randomColor() {
        val cardColor = CardColor.entries.random()
        if (CardColor.WILD == cardColor) {
            randomColor()
        } else {
            color = cardColor
        }
    }
}
