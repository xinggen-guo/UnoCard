package com.card.unoshare.rule

import com.card.unoshare.engine.GameController
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardType
import com.card.unoshare.model.GameStatus

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:00
 * @description
 */
object EffectApplier {

    fun apply(card: Card, gameController: GameController) {
        applyCardEffect(
            card = card,
            gameStatus = GameStatus(
                currentPlayerIndex = gameController.getCurrentPlayerIndex(),
                isCounterWise = gameController.isClockwise(),
                players = gameController.getPlayers()
            ),
            drawPile = gameController.getDeck()
        )
    }

    /**
     * Apply special effects based on the card type
     */
    fun applyCardEffect(card: Card, gameStatus: GameStatus, drawPile: MutableList<Card>): Boolean {
        var needDeal = false
        when (card.type) {
            CardType.REVERSE -> {
                // Reverse the turn order
                gameStatus.reverseOrder()
            }

            CardType.SKIP -> {
                // Skip the next player
                gameStatus.nextPlayer()
            }

            CardType.DRAW_TWO -> {
                // Next player draws 2 cards and is skipped
                val next = gameStatus.peekNextPlayer()
                next.dealDrawCard = true
            }

            CardType.WILD -> {
                needDeal = true
            }

            CardType.WILD_DRAW_FOUR -> {
                val next = gameStatus.peekNextPlayer()
                next.dealDrawCard = true
                needDeal = true
            }

            else -> {
                // Normal number card, do nothing
            }
        }

        return needDeal
    }
}