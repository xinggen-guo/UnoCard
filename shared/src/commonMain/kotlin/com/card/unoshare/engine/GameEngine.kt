package com.card.unoshare.engine

import com.card.unoshare.model.Card
import com.card.unoshare.model.GameStatus
import com.card.unoshare.model.Player
import com.card.unoshare.rule.EffectApplier
import com.card.unoshare.rule.RuleChecker
import com.card.unoshare.rule.SpecialRuleSet
import com.card.unoshare.util.CardShuffler

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:56
 * @description
 */
class GameEngine(
    private val players: List<Player>,
    private val rules: SpecialRuleSet
) {
    private val gameStatus = GameStatus(players)
    private val drawPile = CardShuffler.createDeck().toMutableList()
    private val discardPile = mutableListOf<Card>()

    fun startGame(initialHandSize: Int = 7) {
        repeat(initialHandSize) {
            players.forEach { player ->
                drawCard(player)
            }
        }
        discardPile.add(drawPile.removeFirst())
    }

    fun playTurn(playerIndex: Int, cardIndex: Int): Boolean {
        val player = players[playerIndex]
        val cardToPlay = player.hand.getOrNull(cardIndex) ?: return false
        val topCard = discardPile.last()

        if (!RuleChecker.isValidMove(cardToPlay, topCard)) return false

        discardPile.add(player.playCard(cardIndex)!!)
        EffectApplier.applyEffect(cardToPlay, gameStatus, players, drawPile)

        if (player.hand.isEmpty()) gameStatus.gameEnded = true
        else gameStatus.nextPlayer()

        return true
    }

    fun drawCard(player: Player) {
        if (drawPile.isEmpty()) reshuffleDiscardIntoDrawPile()
        player.drawCard(drawPile.removeFirst())
    }

    fun getCurrentPlayer(): Player = gameStatus.currentPlayer()

    fun getTopCard(): Card = discardPile.last()

    private fun reshuffleDiscardIntoDrawPile() {
        val lastCard = discardPile.removeLast()
        drawPile.addAll(CardShuffler.shuffle(discardPile))
        discardPile.clear()
        discardPile.add(lastCard)
    }
}