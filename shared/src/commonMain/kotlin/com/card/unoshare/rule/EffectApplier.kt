package com.card.unoshare.rule

import com.card.unoshare.engine.GameController
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardType
import com.card.unoshare.model.GameStatus
import com.card.unoshare.model.Player

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
                isClockwise = gameController.isClockwise(),
                players = gameController.getPlayers()
            ),
            drawPile = gameController.getDeck()
        )
    }

    /**
     * Apply special effects based on the card type
     */
    fun applyCardEffect(card: Card, gameStatus: GameStatus, drawPile: MutableList<Card>) {
        when (card.type) {
            CardType.REVERSE -> {
                // Reverse the turn order
                gameStatus.reverseOrder()
            }

            CardType.SKIP -> {
                // Skip the next player
                gameStatus.skipNextPlayer()
            }

            CardType.DRAW_TWO -> {
                // Next player draws 2 cards and is skipped
                val next = gameStatus.peekNextPlayer()
                repeat(2) {
                    drawPile.removeFirstOrNull()?.let { next.hand.add(it) }
                }
                gameStatus.skipNextPlayer()
            }

            CardType.WILD, CardType.WILD_DRAW_FOUR -> {
                // Choose a random color
                card.randomColor()
                if (card.type == CardType.WILD_DRAW_FOUR) {
                    // Next player draws 4 and is skipped
                    val next = gameStatus.peekNextPlayer()
                    repeat(4) {
                        drawPile.removeFirstOrNull()?.let { next.hand.add(it) }
                    }
                    gameStatus.skipNextPlayer()
                } else {
                    // Just change color, no skip
                    gameStatus.nextPlayer()
                }
            }

            else -> {
                // Normal number card, do nothing
            }
        }
    }
}