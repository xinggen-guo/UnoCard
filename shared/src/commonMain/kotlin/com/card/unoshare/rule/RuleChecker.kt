package com.card.unoshare.rule

import com.card.unoshare.model.Card
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardType

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:00
 * @description
 */
object RuleChecker {
    fun isValidMove(card: Card, topCard: Card): Boolean {
        return card.color == topCard.color ||
                (card.type == CardType.NUMBER && card.number == topCard.number) ||
                (card.type == topCard.type && card.type != CardType.NUMBER) ||
                card.type == CardType.WILD || card.type == CardType.WILD_DRAW_FOUR
    }

    fun isPlayable(card: Card, topCard: Card): Boolean {
        return isValidMove(card, topCard)
    }
}