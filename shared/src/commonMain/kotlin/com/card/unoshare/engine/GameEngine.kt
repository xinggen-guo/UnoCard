package com.card.unoshare.engine

import com.card.unoshare.model.Card
import com.card.unoshare.model.CardType
import com.card.unoshare.model.GameStatus
import com.card.unoshare.model.Player
import com.card.unoshare.rule.EffectApplier.applyCardEffect
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
    private val rules: SpecialRuleSet) {
    private val gameStatus = GameStatus(players)
    // The draw pile (cards not yet drawn)
    private val drawPile = mutableListOf<Card>()
    // The discard pile (cards that have been played)
    private val discardPile = mutableListOf<Card>()

    // Game log for debugging or UI
    private val gameLog = mutableListOf<String>()

    fun startGame(initialHandSize: Int = 7) {
        gameStatus.gameStart = true
        drawPile.clear()
        discardPile.clear()
        drawPile.addAll(CardGameResource.cards)
        players.forEach { it.hand.clear() }
        repeat(7) { players.forEach { p -> p.drawCard(drawPile.removeAt(0)) } }
        // 发牌后，从 drawPile 中放一个符合规则的牌进入 discardPile
        val first = drawPile.firstOrNull { it.type == CardType.NUMBER }
        first?.let {
            drawPile.remove(it)
            discardPile.add(it)
        }
    }

    fun isStartGame() = gameStatus.gameStart

    // 执行一次轮询模拟所有玩家出一张牌
    fun playRoundByAi() {
        val player = getCurrentPlayer()
        val top = getTopCard()
        // 根据 shiawasenahikari 仓库 AI 简单逻辑：出首张合法牌，否则抽一张
        val playable = player.hand.firstOrNull { RuleChecker.isValidMove(it, top!!) }
        if (playable != null) {
            player.hand.remove(playable)
            discardPile.add(playable)
            applyCardEffect(playable, gameStatus, drawPile)
        } else {
            player.drawCard(drawPile.removeAt(0))
        }
        gameStatus.nextPlayer()
    }

    fun playTurn(card: Card, player: Player): Boolean {

        player.hand.remove(card)
        discardPile.add(card)
        applyCardEffect(card, gameStatus , drawPile)

        if (player.hand.isEmpty()) {
            gameStatus.gameEnded = true
            gameStatus.gameStart = false
        } else {
            gameStatus.nextPlayer()
        }
        return true
    }

    fun drawCard(player: Player) {
        if (drawPile.isEmpty()) reshuffleDiscardIntoDrawPile()
        player.drawCard(drawPile.removeFirst())
    }

    fun getTopCard(): Card = discardPile.last()

    fun getDiscardPile(): List<Card> = discardPile

    private fun reshuffleDiscardIntoDrawPile() {
        val lastCard = discardPile.removeLast()
        drawPile.addAll(CardShuffler.shuffle(discardPile))
        discardPile.clear()
        discardPile.add(lastCard)
    }
    // Get the current player object
    fun getCurrentPlayer(): Player {
        return gameStatus.currentPlayer()
    }

    // Get the current player's name
    fun getCurrentPlayerName(): String {
        return getCurrentPlayer().name
    }

    // Get all players' hands (for UI display)
    fun getAllPlayerHands(): Map<Player, List<Card>> {
        return gameStatus.players.associate { it to it.hand.toList() }
    }

    // Current player draws a card, with logging
    fun drawCardForCurrentPlayer() {
        val card = drawPile.removeFirstOrNull()
        card?.let {
            getCurrentPlayer().hand.add(it)
            gameLog.add("${getCurrentPlayer().name} drew $it")
        }
        gameStatus.nextPlayer()
    }
    fun getWinnerName(): String? {
        return players.firstOrNull { it.hand.isEmpty() }?.name
    }

    fun getAllPlayers(): List<Player> {
        return players
    }

}