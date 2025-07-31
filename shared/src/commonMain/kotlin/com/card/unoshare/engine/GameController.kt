package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:57
 * @description
 */
import com.card.unoshare.model.*
import com.card.unoshare.rule.*

class GameController(
    private val players: List<Player>,
    private val deck: MutableList<Card>
) {
    private val discardPile = mutableListOf<Card>()
    private var currentIndex = 0
    private var isClockwise = true

    fun startGame() {
        val firstCard = drawNonWildStartCard()
        discardPile.add(firstCard)
        println("Game started with: $firstCard")
    }

    private fun drawNonWildStartCard(): Card {
        var card: Card
        do {
            card = DeckManager.draw(deck, 1).first()
            deck.add(card) // put back if wild
        } while (card.type.isWild())
        return card
    }

    fun playTurn() {
        val currentPlayer = players[currentIndex]
        val topCard = discardPile.last()
        val playable = currentPlayer.hand.firstOrNull { RuleChecker.isPlayable(it, topCard) }

        if (playable != null) {
            currentPlayer.hand.remove(playable)
            discardPile.add(playable)
            println("${currentPlayer.name} played $playable")
            EffectApplier.apply(playable, this)
        } else {
            val drawn = DeckManager.draw(deck, 1)
            currentPlayer.hand.addAll(drawn)
            println("${currentPlayer.name} drew a card")
        }

        advanceTurn()
    }

    fun advanceTurn(skip: Boolean = false, reverse: Boolean = false) {
        if (reverse) isClockwise = !isClockwise
        var step = if (isClockwise) 1 else -1
        if (skip) step *= 2
        currentIndex = (currentIndex + step + players.size) % players.size
    }

    fun getCurrentPlayer(): Player = players[currentIndex]

    fun getCurrentPlayerIndex(): Int = currentIndex

    fun isClockwise(): Boolean = isClockwise

    fun getPlayers(): List<Player> = players

    fun getDeck(): MutableList<Card> = deck

    fun getTopCard(): Card = discardPile.last()
}