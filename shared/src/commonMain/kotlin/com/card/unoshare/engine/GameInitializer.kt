package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 15:42
 * @description
 */
import com.card.unoshare.model.*

object GameInitializer {
    fun initializeGame(playerNames: List<String>): Pair<List<Player>, MutableList<Card>> {
        val deck = DeckManager.shuffle(DeckManager.createDeck())
        val players = playerNames.map { name ->
            Player(name, name = name, hand = DeckManager.draw(deck, 7).toMutableList())
        }
        return players to deck
    }
}