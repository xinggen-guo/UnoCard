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
    fun applyEffect(card: Card, gameStatus: GameStatus , drawPile: MutableList<Card>) {
        when (card.type) {
            CardType.SKIP -> gameStatus.nextPlayer()
            CardType.REVERSE -> gameStatus.isClockwise = !gameStatus.isClockwise
            CardType.DRAW_TWO -> {
                gameStatus.nextPlayer()
                val next = gameStatus.currentPlayer()
                repeat(2) { if (drawPile.isNotEmpty()) next.drawCard(drawPile.removeFirst()) }
            }
            CardType.WILD_DRAW_FOUR -> {
                gameStatus.nextPlayer()
                val next = gameStatus.currentPlayer()
                repeat(4) { if (drawPile.isNotEmpty()) next.drawCard(drawPile.removeFirst()) }
            }
            else -> {}
        }
    }

    fun apply(card: Card, gameController: GameController) {
        applyEffect(
            card = card,
            gameStatus = GameStatus(
                currentPlayerIndex = gameController.getCurrentPlayerIndex(),
                isClockwise = gameController.isClockwise(),
                players = gameController.getPlayers()
            ),
            drawPile = gameController.getDeck()
        )
    }
}