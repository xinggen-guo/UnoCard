package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 15:41
 * @description
 */
import com.card.unoshare.model.*
import kotlinx.datetime.Clock
import kotlin.random.Random

object DeckManager {
    fun createDeck(): MutableList<Card> {
        val deck = mutableListOf<Card>()
        val colors = CardColor.entries.filter { it != CardColor.WILD }
        val numbers = (0..9)
        val actions = listOf(CardType.SKIP, CardType.REVERSE, CardType.DRAW_TWO)

        // Normal colored cards
        colors.forEach { color ->
            numbers.forEach { number ->
                deck.add(Card(color, CardType.NUMBER, number))
                if (number != 0) deck.add(Card(color,CardType.NUMBER, number))
            }
            actions.forEach { action ->
                repeat(2) {
                    deck.add(Card(color, action))
                }
            }
        }

        // Wild cards
        repeat(4) {
            deck.add(Card(CardColor.WILD, CardType.WILD))
            deck.add(Card(CardColor.WILD, CardType.WILD_DRAW_FOUR))
        }

        return deck
    }

    fun shuffle(deck: MutableList<Card>): MutableList<Card> =
        deck.shuffled(Random(Clock.System.now().toEpochMilliseconds())).toMutableList()

    fun draw(deck: MutableList<Card>, count: Int): List<Card> {
        val drawn = deck.take(count)
        repeat(count) { deck.removeAt(0) }
        return drawn
    }
}