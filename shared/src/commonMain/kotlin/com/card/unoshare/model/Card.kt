package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:51
 * @description card base information
 */
data class Card(
    private var color: CardColor,
    var type: CardType,
    var number: Int? = null,
    var cardLocation: CardOffset? = null) {

    fun setColor(color: CardColor){
        this.color = color
    }

    fun getColor():CardColor{
        return color
    }

    fun isWild(): Boolean = type == CardType.WILD || type == CardType.WILD_DRAW_FOUR

    fun isDrawCard(): Boolean = type == CardType.DRAW_TWO || type == CardType.WILD_DRAW_FOUR

    fun displayText(): String {
        return if (type == CardType.NUMBER)
            "${color.name}:${number}"
        else
            "${color.name}:${type.name}"
    }

    fun randomColor() {
        setColor(CardColor.entries.random())
    }

    fun getImageShortName(hand: Boolean = false): String {
        if(!hand){
            return "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString() ?: ""}"
        }else {
            return when (type) {
                CardType.WILD -> {
                    "kw"
                }

                CardType.WILD_DRAW_FOUR -> {
                    "kw4"
                }
                else -> {
                    "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString() ?: ""}"
                }
            }
        }
    }

    fun getDrawNumber(): Int {
        return when (type) {
            CardType.WILD_DRAW_FOUR -> 4
            CardType.DRAW_TWO -> 2
            else -> 0
        }
    }

    fun getCardScore():Int{
        return when (type) {
            CardType.WILD_DRAW_FOUR,CardType.WILD -> 50
            CardType.DRAW_TWO,CardType.REVERSE,CardType.SKIP -> 20
            else -> number ?: 0
        }

    }


}
