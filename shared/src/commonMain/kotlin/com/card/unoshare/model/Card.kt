package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:51
 * @description card base information
 */
data class Card(
    var color: CardColor,
    val type: CardType,
    val number: Int? = null,
    var frontBitmapName: String? = null,
    var darkBitmapName: String? = null
) {

    init {
        frontBitmapName = "front_${getImageShortName()}"
        darkBitmapName = "dark_${getImageShortName()}"
    }

    fun isWild(): Boolean = type == CardType.WILD || type == CardType.WILD_DRAW_FOUR

    fun displayText(): String {
        return if (type == CardType.NUMBER)
            "${color.name}:${number}"
        else
            "${color.name}:${type.name.take(3)}"
    }

    fun randomColor() {
        color = CardColor.entries.random()
    }

    fun getImageShortName(): String {
        return "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString()}"
    }
}
