package com.card.unoshare.engine

import com.card.unoshare.language.I18nManager
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardType
import com.card.unoshare.model.GameStatus
import com.card.unoshare.model.Player
import com.card.unoshare.rule.EffectApplier.applyCardEffect
import com.card.unoshare.rule.RuleChecker
import com.card.unoshare.rule.SpecialRuleSet
import com.card.unoshare.util.AppLog
import com.card.unoshare.util.CardShuffler
import i18n.I18nKeys

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
        gameStatus.gameEnded = false
        drawPile.clear()
        discardPile.clear()
        drawPile.addAll(CardGameResource.cards)
        drawPile.shuffle()
        players.forEach { it.hand.clear() }
        repeat(7) {
            players.forEach { p ->
//                if(!p.isAI){
//                    p.drawCard(drawCardByCardType(CardType.WILD_DRAW_FOUR))
//                } else {
                    p.drawCard(drawPile.removeAt(0))
//                }
            }
        }
        // 发牌后，从 drawPile 中放一个符合规则的牌进入 discardPile
        val first = drawPile.firstOrNull { it.type == CardType.NUMBER }
        first?.let {
            drawPile.remove(it)
            discardPile.add(it)
        }
        gameStatus.randomWhoFirst()
    }

    private fun drawCardByCardType(cardType: CardType?): Card {
        return if (cardType == null)
            drawPile.removeAt(0)
        else {
            val card = drawPile.firstOrNull { it.type == CardType.WILD_DRAW_FOUR }
            drawPile.remove(card)
            card ?: drawPile[0]
        }
    }

    fun needRestartGame() = (gameStatus.gameStart && gameStatus.gameEnded)

    // 执行一次轮询模拟所有玩家出一张牌
    fun playRoundByAi(playCard: (card: Card) -> Boolean, drawCard: (player: Player, card: Card) -> Boolean) {
        val player = getCurrentPlayer()
        val top = getTopCard()
            // AI 简单逻辑：出首张合法牌，否则抽一张
        val playable = player.hand.firstOrNull { RuleChecker.isValidMove(it, top!!) }
        if (playable != null) {
            if (playCard(playable)) {
                player.hand.remove(playable)
                discardPile.add(playable)
                applyCardEffect(playable, gameStatus, drawPile)
            }
        } else {
            val card = drawPile.removeAt(0)
            if (drawCard(player, card)) {
                player.drawCard(card)
            }
        }
        gameStatus.nextPlayer()
        AppLog.i { "playRoundByAi-finish--" }
    }

    fun playTurn(card: Card, player: Player): Boolean {
        var needDeal: Boolean
        if (player.dealDrawCard) {
            needDeal = true
        } else {
            needDeal = applyCardEffect(card, gameStatus, drawPile)
            if (!needDeal) {
                player.hand.remove(card)
                discardPile.add(card)
            }
        }

        if (player.hand.isEmpty()) {
            gameStatus.gameEnded = true
            needDeal = false
        } else {
            if(!needDeal) {
                gameStatus.nextPlayer()
            }
        }
        return needDeal
    }

    fun playSelectColor(cardColor: CardColor, card: Card, player: Player) {
        card.setColor(cardColor)
        player.hand.remove(card)
        discardPile.add(card)
        if(card.isDrawCard()){
            gameStatus.peekNextPlayer().dealDrawCard = true
        }
        gameStatus.nextPlayer()
    }

    fun drawCardFromPile(cardNumber: Int = 1): List<Card>{
        if (drawPile.isEmpty()) reshuffleDiscardIntoDrawPile()
        val cards = mutableListOf<Card>()
        for (i in 0..<cardNumber) {
            cards.add(drawPile.removeFirst())
        }
        return cards
    }

    fun drawCard(card: Card) {
        getCurrentPlayer().drawCard(card)
    }

    private fun drawCardComplete(cards: List<Card>) {
        getCurrentPlayer().drawCards(cards)
        gameStatus.nextPlayer()
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
        val name = players.firstOrNull { it.hand.isEmpty() }?.name
        if (name.isNullOrEmpty()) gameStatus.gameEnded = true
        return name
    }

    fun getAllPlayers(): List<Player> {
        return players
    }

    fun gameClockwise():Boolean = gameStatus.isCounterWise

    fun getTipInfoYouPlayer():String{
        return I18nManager.get(I18nKeys.info_you_return)
    }

    fun getLastDirectionAndCardInfo(): String {
        return if (discardPile.isEmpty()) {
            getTopCard().displayText()
        } else {
            "${gameStatus.getLastPlayer().getDirectionPosition()}:${getTopCard().displayText()}"
        }
    }

    fun getPlayerPositionStr(player: Player): String {
        return player.getDirectionPosition()
    }

    fun countSore(): Int {
        var score = 0
        getAllPlayers().first { !it.isAI }.hand.forEach {
            score += it.getCardScore()
        }
        return score
    }

    fun checkDrawCard(card: Card, player: Player): Boolean {
        player.drawCard(card)
        if(!RuleChecker.isPlayable(card,getTopCard())){
            gameStatus.nextPlayer()
            return false
        }
        return true
    }

    fun drawCardToCurrentPlayer(checkPlayerDealEffect: (cards: List<Card>, player: Player) -> Unit) {
        val player = getCurrentPlayer()
        val cardNumber = getTopCard().getDrawNumber()
        val cards = drawCardFromPile(cardNumber)
        drawCardComplete(cards)
        checkPlayerDealEffect(cards,player)
    }

    fun sortCards(cards: List<Card>): List<Card> {
        return cards.sortedWith(compareBy<Card> { card ->
            when (card.type) {
                CardType.WILD, CardType.WILD_DRAW_FOUR -> 0
                else -> 1
            }
        }.thenBy { card ->
            card.getColor().ordinal
        }.thenBy { card ->
            when (card.type) {
                CardType.SKIP, CardType.REVERSE, CardType.DRAW_TWO -> 0
                else -> 1
            }
        }.thenBy { card ->
            card.number ?: Int.MAX_VALUE
        })
    }

}